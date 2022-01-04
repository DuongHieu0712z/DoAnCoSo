package com.ctk43.doancoso.Service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.ctk43.doancoso.Library.Action;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.CountUpTimer;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;

public class CountUpService extends Service {
    public boolean isRunning = false;
    CountUpTimer timer;
    RemoteViews remoteViews;
    int actionTime;
    private Notification notification;
    private NotificationManager mNotificationManager;
    private JobDetail jobDetail;

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
            JobDetail jobDetail = (JobDetail) bundle.get(Key.SEND_JOB_DETAIL_BY_ACTIVITY);
            if (jobDetail != null) {
                this.jobDetail = jobDetail;
            }
        }

        actionTime = intent.getIntExtra(Key.SEND_ACTION, Action.NONE);
        handleActionTime(actionTime);

        if (jobDetail != null) {
            sendNotification(jobDetail);
        }

        return START_NOT_STICKY;
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
        mNotificationManager.notify(Key.CHANNEL_COUNT_UP_ID, notification);
    }

    public void complete() {
        isRunning = false;
        timer = null;
        sendActionToActivity(Action.ACTION_COMPLETE);
        stopSelf();
    }

    private void resume() {
        if (!isRunning && timer == null) {
            startTime();
            isRunning = true;
            sendActionToActivity(Action.ACTION_RESUME);
        }
    }

    private void pause() {
        if (isRunning && timer != null) {
            timer = null;
            isRunning = false;
            sendActionToActivity(Action.ACTION_PAUSE);
        }
    }

    private void cancel() {
        timer = null;
        isRunning = false;
        sendActionToActivity(Action.ACTION_CANCEL);
        stopSelf();
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void sendNotification(JobDetail jobDetail) {
        Intent intent = new Intent(this, JobDetailActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, Key.CHANNEL_COUNT_UP_ID, callBackActivity(), PendingIntent.FLAG_UPDATE_CURRENT);

        mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        remoteViews = new RemoteViews(getPackageName(), R.layout.layout_notification_count_up);
        remoteViews.setTextViewText(R.id.tv_notification_title, jobDetail.getName());
        remoteViews.setTextViewText(R.id.tv_notification_descripsion, jobDetail.getDescription());

        if (isRunning) {
            remoteViews.setImageViewResource(R.id.img_pause_or_resume, R.drawable.ic_pause);
            remoteViews.setOnClickPendingIntent(R.id.img_pause_or_resume, getPendingIntent(this, Action.ACTION_PAUSE));
        } else {
            remoteViews.setImageViewResource(R.id.img_pause_or_resume, R.drawable.ic_continue);
            remoteViews.setOnClickPendingIntent(R.id.img_pause_or_resume, getPendingIntent(this, Action.ACTION_RESUME));
        }

        remoteViews.setOnClickPendingIntent(R.id.img_finish, getPendingIntent(this, Action.ACTION_COMPLETE));
        remoteViews.setOnClickPendingIntent(R.id.img_cancel_notification, getPendingIntent(this, Action.ACTION_CANCEL));

        notification = new NotificationCompat.Builder(this, Key.CHANNEL_COUNT_UP)
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentIntent(pendingIntent)
                .setSilent(true)
                .setAutoCancel(true)
                .setContent(remoteViews)
                .build();

        startForeground(Key.CHANNEL_COUNT_UP_ID, notification);
    }

    private void startTime() {
        timer = new CountUpTimer() {
            public void onTick(int second) {
                if (!isRunning) {
                    cancel();
                    return;
                }
                updateNotification(jobDetail.getActualCompletedTime() + second);

            }
        };
        timer.start();
    }

    private void startCount() {
        if (!isRunning) {
            isRunning = true;
            startTime();
            sendActionToActivity(Action.ACTION_START);
        } else {
            isRunning = false;
            startCount();
        }
    }


    private PendingIntent getPendingIntent(Context context, int action) {
        Intent intent = new Intent(this, CountUpReceiver.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Key.SEND_JOB_DETAIL_BY_ACTIVITY, jobDetail);
        bundle.putInt(Key.SEND_ACTION, action);
        intent.putExtras(bundle);
        return PendingIntent.getBroadcast(context, action, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void sendActionToActivity(int action) {
        Intent intent = new Intent(Key.SEND_ACTION_TO_ACTIVITY);
        Bundle bundle = new Bundle();
        bundle.putBoolean(Key.SEND_STATUS_OF_COUNT_UP, isRunning);
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


    private Intent callBackActivity() {
        Intent intent = new Intent(this, JobDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Key.SEND_JOB_DETAIL, jobDetail);
        bundle.putBoolean(Key.IS_RUNNING, isRunning);
        intent.putExtras(bundle);
        return intent;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
