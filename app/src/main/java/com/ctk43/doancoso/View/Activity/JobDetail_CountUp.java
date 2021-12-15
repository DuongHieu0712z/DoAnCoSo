package com.ctk43.doancoso.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.ctk43.doancoso.R;
import com.ctk43.doancoso.Service.CountUpService;

public class JobDetail_CountUp extends AppCompatActivity {
    Button btnStartFinish;
    Button btnPauseResume;
    Button btnReset;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detail_count_up);
        init();
    }
    void init(){
        btnStartFinish = findViewById(R.id.btn_startOrFinish);
        btnPauseResume = findViewById(R.id.btn_pause_or_resume);
        btnReset = findViewById(R.id.btn_reset);
        btnCancel = findViewById(R.id.btn_cancel);
        btnStartFinish.setOnClickListener(v -> startCount());
        btnCancel.setOnClickListener(v-> cancelCount());
    }

    private void cancelCount() {
        Intent countIntent = new Intent(this, CountUpService.class);
        stopService(countIntent);
    }

    private void startCount() {
        Intent countIntent = new Intent(this, CountUpService.class);
        startService(countIntent);
    }


}