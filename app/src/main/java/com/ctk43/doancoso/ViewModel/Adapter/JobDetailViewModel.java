package com.ctk43.doancoso.ViewModel.Adapter;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Reponsitory.JobDetailRepository;
import com.ctk43.doancoso.Database.Reponsitory.JobRepository;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

public class JobDetailViewModel extends ViewModel {
    private JobDetailRepository jobDetailRepo;
    private LiveData<List<JobDetail>> jobDetails;
    private Job job;

    public JobDetailViewModel() {
        //     allJob = jobRepository.getAllJob();
    }

    public void setData(Context context,Job job) {
        this.job = job;
        jobDetailRepo = new JobDetailRepository(context,job.ID);
        jobDetails = jobDetailRepo.getallJobDetail();
    }

    public void InsertJobDetail(JobDetail jobDetail) {
        jobDetailRepo.insert(jobDetail);
    }

    public void UpdateJobDetail(JobDetail jobDetail) {
        jobDetailRepo.update(jobDetail);
    }

    public void DeleteJobDetail(JobDetail jobDetail) {
        jobDetailRepo.Delete(jobDetail);
    }

    public LiveData<List<JobDetail>> getAllJobDetail() {
        return jobDetails;
    }
}


