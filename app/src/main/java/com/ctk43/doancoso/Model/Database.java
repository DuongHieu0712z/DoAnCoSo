package com.ctk43.doancoso.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Database {
    private static Database instance;
    ArrayList<Categoty> listcate ;
    ArrayList<Job> curr_week;
    ArrayList<Job> last_week;
    ArrayList<Job> next_week;
    private Database(){}


    public static Database getInstance(){
        if (instance==null)
            instance = new Database();
            return instance;
    }

    public ArrayList<Job> GetCurr_Week(){
        return curr_week;
    }
    public void LoadDataBase(){
        Date date = Calendar.getInstance().getTime();
        fakeData();

    }
    private void fakeData(){
         curr_week = new ArrayList<>();
         Calendar cal = Calendar.getInstance();
        String Date = "31/12/2021";
        Date date;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(Date);
            cal.setTime(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        cal.set(Calendar.HOUR_OF_DAY, 6);// for 6 hour
        cal.set(Calendar.MINUTE, 0);// for 0 min
        cal.set(Calendar.SECOND, 0);// for 0 sec
        Date start = Calendar.getInstance().getTime();
        Date end = cal.getTime();
        curr_week.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên rat nhieu chu", start, end, true, 1.0));
        curr_week.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        curr_week.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        curr_week.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        curr_week.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        curr_week.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        curr_week.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 1.0));
        curr_week.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.5));
        curr_week.add(new Job("Tên Công Việc 1", "Đây là công việc đầu tiên", start, end, true, 0.4));
        curr_week.add(new Job("Tên Công Việc 2", "Đây là công việc đầu tiên", start, end, false, 0.2));

        ArrayList<JobDetail> jobDetails = new ArrayList<>();
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        jobDetails.add(new JobDetail("Job detail name", "Job Detail Description", 30));
        curr_week.get(0).JobDetails = jobDetails;
    }

}
