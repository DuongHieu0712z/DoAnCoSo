package com.ctk43.doancoso.Library;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;

import java.util.Calendar;
import java.util.Date;


public class Extension {

    public static Dialog dialogYesNo(Dialog dialog, String title, String content) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);
        Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAtrributes = window.getAttributes();
        windownAtrributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAtrributes);
        TextView textView = dialog.findViewById(R.id.txt_dialog_string);
        textView.setText(title);
        Button btnYes = dialog.findViewById(R.id.btn_dialog_yes);
        btnYes.setBackgroundTintMode(null);
        Button btnNo = dialog.findViewById(R.id.btn_dialog_no);
        btnNo.setBackgroundTintMode(null);
        TextView tv_des = dialog.findViewById(R.id.tv_dialog_description);
        tv_des.setText(content);
        return dialog;
    }

    public static boolean isEmty(Context context, String value, String name, boolean isdefaut) {
        if (value.isEmpty() || isdefaut) {
            Toast.makeText(context, "Không được để " + name + " trống, vui lòng nhập " + name + "!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static int Curr_Week() {
        Date date = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    public static int Last_Week(int week) {
        if (week == 1)
            return 52;
        return week - 1;
    }

    public static int Next_Week(int week) {
        if (week == 52)
            return 1;
        return week + 1;
    }

    public static Date StartOfWeek(Date date) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.setFirstDayOfWeek(Calendar.SUNDAY);
        return Cal.getTime();
    }

    public static Date EndOfWeek(Date date) {
        Calendar Cal = Calendar.getInstance();
        Cal.setTime(date);
        Cal.setFirstDayOfWeek(Calendar.SATURDAY);
        return Cal.getTime();
    }

    public static int Remaning_day(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        return (int) ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) / (1000 * 60 * 60 * 24));
    }

    public static int Remaning_hour(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        double time = ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) % (1000 * 60 * 60 * 24));
        return (int) time / (1000 * 60 * 60);
    }

    public static int Remaning_minute(Date start, Date end) {
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
        int day = Remaning_day(start, end);
        int hour = Remaning_hour(start, end);
        int minute = Remaning_minute(start, end);
        String timeRe;
        if (minute > 0) {
            timeRe = getTime(day, hour, minute, false);
        } else {
            timeRe = over_time(minute);
        }
        return timeRe;
    }

    public static int CheckStatus(Job job) {
        if (Remaning_minute(Calendar.getInstance().getTime(),job.getEndDate()) >= 0 && job.getProgress() ==1) {
            return 2;
        }else if(Remaning_minute(Calendar.getInstance().getTime(),job.getEndDate()) > 0 && job.getProgress() != 1)
            return 1;
        else if (Remaning_minute(Calendar.getInstance().getTime(),job.getEndDate()) < 0 && job.getProgress() != 1)
            return 3;
        else if(Remaning_minute(Calendar.getInstance().getTime(), job.getEndDate()) < 0 && job.getProgress() == 1){
            return 4;
        }
        return 0;
    }
    public static String getTimeText(double time) {
        int rounded= (int)Math.round(time);
        int seconds = ((rounded %86400)%3600)%60;
        int minutes = ((rounded %86400)%3600)/60;
        int hour = (rounded %86400)/3600;
        return formatTime(seconds,minutes,hour);
    }

    public static String formatTime(int seconds, int minutes, int hour) {
        return String.format("%02d",hour) + " : " + String.format("%02d",minutes) + " : " +String.format("%02d",seconds);
    }

}
