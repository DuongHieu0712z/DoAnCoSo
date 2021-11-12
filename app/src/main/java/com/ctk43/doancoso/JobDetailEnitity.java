package com.ctk43.doancoso;

import java.time.LocalDateTime;

public class JobDetailEnitity {
    public String Name;
    public String Description;
    public int Status;
    public boolean Priority;
    public int EstimatedCompletedTime;
    public int ActualCompletedTime;
    public Double Progress;

    public JobDetailEnitity(String name, String description, int estimatedCompletedTime) {
        Name = name;
        Description = description;
        EstimatedCompletedTime = estimatedCompletedTime;
        ActualCompletedTime = 0;
        Priority = false;
        Status = 0;
        Progress = 0.0;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public void setActualCompletedTime(int actualCompletedTime) {
        ActualCompletedTime = actualCompletedTime;
    }

    public void setProgress(Double progress) {
        Progress = progress;
    }
}
