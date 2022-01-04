package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Repository.NoticationRepository;
import com.ctk43.doancoso.Model.NotificationModel;

import java.util.Date;
import java.util.List;

public class NotificationViewModel extends ViewModel {
    private NoticationRepository noticationRepository;
    Context context;

    public NotificationViewModel() {
    }

    public void setData(Context context) {
        this.context = context;
        noticationRepository = new NoticationRepository(context);
    }

    public LiveData<List<NotificationModel>> getNotifications() {
        return noticationRepository.geAllNotification();
    }

    public NotificationModel getNotificationByJobIDNew(int jobId) {
        return noticationRepository.getNotificationByJobIDNew(jobId);
    }

    public int geNotificationTotal(String status) {
        return noticationRepository.geNotificationTotal(status);
    }

    public NotificationModel getNotificationById(int id) {
        return noticationRepository.getNotificationById(id);
    }

    public LiveData<List<NotificationModel>> getNotificationsStatus(int statusJob, String status) {
        return noticationRepository.geListNotificationStatus(statusJob, status);
    }

    public LiveData<List<NotificationModel>> geListNotificationByStatus(String status) {
        return noticationRepository.geListNotificationByStatus(status);
    }

    public int getTotalNotificationStatus(int statusJob, String string) {
        return noticationRepository.getTotalNotificationStatus(statusJob, string);
    }

    public void insert(NotificationModel... notificationModels) {
        noticationRepository.insert(notificationModels);
    }

    public void update(NotificationModel... notificationModels) {
        noticationRepository.update(notificationModels);
    }

    public void delete(NotificationModel... notificationModels) {
        noticationRepository.delete(notificationModels);
    }

}
