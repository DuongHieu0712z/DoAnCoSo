package com.ctk43.doancoso.View.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;

public class AddJobDetailActivity extends AppCompatActivity {
    private JobDetailViewModel jobDetailViewModel;
    private int jobId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_detail);
        jobDetailViewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);
        jobId = getIntent().getIntExtra("jobId", 0);
        jobDetailViewModel.setContext(this, jobId);
        initViews();
    }

    private void initViews() {
        ImageView img_back = findViewById(R.id.img_close_detail);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        EditText edt_job_detail_name = findViewById(R.id.edt_dlg_job_detail_name);
        EditText edt_job_detail_des = findViewById(R.id.edt_dlg_job_detail_des);
        EditText edt_estimate_time = findViewById(R.id.edt_dlg_job_detail_estimate_time);
        Button btn_add_job_detail = findViewById(R.id.btn_dlg_add_new_job_detail);
        btn_add_job_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xu ly them job detail
                String name = edt_job_detail_name.getText().toString();
                String description = edt_job_detail_des.getText().toString();
                int time = Integer.parseInt(edt_estimate_time.getText().toString());
                JobDetail jobDetail = new JobDetail(jobId, name, time, description);
                jobDetailViewModel.insert(jobDetail);
            }
        });
    }
}
