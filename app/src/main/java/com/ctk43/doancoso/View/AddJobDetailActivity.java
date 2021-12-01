package com.ctk43.doancoso.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ctk43.doancoso.R;

public class AddJobDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_job_detail);
        InnitView();
        super.onCreate(savedInstanceState);
    }

    private void InnitView() {
        ImageView img_back = findViewById(R.id.img_close_detail);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        EditText edt_job_detail_name = findViewById(R.id.edt_dlg_job_detail_name);
        EditText edt_job_detail_des = findViewById(R.id.edt_dlg_job_detail_des);
        EditText edt_estimate_time = findViewById(R.id.edt_dlg_job_detail_estimate_time);
        Button btn_add_job_detail = findViewById(R.id.btn_dlg_add_new_job_detail);
        btn_add_job_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xu ly them job detail
            }
        });



    }
}
