package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Repository.JobRepository;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;

import java.util.Date;
import java.util.List;

public class JobViewModel extends ViewModel {
    private JobRepository jobRepository;
    private LiveData<List<Job>> jobs;
    Context context;

    public JobViewModel() {
    }

    public void setContext(Context context) {
        this.context = context;
        jobRepository = new JobRepository(context);
        jobs = jobRepository.getJobs();
    }

    public LiveData<List<Job>> getJobs() {
        return jobs;
    }

    public LiveData<List<Job>> getJobs(Date endDate) {
        return jobRepository.getJobs(endDate);
    }

    public LiveData<List<Job>> getJobs(Date startDate, Date endDate) {
        return jobRepository.getJobs(startDate, endDate);
    }

    public LiveData<List<Job>> getByCategoryId(int categoryId) {
        return jobRepository.getByCategoryId(categoryId);
    }

    public Job getById(int id) {
        return jobRepository.getById(id);
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

    public void checkOrUncheck(Job job, boolean check) {
        if (check) {
            job.setProgress(1); // 1 is 100%
        } else {
            job.setProgress(0);
        }
        job.setStatus(Extension.CheckStatus(job));
        update(job);
    }
}
