package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Repository.NoticationRepository;
import com.ctk43.doancoso.Model.NotificationModel;

import java.util.List;

public class NotificationViewModel extends ViewModel {
    private NoticationRepository noticationRepository;
    private LiveData<List<NotificationModel>> notifications;
    private List<NotificationModel> notificationList;
    Context context;

    public NotificationViewModel() {
    }

    public void setData(Context context) {
        this.context = context;
        noticationRepository = new NoticationRepository(context);
        notifications = noticationRepository.geAllNotification();
        notificationList = noticationRepository.geListNotification();
        
    }

    public LiveData<List<NotificationModel>> getNotifications() {
        return notifications;
    }

    public List<NotificationModel> getNotificationList() {
        return notificationList;
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
