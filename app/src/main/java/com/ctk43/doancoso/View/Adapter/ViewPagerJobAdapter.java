package com.ctk43.doancoso.View.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.View.Fragment.JobFragment;

import java.util.List;

public class ViewPagerJobAdapter extends FragmentStateAdapter {
    public JobFragment jobFragment = new JobFragment();

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
            case 0:
                jobFragment = new JobFragment();
                jobFragment.setJobs(jobsAgo);
                return jobFragment;
            case 1:
                jobFragment = new JobFragment();
                jobFragment.setJobs(jobsCurr);
               return jobFragment;
            case 2:
                jobFragment = new JobFragment();
                jobFragment.setJobs(jobsNext);
                return jobFragment;
            default:
                return jobFragment = new JobFragment();
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
        this.jobsAgo = jobs;
    }

    /*public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new JobFragment();
            default:
                return  new JobFragment();
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Công Việc";
                break;
        }
        return title;
    }*/
}
