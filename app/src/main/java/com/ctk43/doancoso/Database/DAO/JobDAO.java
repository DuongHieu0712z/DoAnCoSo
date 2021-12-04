package com.ctk43.doancoso.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ctk43.doancoso.Model.Job;

import java.util.List;

@Dao
public interface JobDAO {
    @Query("SELECT * FROM Job")
    LiveData<List<Job>> getAll();

    @Query("SELECT * FROM Job WHERE ID = :id")
    Job getById(int id);

    @Query("SELECT * FROM Job WHERE CategoryID = :categoryId")
    List<Job> getByCategoryId(int categoryId);

    @Insert
    void insert(Job... jobs);

    @Update
    void update(Job... jobs);

    @Delete
    void delete(Job... jobs);
}
