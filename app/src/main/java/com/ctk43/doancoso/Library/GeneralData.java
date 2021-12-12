package com.ctk43.doancoso.Library;

import com.ctk43.doancoso.R;

public class GeneralData {
    private static GeneralData instance;
    private static final int[] imgPriority = {
            R.drawable.ic_baseline_star_outline_24,
            R.drawable.ic_baseline_star_24};

    public static final int[] status = {
            R.string.coming_soon,
            R.string.on_going,
            R.string.complete,
            R.string.over
    };

    public static final int[] statusTime = {
            R.string.time,
            R.string.time_remaining,
            R.string.time_over,
    };

    private static final int[] statusColor = {
            R.color.in_coming,
            R.color.on_ongoing,
            R.color.complete,
            R.color.over,
    };

    public static GeneralData getInstance() {
        if (instance == null)
            instance = new GeneralData();
        return instance;
    }

    public static int getColorStatus(int status){
            return (getInstance().statusColor[status]);

    }

    public static int getImgPriority(int priority) {
        return getInstance().imgPriority[priority];
    }

    public static int getStatus(int status) {
        return getInstance().status[status];
    }

    public static int getTimeTitle(int status) {
        if(status>1)
            return getInstance().statusTime[2];
        else
            return getInstance().statusTime[status];
    }
}
