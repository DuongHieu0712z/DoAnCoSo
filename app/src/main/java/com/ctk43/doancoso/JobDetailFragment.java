package com.ctk43.doancoso;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class JobDetailFragment extends Fragment implements View.OnClickListener{
    private Context mContext;
    FloatingActionButton btn_Add_New_Job_detail;
    private JobEnitity job;

    public JobDetailFragment(JobEnitity job) {
        this.job = job;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_job_detail, container, false);
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
        LinearLayout lnMain = v.findViewById(R.id.ln_job_detail);
        lnMain.removeAllViews();
        btn_Add_New_Job_detail = (FloatingActionButton) v.findViewById(R.id.add_new_job_detail);
        btn_Add_New_Job_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).gotoAddNewJobScreen();
            }
        });

        TextView tvJtName = v.findViewById(R.id.tv_jt_job_name);
        TextView tvJtDes = v.findViewById(R.id.tv_jt_description);
        TextView tvStart = v.findViewById(R.id.tv_jt_time_start);
        TextView tvEnd = v.findViewById(R.id.tv_jt_time_end);
        SeekBar sb_job_progress =  v.findViewById(R.id.sb_jt_progress);
        ImageView img_back = v.findViewById(R.id.img_jt_back);

         tvJtName.setText(job.Name);
         tvJtDes.setText(job.Description);
         tvStart.setText(job.Start.toString());
         tvEnd.setText(job.End.toString());
         sb_job_progress.setProgress((int)(job.Progress*100));
         img_back.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 ((MainActivity) getActivity()).gotoM001Screen();
             }
         });



        ArrayList<JobDetailEnitity> listJobDetail = job.JobDetails;

        //lnMain.removeAllViews();
        if(!listJobDetail.isEmpty()) {
            for (JobDetailEnitity jobDetail : listJobDetail) {
                View vJobDetail = LayoutInflater.from(mContext).inflate(R.layout.job_detail_item, null);
                TextView tvJdName = vJobDetail.findViewById(R.id.tv_jd_name);
                TextView tvJdDes = vJobDetail.findViewById(R.id.tv_jd_description);
                TextView tvEstimatedTime = vJobDetail.findViewById(R.id.tv_jd_estimated_time);
                TextView tvActualTime = vJobDetail.findViewById(R.id.tv_jd_actual_time);
                SeekBar sb_Progress = vJobDetail.findViewById(R.id.sb_jd_progress);
                ImageView img_Priority = vJobDetail.findViewById(R.id.img_jd_level);
                CheckBox cb_status = vJobDetail.findViewById(R.id.chk_status);

                //String prg = String.valueOf((int) (job.Progress*100));

                tvJdName.setText(jobDetail.Name);
                tvJdDes.setText(jobDetail.Description);
                tvEstimatedTime.setText(String.valueOf(jobDetail.EstimatedCompletedTime));
                tvActualTime.setText(String.valueOf(jobDetail.ActualCompletedTime));
                sb_Progress.setProgress((int) (jobDetail.Progress * 100));

                if (jobDetail.Priority == true)
                    img_Priority.setImageResource(R.drawable.ic_baseline_star_24);
                else if (jobDetail.Priority == false)
                    img_Priority.setImageResource(R.drawable.ic_baseline_star_outline_24);

                if (jobDetail.Status == 1) cb_status.setChecked(true);
                else if (jobDetail.Status != 1) cb_status.setChecked(false);

                lnMain.addView(vJobDetail);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) vJobDetail.getLayoutParams();
                params.topMargin = 10;
                vJobDetail.setLayoutParams(params);
            }
        }

    }
    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).gotoAddNewJobScreen();
    }
}