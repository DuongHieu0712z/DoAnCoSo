package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Repository.JobRepository;
import com.ctk43.doancoso.Model.Job;

import java.util.List;

public class JobViewModel extends ViewModel {
    private JobRepository jobRepository;
    private LiveData<List<Job>> jobs;

    public JobViewModel() {
    }

    public void setData(Context context) {
        jobRepository = new JobRepository(context);
        jobs = jobRepository.getJobs();
    }

    public LiveData<List<Job>> getJobs() {
        return jobs;
    }

    public void insert(Job... jobs) {
        jobRepository.insert(jobs);
    }

    public void update(Job... jobs) {
        jobRepository.update(jobs);
    }

    public void delete(Job... jobs) {
        jobRepository.delete(jobs);
    }
}
