package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ctk43.doancoso.Database.Reponsitory.CategoryRepository;
import com.ctk43.doancoso.Database.Reponsitory.JobDetailRepository;
import com.ctk43.doancoso.Database.Reponsitory.JobRepository;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

public class JobDetailViewModel extends ViewModel {
    private JobDetailRepository jobDetailRepo;
    JobViewModel jobViewModel;

    private LiveData<List<JobDetail>> jobDetails;

    private Job job;

    public JobDetailViewModel () {

    }

    public void setData(Context context,Job job) {
        this.job = job;
        jobDetailRepo = new JobDetailRepository(context,job.ID);
        jobDetails = jobDetailRepo.getallJobDetail();


    }


    public void InsertJobDetail(JobDetail jobDetail) {
        jobDetailRepo.insert(jobDetail);
        jobDetails.getValue().add(jobDetail);
        UpdateJob();
    }

    public void UpdateJobDetail(JobDetail jobDetail) {
        jobDetailRepo.update(jobDetail);
        for (JobDetail mjobDetail : jobDetails.getValue()
             ) {
            if(jobDetail.ID == mjobDetail.ID)
                mjobDetail = jobDetail;
        }
        UpdateJob();
    }

    public void DeleteJobDetail(JobDetail jobDetail) {

        jobDetailRepo.Delete(jobDetail);
        jobDetails.getValue().remove(jobDetail);
        UpdateJob();
    }

    public LiveData<List<JobDetail>> getAllJobDetail() {
        return jobDetails;
    }
    public void UpdateJob(){
        double before =0;
        for (JobDetail jobDetail: jobDetails.getValue()
             ) {
            if(jobDetail.Priority)
                before++;
        }
        double after  = jobDetails.getValue().size();
        job.Progress = before / after;
    }
}


