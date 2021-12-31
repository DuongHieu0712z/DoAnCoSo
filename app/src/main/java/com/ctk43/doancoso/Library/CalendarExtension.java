package com.ctk43.doancoso.Library;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class CalendarExtension {
    private static final Calendar calendar = Calendar.getInstance();
    // to milisecon
    public static final int ONE_DAY = 24*60 * 60 *1000 ;
    public static final int ONE_HOUR = 60 * 60 *1000;
    public static final int ONE_MINUTE = 60*1000;

    public static int getCurrentWeek() {
        return getWeek(Calendar.getInstance().getTime());
    }

    public static Date currDate(){
        return Calendar.getInstance().getTime();
    }

    public static int getMonth(Date date, int position) {
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, position);
        Date date1 = calendar.getTime();
        return calendar.get(Calendar.MONTH);
    }

    public static Date getMonthByPosition(Date date, int position) {
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, position);
        return calendar.getTime();
    }

    public static int getYear(Date date, int position) {
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, position);
        return calendar.get(Calendar.YEAR);
    }

    public static void setFirstDayOfWeek(int value) {
        calendar.setFirstDayOfWeek(value);
    }

    public static int getDaysInMonth(Date date) {
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    public static int getWeek(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public static int getDateOfWeek(Date date) {
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK)-1;
    }

    @NonNull
    public static Date getStartDateOfWeek(Date date, int position) {
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, position);
        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
        return calendar.getTime();
    }

    @NonNull
    public static Date getEndDateOfWeek(Date date, int position) {
        calendar.setTime(date);
        calendar.add(Calendar.WEEK_OF_YEAR, position);
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

    @NonNull
    public static Date getDateStartOfMonth(int month, int year) {
        calendar.set(year, month, 1);
        return calendar.getTime();
    }

    @NonNull
    public static Date getDateEndOfMonth(int month, int year) {
        calendar.set(year, month, 1);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }


    @NonNull
    public static Date getStartTimeOfWeek(Date date, int position) {
        return getStartTimeOfDate(getStartDateOfWeek(date,position));
    }

    @NonNull
    public static Date getEndTimeOfWeek(Date date, int position) {
        return getEndTimeOfDate(getEndDateOfWeek(date,position));
    }

    @NonNull
    public static Date getStartTimeOfMonth(int month, int year) {
        return getStartTimeOfDate(getDateStartOfMonth(month, year));
    }

    @NonNull
    public static Date getEndTimeOfMonth(int month, int year) {
        return getEndTimeOfDate(getDateEndOfMonth(month, year));
    }

    public static long Remaining_day(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        long starttest = calStart.getTimeInMillis();
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        long endtest = calEnd.getTimeInMillis();
        return ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) /CalendarExtension.ONE_DAY);
    }

    public static long Remaining_hour(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        long time = ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) % CalendarExtension.ONE_DAY);
        return (time / CalendarExtension.ONE_HOUR);
    }

    public static long Remaining_minute(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        long time = ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) % CalendarExtension.ONE_DAY % CalendarExtension.ONE_HOUR);
        return  time / (1000 *60);
    }


    public static String getTime(long day, long hour, long minute, boolean negative) {
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
        long day = Remaining_day(start, end);
        long hour = Remaining_hour(start, end);
        long minute = Remaining_minute(start, end);
        String timeRe;
        if (minute > 0 || hour >0 || day >0) {
            timeRe = getTime(day, hour, minute, false);
        } else {
            timeRe = getTime(day, hour, minute, true);
        }
        return timeRe;
    }

    public static String getTimeText(double time) {
        int rounded = (int) Math.round(time);
        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;
        int hour = (rounded % 86400) / 3600;
        return formatTime(seconds, minutes, hour);
    }

    public static String dateToString(Date date) {
        calendar.setTime(date);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        month+=1;
        return "Ngày " + day + "/" + month + "/" + year + " Giờ " +String.format("%02d", hour) + ":" + String.format("%02d", minute);
    }

    public static String formatTime(int seconds, int minutes, int hour) {
        return String.format("%02d", hour) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

    public static boolean isJanuary(int month){
        if(month == calendar.getActualMinimum(Calendar.MONTH))
            return true;
        return false;
    }
    public static boolean isDeccember(int month){
        if(month == calendar.getActualMinimum(Calendar.MONTH))
            return true;
        return false;
    }
}
