package com.ctk43.doancoso.Model;

import java.time.LocalDateTime;

public class Job {
    public  int ID;
    public String Name;
    public LocalDateTime Start;
    public LocalDateTime End;
    public String Description;
    public Boolean Priority;
    public Double Progress;
    public int Status; //0 - on going; -1 - drop; 1 - complete; 2- over
    public int CategoryID;

    public Job(int ID, String name, LocalDateTime start, LocalDateTime end, String description, Boolean priority, Double progress, int status, int categoryID) {
        this.ID = ID;
        Name = name;
        Start = start;
        End = end;
        Description = description;
        Priority = priority;
        Progress = progress;
        Status = status;
        CategoryID = categoryID;
    }

    public Job(String name, String description, LocalDateTime start, LocalDateTime end, Boolean priority, Double progress) {
        Name = name;
        Description = description;
        Start = start;
        End = end;
        Priority = priority;
        Progress = progress;
        Status = 0;
    }

}
