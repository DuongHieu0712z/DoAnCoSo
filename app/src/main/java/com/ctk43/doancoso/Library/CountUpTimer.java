package com.ctk43.doancoso.Library;

import android.os.CountDownTimer;

public abstract class CountUpTimer extends CountDownTimer {
    private static  final  long INTERVAL_MS = 1000;
    private static final long DURATIONMS = 604800000;


    public CountUpTimer() {
        super(DURATIONMS, INTERVAL_MS);
    }

    public abstract void onTick(int second);
    @Override
    public void onTick(long msUntilFinished) {
        int second = (int) ((DURATIONMS - msUntilFinished)/200 );
        onTick(second);
    }

    @Override
    public void onFinish() {
        onTick(DURATIONMS / 1000);
    }
}
