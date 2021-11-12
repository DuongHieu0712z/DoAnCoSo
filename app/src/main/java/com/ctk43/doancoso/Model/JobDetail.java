package com.ctk43.doancoso.Model;

import java.time.LocalDateTime;

public class JobDetail {
    public  int ID;
    public  int JOBID;
    public String Name;
    public LocalDateTime Time;
    public String Description;
    public Boolean Priority;
    public Double Progress;
    public int Status; //0 - on going; -1 - drop; 1 - complete; 2- over

    public JobDetail(int ID, int JOBID, String name, LocalDateTime time, String description, Boolean priority, Double progress, int status) {
        this.ID = ID;
        this.JOBID = JOBID;
        Name = name;
        Time = time;
        Description = description;
        Priority = priority;
        Progress = progress;
        Status = status;
    }
}
