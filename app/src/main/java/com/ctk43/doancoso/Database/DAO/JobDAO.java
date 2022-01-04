package com.ctk43.doancoso.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.ctk43.doancoso.Database.DateConvertor;
import com.ctk43.doancoso.Model.Job;

import java.util.Date;
import java.util.List;

@Dao
public interface JobDAO {
    @Query("SELECT * FROM Job")
    LiveData<List<Job>> getAll();

    @Query("SELECT * FROM Job WHERE EndDate = :endDate")
    @TypeConverters(DateConvertor.class)
    LiveData<List<Job>> getJobs(Date endDate);

    @Query("SELECT * FROM Job WHERE EndDate >= :startDate AND EndDate <= :endDate")
    @TypeConverters(DateConvertor.class)
    LiveData<List<Job>> getJobs(Date startDate, Date endDate);

    @Query("SELECT * FROM Job WHERE ID = :id")
    Job getById(int id);

    @Query("SELECT Name FROM Job WHERE ID = :id")
    String getNameJob(int id);

    @Query("SELECT * FROM Job WHERE CategoryID = :categoryId")
    LiveData<List<Job>> getByCategoryId(int categoryId);

    @Query("SELECT * FROM JOB WHERE Status = :status")
    List<Job> getJobByStatus(int status);

    @Query("SELECT * FROM JOB WHERE priority = :priority")
    List<Job> getJobByPriority(int priority);

    @Query("SELECT * FROM JOB WHERE CategoryID = :categoryId")
    List<Job> getJobByCategory(int categoryId);

    @Query("SELECT COUNT(1) FROM JOB WHERE Status =:status")
    int getTotalStatus(int status);

    @Query("SELECT COUNT(1) FROM JOB WHERE Status =:status AND EndDate >=:start AND EndDate<=:end")
    @TypeConverters(DateConvertor.class)
    int getRowCountByStatusMonth(int status,Date start,Date end);

    @Query("SELECT * FROM JOB WHERE Status =:status AND EndDate >=:start AND EndDate<=:end")
    @TypeConverters(DateConvertor.class)
    LiveData<List<Job>> getJobByStatusTime(int status,Date start,Date end);

    @Query("SELECT COUNT(1) FROM JOB WHERE EndDate >=:start AND EndDate<=:end")
    @TypeConverters(DateConvertor.class)
    int getRowCountByTimeEndDate(Date start,Date end);

    @Query("SELECT * FROM JOB WHERE EndDate >=:start AND EndDate<=:end ORDER BY Status,Priority DESC ")
    @TypeConverters(DateConvertor.class)
    LiveData<List<Job>> getJobAboutTimeEndDate(Date start,Date end);

    @Query("SELECT * FROM JOB WHERE EndDate >=:start AND EndDate<=:end ORDER BY Status,Priority DESC ")
    @TypeConverters(DateConvertor.class)
    List<Job> getListJobAboutTimeEndDate(Date start,Date end);

    @Insert
    void insert(Job... jobs);

    @Update
    void update(Job... jobs);

    @Delete
    void delete(Job... jobs);
}
