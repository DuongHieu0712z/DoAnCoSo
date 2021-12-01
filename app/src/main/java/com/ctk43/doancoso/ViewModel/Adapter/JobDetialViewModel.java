package com.ctk43.doancoso.ViewModel.Adapter;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Reponsitory.JobDetailRepository;
import com.ctk43.doancoso.Database.Reponsitory.JobRepository;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

public class JobDetialViewModel extends ViewModel {
    private JobDetailRepository jobDetailRepo;
    private LiveData<List<JobDetail>> jobDetails;

    public JobDetialViewModel() {
        //     allJob = jobRepository.getAllJob();
    }

    public void setData(Context context) {
        jobDetailRepo = new JobDetailRepository(context);
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


