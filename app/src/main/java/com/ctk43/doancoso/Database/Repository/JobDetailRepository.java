package com.ctk43.doancoso.Database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.JobDetailDAO;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

public class JobDetailRepository {
    private  JobDetailDAO jobDetailDAO;
    private  LiveData<List<JobDetail>> jobDetails;
    private  List<JobDetail> listJobDetail;
    private JobDetail jobDetail;

    public JobDetailRepository(Context context, int jobId) {
        AppDatabase data = AppDatabase.getInstance(context);
        jobDetailDAO = data.getJobDetailDAO();
        jobDetails = jobDetailDAO.getByJobId(jobId);
        listJobDetail = jobDetailDAO.getListByJobId(jobId);
    }
    public JobDetailRepository(Context context) {
        AppDatabase data = AppDatabase.getInstance(context);
        jobDetailDAO = data.getJobDetailDAO();
    }

    public LiveData<List<JobDetail>> getJobDetails() {
        return jobDetails;
    }

    public List<JobDetail> getListJobDetail() {
        return listJobDetail;
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
