package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Repository.JobDetailRepository;
import com.ctk43.doancoso.Database.Repository.JobRepository;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.Calendar;
import java.util.List;

public class JobDetailViewModel extends ViewModel {
    private JobDetailRepository jobDetailRepository;
    private LiveData<List<JobDetail>> jobDetails;

    public JobDetailViewModel() {
    }

    public void setContext(Context context, int jobId) {
        jobDetailRepository = new JobDetailRepository(context, jobId);
        jobDetails = jobDetailRepository.getJobDetails();
    }

    public LiveData<List<JobDetail>> getJobDetails() {
        return jobDetails;
    }

    public void insert(JobDetail... jobDetails) {
        jobDetailRepository.insert(jobDetails);
    }

    public void update(JobDetail... jobDetails) {
        jobDetailRepository.update(jobDetails);
    }

    public void delete(JobDetail... jobDetails) {
        jobDetailRepository.delete(jobDetails);
    }


    public double updateProgress() {
        double before = 0;
        for (JobDetail jobDetail : jobDetails.getValue()) {
            if (jobDetail.getStatus()) {
                ++before;
            }
        }
        double after = jobDetails.getValue().size();
        return before / after;
    }

    public void syncJob(Job job) {
        double curr = updateProgress();
        if (job.getProgress() == 1 && job.getStatus() == 2 && curr != 1.0) {
            for (JobDetail jobDetail : jobDetails.getValue()
            ) {
                jobDetail.setStatus(true);
                jobDetailRepository.update(jobDetail);
            }
        } else if (job.getProgress() == 0 && job.getProgress() != curr && job.getStatus() != 2) {
            for (JobDetail jobDetail : (jobDetails.getValue())
            ) {
                jobDetail.setStatus(false);
                jobDetailRepository.update(jobDetail);
            }
        }
    }
}
