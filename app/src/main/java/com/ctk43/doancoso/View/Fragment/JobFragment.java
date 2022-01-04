package com.ctk43.doancoso.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.AddJobActivity;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class JobFragment extends Fragment {
    FloatingActionButton btn_Add_New_Job;
    private Context mContext;
    private JobAdapter jobAdapter;
    public RecyclerView rcv;
    private JobViewModel jobViewModel;
    private LiveData<List<Job>> jobs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_jobs, container, false);
    }

    @Override
    public void onAttach( @NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobViewModel = new ViewModelProvider(requireActivity()).get(JobViewModel.class);
        initViews(view);
    }

    private void initViews(View v) {
        rcv = v.findViewById(R.id.rcv_display_job);
        jobViewModel.setData(mContext);
        TextView tv_onGoing = v.findViewById(R.id.tv_ongoing);
        TextView tv_finish = v.findViewById(R.id.tv_finish);
        TextView tv_total = v.findViewById(R.id.tv_total);

        if(jobs == null )
            Toast.makeText(mContext,"Đã xảy ra lỗi vui lòng kiểm tra lại",Toast.LENGTH_LONG).show();
        try{
            jobs.observe(requireActivity(), jobs -> {
                jobAdapter = new JobAdapter(mContext, jobViewModel);
                jobAdapter.setJob(jobs);
                rcv.setLayoutManager(new LinearLayoutManager(mContext));
                rcv.setAdapter(jobAdapter);
                tv_onGoing.setText( String.valueOf(jobAdapter.getNumJob(GeneralData.STATUS_ON_GOING)));
                tv_finish.setText(String.valueOf((jobAdapter.getNumJob(GeneralData.STATUS_FINISH)+jobAdapter.getNumJob(GeneralData.STATUS_FINISH_LATE))));
                tv_total.setText(String.valueOf(jobAdapter.getNumJob(GeneralData.NON_STATUS)));
            });
        }catch (Exception e){

        }


    }
    public JobAdapter getAdapter(){
        if(jobAdapter == null){
            return null;
        }
        return jobAdapter;
    }

    public RecyclerView getRecycleView(){
        return rcv;
    }

    public void Filter(String str){
        jobAdapter.getFilter().filter(str);
    }

    public LiveData<List<Job>> getJobs() {
        return jobs;
    }

    public void setJobs(LiveData<List<Job>> jobs) {
        this.jobs = jobs;
    }
}
