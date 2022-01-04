package com.ctk43.doancoso.Library;

import android.content.Context;

import com.ctk43.doancoso.R;

public class GeneralData {


    public static final int  STATUS_COMING = 1;
    public static final int  NON_STATUS = -1;
    public static final int  STATUS_ON_GOING = 0;
    public static final int  STATUS_FINISH = 3;
    public static final int  STATUS_OVER = 2;
    public static final int  STATUS_FINISH_LATE = 4;

    public static final String  STATUS_NOTIFICATION_ACTIVE = "A";
    public static final String  STATUS_NOTIFICATION_SEEN = "S";
    public static final String  STATUS_NOTIFICATION_DELETE = "D";

    public static final int  STATUS_DETAIL_FINISH = 1;
    public static final int  STATUS_DETAIL_ONGOING = 0;
    public static final int ID_CATEGORY_ALL = 0;
    public static final int ID_CATEGORY_WEEK = -1;
    public static final int ID_CATEGORY_MONTH = -2;

    private static final int[] imgPriority = {
            R.drawable.ic_baseline_star_outline_24,
            R.drawable.ic_baseline_star_priority_normal,
            R.drawable.ic_baseline_star_priority_important,
            R.drawable.ic_baseline_star_priority_very_important
    };
    public static final int[] priorities={
            R.string.priority_0,
            R.string.priority_1,
            R.string.priority_2,
            R.string.priority_3
    };

    public static final int[] status = {
            R.string.on_going,
            R.string.coming_soon,
            R.string.over,
            R.string.finish,
            R.string.finish_late
    };

    public static final int[] statusTime = {
            R.string.time,
            R.string.time_remaining,
            R.string.time_over
    };

    private static final int[] statusColor = {
            R.color.on_ongoing,
            R.color.in_coming,
            R.color.over,
            R.color.finish,
            R.color.fisnish_late,
    };

    public static int getColorStatus(int status){
            return statusColor[status];
    }

    public static int getImgPriority(int priority) {
        return imgPriority[priority];
    }

    public static int getStatus(int sta) {
        return status[sta];
    }

    public static int getPriority(int priority) {
        return priorities[priority];
    }

    public static int getTimeTitle(int status) {
        if(status>1)
            return statusTime[2];
        else
            return statusTime[status];
    }
    public static String[] getListPriority(Context context){
            String string[] = new String[priorities.length];
            for (int i =0;i<priorities.length ;i++ )
                    string[i] = context.getString(priorities[i]);
            return string;
    }
}
