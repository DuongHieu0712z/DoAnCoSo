package com.ctk43.doancoso.Database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.NotificationModelDAO;
import com.ctk43.doancoso.Database.DAO.NotificationModelDAO;
import com.ctk43.doancoso.Model.NotificationModel;
import com.ctk43.doancoso.Model.NotificationModel;

import java.util.List;

public class NoticationRepository {
    NotificationModelDAO notificationModelDAO;
    LiveData<List<NotificationModel>> notifications;
    List<NotificationModel> notificationList;

    public NoticationRepository(Context context) {
        AppDatabase data = AppDatabase.getInstance(context);
        notificationModelDAO = data.getNotificationModelDAO();
        notifications = notificationModelDAO.getAllNotification();
        notificationList = notificationModelDAO.getListAllNotification();
    }

    public LiveData<List<NotificationModel>> geAllNotification(){
        return notifications;
    }
    public List<NotificationModel> geListNotification(){
        return notificationList;
    }

    public void insert(NotificationModel... notifications) {
        new NoticationRepository.NotificationModelInsertAsyncTask(notificationModelDAO).execute(notifications);
    }

    public void update(NotificationModel... notifications) {
        new NoticationRepository.NotificationModelUpdateAsyncTask(notificationModelDAO).execute(notifications);
    }

    public void delete(NotificationModel... notifications) {
        new NoticationRepository.NotificationModelDeleteAsyncTask(notificationModelDAO).execute(notifications);
    }

    private static class NotificationModelInsertAsyncTask extends AsyncTask<NotificationModel, Void, Void> {
        private final NotificationModelDAO NotificationModelDAO;

        private NotificationModelInsertAsyncTask(NotificationModelDAO NotificationModelDAO) {
            super();
            this.NotificationModelDAO = NotificationModelDAO;
        }

        @Override
        protected Void doInBackground(NotificationModel... notifications) {
            NotificationModelDAO.insert(notifications);
            return null;
        }
    }

    private static class NotificationModelUpdateAsyncTask extends AsyncTask<NotificationModel, Void, Void> {
        private final NotificationModelDAO NotificationModelDAO;

        private NotificationModelUpdateAsyncTask(NotificationModelDAO NotificationModelDAO) {
            super();
            this.NotificationModelDAO = NotificationModelDAO;
        }

        @Override
        protected Void doInBackground(NotificationModel... notifications) {
            NotificationModelDAO.update(notifications);
            return null;
        }
    }

    private static class NotificationModelDeleteAsyncTask extends AsyncTask<NotificationModel, Void, Void> {
        private final NotificationModelDAO NotificationModelDAO;

        private NotificationModelDeleteAsyncTask(NotificationModelDAO NotificationModelDAO) {
            super();
            this.NotificationModelDAO = NotificationModelDAO;
        }

        @Override
        protected Void doInBackground(NotificationModel... notifications) {
            NotificationModelDAO.delete(notifications);
            return null;
        }
    }
}


