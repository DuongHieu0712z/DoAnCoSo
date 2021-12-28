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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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

    public static boolean isEmpty(Context context, String value, String name, boolean isDefault) {
        if (value.isEmpty() || isDefault) {
            Toast.makeText(context, "Không được để " + name + " trống, vui lòng nhập " + name + "!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static int getWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }

    public static int getCurrentWeek() {
        return getWeek(Calendar.getInstance().getTime());
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

    public static boolean isEmty(Context context, String value, String name, boolean isdefaut) {
        if (value.isEmpty() || isdefaut) {
            Toast.makeText(context, "Không được để " + name + " trống, vui lòng nhập " + name + "!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }


    public static int CheckStatus(Job job) {
        if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(),job.getEndDate()) >= 0 && job.getProgress() ==1) {
            return 2;
        }else if(CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(),job.getEndDate()) > 0 && job.getProgress() != 1)
            return 1;
        else if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(),job.getEndDate()) < 0 && job.getProgress() != 1)
            return 3;
        else if(CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate()) < 0 && job.getProgress() == 1){
            return 4;
        }
        return 0;
    }

    public static boolean statusIsChange(Job job){
        int status = job.getStatus();
        job.setStatus(CheckStatus(job));
        if(status != job.getStatus())
            return true;
        return false;
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

    public static List<Job> getJobsChange(List<Job> jobList){
        List<Job> jobs = new ArrayList<Job>();
        for (Job job: jobList) {
            if(statusIsChange(job)){
                jobs.add(job);
            }
        }
        return jobs;
    }

}
