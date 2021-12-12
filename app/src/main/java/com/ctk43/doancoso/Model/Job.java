package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ctk43.doancoso.Database.DateTypeConvertor;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(
        entity = Category.class,
        parentColumns = "ID",
        childColumns = "CategoryID",
        onDelete = ForeignKey.CASCADE))
public class Job {
    @ColumnInfo(name = "ID")
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "CategoryID", index = true)
    private int categoryId;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    @ColumnInfo(name = "StartDate")
    @TypeConverters({DateTypeConvertor.class})
    @NonNull
    private Date startDate;

    @ColumnInfo(name = "EndDate")
    @TypeConverters({DateTypeConvertor.class})
    @NonNull
    private Date endDate;

    @ColumnInfo(name = "Description")
    private String description;

    @ColumnInfo(name = "Priority")
    private int priority = 0;

    @ColumnInfo(name = "Progress")
    private double progress = 0.0;

    @ColumnInfo(name = "Status")
    private int status = 0; // 0-in coming-1 - on going; 2 - complete; 3- over

    public Job(int categoryId, @NonNull String name, @NonNull Date startDate, @NonNull Date endDate, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    @Ignore
    public Job(int id, int categoryId, @NonNull String name, @NonNull Date startDate, @NonNull Date endDate, String description, int priority, double progress, int status) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.priority = priority;
        this.progress = progress;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(@NonNull Date startDate) {
        this.startDate = startDate;
    }

    @NonNull
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(@NonNull Date endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
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
}
