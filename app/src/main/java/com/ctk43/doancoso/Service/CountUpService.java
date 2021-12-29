package com.ctk43.doancoso.Service;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Library.Action;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.CountUpTimer;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;

public class CountUpService extends Service {


    private NotificationCompat.Builder mBuilder;
    private NotificationManager mNotificationManager;
    boolean isRuning = false;
    private JobDetail jobDetail;
    CountUpTimer timer;
    RemoteViews remoteViews;
    int actionTime;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            this.jobDetail = (JobDetail) bundle.get(Key.SEND_JOB_DETAIL_BY_ACTIVITY);
            actionTime = (int) bundle.get(Key.SEND_ACTION);
            handleActionTime(actionTime);
            sendNotification(this.jobDetail);
            Log.e("ALO", "onStartCommand: " );
        }
        return START_REDELIVER_INTENT;
    }

    private void handleActionTime(int action) {
        switch (action) {
            case Action.ACTION_START:
                startCount();
                break;
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
        }
    }

    private void updateNotification(int second) {
        sendSecondToActivity(second);
        remoteViews.setTextViewText(R.id.tv_clock_notification, CalendarExtension.getTimeText(second));
        mNotificationManager.notify(Key.COUNT_UP_ID, mBuilder.build());
    }

    private void pause() {
        if(isRuning && timer !=null){
            sendActionToActivity(Action.ACTION_PAUSE);
            isRuning = false;
            timer = null;
            stopSelf();
        }
    }

    private void complete() {
        sendActionToActivity(Action.ACTION_COMPLETE);
        isRuning = false;
        timer = null;
        stopSelf();
    }

    private void resume() {
        if(!isRuning && timer ==null){
            isRuning = true;
            sendActionToActivity(Action.ACTION_RESUME);
            startTime();
        }
    }

    private void cancel() {
        sendActionToActivity(Action.ACTION_CANCEL);
        isRuning = false;
        stopSelf();
        timer = null;
    }

    private void sendNotification(JobDetail jobDetail) {
        Intent intent = new Intent(this, JobDetailActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification_count_up);
        remoteViews.setTextViewText(R.id.tv_notification_title, jobDetail.getName());
        remoteViews.setTextViewText(R.id.tv_clock_notification, CalendarExtension.getTimeText(0));
        remoteViews.setTextViewText(R.id.tv_notification_descripsion, jobDetail.getDescription());
        remoteViews.setImageViewResource(R.id.img_pause_or_resume, R.drawable.ic_pause);
        if (isRuning) {
            remoteViews.setOnClickPendingIntent(R.id.img_pause_or_resume, getPendingIntent(this, Action.ACTION_PAUSE));
            remoteViews.setImageViewResource(R.id.img_pause_or_resume, R.drawable.ic_continue);
        } else {
            remoteViews.setOnClickPendingIntent(R.id.img_pause_or_resume, getPendingIntent(this, Action.ACTION_RESUME));
            remoteViews.setImageViewResource(R.id.img_pause_or_resume, R.drawable.ic_pause);
        }
        remoteViews.setOnClickPendingIntent(R.id.img_finish, getPendingIntent(this, Action.ACTION_COMPLETE));
        remoteViews.setOnClickPendingIntent(R.id.img_cancel_notification, getPendingIntent(this, Action.ACTION_CANCEL));
        mBuilder = new NotificationCompat.Builder(this, Key.CHANNEL_COUNT_UP)
                .setSmallIcon(R.drawable.ic_delete)
                .setContentIntent(pendingIntent)
                .setSilent(true)
                .setAutoCancel(true)
                .setCustomContentView(remoteViews);
        startForeground(Key.COUNT_UP_ID,mBuilder.build());
      //  mNotificationManager.notify(Key.COUNT_UP_ID, mBuilder.build());
    }

    private void startTime() {
        timer = new CountUpTimer() {
            public void onTick(int second) {
                if(!isRuning){
                    cancel();
                    return;
                }
                updateNotification(jobDetail.getActualCompletedTime()+second);

            }
        };
        timer.start();
    }

    private void startCount() {
        if (isRuning == false) {
            isRuning = true;
            startTime();
            sendActionToActivity(Action.ACTION_START);
        } else {
            isRuning = false;
            startCount();
        }
    }


    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, CountUpReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Key.SEND_JOB_DETAIL_BY_ACTIVITY, jobDetail);
        bundle.putInt(Key.SEND_ACTION, action);
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(context.getApplicationContext(), action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void sendActionToActivity(int action) {
        Intent intent = new Intent(Key.SEND_ACTION_TO_ACTIVITY);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Key.SEND_STATUS_OF_COUNT_UP, isRuning);
        bundle.putInt(Key.SEND_ACTION, action);
        bundle.putSerializable(Key.SEND_JOB_DETAIL_BY_SERVICE, jobDetail);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendSecondToActivity(int second) {
        Intent intent = new Intent(Key.SEND_SECOND_BY_SERVICE);
        Bundle bundle = new Bundle();
        bundle.putInt(Key.SEND_SECOND, second);
        intent.putExtras(bundle);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRuning = false;
    }
}
