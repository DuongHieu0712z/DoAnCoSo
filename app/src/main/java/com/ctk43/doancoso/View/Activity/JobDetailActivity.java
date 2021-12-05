package com.ctk43.doancoso.View.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Database.Repository.JobRepository;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.JobDetailAdapter;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class JobDetailActivity extends AppCompatActivity {

    FloatingActionButton btn_Add_New_Job_detail;
    private JobDetailViewModel jobDetailViewModel;
    private Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        jobDetailViewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);
        int jobID = getIntent().getIntExtra("JobID", 0);
        job = new JobRepository(this).getById(jobID);
        init();
    }

    private void AddJobDetail() {
        btn_Add_New_Job_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity) getActivity()).gotoAddNewJobScreen();
                Intent intent = new Intent(getApplicationContext(), AddJobDetailActivity.class);
                intent.putExtra("jobId", job.getId());
                startActivity(intent);
            }
        });
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.rcv_job_detail);
        jobDetailViewModel.setContext(this, job);
        jobDetailViewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);
        jobDetailViewModel.getJobDetails().observe(this, new Observer<List<JobDetail>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChanged(List<JobDetail> jobDetails) {
                JobDetailAdapter adapter = new JobDetailAdapter(getApplicationContext(), jobDetailViewModel);
                adapter.setData(jobDetails);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        TextView tv_job_name = findViewById(R.id.tv_jt_job_name);
        tv_job_name.setText(job.getName());

        TextView tv_job_des = findViewById(R.id.tv_jt_description);
        tv_job_des.setText(job.getDescription());

        TextView tv_job_start = findViewById(R.id.tv_jt_time_start);
        tv_job_start.setText(job.getStartDate().toString());

        TextView tv_job_end = findViewById(R.id.tv_jt_time_end);
        tv_job_end.setText(job.getEndDate().toString());

        TextView tv_job_progress = findViewById(R.id.tv_jt_prg);
        double prg = job.getProgress() * 100;

        tv_job_progress.setText(String.valueOf((int) prg) + "%");
        SeekBar sb = findViewById(R.id.sb_jt_progress);

        sb.setProgress((int) prg);
        btn_Add_New_Job_detail = (FloatingActionButton) findViewById(R.id.add_new_job_detail);
        AddJobDetail();


        ImageView img_back = findViewById(R.id.img_jt_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   ((MainActivity) getActivity()).gotoM001Screen();
            }
        });

    }
}