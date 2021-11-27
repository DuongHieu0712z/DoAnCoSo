package com.ctk43.doancoso.Model;

import android.database.Cursor;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.ctk43.doancoso.Database.DateTypeConvertor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Entity(foreignKeys = {@ForeignKey(entity = Category.class,
        parentColumns = "ID",
        childColumns = "CategoryID",
        onDelete = ForeignKey.CASCADE)})

public class Job {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "CategoryID")
    public int CategoryID;

    @ColumnInfo(name = "Name")
    public String Name;

    @TypeConverters({DateTypeConvertor.class})
    public Date StartDate;

    @TypeConverters({DateTypeConvertor.class})
    public Date EndDate;

    @ColumnInfo(name = "Description")
    public String Description;

    @ColumnInfo(name = "Priority")
    public Boolean Priority;

    @ColumnInfo(name = "Progress")
    public Double Progress;

    @ColumnInfo(name = "Status")
    public int Status; //0 - on going; -1 - drop; 1 - complete; 2- over

    public Job() {
    }

    public Job(int ID, String name, Date start, Date end, String description, Boolean priority, Double progress, int status, int categoryID) {
        this.ID = ID;
        Name = name;
        StartDate = start;
        EndDate = end;
        Description = description;
        Priority = priority;
        Progress = progress;
        Status = status;
        CategoryID = categoryID;
    }

    public Job(String name, String description, Date start, Date end, Boolean priority, Double progress) {
        Name = name;
        Description = description;
        StartDate = start;
        EndDate = end;
        Priority = priority;
        Progress = progress;
        Status = 0;
    }
    public Job(int categoryID,String name, String description, Date start, Date end, Boolean priority, Double progress) {
        Name = name;
        CategoryID = categoryID;
        Description = description;
        StartDate = start;
        EndDate = end;
        Priority = priority;
        Progress = progress;
        Status = 0;
    }

    public Job(Cursor c) {
        ID = c.getInt(0);
        CategoryID = c.getInt(1);

        SimpleDateFormat outputFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        try {
            String date = c.getString(2);
            StartDate = outputFormatter.parse(date);
            date = c.getString(3);
            EndDate = outputFormatter.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Priority = c.getInt(4) == 1 ? true : false;
        Progress = c.getDouble(5);
        Status = c.getInt(6);
    }


}
