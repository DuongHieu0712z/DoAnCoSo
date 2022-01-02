package com.ctk43.doancoso.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

        jobAdapter = new JobAdapter(mContext, jobViewModel);
        if(jobs == null )
            Toast.makeText(mContext,"Đã xảy ra lỗi vui lòng kiểm tra lại",Toast.LENGTH_LONG).show();
        try{
            jobs.observe(requireActivity(), jobs -> {
                jobAdapter.setJob(jobs);
                rcv.setLayoutManager(new LinearLayoutManager(mContext));
                rcv.setAdapter(jobAdapter);
            });
        }catch (Exception e){

        }

        /*btn_Add_New_Job = v.findViewById(R.id.add_new_job);
        btn_Add_New_Job.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, AddJobActivity.class);
            mContext.startActivity(intent);
        });*/
        /*new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                jobViewModel.delete(jobListAdapter.getJobAt(viewHolder.getAdapterPosition()));
                Toast.makeText(mContext,"Xóa xong",Toast.LENGTH_LONG).show();
            }
        }).attachToRecyclerView(rcv);*/
    }
    public JobAdapter getAdapter(){
        if(jobAdapter == null){
            Log.e("JobAdapter", "getAdapter: Bị null" );
            return null;
        }
        Log.e("JobAdapter", "getAdapter: Không null" );
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
