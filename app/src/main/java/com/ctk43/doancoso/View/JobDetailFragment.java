package com.ctk43.doancoso.View;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.Adapter.JobDetailAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class JobDetailFragment extends Fragment implements View.OnClickListener {
        private Context mContext;
        FloatingActionButton btn_Add_New_Job_detail;
        private Job job;

        public JobDetailFragment(Job job) {
            this.job = job;
            ArrayList<JobDetail> jobDetails = new ArrayList<>();
            jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
            jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
            jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
            jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
            jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
            jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        job.JobDetails=jobDetails;
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

            btn_Add_New_Job_detail = (FloatingActionButton) v.findViewById(R.id.add_new_job_detail);
            btn_Add_New_Job_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity) getActivity()).gotoAddNewJobScreen();
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
}