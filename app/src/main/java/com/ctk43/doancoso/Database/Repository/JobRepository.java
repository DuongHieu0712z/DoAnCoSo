package com.ctk43.doancoso.Database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.JobDAO;
import com.ctk43.doancoso.Model.Job;

import java.util.Date;
import java.util.List;

public class JobRepository {
    private final JobDAO jobDAO;
    private final LiveData<List<Job>> jobs;

    public JobRepository(Context context) {
        AppDatabase data = AppDatabase.getInstance(context);
        jobDAO = data.getJobDAO();
        jobs = jobDAO.getAll();
    }

    public LiveData<List<Job>> getJobs() {
        return jobs;
    }

    public LiveData<List<Job>> getJobs(Date endDate) {
        return jobDAO.getJobs(endDate);
    }

    public LiveData<List<Job>> getJobs(Date startDate, Date endDate) {
        return jobDAO.getJobs(startDate, endDate);
    }

    public LiveData<List<Job>> getByCategoryId(int categoryId) {
        return jobDAO.getByCategoryId(categoryId);
    }

    public Job getById(int id) {
        return jobDAO.getById(id);
    }

    public void insert(Job... jobs) {
        new JobInsertAsyncTask(jobDAO).execute(jobs);
    }

    public void update(Job... jobs) {
        new JobUpdateAsyncTask(jobDAO).execute(jobs);
    }

    public void delete(Job... jobs) {
        new JobDeleteAsyncTask(jobDAO).execute(jobs);
    }

    private static class JobInsertAsyncTask extends AsyncTask<Job, Void, Void> {
        private final JobDAO jobDAO;

        private JobInsertAsyncTask(JobDAO jobDAO) {
            super();
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.insert(jobs);
            return null;
        }
    }

    private static class JobUpdateAsyncTask extends AsyncTask<Job, Void, Void> {
        private final JobDAO jobDAO;

        private JobUpdateAsyncTask(JobDAO jobDAO) {
            super();
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.update(jobs);
            return null;
        }
    }

    private static class JobDeleteAsyncTask extends AsyncTask<Job, Void, Void> {
        private final JobDAO jobDAO;

        private JobDeleteAsyncTask(JobDAO jobDAO) {
            super();
            this.jobDAO = jobDAO;
        }

        @Override
        protected Void doInBackground(Job... jobs) {
            jobDAO.delete(jobs);
            return null;
        }
    }
}
