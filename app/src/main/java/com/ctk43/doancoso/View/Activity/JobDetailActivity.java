package com.ctk43.doancoso.View.Activity;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.Action;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.DialogExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.Model.NotificationModel;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.Service.CountUpService;
import com.ctk43.doancoso.View.Adapter.JobDetailAdapter;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.ctk43.doancoso.ViewModel.NotificationViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JobDetailActivity extends AppCompatActivity {
    public boolean isRunning;
    CheckBox chk_job_title_finish_job;
    ProgressBar sb;
    TextView tv_job_progress;
    private FloatingActionButton btn_Add_New_Job_detail;
    private JobDetailViewModel jobDetailViewModel;
    private JobViewModel jobViewModel;
    private Job job;
    private JobDetailAdapter adapter;
    private int second;

    private ImageView img_finish, img_resumOrPause, img_cancel;
    private TextView tv_title, tv_desciption, tv_time;
    private RelativeLayout layout_count_up;
    private JobDetail jobDetail;
    private int action;
    private TextView tv_ongoing;
    private TextView tv_finish;
    private TextView tv_Total;

    private SearchView searchView;
    private MenuItem addition_menu;
    private MenuItem notificationManagement;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            if (bundle == null) return;

            if (Key.SEND_ACTION_TO_ACTIVITY.equals(intent.getAction())) {
                jobDetail = (JobDetail) bundle.get(Key.SEND_JOB_DETAIL_BY_SERVICE);
                isRunning = (boolean) bundle.get(Key.SEND_STATUS_OF_COUNT_UP);
                action = (int) bundle.get(Key.SEND_ACTION);
                handleLayoutCountUp(action);
            } else {
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
        int jobID = getIntent().getIntExtra(Key.JOB_ID, -1);
        int notificationID = getIntent().getIntExtra(Key.SEND_ID_NOTIFICATION, -1);
        if (jobID == -1) {
            Bundle bundle = getIntent().getExtras();
            jobDetail = (JobDetail) bundle.get(Key.SEND_JOB_DETAIL);
            isRunning = (boolean) bundle.get(Key.IS_RUNNING);
            jobID = jobDetail.getJobId();
        }
        if(notificationID!=-1){
            NotificationViewModel notificationViewModel = new NotificationViewModel();
            notificationViewModel.setData(getApplicationContext());
            NotificationModel notificationModel = notificationViewModel.getNotificationById(notificationID);
            notificationModel.setStatus(GeneralData.STATUS_NOTIFICATION_SEEN);
            notificationViewModel.update(notificationModel);
        }

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
        TextView tv_job_status = findViewById(R.id.job_title_status);
        ImageView img_priority = findViewById(R.id.job_title_priority);
        ImageView img_edit = findViewById(R.id.job_title_edit);

        tv_job_progress = findViewById(R.id.tv_jt_prg);
        chk_job_title_finish_job = findViewById(R.id.chk_job_title_finish_job);
        sb = findViewById(R.id.sb_jt_progress);
        tv_ongoing = findViewById(R.id.tv_ongoing);
        tv_finish = findViewById(R.id.tv_finish);
        tv_Total= findViewById(R.id.tv_total);
        img_finish = findViewById(R.id.img_finish);
        img_resumOrPause = findViewById(R.id.img_pause_or_resume);
        img_cancel = findViewById(R.id.img_cancel_notification);
        tv_title = findViewById(R.id.tv_notification_title);
        tv_desciption = findViewById(R.id.tv_notification_descripsion);
        tv_time = findViewById(R.id.tv_clock_notification);
        layout_count_up = findViewById(R.id.layout_count_up_bottom);
        ProgressBar sb = findViewById(R.id.sb_jt_progress);
        btn_Add_New_Job_detail = findViewById(R.id.add_new_job_detail);
        jobDetailViewModel.getJobDetails().observe(this, jobDetails -> {
            syncJob();
            adapter = new JobDetailAdapter(this, jobDetailViewModel,job);
            adapter.setData(jobDetails);
            recyclerView.setAdapter(adapter);
            tv_job_name.setText(job.getName());
            tv_job_des.setText(job.getDescription());
            tv_job_start.setText(CalendarExtension.formatDateTime(job.getStartDate()));
            tv_job_end.setText(CalendarExtension.formatDateTime(job.getEndDate()));
            tv_job_status.setText(GeneralData.getStatus(job.getStatus()));
            img_priority.setImageResource(GeneralData.getImgPriority(job.getPriority()));
            recyclerView.setLayoutManager(new LinearLayoutManager(JobDetailActivity.this));
            chk_job_title_finish_job.setChecked(Extension.isFinishJob(job));
            tv_ongoing.setText(String.valueOf(adapter.getNumJobDetail(GeneralData.STATUS_DETAIL_ONGOING)));
            tv_finish.setText(String.valueOf(adapter.getNumJobDetail(GeneralData.STATUS_DETAIL_FINISH)));
            tv_Total.setText(String.valueOf(adapter.getNumJobDetail(GeneralData.NON_STATUS)));
            Extension.setProgress(tv_job_progress,sb,job);
        });
        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOpenUpdateJobActivity(job);
            }
        });
        chk_job_title_finish_job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Extension.canCheck(getApplicationContext(), chk_job_title_finish_job, job)) {
                    Extension.CheckOrUncheckJob(JobDetailActivity.this, chk_job_title_finish_job, job, tv_job_progress, sb);
                }
            }
        });
        btn_Add_New_Job_detail.setOnClickListener(view -> AddJobDetail());
        if (isRunning) {
            start();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search) {
                adapter.getFilter().filter(search);
                return false;
            }
        });
        addition_menu = menu.findItem(R.id.addition_menu);
        addition_menu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DialogExtension.onOpenMenuDialog(JobDetailActivity.this);
                return true;
            }
        });
        notificationManagement = menu.findItem(R.id.menu_item_notification);
        updateNotification();
        notificationManagement.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(JobDetailActivity.this, NotificationManagementActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }

    private void syncJob() {
        if ((job.getProgress() == jobDetailViewModel.updateProgress()))
            return;

        if (jobDetailViewModel.checkList().size() == 0 && Extension.isFinishJob(job)) {
            job.setProgress(1);
        } else {
            job.setProgress(jobDetailViewModel.updateProgress());
        }
        job.setStatus(Extension.CheckStatus(job));
        jobViewModel.update(job);
    }

    private void onOpenUpdateJobActivity(Job job) {
        Intent intent = new Intent(JobDetailActivity.this, AddJobActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("JobToUpdate", job);
        intent.putExtras(bundle);
        JobDetailActivity.this.startActivity(intent);
    }

    private void AddJobDetail() {
        Intent intent = new Intent(getApplicationContext(), AddJobDetailActivity.class);
        intent.putExtra("jobId", job.getId());
        startActivity(intent);
    }

    private void handleLayoutCountUp(int action) {
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

    private void registerReceiver() {
        IntentFilter filter = new IntentFilter(Key.SEND_ACTION_TO_ACTIVITY);
        filter.addAction(Key.SEND_SECOND_BY_SERVICE);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, filter);
    }

    private void start() {
        layout_count_up.setVisibility(View.VISIBLE);
        showInfoCountUp();
        setStatusButtonPlayOrPause();
    }

    private void cancel() {
        layout_count_up.setVisibility(View.GONE);
    }

    private void resume() {
        isRunning = true;
        setStatusButtonPlayOrPause();
        sendActionToService(Action.ACTION_RESUME);
    }

    private void pause() {
        isRunning = false;
        setStatusButtonPlayOrPause();
        UpdateJobDetail(false, false);
        sendActionToService(Action.ACTION_PAUSE);
    }

    public void complete() {
        layout_count_up.setVisibility(View.GONE);
        sendActionToService(Action.ACTION_CANCEL);
        UpdateJobDetail(true, false);
    }

    private void UpdateJobDetail(boolean isFinish, boolean isCancel) {
        jobDetail.setStatus(isFinish);
        if (!isCancel)
            jobDetail.setActualCompletedTime(second);
        jobDetailViewModel.update(jobDetail);
    }
    public void updateNotification(){
        NotificationViewModel notificationViewModel = new NotificationViewModel();
        notificationViewModel.setData(this);
        if(notificationViewModel.geNotificationTotal(GeneralData.STATUS_NOTIFICATION_ACTIVE)>0){
            notificationManagement.setIcon(R.drawable.ic_notifications);
        }else{
            notificationManagement.setIcon(R.drawable.ic_baseline_notifications_24);
        }
    }

    private void showInfoCountUp() {
        if (jobDetail == null)
            return;
        tv_title.setText(jobDetail.getName());
        tv_desciption.setText(jobDetail.getDescription());
        img_resumOrPause.setOnClickListener(v -> {
            if (isRunning) {
                setStatusButtonPlayOrPause();
                sendActionToService(Action.ACTION_PAUSE);
                pause();
            } else {
                setStatusButtonPlayOrPause();
                sendActionToService(Action.ACTION_RESUME);
                resume();
            }
        });
        img_cancel.setOnClickListener(v -> {
            sendActionToService(Action.ACTION_CANCEL);
        });
        img_finish.setOnClickListener(v -> {
            sendActionToService(Action.ACTION_COMPLETE);
            complete();
        });
    }

    private void setStatusButtonPlayOrPause() {
        img_resumOrPause.setImageResource(isRunning ? R.drawable.ic_pause : R.drawable.ic_continue);
    }

    private void sendActionToService(int action) {
        Log.e("TAG", "sendActionToService: " + action);
        Intent intent = new Intent(this, CountUpService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Key.SEND_JOB_DETAIL_BY_ACTIVITY, jobDetail);
        bundle.putInt(Key.SEND_ACTION, action);
        intent.putExtras(bundle);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    private void getSecond(int second) {
        this.second = second;
        tv_time.setText(CalendarExtension.getTimeText(second));
    }

    @Override
    protected void onResume() {
        super.onResume();
        invalidateOptionsMenu();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
