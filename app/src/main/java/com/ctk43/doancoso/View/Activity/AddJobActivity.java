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
import android.view.Window;
import android.view.WindowManager;
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
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.DialogExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Fragment.DatePickerFragment;
import com.ctk43.doancoso.View.Fragment.TimePickerFragment;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.text.ParseException;
import java.util.Date;

public class AddJobActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    EditText edt_job_name;
    EditText edt_job_des;
    TextView tv_date_start;
    TextView tv_time_start;
    TextView tv_date_end;
    TextView tv_time_end;
    TextView tv_title;
    Spinner spnCategory;
    Spinner spnPriority;
    private int mode = 0;
    private JobViewModel jobViewModel;
    private CategoryViewModel categoryViewModel;
    private Job jobToUpdate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_dialog_add_new_job);
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.setData(this);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        categoryViewModel.setContext(this);
        initView();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jobToUpdate = (Job) bundle.get("JobToUpdate");
            loadJob();
            tv_title.setText(R.string.update_job);
        }
    }

    @SuppressLint("SimpleDateFormat")
    private void initView() {
        spnCategory = findViewById(R.id.spiner_job_type);
        categoryViewModel.getCategories().observe(this, categories -> {
            ArrayAdapter<Category> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
            adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
            spnCategory.setAdapter(adapter);
            spnCategory.post(() -> {
                if (jobToUpdate != null) {
                    int pos = adapter.getPosition(categoryViewModel.get(jobToUpdate.getCategoryId()));
                    spnCategory.setSelection(pos, true);
                }
            });
            adapter.notifyDataSetChanged();
        });

        ImageView img_add_job_type = findViewById(R.id.img_add_job_type);
        img_add_job_type.setOnClickListener(v -> DialogExtension.onOpenCategoryDiaLog(this, categoryViewModel, null));

        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(v -> onBackPressed());

        tv_title = findViewById(R.id.tv_title_add_new_job);

        edt_job_name = findViewById(R.id.edt_dlg_job_name);
        edt_job_des = findViewById(R.id.edt_dlg_job_des);
        tv_date_start = findViewById(R.id.tv_dlg_date_start);
        tv_time_start = findViewById(R.id.tv_dlg_time_start);
        tv_date_end = findViewById(R.id.tv_dlg_date_end);
        tv_time_end = findViewById(R.id.tv_dlg_time_end);

        tv_date_start.setOnClickListener(view -> openDateDialog(0));
        tv_date_end.setOnClickListener(view -> openDateDialog(1));
        tv_time_start.setOnClickListener(view -> openTimeDialog(0));
        tv_time_end.setOnClickListener(view -> openTimeDialog(1));

        spnPriority = findViewById(R.id.spiner_job_priority);
        String[] priorities = GeneralData.getListPriority(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, priorities);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spnPriority.setAdapter(adapter);

        Button btn_Add = findViewById(R.id.btn_dlg_add_new_job);
        btn_Add.setBackgroundTintMode(null);
        btn_Add.setOnClickListener(view -> {
            try {
                saveJob();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    private void loadJob() {
        edt_job_name.setText(jobToUpdate.getName());
        edt_job_des.setText(jobToUpdate.getDescription());

        tv_date_start.setText(CalendarExtension.formatDate(jobToUpdate.getStartDate()));
        tv_time_start.setText(CalendarExtension.formatTime(jobToUpdate.getStartDate()));

        tv_date_end.setText(CalendarExtension.formatDate(jobToUpdate.getEndDate()));
        tv_time_end.setText(CalendarExtension.formatTime(jobToUpdate.getEndDate()));

        spnPriority.post(() -> spnPriority.setSelection(jobToUpdate.getPriority(), true));
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
        Date date = CalendarExtension.getDate(i, i1, i2);
        String result = CalendarExtension.formatDate(date);
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
        Date time = CalendarExtension.getTime(i, i1);
        String result = CalendarExtension.formatTime(time);
        TextView textView;
        if (mode == 0) {
            textView = findViewById(R.id.tv_dlg_time_start);
        } else {
            textView = findViewById(R.id.tv_dlg_time_end);
        }
        textView.setText(result);
    }

    private void saveJob() throws ParseException {
        if (validateInput()) {
            if (jobToUpdate == null) {
                Job job = getJob();
                job.setStatus(GeneralData.STATUS_COMING);
                jobViewModel.insert(job);
                Toast.makeText(AddJobActivity.this, getString(R.string.add_job_sucess), Toast.LENGTH_LONG).show();
            } else {
                updateJob();
                jobToUpdate.setStatus(GeneralData.STATUS_COMING);
                jobViewModel.update(jobToUpdate);
                Toast.makeText(AddJobActivity.this, getString(R.string.update_job_sucess), Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

    private boolean validateInput() {
        String name = edt_job_name.getText().toString();
        String startDate = tv_date_start.getText().toString();
        String startTime = tv_time_start.getText().toString();
        String endDate = tv_date_end.getText().toString();
        String endTime = tv_time_end.getText().toString();

        if (Extension.isEmpty(this, name, getString(R.string.job_name), false)) {
            return false;
        }

        if (Extension.isEmpty(this, startDate, getString(R.string.date_start), startDate.equals(getString(R.string.day)))) {
            return false;
        }

        if (Extension.isEmpty(this, startTime, getString(R.string.hour_start), startTime.equals(getString(R.string.hour)))) {
            return false;
        }

        if (Extension.isEmpty(this, endDate, getString(R.string.date_end), endDate.equals(getString(R.string.day)))) {
            return false;
        }

        if (Extension.isEmpty(this, endTime, getString(R.string.hour_end), endTime.equals(getString(R.string.hour)))) {
            return false;
        }

        return true;
    }

    private Job getJob() throws ParseException {
        String name = edt_job_name.getText().toString();
        String description = edt_job_des.getText().toString();

        String startDate = tv_date_start.getText().toString();
        String startTime = tv_time_start.getText().toString();
        Date start = CalendarExtension.getDate(startDate, startTime);

        String endDate = tv_date_end.getText().toString();
        String endTime = tv_time_end.getText().toString();
        Date end = CalendarExtension.getDate(endDate, endTime);

        int priority = spnPriority.getSelectedItemPosition();
        int categoryId = ((Category) spnCategory.getSelectedItem()).getId();

        return new Job(categoryId, name, start, end, description, priority);
    }

    private void updateJob() throws ParseException {
        String name = edt_job_name.getText().toString();
        String description = edt_job_des.getText().toString();

        String startDate = tv_date_start.getText().toString();
        String startTime = tv_time_start.getText().toString();
        Date start = CalendarExtension.getDate(startDate, startTime);

        String endDate = tv_date_end.getText().toString();
        String endTime = tv_time_end.getText().toString();
        Date end = CalendarExtension.getDate(endDate, endTime);

        int priority = spnPriority.getSelectedItemPosition();
        int categoryId = ((Category) spnCategory.getSelectedItem()).getId();

        jobToUpdate.setName(name);
        jobToUpdate.setDescription(description);
        jobToUpdate.setStartDate(start);
        jobToUpdate.setEndDate(end);
        jobToUpdate.setPriority(priority);
        jobToUpdate.setCategoryId(categoryId);
    }

}
