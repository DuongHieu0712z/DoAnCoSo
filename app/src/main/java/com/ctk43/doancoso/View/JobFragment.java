package com.ctk43.doancoso.View;

import android.content.Context;

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

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.ctk43.doancoso.Model.Database;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

        ArrayList<Job> listJob = Database.getInstance().GetCurr_Week();

        //lnMain.removeAllViews();
        for (Job job:listJob) {
            View vJob = LayoutInflater.from(mContext).inflate(R.layout.job_item, null);
            TextView tvJobName = vJob.findViewById(R.id.tv_job_name);
            TextView tvDes = vJob.findViewById(R.id.tv_job_description);
            TextView tvEndTime = vJob.findViewById(R.id.tv_end_time);
            TextView tvProgress = vJob.findViewById(R.id.tv_progress);
            ProgressBar pb_Progress = vJob.findViewById(R.id.prg_progress);
            ImageView img_Priority = vJob.findViewById(R.id.img_level);
            img_Priority.bringToFront();
            TextView tvStatus = vJob.findViewById(R.id.tv_Status);
            String prg = String.valueOf((int) (job.Progress*100));
            tvJobName.setText(job.Name);
            tvDes.setText(job.Description);
            tvEndTime.setText(job.End.toString());
            tvProgress.setText(prg+"%");
            pb_Progress.setProgress((int) (job.Progress*100));
            /*if(job.Progress>=0.5)
            {
                pb_Progress.setIndeterminate(false);
                pb_Progress.setIndeterminateTintList(ColorStateList.valueOf(Color.GREEN));
                pb_Progress.setProgressDrawable(getResources().getDrawable(R.drawable.prg_circle));
            }
            else if(job.Progress<50)
            {
                pb_Progress.setIndeterminate(false);
                pb_Progress.setIndeterminateTintList(ColorStateList.valueOf(Color.RED));
                pb_Progress.setProgressDrawable(getResources().getDrawable(R.drawable.prg_circle2));
            }*/
            if(job.Priority==true)
                img_Priority.setImageResource(R.drawable.ic_baseline_star_24);
            else if(job.Priority==false)
                img_Priority.setImageResource(R.drawable.ic_baseline_star_outline_24);
            if(job.Status==0) {
                tvStatus.setTextColor(Color.rgb(154,183,114));
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
            vJob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).gotoM002Screen(job);
                }
            });
            lnMain.addView(vJob);

            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vJob.getLayoutParams();
            params.topMargin = 10;
            vJob.setLayoutParams(params);
        }


    }
  /*  //fake data
    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<Job> readJob() {
        ArrayList<Job> listJob = new ArrayList<>();
        Date start = Calendar.getInstance().getTime();
        Date end = Calendar.getInstance().getTime();
        listJob.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        listJob.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        listJob.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        listJob.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        listJob.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        listJob.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 0.4));
        listJob.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.2));

        ArrayList<JobDetail> jobDetails = new ArrayList<>();
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));

        listJob.get(0).JobDetails = jobDetails;
        return  listJob;
    }*/

    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).gotoAddNewJobScreen();
    }
}
