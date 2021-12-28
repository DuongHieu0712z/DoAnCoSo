package com.ctk43.doancoso.Library;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class CalendarExtension {
    private static final Calendar calendar = Calendar.getInstance();
    public static int getCurrentWeek() {
        return getWeek(Calendar.getInstance().getTime());
    }

    public static void setFirstDayOfWeek(int value) {
        calendar.setFirstDayOfWeek(value);
    }

    public static int getWeek(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    @NonNull
    public static Date getStartDateOfWeek(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    @NonNull
    public static Date getEndDateOfWeek(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek() + 6);
        return calendar.getTime();
    }

    @NonNull
    public static Date getStartTimeOfDate(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @NonNull
    public static Date getEndTimeOfDate(Date date) {
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }



    public static Date getStartOfDate(Date date){
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);
        return calendar.getTime();
    }
    public static Date getEndOfDate(Date date){
        calendar.setTime(date);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.HOUR,0);
        calendar.add(Calendar.DATE,1);
        return calendar.getTime();
    }
    public static Date getDateStartOfMonth(int month,int year){
        calendar.set(year,month,1);
        return getStartOfDate(calendar.getTime());
    }

    public static Date getDateEndOfMonth(int month,int year){
        calendar.set(year,month,-1);
        return calendar.getTime();
    }

    public static Date EndOfWeek(Date date) {
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.SATURDAY);
        return calendar.getTime();
    }


    public static int Remaining_day(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        return (int) ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    public static int Remaining_hour(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        double time = ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) % (1000 * 60 * 60 * 24));
        return (int) time / (1000 * 60 * 60);
    }

    public static int Remaining_minute(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        double time = ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) % (1000 * 60 * 60 * 24));
        return (int) time % (1000 * 60 * 60);
    }

    public static String over_time(int minute) {
        int day = minute % 60 % 24;
        int hour = minute / 60 % 24;
        int _minute = minute / 60 / 24;
        return getTime(day, hour, _minute, true);
    }

    public static String getTime(int day, int hour, int minute, boolean negative) {
        if (negative) {
            day *= -1;
            hour *= -1;
            minute *= -1;
        }
        String timeRe;
        if (day > 0 && hour > 0) {
            timeRe = day + " ngày " + hour + " giờ";
        } else if (day > 0) {
            timeRe = day + " ngày ";
        } else if (minute > 0 && hour > 0) {
            timeRe = hour + " giờ " + minute + " phút";
        } else if (hour > 0) {
            timeRe = hour + " giờ ";
        } else {
            timeRe = minute + " phút ";
        }
        return timeRe;
    }

    public static String TimeRemaining(Date start, Date end) {
        int day = Remaining_day(start, end);
        int hour = Remaining_hour(start, end);
        int minute = Remaining_minute(start, end);
        String timeRe;
        if (minute > 0) {
            timeRe = getTime(day, hour, minute, false);
        } else {
            timeRe = over_time(minute);
        }
        return timeRe;
    }

}
