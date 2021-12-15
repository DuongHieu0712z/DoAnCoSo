package com.ctk43.doancoso.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.JobDetailAdapter;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JobDetailActivity extends AppCompatActivity {

    FloatingActionButton btn_Add_New_Job_detail;
    private JobDetailViewModel jobDetailViewModel;
    JobViewModel jobViewModel;
    RecyclerView recyclerView;
    private Job job;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail);
        initViewModel();
    }


    private void initViewModel() {
        int jobID = getIntent().getIntExtra("JobID", 0);
        jobViewModel = new JobViewModel();
        jobViewModel.setData(this);
        job = jobViewModel.getJobById(jobID);
        jobDetailViewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);
        jobDetailViewModel.setContext(this, jobID);
        init();
    }

    private void init() {
        recyclerView = findViewById(R.id.rcv_job_detail);
        TextView tv_job_name = findViewById(R.id.tv_jt_job_name);
        TextView tv_job_des = findViewById(R.id.tv_jt_description);
        TextView tv_job_start = findViewById(R.id.tv_jt_time_start);
        TextView tv_job_end = findViewById(R.id.tv_jt_time_end);
        TextView tv_job_progress = findViewById(R.id.tv_jt_prg);
        SeekBar sb = findViewById(R.id.sb_jt_progress);
        btn_Add_New_Job_detail = findViewById(R.id.add_new_job_detail);
        JobDetailAdapter adapter = new JobDetailAdapter(this, jobDetailViewModel,jobViewModel);
        jobDetailViewModel.getJobDetails().observe(this, jobDetails -> {
            adapter.setData(jobDetails);
            UpdateJob();
            recyclerView.setAdapter(adapter);
            tv_job_name.setText(job.getName());
            tv_job_des.setText(job.getDescription());
            tv_job_start.setText(job.getStartDate().toString());
            tv_job_end.setText(job.getEndDate().toString());
            setProgress(tv_job_progress,sb,job);
            recyclerView.setLayoutManager(new LinearLayoutManager(JobDetailActivity.this));
        });
        btn_Add_New_Job_detail.setOnClickListener(view -> AddJobDetail());


        ImageView img_back = findViewById(R.id.img_jt_back);
        img_back.setOnClickListener(view -> {
            //   ((MainActivity) getActivity()).gotoM001Screen();
        });

    }
    private void UpdateJob(){
        if(job.getProgress() == 1 && jobDetailViewModel.count()==0)
            return;
        if(job.getProgress() == jobDetailViewModel.updateProgress())
            return;
        job.setProgress(jobDetailViewModel.updateProgress());
        job.setStatus(Extension.CheckStatus(job));
        jobViewModel.update(job);
    }
    private void AddJobDetail() {
        Intent intent = new Intent(getApplicationContext(), AddJobDetailActivity.class);
        intent.putExtra("jobId", job.getId());
        startActivity(intent);
    }

    void setProgress(TextView tv_progress, ProgressBar progressBar, Job job) {
        int progress = (int) (job.getProgress() * 100.0);
        String prgString = progress + " %";
        tv_progress.setText(prgString);
        progressBar.setProgress(progress);
    }
}