package com.ctk43.doancoso.Database.DataLocal;

import android.app.Application;

public class myApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DataLocalManager.init(getApplicationContext());
    }
}
