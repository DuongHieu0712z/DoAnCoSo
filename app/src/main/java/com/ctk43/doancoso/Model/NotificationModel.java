package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ctk43.doancoso.Database.DateTypeConvertor;

import java.util.Calendar;
import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Job.class,
        parentColumns = "ID",
        childColumns = "JobID",
        onDelete = ForeignKey.SET_NULL))
public class NotificationModel {

    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "JobID", index = true)
    private int jobId;

    @ColumnInfo(name = "Status")
    private int status;

    @ColumnInfo(name = "DateOfRecord")
    @TypeConverters({DateTypeConvertor.class})
    @NonNull
    private Date dateOfRecord;

    public NotificationModel(int jobId,int status) {
        this.jobId = jobId;
        this.status =status;
        dateOfRecord= Calendar.getInstance().getTime();
    }


    @NonNull
    public Date getDateOfRecord() {
        return dateOfRecord;
    }

    public void setDateOfRecord(@NonNull Date dateOfRecord) {
        this.dateOfRecord = dateOfRecord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
