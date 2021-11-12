package com.ctk43.doancoso.Model;

import java.time.LocalDateTime;

public class JobDetail {
    public  int ID;
    public  int JOBID;
    public String Name;
    public int EstimatedCompletedTime;
    public int ActualCompletedTime;
    public String Description;
    public Boolean Priority;
    public Double Progress;
    public int Status; //0 - on going; -1 - drop; 1 - complete; 2- over

    public JobDetail(int ID, int JOBID, String name, int estimatedCompletedTime, int actualCompletedTime, String description, Boolean priority, Double progress, int status) {
        this.ID = ID;
        this.JOBID = JOBID;
        Name = name;
        EstimatedCompletedTime = estimatedCompletedTime;
        ActualCompletedTime = actualCompletedTime;
        Description = description;
        Priority = priority;
        Progress = progress;
        Status = status;
    }

    public JobDetail(String name,  String description, int estimatedCompletedTime) {
        Name = name;
        EstimatedCompletedTime = estimatedCompletedTime;
        Description = description;
        ActualCompletedTime = 0;
        Priority = false;
        Status = 0;
        Progress = 0.0;
    }
}
