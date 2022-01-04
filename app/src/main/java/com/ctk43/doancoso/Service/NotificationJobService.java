package com.ctk43.doancoso.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.res.ResourcesCompat;

import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.NotificationModel;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.ctk43.doancoso.ViewModel.NotificationViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class NotificationJobService extends Service {

    private NotificationModel notificationModel;
    private RemoteViews remoteViews;
    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    private JobViewModel jobViewModel;
    private Job job;
    private int id_notification;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int idJob = intent.getIntExtra(Key.SEND_NOTIFICATION_JOB_ID,0);
        if(idJob!=0){
            initViewModel(idJob);
            sendNotification();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void initViewModel(int jobId) {
        jobViewModel = new JobViewModel();
        NotificationViewModel notificationViewModel = new NotificationViewModel();
        jobViewModel.setData(getApplicationContext());
        notificationViewModel.setData(getApplicationContext());
        notificationModel = notificationViewModel.getNotificationByJobIDNew(jobId);
        job = jobViewModel.getJobById(jobId);
        id_notification = (int) (notificationModel.getId() + CalendarExtension.ONE_HOUR);
    }
    private void sendNotification() {
        Intent intent = new Intent(this, JobDetailActivity.class);
        intent.putExtra(Key.JOB_ID, notificationModel.getJobId());
        intent.putExtra(Key.SEND_ID_NOTIFICATION, notificationModel.getId());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notification_for_job);
        remoteViews.setTextViewText(R.id.tv_app_name, getApplicationContext().getString(R.string.app_name));
        remoteViews.setImageViewResource(R.id.img_notification_priority, GeneralData.getImgPriority(job.getPriority()));
        remoteViews.setTextViewText(R.id.tv_notification_time, CalendarExtension.formatDateTime(notificationModel.getDateOfRecord()));
        remoteViews.setTextViewText(R.id.tv_content,Extension.setContent(this,job));
        mBuilder = new NotificationCompat.Builder(this, Key.CHANNEL_NOTIFICATION_JOB)
                .setSmallIcon(R.drawable.logo_final)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setCustomContentView(remoteViews);
        mNotificationManager.notify(id_notification, mBuilder.build());
    }



}
