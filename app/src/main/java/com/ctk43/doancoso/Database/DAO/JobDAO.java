package com.ctk43.doancoso.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface JobDAO {
    @Query("SELECT * FROM Job")
    List<Job> getAllJobList();

    @Insert
    void insertJob(Job... jobs);

    @Query("SELECT * FROM Job WHERE ID = :Id ")
    Job getJobFormID(Integer Id);

    @Update
    void updateJob(Job job);

    @Delete
    void deleteJob(Job Job);

    @Query("SELECT * FROM job where CategoryID = :catID")
    List<Job> getAllJobs(int catID);
}
