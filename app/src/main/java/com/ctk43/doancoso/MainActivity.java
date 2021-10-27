package com.ctk43.doancoso;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton btn_add_new_job;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        btn_add_new_job = findViewById(R.id.add_new_job);
        btn_add_new_job.setBackgroundTintMode(null);
    }
}
