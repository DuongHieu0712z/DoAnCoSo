package com.ctk43.doancoso;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class JobFragment extends Fragment {
    private Context mContext;
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
        return listJob;
    }
}
