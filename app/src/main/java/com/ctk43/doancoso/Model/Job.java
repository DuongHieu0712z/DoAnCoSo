package com.ctk43.doancoso.Model;

import java.util.ArrayList;
import java.util.Date;

public class Job {
    public  int ID;
    public String Name;
    public Date Start;
    public Date End;
    public String Description;
    public Boolean Priority;
    public Double Progress;
    public int Status; //0 - on going; -1 - drop; 1 - complete; 2- over
    public int CategoryID;
    public ArrayList<JobDetail> JobDetails;

    public Job() {
    }

    public Job(int ID, String name, Date start, Date end, String description, Boolean priority, Double progress, int status, int categoryID) {
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

    public Job(String name, String description, Date start, Date end, Boolean priority, Double progress) {
        ArrayList<Job> listJob = Database.getInstance().GetCurr_Week();
        if(listJob ==null)
            this.ID=1;
        else
            this.ID = listJob.size()+1;
        Name = name;
        Description = description;
        Start = start;
        End = end;
        Priority = priority;
        Progress = progress;
        Status = 0;
        JobDetails = new ArrayList<>();
    }


}
