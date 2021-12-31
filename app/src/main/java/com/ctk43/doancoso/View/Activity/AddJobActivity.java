package com.ctk43.doancoso.View.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Fragment.DatePickerFragment;
import com.ctk43.doancoso.View.Fragment.TimePickerFragment;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class AddJobActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private int mode = 0;
    private JobViewModel jobViewModel;
    private CategoryViewModel categoryViewModel;
    EditText edt_job_name;
    EditText edt_job_des;
    TextView tv_date_start;
    TextView tv_time_start;
    TextView tv_date_end;
    TextView tv_time_end;

    TextView tv_title;

    Spinner spnCategory;
    Spinner spnPriority;

    private Job jobToUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_dialog_add_new_job);
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.setData(this);

        Bundle bundle = getIntent().getExtras();
        if(bundle!=null)
            jobToUpdate = (Job) bundle.get("JobToUpdate");

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.setContext(this);
        try {
            initView();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void initView() throws ParseException {
        spnCategory = findViewById(R.id.spiner_job_type);
        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(AddJobActivity.this, spnCategory.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        categoryViewModel.getCategories().observe(this, categories -> {
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnCategory.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        });


        ImageView img_add_job_type = findViewById(R.id.img_add_job_type);
        img_add_job_type.setOnClickListener(v -> onOpenDialog());

        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(v -> onBackPressed());

        edt_job_name = findViewById(R.id.edt_dlg_job_name);
        edt_job_des = findViewById(R.id.edt_dlg_job_des);
        tv_date_start = findViewById(R.id.tv_dlg_date_start);
        tv_time_start = findViewById(R.id.tv_dlg_time_start);
        tv_date_end = findViewById(R.id.tv_dlg_date_end);
        tv_time_end = findViewById(R.id.tv_dlg_time_end);

        spnPriority = findViewById(R.id.spiner_job_priority);
        String[] priorities = GeneralData.getListPriority(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, priorities);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnPriority.setAdapter(adapter);

        tv_title = findViewById(R.id.tv_title_add_new_job);
        if(jobToUpdate!= null) tv_title.setText(R.string.update_job);

        Button btn_Add = findViewById(R.id.btn_dlg_add_new_job);

        btn_Add.setBackgroundTintMode(null);
        tv_date_start.setOnClickListener(v -> openDateDialog(0));
        tv_date_end.setOnClickListener(view -> openDateDialog(1));
        tv_time_start.setOnClickListener(view -> openTimeDialog(0));
        tv_time_end.setOnClickListener(view -> openTimeDialog(1));
        btn_Add.setOnClickListener(view -> {
            if(getInput()){
                Toast.makeText(AddJobActivity.this,getString(R.string.add_job_sucess),Toast.LENGTH_LONG).show();
            }
        });
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.setContext(this);

        SetDataForJob();


    }

    private void SetDataForJob() throws ParseException {
        if(jobToUpdate != null){
            edt_job_name.setText(jobToUpdate.getName());
            edt_job_des.setText(jobToUpdate.getDescription());
            tv_date_start.setText(ParseDate(jobToUpdate.getStartDate()));

            tv_date_end.setText(ParseDate(jobToUpdate.getEndDate()));
            tv_time_start.setText(ParseTime(jobToUpdate.getStartDate()));
            tv_time_end.setText(ParseTime(jobToUpdate.getEndDate()));

            //Bạn Hiếu fix xong cái getValue của Category rồi mở nó ra hé!
            categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
            categoryViewModel.setContext(this);

            categoryViewModel.getCategories().observe(this, categories -> {
                ArrayAdapter<Category> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                spnCategory.setAdapter(adapter);
                spnCategory.post(new Runnable() {
                    public void run() {
                        spnCategory.setSelection(getSpinerIndex(spnCategory,jobToUpdate.getCategoryId()), true);
                    }
                });
                adapter.notifyDataSetChanged();
            });
            spnPriority.post(new Runnable() {
                @Override
                public void run() {
                    spnPriority.setSelection(jobToUpdate.getPriority(), true);
                }
            });

        }
    }


    private int getSpinerIndex(Spinner spinner, int id){
        //Category category = new Category();

        List<Category> categories = categoryViewModel.getCategoryList();

        /*for(Category cat:categories){
            if(cat.getId() == id)
                category = cat;
        }*/

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            Category category1 = (Category) spinner.getItemAtPosition(i);
            if (category1.getId() == id){
                index = i;
            }
        }
        return index;
    }

    private String ParseDate(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return month+"/"+day+"/"+year;
    }
    private String ParseTime(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int hour = cal.get(Calendar.HOUR);
        int minutes = cal.get(Calendar.MINUTE);
        String str = "";
        if(minutes ==0)
            str = hour+":0"+minutes;
        else
           str= hour+":"+minutes;
        return str;
    }

    private void openDateDialog(int mode) {
        this.mode = mode;
        DialogFragment dateDialog = new DatePickerFragment();
        dateDialog.show(getFragmentManager(), "");
    }

    private void openTimeDialog(int mode) {
        this.mode = mode;
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "time picker");
    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, i);
        c.set(Calendar.MONTH, i1);
        c.set(Calendar.DAY_OF_MONTH, i2);
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        //String result = DateFormat.getDateInstance().format(c.getTime());
        String result = dateFormat.format(c.getTime());
        TextView textView;
        if (mode == 0) {
            textView = findViewById(R.id.tv_dlg_date_start);
        } else {
            textView = findViewById(R.id.tv_dlg_date_end);
        }
        textView.setText(result);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String result = i + ":" + i1;
        TextView textView;
        if (mode == 0) {
            textView = findViewById(R.id.tv_dlg_time_start);
        } else {
            textView = findViewById(R.id.tv_dlg_time_end);
        }
        textView.setText(result);
    }
    public boolean getInput(){
            String name = edt_job_name.getText().toString();
            String description = edt_job_des.getText().toString();
            String startDate = tv_date_start.getText().toString();
            String startTime = tv_time_start.getText().toString();
            String endDate = tv_date_end.getText().toString();
            String endTime = tv_time_end.getText().toString();
            int priority = spnPriority.getSelectedItemPosition();
            int category = spnCategory.getSelectedItemPosition()+1;
            if(Extension.isEmty(this,name ,getString(R.string.job_name),false))
                return false;
            if(Extension.isEmty(this,endDate, getString(R.string.date_end),endDate.equals( getString(R.string.day))) )
                return false;
            if(Extension.isEmty(this,endTime, getString(R.string.hour_end),endTime.equals(getString(R.string.hour)) ) )
                return false;
        try {
            Date start = new SimpleDateFormat("MM/dd/yyyy hh:mm", Locale.getDefault()).parse(startDate + " " + startTime);
            Date end = new SimpleDateFormat("MM/dd/yyyy hh:mm",Locale.getDefault()).parse(endDate + " " + endTime);
            if (start ==null || end ==null)
                return false;
            Job job = new Job(category,priority, name, start, end, description);
            job.setStatus(Extension.CheckStatus(job));
            jobViewModel.insert(job);
            finish();
            return  true;

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(AddJobActivity.this,getString(R.string.add_job_not_sucess),Toast.LENGTH_LONG).show();
            return false;
        }
    }

    private void onOpenDialog() {
        final Dialog dialog = new Dialog(AddJobActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.floating_dialog_add_job_type);
        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(true);
        EditText edt_job_type_name = dialog.findViewById(R.id.edt_dlg_job_type);

        Button btn_add_job_type = dialog.findViewById(R.id.btn_dlg_add_job_type);
        btn_add_job_type.setOnClickListener(view -> {
            //xu ly them loai cong viec
            categoryViewModel.insert(new Category(edt_job_type_name.getText().toString(), DataLocalManager.getEmail()));
            dialog.dismiss();
        });

        dialog.show();
    }
}
