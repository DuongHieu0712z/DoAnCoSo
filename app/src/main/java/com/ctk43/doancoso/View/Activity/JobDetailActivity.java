package com.ctk43.doancoso.View.Activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.Action;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.Service.CountUpReceiver;
import com.ctk43.doancoso.Service.CountUpService;
import com.ctk43.doancoso.View.Adapter.JobDetailAdapter;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

public class JobDetailActivity extends AppCompatActivity {

    FloatingActionButton btn_Add_New_Job_detail;
    private JobDetailViewModel jobDetailViewModel;
    JobViewModel jobViewModel;
    RecyclerView recyclerView;
    private Job job;
    private int second;
    private  ImageView img_finish, img_resumOrPause, img_cancel;
    private TextView tv_title, tv_desciption, tv_time;
    private RelativeLayout layout_count_up;
    private JobDetail jobDetail;
    private boolean isRunning;
    private int action;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if(bundle == null)
                return;
            else if(Key.SEND_ACTION_TO_ACTIVITY.equals(intent.getAction()))
            {
                jobDetail = (JobDetail) bundle.get(Key.SEND_JOB_DETAIL_BY_SERVICE);
                isRunning = (boolean) bundle.get(Key.SEND_STATUS_OF_COUNT_UP);
                action = (int) bundle.get(Key.SEND_ACTION);
                handleLayoutCoutUp(action);
            }
            else{
                int second = (int) bundle.get(Key.SEND_SECOND);
                getSecond(second);
            }
        }
    };

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
        registerReceiver();
        init();
    }

    private void init() {
        RecyclerView recyclerView = findViewById(R.id.rcv_job_detail);
        TextView tv_job_name = findViewById(R.id.tv_jt_job_name);
        TextView tv_job_des = findViewById(R.id.tv_jt_description);
        TextView tv_job_start = findViewById(R.id.tv_jt_time_start);
        TextView tv_job_end = findViewById(R.id.tv_jt_time_end);
        TextView tv_job_progress = findViewById(R.id.tv_jt_prg);
        img_finish = findViewById(R.id.img_finish);
        img_resumOrPause = findViewById(R.id.img_pause_or_resume);
        img_cancel = findViewById(R.id.img_cancel_notification);
        tv_title = findViewById(R.id.tv_notification_title);
        tv_desciption = findViewById(R.id.tv_notification_descripsion);
        tv_time = findViewById(R.id.tv_clock_notification);
        layout_count_up = findViewById(R.id.layout_count_up_bottom);
        ProgressBar sb = findViewById(R.id.sb_jt_progress);
        btn_Add_New_Job_detail = findViewById(R.id.add_new_job_detail);
        JobDetailAdapter adapter = new JobDetailAdapter(this, jobDetailViewModel,jobViewModel);
        jobDetailViewModel.getJobDetails().observe(this, jobDetails -> {
            adapter.setData(jobDetails);
            UpdateJob();
            recyclerView.setAdapter(adapter);
            tv_job_name.setText(job.getName());
            tv_job_des.setText(job.getDescription());
            tv_job_start.setText( CalendarExtension.TimeRemaining(job.getEndDate(),Calendar.getInstance().getTime()));
            tv_job_end.setText(CalendarExtension.dateToString(job.getEndDate()));
            setProgress(tv_job_progress,sb,job);
            recyclerView.setLayoutManager(new LinearLayoutManager(JobDetailActivity.this));
        });
        btn_Add_New_Job_detail.setOnClickListener(view -> AddJobDetail());

    }

    private void UpdateJob(){
        if(job.getProgress() == jobDetailViewModel.updateProgress())
            return;
        if(jobDetailViewModel.count()==0)
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
    private void handleLayoutCoutUp(int action) {
            switch (action) {
                case Action.ACTION_COMPLETE:
                    complete();
                    break;
                case Action.ACTION_PAUSE:
                    pause();
                    break;
                case Action.ACTION_RESUME:
                    resume();
                    break;
                case Action.ACTION_CANCEL:
                    cancel();
                    break;
                case Action.ACTION_START:
                    start();
                    break;
            }
    }

    private void registerReceiver(){
        IntentFilter filter = new IntentFilter(Key.SEND_ACTION_TO_ACTIVITY);
        filter.addAction(Key.SEND_SECOND_BY_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }
    private void start(){
        layout_count_up.setVisibility(View.VISIBLE);
        showInforCountUp();
        setStatusButtonPlayORPause();
    }
    private void cancel(){
        layout_count_up.setVisibility(View.GONE);

    }

    private void resume() {
        setStatusButtonPlayORPause();
        SendActionToService(Action.ACTION_RESUME);
        UpdateJobDetail(false);
    }

    private void pause() {
        setStatusButtonPlayORPause();
        SendActionToService(Action.ACTION_PAUSE);
        UpdateJobDetail(false);
    }

    private void complete() {
        layout_count_up.setVisibility(View.GONE);
        UpdateJobDetail(true);
    }

    private void UpdateJobDetail(boolean isFinish){
            jobDetail.setStatus(isFinish);
            jobDetail.setActualCompletedTime(second);
            jobDetailViewModel.update(jobDetail);
    }

    private void showInforCountUp(){
        if(jobDetail == null)
            return;
        tv_title.setText(jobDetail.getName());
        tv_desciption.setText(jobDetail.getDescription());
        img_resumOrPause.setOnClickListener(v -> {
            if(isRunning){
                SendActionToService(Action.ACTION_PAUSE);
                pause();
            }else{
                SendActionToService(Action.ACTION_RESUME);
                resume();
            }
        });
        img_cancel.setOnClickListener(v->{
            SendActionToService(Action.ACTION_CANCEL);
        });
        img_finish.setOnClickListener(v->{
            SendActionToService(Action.ACTION_COMPLETE);
            complete();
        });
    }
    private void setStatusButtonPlayORPause(){
        if(isRunning)
            img_resumOrPause.setImageResource(R.drawable.ic_pause);
        else
            img_resumOrPause.setImageResource(R.drawable.ic_continue);
    }
    private void SendActionToService(int action){
        Intent intent = new Intent(this,CountUpService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Key.SEND_JOB_DETAIL_BY_ACTIVITY,jobDetail);
        bundle.putInt(Key.SEND_ACTION,action);
        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }
    private void getSecond(int second){
        this.second = second;
        tv_time.setText(CalendarExtension.getTimeText(second) );
    }
}