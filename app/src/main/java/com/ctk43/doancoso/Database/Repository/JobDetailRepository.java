package com.ctk43.doancoso.Database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.JobDetailDAO;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

public class JobDetailRepository {
    private final JobDetailDAO jobDetailDAO;
    private final LiveData<List<JobDetail>> jobDetails;

    public JobDetailRepository(Context context, int jobId) {
        AppDatabase data = AppDatabase.getInstance(context);
        jobDetailDAO = data.jobDetailDAO();
        jobDetails = jobDetailDAO.getByJobId(jobId);
    }

    public LiveData<List<JobDetail>> getJobDetails() {
        return jobDetails;
    }

    public void insert(JobDetail... jobDetails) {
        new JobDetailInsertAsyncTask(jobDetailDAO).execute(jobDetails);
    }

    public void update(JobDetail... jobDetails) {
        new JobDetailUpdateAsyncTask(jobDetailDAO).execute(jobDetails);
    }

    public void delete(JobDetail... jobDetails) {
        new JobDetailDeleteAsyncTask(jobDetailDAO).execute(jobDetails);
    }

    private static class JobDetailInsertAsyncTask extends AsyncTask<JobDetail, Void, Void> {
        private final JobDetailDAO jobDetailDAO;

        private JobDetailInsertAsyncTask(JobDetailDAO JobDetailDAO) {
            super();
            this.jobDetailDAO = JobDetailDAO;
        }

        @Override
        protected Void doInBackground(JobDetail... jobDetails) {
            jobDetailDAO.insert(jobDetails);
            return null;
        }
    }

    private static class JobDetailUpdateAsyncTask extends AsyncTask<JobDetail, Void, Void> {
        private final JobDetailDAO jobDetailDAO;

        private JobDetailUpdateAsyncTask(JobDetailDAO JobDetailDAO) {
            super();
            this.jobDetailDAO = JobDetailDAO;
        }

        @Override
        protected Void doInBackground(JobDetail... jobDetails) {
            jobDetailDAO.update(jobDetails);
            return null;
        }
    }

    private static class JobDetailDeleteAsyncTask extends AsyncTask<JobDetail, Void, Void> {
        private final JobDetailDAO jobDetailDAO;

        private JobDetailDeleteAsyncTask(JobDetailDAO JobDetailDAO) {
            super();
            this.jobDetailDAO = JobDetailDAO;
        }

        @Override
        protected Void doInBackground(JobDetail... jobDetails) {
            jobDetailDAO.delete(jobDetails);
            return null;
        }
    }
}
