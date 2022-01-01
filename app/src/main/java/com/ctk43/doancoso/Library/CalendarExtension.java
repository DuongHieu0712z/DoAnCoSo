package com.ctk43.doancoso.Library;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarExtension {
    private static final Calendar calendar = Calendar.getInstance();

    @SuppressLint("ConstantLocale")
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

    @SuppressLint("ConstantLocale")
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    @SuppressLint("ConstantLocale")
    private static final SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public static int getCurrentWeek() {
        return getWeek(Calendar.getInstance().getTime());
    }

    public static int getMonth(Date date, int position) {
        calendar.setTime(date);
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, position);
        Date date1 = calendar.getTime();
        return calendar.get(Calendar.MONTH);
    }

    public static int getYear(Date date, int position) {
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, position);
        return calendar.get(Calendar.YEAR);
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

    @NonNull
    public static Date getDateStartOfMonth(int month, int year) {
        calendar.set(year, month, 1);
        return calendar.getTime();
    }

    @NonNull
    public static Date getDateEndOfMonth(int month, int year) {
        calendar.set(year, month, 1);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, calendar.get(Calendar.MONTH));
        return calendar.getTime();
    }

    public static Date getStartTimePreviousWeek() {
        calendar.setTime(Calendar.getInstance().getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.setTime(getStartTimeOfWeek(calendar.getTime()));
        return calendar.getTime();
    }

    public static Date getEndTimePreviousNextWeek() {
        calendar.setTime(Calendar.getInstance().getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.setTime(getEndTimeOfWeek(calendar.getTime()));
        return calendar.getTime();
    }

    public static Date getStartTimeNextWeek() {
        calendar.setTime(Calendar.getInstance().getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.setTime(getStartTimeOfWeek(calendar.getTime()));
        return calendar.getTime();
    }

    public static Date getEndTimeNextWeek() {
        calendar.setTime(Calendar.getInstance().getTime());
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
        calendar.setTime(getEndTimeOfWeek(calendar.getTime()));
        return calendar.getTime();
    }

    @NonNull
    public static Date getStartTimeOfWeek(Date date) {
        return getStartTimeOfDate(getStartDateOfWeek(date));
    }

    @NonNull
    public static Date getEndTimeOfWeek(Date date) {
        return getEndDateOfWeek(getStartDateOfWeek(date));
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
        return ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    public static long Remaining_hour(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        long time = ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) % (1000 * 60 * 60 * 24));
        return (time / (1000 * 60 * 60));
    }

    public static long Remaining_minute(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        long time = ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) % (1000 * 60 * 60 * 24));
        return ((time / 1000) % 60 % 60);
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
        if (minute > 0 || hour > 0 || day > 0) {
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
        return "Ngày " + formatDate(date) + " Giờ " + formatTime(date);
    }

    @SuppressLint("DefaultLocale")
    public static String formatTime(int seconds, int minutes, int hour) {
        return String.format("%02d", hour) + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", seconds);
    }

    public static Date getDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    public static Date getDate(String date, String time) throws ParseException {
        return dateTimeFormat.parse(date + " " + time);
    }

    public static Date getDate(int year, int month, int day) {
        return getDate(year, month, day, 0, 0);
    }

    public static Date getDate(int year, int month, int day, int hour, int minute) {
        return getDate(year, month, day, hour, minute, 0);
    }

    public static Date getDate(int year, int month, int day, int hour, int minute, int second) {
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, second);
        return calendar.getTime();
    }

    public static Date getTime(String time) throws ParseException {
        return timeFormat.parse(time);
    }

    public static Date getTime(int hour, int minute) {
        return getTime(hour, minute, 0);
    }

    public static Date getTime(int hour, int minute, int second) {
        return getDate(0, 0, 0, hour, minute, second);
    }

    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    public static String formatTime(Date time) {
        return timeFormat.format(time);
    }

    public static String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }
}
