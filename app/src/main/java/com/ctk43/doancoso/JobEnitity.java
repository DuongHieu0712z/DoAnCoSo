package com.ctk43.doancoso;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class JobEnitity {
    public String Name;
    public String Description;
    public LocalDateTime Start;
    public LocalDateTime End;
    public Boolean Priority;
    public Double Progress;
    public int Status; //0 - on going; -1 - drop; 1 - complete; 2- over
    public ArrayList<JobDetailEnitity> JobDetails;

    public JobEnitity(String name, String description, LocalDateTime start, LocalDateTime end, Boolean priority, Double progress) {
        Name = name;
        Description = description;
        Start = start;
        End = end;
        Priority = priority;
        Progress = progress;
        Status = 0;
        JobDetails = new ArrayList<>();
    }

    public void setJobDetails(ArrayList<JobDetailEnitity> jobDetails) {
        JobDetails = jobDetails;
    }
}
