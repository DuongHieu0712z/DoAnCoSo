package com.ctk43.doancoso.Application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;

public class myApplication extends Application {
    public static final String CHANNEL_COUNT_UP = "channel_service_count_up";
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
        createChanelNotification();
    }

    private void createChanelNotification() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channelCountUp = new NotificationChannel(
                    CHANNEL_COUNT_UP,
                    "Tới công chuyện",
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            if(manager !=null)
            manager.createNotificationChannel(channelCountUp);
        }
    }
}
