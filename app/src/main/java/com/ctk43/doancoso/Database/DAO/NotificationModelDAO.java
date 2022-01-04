package com.ctk43.doancoso.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.ctk43.doancoso.Database.DateConvertor;
import com.ctk43.doancoso.Model.NotificationModel;

import java.util.Date;
import java.util.List;

@Dao
public interface NotificationModelDAO {

    @Insert
    void insert(NotificationModel... notificationModels);

    @Update
    void update(NotificationModel... notificationModels);

    @Delete
    void delete(NotificationModel... notificationModels);

    @Query("SELECT * FROM NotificationModel ")
    List<NotificationModel> getListAllNotification();

    @Query("SELECT * FROM NotificationModel WHERE statusJob =:statusJob AND status =:status ORDER BY DateOfRecord DESC")
    LiveData<List<NotificationModel>> getListAllNotificationStatus(int statusJob, String status);

    @Query("SELECT * FROM NotificationModel WHERE status =:status ORDER BY DateOfRecord DESC LIMIT 100")
    LiveData<List<NotificationModel>> geListNotificationByStatus(String status);

    @Query("SELECT COUNT(1) FROM NotificationModel WHERE status =:status")
    int geNotificationTotal(String status);

    @Query("SELECT * FROM NotificationModel WHERE ID=:id")
    NotificationModel getNotificationById(int id);

    @Query("SELECT * FROM NotificationModel WHERE JobID=:iobId ORDER BY DateOfRecord DESC LIMIT 1")
    @TypeConverters(DateConvertor.class)
    NotificationModel getNotificationByJobIDNew(int iobId);

    @Query("SELECT COUNT(1) FROM NotificationModel WHERE statusJob =:statusJob AND status =:status")
    int getTotalNotificationStatus(int statusJob, String status);

    @Query("SELECT * FROM NotificationModel ORDER BY DateOfRecord ASC")
    LiveData<List<NotificationModel>> getAllNotification();
}
