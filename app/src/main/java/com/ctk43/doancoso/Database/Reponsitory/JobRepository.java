package com.ctk43.doancoso.Database.Reponsitory;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.JobDAO;
import com.ctk43.doancoso.Model.Job;

import java.util.List;

public class JobRepository {
    private JobDAO jobDAO;
    private LiveData<List<Job>> allJob;

    public JobRepository(Context context) {
        AppDatabase data = AppDatabase.getInstance(context);
        jobDAO = data.getJobDAO();
        allJob = new MutableLiveData<>();
        allJob = (jobDAO.getAll());
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

    public LiveData<List<Job>> getAllJob() {
        return allJob;
    }
    public Job getJobFormID(int id) {
        return jobDAO.getById(id);
    }

    private static class InsertJobAsyncTask extends AsyncTask<Job, Void, Void> {
        private JobDAO jobDAO;

        private InsertJobAsyncTask(JobDAO jobDAO) {
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.insert(jobs[0]);
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
            jobDAO.delete(jobs[0]);
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
            jobDAO.update(jobs[0]);
            return null;
        }
    }
}
