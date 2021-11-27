package com.ctk43.doancoso.Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class JobData {
    private static Date start;
    private static Date end;
    public static Job[] populateMovieData(){
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
        return new Job[]{
        new Job("Tên Công Việc 1", "Đây là công việc đầu 1 rat nhieu chu", start,end , true, 0.2),
        new Job("Tên Công Việc 2", "Đây là công việc 3 rat nhieu chu", start,end , true, 0.0),
        new Job("Tên Công Việc 3", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 0.),
        new Job("Tên Công Việc 4", "Đây là công việc đên rat nhieu chu", start,end , true, 0.8),
        new Job("Tên Công Việc 5", "Đây là công việc  rat nhieu chu", start,end , true, 0.4),
        new Job("Tên Công Việc 6", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 0.5),
        new Job("Tên Công Việc 6", "Đây là công việiên rat nhieu chu", start,end , true, 0.5),
        new Job("Tên Công Việc 8", "Đây là công việiên rat nhieu chu", start,end , true, 0.5),
        new Job("Tên Công Việc 8", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 0.5),
        new Job("Tên Công Việc 8", "Đây là công v tiên rat nhieu chu", start,end , true, 0.7),
        new Job("Tên Công Việc 9", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 0.7),
        new Job("Tên Công Việc 0", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 4", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 5", "Đây là công vtiên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 6", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 7", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 8", "Đây là côngu tiên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 8", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 9", "Đây là công iên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 7", "Đây là cônu tiên rat nhieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc ", "Đây là công u thieu chu", start,end , true, 1.0),
        new Job("Tên Công Việc 1", "Đây là công vt nhieu chu", start,end , true, 1.0)
        };
    }
}
