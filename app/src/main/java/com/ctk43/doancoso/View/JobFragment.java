package com.ctk43.doancoso.View;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.AddJobActivity;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.View.Adapter.OnItemClickListener;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class JobFragment extends Fragment  {
    FloatingActionButton btn_Add_New_Job;
    private Context mContext;
    private JobAdapter jobListAdapter;
    RecyclerView rcv;
    private JobViewModel jobViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_jobs, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
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
        //    jobListAdapter.setJob((jobViewModel.getJobs().getValue()));
        jobListAdapter = new JobAdapter(mContext, jobViewModel);
        jobViewModel.getJobs().observe(requireActivity(), new Observer<List<Job>>() {
            @Override
            public void onChanged(List<Job> jobs) {
                jobListAdapter.setJob(jobs);
                rcv.setLayoutManager(new LinearLayoutManager(mContext));
                rcv.setAdapter(jobListAdapter);
            }
        });
        btn_Add_New_Job = (FloatingActionButton) v.findViewById(R.id.add_new_job);
        btn_Add_New_Job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddJobActivity.class);
                mContext.startActivity(intent);
            }
        });
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



    private void setPopUpBottomLongClickListener() {

    }



}
