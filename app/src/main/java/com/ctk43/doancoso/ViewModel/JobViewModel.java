package com.ctk43.doancoso.ViewModel;

import android.content.Context;
import android.content.Intent;
import android.security.keystore.StrongBoxUnavailableException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Repository.JobDetailRepository;
import com.ctk43.doancoso.Database.Repository.JobRepository;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.Service.NotificationService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JobViewModel extends ViewModel {
    private JobRepository jobRepository;
    private LiveData<List<Job>> jobs;
    Context context;

    public JobViewModel() {
    }

    public void setData(Context context) {
        this.context = context;
        jobRepository = new JobRepository(context);
        jobs = jobRepository.getJobs();
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

    public Job getJobById(int Id){
        return jobRepository.getById(Id);
    }

    public LiveData<List<Job>> getJobs() {
        return jobs;
    }

    public List<Job> getJobsInDay(Date date){
        return jobRepository.getJobAboutTime(Extension.getStartOfDate(date),Extension.getEndOfDate(date));
    }

    public List<Job> getJobsMoth(int month,int year){
        return jobRepository.getJobAboutTime(Extension.getDateStartOfMonth(month,year),Extension.getDateEndOfMonth(month,year));
    }

    public List<Job> getJobsByCategory(int id){
        return jobRepository.getJobByCategory(id);
    }

    public int sumStatus(int status){
        return jobRepository.getSumRow(status);
    }

    public void getJobBy(Job... jobs) {
        jobRepository.update(jobs);
    }

    public List<Job> getListByStatus(int status){
        return jobRepository.getJobByStatus(status);
    }

    public void checkOrUncheck(Job job,boolean check){
        if(check){
            job.setProgress(1); // 1 is 100%
            job.setStatus(Extension.CheckStatus(job));
        }else{
            job.setProgress(0);
            job.setStatus(Extension.CheckStatus(job));
        }
        update(job);
    }

    private void startService(){
        Intent intent = new Intent(context, NotificationService.class);
        context.startService(intent);
    }
}
