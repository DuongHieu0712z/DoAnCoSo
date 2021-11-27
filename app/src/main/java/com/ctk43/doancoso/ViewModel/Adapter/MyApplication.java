package com.ctk43.doancoso.ViewModel.Adapter;

import android.app.Application;

import com.ctk43.doancoso.View.MainActivity;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        if(instance == null)
            instance = this;
    }
    public static MyApplication getInstance(){
        return instance;
    }
}
