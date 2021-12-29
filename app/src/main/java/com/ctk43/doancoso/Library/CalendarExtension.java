package com.ctk43.doancoso.Library;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;

public class CalendarExtension {
    private static final Calendar calendar = Calendar.getInstance();

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

    public static int getCurrentWeek() {
        return getWeek(Calendar.getInstance().getTime());
    }
}
