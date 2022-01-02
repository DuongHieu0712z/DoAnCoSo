package com.ctk43.doancoso.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ctk43.doancoso.Library.Action;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.Model.NotificationModel;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.ctk43.doancoso.ViewModel.NotificationViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class NotificationService extends Service {
    private NotificationViewModel notificationViewModel ;
    private JobViewModel jobViewModel ;
    private List<Job> jobsComing;
    private List<Job> jobsOnGoing;
    private boolean isNew = false;
    RemoteViews remoteViews;
    private NotificationCompat.Builder mBuilder;
    NotificationManager mNotificationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationViewModel = new NotificationViewModel();
        notificationViewModel.setData(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initViewModel();
        getComingAndOnGoing();
        return START_REDELIVER_INTENT;
    }

    private void getComingAndOnGoing(){
        jobsComing = setJobAndUpDate(GeneralData.STATUS_COMING);
        jobsOnGoing= setJobAndUpDate(GeneralData.STATUS_ON_GOING);
        if(jobsOnGoing != null)
        Collections.sort(jobsComing, new Comparator<Job>() {
                @Override
                public int compare(Job o1, Job o2) {
                    return (o1.getStartDate()).compareTo(o2.getStartDate());
                }
            });
        if(jobsComing != null)
        Collections.sort(jobsOnGoing, new Comparator<Job>() {
            @Override
            public int compare(Job o1, Job o2) {
                return (o1.getEndDate()).compareTo(o2.getEndDate());
            }
        });
        notificationViewModel.getNotificationList();
    }

    private List<Job> setJobAndUpDate(int status){
        List<Job> jobs;
        List<Job> jobList = jobViewModel.getListByStatus(status);
        jobs = Extension.getJobsChange(jobList);
        if(jobs.size()!=0){
        jobList.removeAll(jobs);
        jobViewModel.update(jobs.toArray(new Job[0]));
        AddNotificationModel(jobs);
        }
        return jobList;
    }

    private void sendNotification(Job job) {
        Intent intent = new Intent(this, MainActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_for_job);
        remoteViews.setTextViewText(R.id.tv_app_name,getApplicationContext().getString(R.string.app_name));
        remoteViews.setTextViewText(R.id.tv_notification_content, CalendarExtension.getTimeText(0));
        mBuilder = new NotificationCompat.Builder(this, Key.CHANNEL_COUNT_UP)
                .setSmallIcon(R.drawable.ic_delete)
                .setContentIntent(pendingIntent)
                .setSilent(true)
                .setAutoCancel(true)
                .setCustomContentView(remoteViews);
        startForeground(Key.CHANNEL_NOTIFICATION_JOB_ID,mBuilder.build());
    }


    private void AddNotificationModel(List<Job> jobs){
        List<NotificationModel> notificationModels = new ArrayList<>();
        NotificationModel notificationModel;
        for (Job job: jobs) {
            notificationModel = new NotificationModel(job.getId(),job.getStatus());
            notificationModels.add(notificationModel);
        }
        notificationViewModel.insert(notificationModels.toArray(new NotificationModel[0]));
    }

    private void initViewModel(){
        jobViewModel = new JobViewModel();
        jobViewModel.setData(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
