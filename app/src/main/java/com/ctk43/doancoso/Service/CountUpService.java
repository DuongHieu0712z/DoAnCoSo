package com.ctk43.doancoso.Service;

import static com.ctk43.doancoso.Application.myApplication.CHANNEL_COUNT_UP;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetail_CountUp;

import java.security.Provider;
import java.util.List;
import java.util.Map;

public class CountUpService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Trong sevice","đã tạo service");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendNotification();
        return START_STICKY;
    }

    private void sendNotification() {
        Intent intent = new Intent(this, JobDetail_CountUp.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_COUNT_UP)
                .setContentTitle("Thử nè")
                .setContentText("Hừ hừ hừ hừ hớ hớ hơ")
                .setSmallIcon(R.drawable.ic_delete)
                .setContentIntent(pendingIntent)
                .build();
        startForeground(1,notification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Trong sevice","đã hủy");
    }
}
