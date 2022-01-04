package com.ctk43.doancoso.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

@Dao
public interface JobDetailDAO {
    @Query("SELECT * FROM JobDetail")
    LiveData<List<JobDetail>> getAll();

    @Query("SELECT * FROM JobDetail WHERE JobID = :jobId ORDER BY Status,Priority DESC")
    LiveData<List<JobDetail>> getByJobId(int jobId);

    @Query("SELECT * FROM JobDetail WHERE JobID = :jobId")
    List<JobDetail> getListByJobId(int jobId);

    @Query("SELECT * FROM JobDetail WHERE ID = :Id")
    JobDetail getById(int Id);

    @Insert
    void insert(JobDetail... jobDetails);

    @Update
    void update(JobDetail... jobDetails);

    @Delete
    void delete(JobDetail... jobDetails);
}
