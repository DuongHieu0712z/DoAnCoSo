package com.ctk43.doancoso.Database.Reponsitory;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.JobDetailDAO;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

public class JobDetailRepository {
    private JobDetailDAO jobDetailDAO;
    private MutableLiveData<List<JobDetail>> allJobDetail;

    public JobDetailRepository(Context context,int idJob) {
        AppDatabase data = AppDatabase.getInstance(context);
        jobDetailDAO = data.jobDetailDAO();
        allJobDetail = new MutableLiveData<>();
        allJobDetail.setValue(jobDetailDAO.GetListJobDetail(idJob));
    }

    public void insert(JobDetail jobDetail) {
        new InsertJobDetailAsyncTask(jobDetailDAO).execute(jobDetail);
    }

    public void update(JobDetail jobDetail) {
        new UpdateJobDetailAsyncTask(jobDetailDAO).execute(jobDetail);
    }

    public void Delete(JobDetail jobDetail) {
        new DeleteJobDetailAsyncTask(jobDetailDAO).execute(jobDetail);
    }

    public LiveData<List<JobDetail>> getallJobDetail() {
        return allJobDetail;
    }

//    public LiveData<List<Job>> getallJobD() {
//        return allJobD;
//    }


    private static class InsertJobDetailAsyncTask extends AsyncTask<JobDetail, Void, Void> {
        private JobDetailDAO jobDetailDAO;

        private InsertJobDetailAsyncTask(JobDetailDAO JobDetailDAO) {
            this.jobDetailDAO = JobDetailDAO;
        }

        @Override
        protected Void doInBackground(JobDetail... details) {
            jobDetailDAO.deleteJobDetail(details[0]);
            return null;
        }
    }

    private static class DeleteJobDetailAsyncTask extends AsyncTask<JobDetail, Void, Void> {
        private JobDetailDAO jobDetailDAO;

        private DeleteJobDetailAsyncTask(JobDetailDAO JobDetailDAO) {
            this.jobDetailDAO = JobDetailDAO;
        }

        @Override
        protected Void doInBackground(JobDetail... jobDetails) {
            jobDetailDAO.deleteJobDetail(jobDetails[0]);
            return null;
        }
    }

    private static class UpdateJobDetailAsyncTask extends AsyncTask<JobDetail, Void, Void> {
        private JobDetailDAO jobDetailDAO;

        private UpdateJobDetailAsyncTask(JobDetailDAO JobDetailDAO) {
            this.jobDetailDAO = JobDetailDAO;
        }

        @Override
        protected Void doInBackground(JobDetail... jobDetails) {
            jobDetailDAO.updateJobDetail(jobDetails[0]);
            return null;
        }
    }
}
