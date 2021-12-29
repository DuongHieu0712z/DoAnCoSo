package com.ctk43.doancoso.Library;

import com.ctk43.doancoso.R;

public class GeneralData {

    public static final int  STATUS_COMING = 0;
    public static final int  STATUS_ON_GOING = 1;
    public static final int  STATUS_FINISH = 2;
    public static final int  STATUS_OVER = 3;
    public static final int  STATUS_FINISH_LATE = 4;

    private static final int[] imgPriority = {
            R.drawable.ic_baseline_star_outline_24,
            R.drawable.ic_baseline_star_priority_normal,
            R.drawable.ic_baseline_star_priority_important,
            R.drawable.ic_baseline_star_priority_very_important
    };

    public static final int[] status = {
            R.string.coming_soon,
            R.string.on_going,
            R.string.complete,
            R.string.over,
            R.string.over_complete
    };

    public static final int[] statusTime = {
            R.string.time,
            R.string.time_remaining,
            R.string.time_over
    };

    private static final int[] statusColor = {
            R.color.in_coming,
            R.color.on_ongoing,
            R.color.complete,
            R.color.over,
            R.color.over_complete,
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

    public static int getTimeTitle(int status) {
        if(status>1)
            return statusTime[2];
        else
            return statusTime[status];
    }
}
