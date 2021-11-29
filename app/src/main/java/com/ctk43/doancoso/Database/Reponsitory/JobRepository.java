package com.ctk43.doancoso.Database.Reponsitory;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
<<<<<<< HEAD
import androidx.lifecycle.MutableLiveData;
=======
>>>>>>> c73e58aa893e6c809d4afaaba9c88be8f71da0b5

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.JobDAO;
import com.ctk43.doancoso.Model.Job;

import java.util.List;

public class JobRepository {
    private JobDAO jobDAO;
<<<<<<< HEAD
    private MutableLiveData<List<Job>> allJob;
    public JobRepository(Context context){
        AppDatabase data = AppDatabase.getInstance(context);
        jobDAO = data.jobDAO();
        allJob = new MutableLiveData<>();
        allJob.setValue(jobDAO.getAllJobList());
=======
//    private LiveData<List<Job>> allJob;
    private List<Job> allJob;

    public JobRepository(Context context) {
        AppDatabase data = AppDatabase.getInstance(context);
        jobDAO = data.jobDAO();
        allJob = jobDAO.getAllJobList();
>>>>>>> c73e58aa893e6c809d4afaaba9c88be8f71da0b5
    }

    public void insert(Job job) {
        new InsertJobAsyncTask(jobDAO).execute(job);
    }

    public void update(Job job) {
        new UpdateJobAsyncTask(jobDAO).execute(job);
    }

    public void Delete(Job job) {
        new DeleteJobAsyncTask(jobDAO).execute(job);
    }
<<<<<<< HEAD
    public LiveData<List<Job>> getAlljob(){
=======

    public List<Job> getAlljob() {
>>>>>>> c73e58aa893e6c809d4afaaba9c88be8f71da0b5
        return allJob;
    }

//    public LiveData<List<Job>> getAlljob() {
//        return allJob;
//    }

    private static class InsertJobAsyncTask extends AsyncTask<Job, Void, Void> {
        private JobDAO jobDAO;

        private InsertJobAsyncTask(JobDAO jobDAO) {
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.insertJob(jobs[0]);
            return null;
        }
    }

    private static class DeleteJobAsyncTask extends AsyncTask<Job, Void, Void> {
        private JobDAO jobDAO;

        private DeleteJobAsyncTask(JobDAO jobDAO) {
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.deleteJob(jobs[0]);
            return null;
        }
    }

    private static class UpdateJobAsyncTask extends AsyncTask<Job, Void, Void> {
        private JobDAO jobDAO;

        private UpdateJobAsyncTask(JobDAO jobDAO) {
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.updateJob(jobs[0]);
            return null;
        }
    }

}
