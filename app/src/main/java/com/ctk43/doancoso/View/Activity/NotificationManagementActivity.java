package com.ctk43.doancoso.View.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ctk43.doancoso.R;

public class NotificationManagementActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        innitView();
    }

    private void innitView() {
        getSupportActionBar().setTitle("Quản lý thông báo");
    }
}
