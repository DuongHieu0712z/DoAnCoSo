package com.ctk43.doancoso.Library;


import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.ctk43.doancoso.R;

import java.util.Calendar;
import java.util.Date;


public class Extension {

    public static Dialog dialogYesNo(Dialog dialog, String title, String content, String description) {
        boolean bool;
        int theme = R.attr.dialogTheme;
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
        textView.setText(content);
        Button btnYes = dialog.findViewById(R.id.btn_dialog_yes);
        btnYes.setBackgroundTintMode(null);
        Button btnNo = dialog.findViewById(R.id.btn_dialog_no);
        btnNo.setBackgroundTintMode(null);
        TextView tv_des = dialog.findViewById(R.id.tv_dialog_description);
        tv_des.setText(description);
     /*   Button btnyes = dialog.findViewById(R.id.btn_dialog_yes);
        Button btnno = dialog.findViewById(R.id.btn_dialog_no);*/
        return dialog;
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
        int hour = (int) time / (1000 * 60 * 60);
        return hour;
    }

    public static int Remaning_minute(Date start, Date end) {
        Calendar calStart = Calendar.getInstance();
        calStart.setTime(start);
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(end);
        double time = ((calEnd.getTimeInMillis() - calStart.getTimeInMillis()) % (1000 * 60 * 60 * 24));
        int minute = (int) time % (1000 * 60 * 60);
        return minute;
    }

    public static String TimeRemaining(Date start, Date end) {
        int day = Remaning_day(start, end);
        int hour = Remaning_hour(start, end);
        int minute = Remaning_minute(start, end);
        String timeRe = new String();
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
}
