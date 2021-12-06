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
<<<<<<< HEAD
    private int priority;
=======
    private boolean priority = false;
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813

    @ColumnInfo(name = "Progress")
    private double progress = 0.0;

    @ColumnInfo(name = "Status")
    private int status = 0; // 0 - on going; -1 - drop; 1 - complete; 2- over

    public Job(int categoryId, @NonNull String name, @NonNull Date startDate, @NonNull Date endDate, String description) {
        this.categoryId = categoryId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    @Ignore
<<<<<<< HEAD
    public Job(int id, int categoryId, @NonNull String name, @NonNull Date startDate, @NonNull Date endDate, String description, int priority, Double progress, int status) {
=======
    public Job(int id, int categoryId, @NonNull String name, @NonNull Date startDate, @NonNull Date endDate, String description, Boolean priority, Double progress, int status) {
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
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

    @Ignore
<<<<<<< HEAD
    public Job(@NonNull String name, String description, @NonNull Date start, @NonNull Date end, int priority, Double progress) {
=======
    public Job(@NonNull String name, String description, @NonNull Date start, @NonNull Date end, Boolean priority, Double progress) {
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
        this.name = name;
        this.description = description;
        startDate = start;
        endDate = end;
        this.priority = priority;
        this.progress = progress;
        status = 0;
    }

    @Ignore
<<<<<<< HEAD
    public Job(int categoryId, @NonNull String name, String description, @NonNull Date start, @NonNull Date end, int priority, Double progress) {
=======
    public Job(int categoryId, @NonNull String name, String description, @NonNull Date start, @NonNull Date end, Boolean priority, Double progress) {
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
        this.name = name;
        this.categoryId = categoryId;
        this.description = description;
        startDate = start;
        endDate = end;
        this.priority = priority;
        this.progress = progress;
        status = 0;
    }

//    @SuppressLint("SimpleDateFormat")
//    public Job(Cursor c) {
//        id = c.getInt(0);
//        categoryId = c.getInt(1);
//
//        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        try {
//            String date = c.getString(2);
//            StartDate = outputFormatter.parse(date);
//            date = c.getString(3);
//            EndDate = outputFormatter.parse(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        Priority = c.getInt(4) == 1;
//        Progress = c.getDouble(5);
//        Status = c.getInt(6);
//    }

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

<<<<<<< HEAD
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
=======
    public boolean isPriority() {
        return priority;
    }

    public void setPriority(boolean priority) {
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
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
