package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = Job.class,
        parentColumns = "ID",
        childColumns = "JobID",
        onDelete = ForeignKey.CASCADE))
public class JobDetail {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "JobID")
    private int jobId;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    @ColumnInfo(name = "EstimatedTime")
    private int estimatedCompletedTime = 0;

    @ColumnInfo(name = "ActualTime")
    private int actualCompletedTime = 0;

    @ColumnInfo(name = "Description")
    private String description;

    @ColumnInfo(name = "Priority")
    private boolean priority = false;

    @ColumnInfo(name = "Progress")
    private double progress = 0.0;

    @ColumnInfo(name = "Status")
    private int status = 0; // 0 - on going; -1 - drop; 1 - complete; 2- over

    @ColumnInfo(name = "IDParent")
    private int idParent;

    public JobDetail(int jobId, @NonNull String name, int estimatedCompletedTime, String description) {
        this.jobId = jobId;
        this.name = name;
        this.estimatedCompletedTime = estimatedCompletedTime;
        this.description = description;
    }

    @Ignore
    public JobDetail(int id, int jobId, @NonNull String name, int estimatedCompletedTime, int actualCompletedTime, String description, boolean priority, double progress, int status, int idParent) {
        this.id = id;
        this.jobId = jobId;
        this.name = name;
        this.estimatedCompletedTime = estimatedCompletedTime;
        this.actualCompletedTime = actualCompletedTime;
        this.description = description;
        this.priority = priority;
        this.progress = progress;
        this.status = status;
        this.idParent = idParent;
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

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public int getEstimatedCompletedTime() {
        return estimatedCompletedTime;
    }

    public void setEstimatedCompletedTime(int estimatedCompletedTime) {
        this.estimatedCompletedTime = estimatedCompletedTime;
    }

    public int getActualCompletedTime() {
        return actualCompletedTime;
    }

    public void setActualCompletedTime(int actualCompletedTime) {
        this.actualCompletedTime = actualCompletedTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
        this.priority = priority;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getIdParent() {
        return idParent;
    }

    public void setIdParent(int idParent) {
        this.idParent = idParent;
    }
}
