package com.ctk43.doancoso.Service;

import static com.ctk43.doancoso.Library.Key.SEND_ACTION;
import static com.ctk43.doancoso.Library.Key.SEND_JOB_DETAIL_BY_SERVICE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.ctk43.doancoso.Library.Action;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.JobDetail;

public class CountUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        context.startService(intent);
    }
}
