package com.ctk43.doancoso.Library;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarExtension {
    // to milisecon
    public static final long ONE_DAY = 24 * 60 * 60 * 1000;
    public static final long ONE_HOUR = 60 * 60 * 1000;
    public static final long ONE_MINUTE = 60 * 1000;
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

    public static Date currDate() {
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
        return calendar.get(Calendar.DAY_OF_WEEK) - 1;
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
        return getStartTimeOfDate(getStartDateOfWeek(date, position));
    }

    @NonNull
    public static Date getEndTimeOfWeek(Date date, int position) {
        return getEndTimeOfDate(getEndDateOfWeek(date, position));
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
        return ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / CalendarExtension.ONE_DAY);
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
        return time / (1000 * 60);
    }

    public static long timeRemaining(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        long test = ((calEnd.getTimeInMillis()-calStart.getTimeInMillis()));
        Log.e("Tính toán", "timeRemaining: "+test );
        return test;
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
        int hour = (rounded / 60) ;
        int day = (rounded % 60) / 24;
        int minutes = (rounded % 60);
        return formatTime(minutes, hour, day);
    }

    public static String dateToString(Date date) {
        return "Ngày " + formatDate(date) + " Giờ " + formatTime(date);
    }
    public static String getString(Date date) {
        return "Ngày " + formatDate(date);
    }

    @NonNull
    @SuppressLint("DefaultLocale")
    public static String formatTime(int minute, int hour, int day) {
        return String.format("%02d:%02d:%02d", day, day, minute);
    }

    public static Date getDate(String date) throws ParseException {
        return dateFormat.parse(date);
    }

    public static Date getDate(String date, String time) throws ParseException {
        return dateTimeFormat.parse(date + " " + time);
    }

    @NonNull
    public static Date getDate(int year, int month, int day) {
        return getDate(year, month, day, 0, 0);
    }

    @NonNull
    public static Date getDate(int year, int month, int day, int hour, int minute) {
        return getDate(year, month, day, hour, minute, 0);
    }

    @NonNull
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

    @NonNull
    public static Date getTime(int hour, int minute) {
        return getTime(hour, minute, 0);
    }

    @NonNull
    public static Date getTime(int hour, int minute, int second) {
        return getDate(0, 0, 0, hour, minute, second);
    }

    @NonNull
    public static String formatDate(Date date) {
        return dateFormat.format(date);
    }

    @NonNull
    public static String formatTime(Date time) {
        return timeFormat.format(time);
    }

    @NonNull
    public static String formatDateTime(Date date) {
        return dateTimeFormat.format(date);
    }

    public static boolean isJanuary(int month) {
        return month == calendar.getActualMinimum(Calendar.MONTH);
    }

    public static boolean isDecember(int month) {
        return month == calendar.getActualMinimum(Calendar.MONTH);
    }
}
