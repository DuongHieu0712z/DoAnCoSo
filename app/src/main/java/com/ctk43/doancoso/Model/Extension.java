package com.ctk43.doancoso.Model;




import com.google.type.DateTime;

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
}
