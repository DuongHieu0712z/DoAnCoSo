package com.ctk43.doancoso.View.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;

public class AddJobDetailActivity extends AppCompatActivity {
    private JobDetailViewModel jobDetailViewModel;
    private JobDetail jobDetailToUpdate;
    private int jobId;

    EditText edt_job_detail_name ;
    EditText edt_job_detail_des ;
    EditText edt_estimate_time ;
    EditText edt_actual_time ;

    Spinner spn_priority;

    TextView tv_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_detail);
        jobDetailViewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);
        jobId = getIntent().getIntExtra("jobId", 0);

        Bundle bundle = getIntent().getExtras();
        jobDetailToUpdate = (JobDetail) bundle.get("JobDetailToUpdate");

        jobDetailViewModel.setContext(this, jobId);
        initViews();
    }

    private void initViews() {
        ImageView img_back = findViewById(R.id.img_close_detail);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        edt_job_detail_name = findViewById(R.id.edt_dlg_job_detail_name);
        edt_job_detail_des = findViewById(R.id.edt_dlg_job_detail_des);
        edt_estimate_time = findViewById(R.id.edt_dlg_job_detail_estimate_time);
        edt_actual_time = findViewById(R.id.edt_dlg_job_detail_actual_time);

        spn_priority = findViewById(R.id.spiner_jobdetail_priority);
        String[] priorities = {"Quan trọng", "Không quan trọng"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, priorities);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_priority.setAdapter(adapter);

        tv_title = findViewById(R.id.tv_title_add_new_job_detail);
        if(jobDetailToUpdate!=null) tv_title.setText(R.string.update_job_detail);

        Button btn_add_job_detail = findViewById(R.id.btn_dlg_add_new_job_detail);
        btn_add_job_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //xu ly them job detail
                String name = edt_job_detail_name.getText().toString();
                String description = edt_job_detail_des.getText().toString();
                int index = spn_priority.getSelectedItemPosition();
                boolean priority = false;
                if(index == 0) priority =true;
                int time = Integer.parseInt(edt_estimate_time.getText().toString());
                JobDetail jobDetail = new JobDetail(jobId, priority, name, time, description);
                jobDetailViewModel.insert(jobDetail);
                finish();
            }
        });
        SetDataJobDetail();

    }

    private void SetDataJobDetail() {
        if(jobDetailToUpdate!=null){
            edt_job_detail_name.setText(jobDetailToUpdate.getName());
            edt_job_detail_des.setText(jobDetailToUpdate.getDescription());
            edt_estimate_time.setText(String.valueOf(jobDetailToUpdate.getEstimatedCompletedTime()));
            edt_actual_time.setText(String.valueOf(jobDetailToUpdate.getActualCompletedTime()));
        }
    }
}
