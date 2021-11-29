package com.ctk43.doancoso.Database;

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

//    @Query("SELECT * FROM Job")
//    LiveData<List<Job>> getAllJobList();

    @Insert
    void insertJob(Job...jobs);

    @Update
    void updateJob(Job job);

    @Delete
    void deleteJob(Job Job);

    @Query("SELECT * FROM job where CategoryID = :catID")
<<<<<<< HEAD
    LiveData<List<Job>> getAllJobs(int catID);




=======
    List<Job> getAllJobs(int catID);
>>>>>>> c73e58aa893e6c809d4afaaba9c88be8f71da0b5
}
