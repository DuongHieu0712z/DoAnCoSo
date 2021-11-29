package com.ctk43.doancoso.View;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddJobActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private int mode = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.floating_dialog_add_new_job);
        initView();
    }

    @SuppressLint("SimpleDateFormat")
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
                openDateDialog(0);
            }
        });

        tv_date_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDateDialog(1);
            }
        });

        tv_time_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeDialog(0);
            }
        });

        tv_time_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTimeDialog(1);
            }
        });

        Button btn_Add = findViewById(R.id.btn_dlg_add_new_job);
        btn_Add.setBackgroundTintMode(null);
        btn_Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String name = edt_job_name.getText().toString();
                    if (name.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Không được để tên công việc trống, vui lòng nhập tên công việc!", Toast.LENGTH_SHORT).show();
                    }
                    String description = edt_job_des.getText().toString();

                    String startDate = tv_date_start.getText().toString();
                    String startTime = tv_time_start.getText().toString();
                    Date start = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(startDate + " " + startTime);

                    String endDate = tv_date_start.getText().toString();
                    String endTime = tv_time_start.getText().toString();
                    Date end = new SimpleDateFormat("dd/MM/yyyy hh:mm").parse(endDate + " " + endTime);

                    Job job = new Job(1, name, start, end, description);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
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
        String result = DateFormat.getDateInstance(DateFormat.SHORT).format(c.getTime());
        TextView textView;
        if (mode == 0)
            textView = findViewById(R.id.tv_dlg_date_start);
        else
            textView = findViewById(R.id.tv_dlg_date_end);
        textView.setText(result);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {
        String result = i + ":" + i1;
        TextView textView;
        if (mode == 0)
            textView = findViewById(R.id.tv_dlg_time_start);
        else
            textView = findViewById(R.id.tv_dlg_time_end);
        textView.setText(result);
    }
}
