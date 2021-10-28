package com.ctk43.doancoso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JobFragment extends Fragment implements View.OnClickListener{
    private Context mContext;
    FloatingActionButton btn_Add_New_Job;
    //private ArrayList<JobEnitity> listJob;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_jobs, container, false);
        initViews(rootView);
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initViews(View v) {
        LinearLayout lnMain = v.findViewById(R.id.ln_job);
        lnMain.removeAllViews();
        btn_Add_New_Job = (FloatingActionButton) v.findViewById(R.id.add_new_job);
        btn_Add_New_Job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).gotoAddNewJobScreen();
            }
        });

        ArrayList<JobEnitity> listJob = readJob();

        //lnMain.removeAllViews();
        for (JobEnitity job:listJob) {
            View vJob = LayoutInflater.from(mContext).inflate(R.layout.job_item, null);
            TextView tvJobName = vJob.findViewById(R.id.tv_job_name);
            TextView tvDes = vJob.findViewById(R.id.tv_job_description);
            TextView tvEndTime = vJob.findViewById(R.id.tv_end_time);
            TextView tvProgress = vJob.findViewById(R.id.tv_progress);
            ProgressBar pb_Progress = vJob.findViewById(R.id.prg_progress);
            ImageView img_Priority = vJob.findViewById(R.id.img_level);
            TextView tvStatus = vJob.findViewById(R.id.tv_Status);

            String prg = String.valueOf((int) (job.Progress*100));

            tvJobName.setText(job.Name);
            tvDes.setText(job.Description);
            tvEndTime.setText(job.End.toString());
            tvProgress.setText(prg+"%");
            pb_Progress.setProgress((int) (job.Progress*100));
            if(job.Priority==true)
                img_Priority.setImageResource(R.drawable.ic_baseline_star_24);
            else if(job.Priority==false)
                img_Priority.setImageResource(R.drawable.ic_baseline_star_outline_24);
            if(job.Status==0) {
                tvStatus.setTextColor(Color.GREEN);
                tvStatus.setText("On going");
            }
            else if(job.Status == -1){
                tvStatus.setTextColor(Color.rgb(64,64,64));
                tvStatus.setText("Droped");
            }
            else if(job.Status == 1) {
                tvStatus.setTextColor(Color.GREEN);
                tvStatus.setText("Completed");
            }
            else if(job.Status == 2) {
                tvStatus.setTextColor(Color.RED);
                tvStatus.setText("Over time");
            }
            lnMain.addView(vJob);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vJob.getLayoutParams();
            params.bottomMargin = 40;
            vJob.setLayoutParams(params);
        }


    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<JobEnitity> readJob() {
        ArrayList<JobEnitity> listJob = new ArrayList<>();
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now();
        listJob.add(new JobEnitity("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new JobEnitity("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        listJob.add(new JobEnitity("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new JobEnitity("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        listJob.add(new JobEnitity("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new JobEnitity("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        listJob.add(new JobEnitity("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new JobEnitity("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        listJob.add(new JobEnitity("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new JobEnitity("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        return listJob;
    }

    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).gotoAddNewJobScreen();
    }
}
