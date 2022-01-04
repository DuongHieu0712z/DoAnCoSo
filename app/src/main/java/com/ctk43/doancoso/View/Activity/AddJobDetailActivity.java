package com.ctk43.doancoso.View.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;

public class AddJobDetailActivity extends AppCompatActivity {
    EditText edt_job_detail_name;
    EditText edt_job_detail_des;
    EditText edt_estimate_time;
    EditText edt_actual_time;
    Spinner spn_priority;
    TextView tv_title;
    private JobDetailViewModel jobDetailViewModel;
    private JobDetail jobDetailToUpdate;
    private int jobId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_job_detail);

        jobDetailViewModel = new ViewModelProvider(this).get(JobDetailViewModel.class);
        jobId = getIntent().getIntExtra("jobId", 0);
        jobDetailViewModel.setContext(this, jobId);
        initViews();

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            jobDetailToUpdate = (JobDetail) bundle.get("JobDetailToUpdate");
            if (jobDetailToUpdate != null) {
                loadJobDetail();
                tv_title.setText(R.string.update_job_detail);
            }
        }
    }

    private void initViews() {
        ImageView img_back = findViewById(R.id.img_close_detail);
        img_back.setOnClickListener(view -> onBackPressed());

        edt_job_detail_name = findViewById(R.id.edt_dlg_job_detail_name);
        edt_job_detail_des = findViewById(R.id.edt_dlg_job_detail_des);
        edt_estimate_time = findViewById(R.id.edt_dlg_job_detail_estimate_time);

        spn_priority = findViewById(R.id.spiner_jobdetail_priority);
        String[] priorities = {"Quan trọng", "Không quan trọng"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, priorities);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spn_priority.setAdapter(adapter);
        spn_priority.post(() -> {
            if (jobDetailToUpdate != null) {
                int pos = jobDetailToUpdate.isPriority() ? 0 : 1;
                spn_priority.setSelection(pos, true);
            }
        });

        tv_title = findViewById(R.id.tv_title_add_new_job_detail);

        Button btn_add_job_detail = findViewById(R.id.btn_dlg_add_new_job_detail);
        btn_add_job_detail.setOnClickListener(view -> saveJobDetail());
    }

    private void loadJobDetail() {
        edt_job_detail_name.setText(jobDetailToUpdate.getName());
        edt_job_detail_des.setText(jobDetailToUpdate.getDescription());
        edt_estimate_time.setText(String.valueOf(jobDetailToUpdate.getEstimatedCompletedTime()));
    }

    private void saveJobDetail() {
        if (validateInput()) {
            if (jobDetailToUpdate == null) {
                JobDetail jobDetail = getJobDetail();
                jobDetailViewModel.insert(jobDetail);
                Toast.makeText(this, getString(R.string.add_job_detail_sucess), Toast.LENGTH_SHORT).show();
            } else {
                updateJobDetail();
                jobDetailViewModel.update(jobDetailToUpdate);
                Toast.makeText(this, getString(R.string.update_job_detail_sucess), Toast.LENGTH_SHORT).show();
            }
            finish();
        }
    }

    private boolean validateInput() {
        String name = edt_job_detail_name.getText().toString();
        String time = edt_estimate_time.getText().toString();

        return !Extension.isEmpty(this, name, getString(R.string.job_detail_name), false) &&
                !Extension.isEmpty(this, time, getString(R.string.estimal_time), false);
    }

    private JobDetail getJobDetail() {
        String name = edt_job_detail_name.getText().toString();
        String description = edt_job_detail_des.getText().toString();
        boolean priority = spn_priority.getSelectedItemPosition() == 0;
        int time = Integer.parseInt(edt_estimate_time.getText().toString());

        return new JobDetail(jobId, priority, name, time, description);
    }

    private void updateJobDetail() {
        String name = edt_job_detail_name.getText().toString();
        String description = edt_job_detail_des.getText().toString();
        boolean priority = spn_priority.getSelectedItemPosition() == 0;
        int time = Integer.parseInt(edt_estimate_time.getText().toString());

        jobDetailToUpdate.setName(name);
        jobDetailToUpdate.setDescription(description);
        jobDetailToUpdate.setPriority(priority);
        jobDetailToUpdate.setEstimatedCompletedTime(time);
    }
}
