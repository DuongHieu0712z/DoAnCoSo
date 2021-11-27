package com.ctk43.doancoso.Library;




<<<<<<< HEAD

import android.database.Cursor;

=======
>>>>>>> parent of 1bb4a00 (thanh)
import java.util.Calendar;
import java.util.Date;


public class Extension {
    public static int Curr_Week(){
        Date date = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }
    public static int Last_Week(int week){
        if(week==1)
            return 52;
        return week-1;
    }
    public static int Next_Week(int week){
        if(week==52)
            return 1;
        return week+1;
    }
    public static Date StartOfWeek(Date date){
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.setFirstDayOfWeek(Calendar.SUNDAY);
        return  Cal.getTime();

    }
    public static Date EndOfWeek(Date date){
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.setFirstDayOfWeek(Calendar.SATURDAY);
        return  Cal.getTime();
    }
    public static int Remaning_day(Date start,Date end){
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        return  (int)((calEnd.getTimeInMillis()-calStart.getTimeInMillis())/(1000 * 60 * 60 * 24));
    }
    public static int Remaning_hour(Date start,Date end){
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        double time = ((calEnd.getTimeInMillis()-calStart.getTimeInMillis())%(1000 * 60 * 60 * 24));
        int hour = (int)time/(1000 * 60 * 60);
        return  hour;
    }
    public static int Remaning_minute(Date start,Date end){
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        double time = ((calEnd.getTimeInMillis()-calStart.getTimeInMillis())%(1000 * 60 * 60 * 24));
        int minute = (int)time % (1000 * 60 * 60);
        return  minute;
    }
    public static String TimeRemaining(Date start,Date end){
        int day = Remaning_day(start,end);
        int hour = Remaning_hour(start,end);
        int minute = Remaning_minute(start,end);
        String timeRe = new String();
        if(day > 0 && hour > 0){
            timeRe = day +" ngày " + hour + " giờ";
        }else if (day > 0 ){
            timeRe = day +" ngày ";
        }else if (minute > 0 && hour > 0){
            timeRe = hour + " giờ "+minute +" phút";
        }else if (hour > 0){
            timeRe = hour + " giờ ";
        }else {
            timeRe = minute + " phút ";
        }
        return  timeRe;
    }
<<<<<<< HEAD

=======
>>>>>>> parent of 1bb4a00 (thanh)
}
