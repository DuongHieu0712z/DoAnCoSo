package com.ctk43.doancoso.View.Adapter;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ctk43.doancoso.Library.KeyFragment;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.View.Fragment.JobFragment;

import java.util.HashMap;
import java.util.List;

public class ViewPagerJobAdapter extends FragmentStateAdapter {
    private JobFragment jobFragment;
    private HashMap<Integer, Fragment> hashMap = new HashMap<>();
    LiveData<List<Job>> jobsCurr;
    LiveData<List<Job>> jobsAgo;
    LiveData<List<Job>> jobsNext;
    public ViewPagerJobAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {

            case KeyFragment.MANAGE_JOBS_CURR:
                jobFragment = new JobFragment();
                jobFragment.setJobs(jobsCurr);
                hashMap.put(position, jobFragment);
               return jobFragment;
            case KeyFragment.MANAGE_JOBS_NEXT:
                jobFragment = new JobFragment();
                jobFragment.setJobs(jobsNext);
                hashMap.put(position, jobFragment);
                return jobFragment;
            case KeyFragment.MANAGE_JOBS_PREVIOUS:
            default:
                jobFragment = new JobFragment();
                jobFragment.setJobs(jobsAgo);
                hashMap.put(position, jobFragment);
                return jobFragment;
        }

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public void setJobs(LiveData<List<Job>>jobsAgo, LiveData<List<Job>>jobsCurr, LiveData<List<Job>>jobsNext){
        this.jobsAgo = jobsAgo;
        this.jobsCurr = jobsCurr;
        this.jobsNext = jobsNext;
    }
    public void setJobs(LiveData<List<Job>> jobs){
        jobsAgo = jobs;
    }

    public HashMap<Integer, Fragment> getHashMap() {
        return hashMap;
    }

}
