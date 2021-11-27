package com.ctk43.doancoso.View;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Database.Database;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.Adapter.JobDetailAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class JobDetailFragment extends Fragment implements View.OnClickListener {
<<<<<<< HEAD
        private Context mContext;
        FloatingActionButton btn_Add_New_Job_detail;
        private Job job;

        public JobDetailFragment(Job job) {
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
            tvStart.setText(job.StartDate.toString());
            tvEnd.setText(job.EndDate.toString());
            sb_job_progress.setProgress((int)(job.Progress*100));
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).gotoM001Screen();
                }
            });



            List<JobDetail> listJobDetail = Database.getInstance().jobDetails;

            //lnMain.removeAllViews();
            if(!listJobDetail.isEmpty()) {
                for (JobDetail jobDetail : listJobDetail) {
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
=======
    private Context mContext;
    FloatingActionButton btn_Add_New_Job_detail;
    private Job job;

    public JobDetailFragment(Job job) {
        this.job = job;
        ArrayList<JobDetail> jobDetails = new ArrayList<>();
        jobDetails.add(new JobDetail("Job detail name333", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        //job.JobDetails=jobDetails;
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
    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initViews(View v) {
        TextView tv_job_name = v.findViewById(R.id.tv_jt_job_name);
        tv_job_name.setText(job.Name);
        TextView tv_job_des = v.findViewById(R.id.tv_jt_description);
        tv_job_des.setText(job.Description);
        TextView tv_job_start = v.findViewById(R.id.tv_jt_time_start);
        tv_job_start.setText(job.Start.toString());
        TextView tv_job_end = v.findViewById(R.id.tv_jt_time_end);
        tv_job_end.setText(job.End.toString());
        TextView tv_job_progress = v.findViewById(R.id.tv_jt_prg);
         double prg = job.Progress*100;
        tv_job_progress.setText(String.valueOf((int)prg)+"%");
        SeekBar sb = v.findViewById(R.id.sb_jt_progress);
        sb.setProgress((int) prg);

        btn_Add_New_Job_detail = (FloatingActionButton) v.findViewById(R.id.add_new_job_detail);
        btn_Add_New_Job_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity) getActivity()).gotoAddNewJobScreen();
                Intent intent = new Intent(mContext, AddJobActivity.class);
                mContext.startActivity(intent);
>>>>>>> ca30ae89b3add29b618d9bec225a2dd4a62a1703
            }
        });
        ImageView img_back = v.findViewById(R.id.img_jt_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).gotoM001Screen();
            }
        });

        ArrayList<JobDetail> listJobDetail = job.JobDetails;
        RecyclerView rcv= v.findViewById(R.id.rcv_job_detail);
        JobDetailAdapter adapter = new JobDetailAdapter(listJobDetail, mContext);
        rcv.setAdapter(adapter);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));
    }
    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).gotoAddNewJobScreen();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}