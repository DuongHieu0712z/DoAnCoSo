package com.ctk43.doancoso.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface JobDetailDAO {
    @Query("SELECT * FROM JobDetail")
    List<JobDetail> getAllJobDetailList();

    @Insert
    void insertJobDetail(JobDetail... jobDetails);

    @Update
    void updateJobDetail(JobDetail jobDetail);

    @Delete
    void deleteJobDetail(JobDetail jobDetail);

    @Query("SELECT * FROM jobdetail where ID = :jobID")
    List<JobDetail> GetListJobDetail(int jobID);
}
