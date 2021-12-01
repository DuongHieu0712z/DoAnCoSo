package com.ctk43.doancoso.View;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Adapter.JobDetailAdapter;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class JobDetailFragment extends Fragment implements View.OnClickListener {
    private Context mContext;
    FloatingActionButton btn_Add_New_Job_detail;
    private JobDetailViewModel jobDetailViewModel;
    private Job job;

    public JobDetailFragment(Job job) {
        this.job = job;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_job_detail, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobDetailViewModel = new ViewModelProvider(requireActivity()).get(JobDetailViewModel.class);
        initViews(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }


    private void initViews(View v) {
        RecyclerView recyclerView = v.findViewById(R.id.rcv_job_detail);
        JobDetailAdapter adapter = new JobDetailAdapter(mContext,jobDetailViewModel);
        jobDetailViewModel.setData(mContext,job);
        jobDetailViewModel.getAllJobDetail().observe(requireActivity(), new Observer<List<JobDetail>>() {
            @Override
            public void onChanged(List<JobDetail> jobDetails) {
                adapter.setData(jobDetails);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        TextView tv_job_name = v.findViewById(R.id.tv_jt_job_name);
        tv_job_name.setText(job.Name);

        TextView tv_job_des = v.findViewById(R.id.tv_jt_description);
        tv_job_des.setText(job.Description);

        TextView tv_job_start = v.findViewById(R.id.tv_jt_time_start);
        tv_job_start.setText(job.StartDate.toString());

        TextView tv_job_end = v.findViewById(R.id.tv_jt_time_end);
        tv_job_end.setText(job.EndDate.toString());

        TextView tv_job_progress = v.findViewById(R.id.tv_jt_prg);
         double prg = job.Progress*100;

        tv_job_progress.setText(String.valueOf((int)prg)+"%");
        SeekBar sb = v.findViewById(R.id.sb_jt_progress);

        sb.setProgress((int) prg);
        btn_Add_New_Job_detail = (FloatingActionButton) v.findViewById(R.id.add_new_job_detail);
        AddJobDetail();


        ImageView img_back = v.findViewById(R.id.img_jt_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).gotoM001Screen();
            }
        });



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
    private void AddJobDetail(){

        btn_Add_New_Job_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity) getActivity()).gotoAddNewJobScreen();
                Intent intent = new Intent(mContext, AddJobActivity.class);
                mContext.startActivity(intent);
            }
        });

    }
}