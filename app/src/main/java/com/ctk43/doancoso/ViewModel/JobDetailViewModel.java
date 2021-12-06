package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Repository.JobDetailRepository;
import com.ctk43.doancoso.Database.Repository.JobRepository;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

public class JobDetailViewModel extends ViewModel {
    private JobDetailRepository jobDetailRepository;
    private LiveData<List<JobDetail>> jobDetails;
    private Job job;
<<<<<<< HEAD
    private JobRepository jobRepository;
    public JobDetailViewModel() {
    }

    public void setContext(Context context, int jobId) {
        jobRepository = new JobRepository(context);
        this.job = jobRepository.getById(jobId);
        jobDetailRepository = new JobDetailRepository(context, jobId);
        jobDetails = jobDetailRepository.getJobDetails();
    }

    public LiveData<List<JobDetail>> getJobDetails() {
        return jobDetails;
    }

    public void insert(JobDetail... jobDetails) {
        jobDetailRepository.insert(jobDetails);
        updateProgress();
    }

=======

    public JobDetailViewModel() {
    }

    public void setContext(Context context, @NonNull Job job) {
        this.job = job;
        jobDetailRepository = new JobDetailRepository(context, job.getId());
        jobDetails = jobDetailRepository.getJobDetails();
    }

    public void setContext(Context context, int jobId) {
        this.job = new JobRepository(context).getById(jobId);
        jobDetailRepository = new JobDetailRepository(context, jobId);
        jobDetails = jobDetailRepository.getJobDetails();
    }

    public LiveData<List<JobDetail>> getJobDetails() {
        return jobDetails;
    }

    public void insert(JobDetail... jobDetails) {
        jobDetailRepository.insert(jobDetails);
        updateProgress();
    }

>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
    public void update(JobDetail... jobDetails) {
        jobDetailRepository.update(jobDetails);
        updateProgress();
    }

    public void delete(JobDetail... jobDetails) {
        jobDetailRepository.delete(jobDetails);
        updateProgress();
<<<<<<< HEAD
    }
    public Job getJob(){
        return job;
=======
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
    }

    private void updateProgress() {
        double before = 0;
        for (JobDetail jobDetail : jobDetails.getValue()) {
            if (jobDetail.isPriority()) {
                ++before;
            }
        }
        double after = jobDetails.getValue().size();
        job.setProgress(before / after);
<<<<<<< HEAD
        jobRepository.update(job);
=======
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
    }
}
