package com.ctk43.doancoso.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ctk43.doancoso.Model.NotificationModel;

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

    @Query("SELECT * FROM NotificationModel ORDER BY DateOfRecord DESC")
    LiveData<List<NotificationModel>> getAllNotification();
}
