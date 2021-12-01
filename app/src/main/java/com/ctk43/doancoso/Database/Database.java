package com.ctk43.doancoso.Database;

import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Database {
    private static Database instance;
    ArrayList<Category> listcate ;
    ArrayList<Job> curr_week;

    public void setLast_week(ArrayList<Job> last_week) {
        this.last_week = last_week;
    }
    public List<JobDetail> jobDetails;
    ArrayList<Job> last_week;
    ArrayList<Job> next_week;
    private Database(){}

    public static Database getInstance(){
        if (instance==null)
            instance = new Database();
            return instance;
    }

    public ArrayList<Job> GetCurr_Week(){
        fakeData();
        return curr_week;
    }
    public ArrayList<Job> GetLast_Week(){
        return last_week;
    }
    public void LoadDataBase(){
        Date date = Calendar.getInstance().getTime();
        fakeData();
    }
    private void fakeData(){
         curr_week = new ArrayList<Job>();
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

        jobDetails = new ArrayList<>();

        jobDetails.add(new JobDetail("Thiết kế giao diện", "Giao diện phải đơn giản", 300));
        jobDetails.add(new JobDetail("Thiết kế csdl", "Bạn Thanh làm nên em từ chối hiểu", 300));
        jobDetails.add(new JobDetail("Trình bày", "Trình bày demo sương sương", 30));
    }

}
