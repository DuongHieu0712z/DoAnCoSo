package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Reponsitory.JobRepository;
import com.ctk43.doancoso.Model.Job;

import java.util.List;

public class JobViewModel extends ViewModel {
    private JobRepository jobRepository;
    private LiveData<List<Job>> jobs;

    public JobViewModel() {
   //     jobRepository =new JobRepository(context);
   //     allJob = jobRepository.getAllJob();
    }
    public void setData(Context context){
        jobRepository =new JobRepository(context);
        jobs =jobRepository.getAllJob();
    }
    public void InsertJob(Job job){
        jobRepository.insert(job);
    }
    public void UpdateJob(Job job){
        jobRepository.update(job);
    }
    public void DeleteJob(Job job){
        jobRepository.Delete(job);
    }
    public LiveData<List<Job>> getAllJob(){
        return  jobs;
    }
/*    private MutableLiveData<List<Job>> listOfJob;
    private AppDatabase appDatabase;


    public MutableLiveData<List<Job>> getJobListObserver(){
        return listOfJob;
    }
    public void GetAllJobList(){
        List<Job> jobList= appDatabase.jobDAO().getAllJobList();
        if(jobList.size() >0)
        {
            listOfJob.postValue(jobList);
        }else{
            listOfJob.postValue(null);
        }
    }*/
 /*   public void insertJob(int CategoryId, String Name, Date StartDate,Date EndDate, String Description, double Progress,int Status){
        Job job = new Job();
        job.CategoryID = CategoryId;
        job.Name = Name;
        job.StartDate = StartDate;
        job.EndDate = EndDate;
        job.Description = Description;
        job.Progress = Progress;
        job.Status = Status;
        GetAllJobList();
    }*/


   /* public void UpdateJob(Job job){
        appDatabase.jobDAO().updateJob(job);
    }
    public void DeleteJob(Job job){
        appDatabase.jobDAO().deleteJob(job);
    }*/
}
