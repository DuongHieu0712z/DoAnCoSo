package com.ctk43.doancoso.View;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.ctk43.doancoso.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddJobActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{
    private int mode = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_dialog_add_new_job);
        initView();
    }

    private void initView() {
        ImageView img_close = findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        EditText edt_job_name = findViewById(R.id.edt_dlg_job_name);
        EditText edt_job_des = findViewById(R.id.edt_dlg_job_des);

        TextView tv_date_start = findViewById(R.id.tv_dlg_date_start);
        TextView tv_time_start = findViewById(R.id.tv_dlg_time_start);

        TextView tv_date_end = findViewById(R.id.tv_dlg_date_end);
        TextView tv_time_end = findViewById(R.id.tv_dlg_time_end);

        tv_date_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDateDialog(0);
            }
        });

        tv_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenDateDialog(1);
            }
        });

        tv_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimeDialog(0);
            }
        });
        tv_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenTimeDialog(1);
            }
        });



        Button btn_Add = findViewById(R.id.btn_dlg_add_new_job);
        btn_Add.setBackgroundTintMode(null);
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Bạn thanh xử lý thêm công việc ở đây nha
            }
        });
    }

    private void OpenDateDialog(int mode){
        this.mode = mode;
        DialogFragment dateDialog = new DatePickerFragment();
        dateDialog.show(getFragmentManager(), "");
    }
    private void OpenTimeDialog(int mode){
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
        String result = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        TextView textView;
        if(mode == 0)
            textView = findViewById(R.id.tv_dlg_date_start);
        else
            textView = findViewById(R.id.tv_dlg_date_end);
        textView.setText(result);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
       String result=i + " : " + i1;
        TextView textView;
        if(mode == 0)
            textView = findViewById(R.id.tv_dlg_time_start);
        else
            textView = findViewById(R.id.tv_dlg_time_end);
        textView.setText(result);
    }
}
