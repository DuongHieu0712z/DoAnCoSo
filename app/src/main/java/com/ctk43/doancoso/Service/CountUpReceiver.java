package com.ctk43.doancoso.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ctk43.doancoso.Library.Action;
import com.ctk43.doancoso.Library.Key;

public class CountUpReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(@NonNull Context context, Intent intent) {
        int action = intent.getIntExtra(Key.SEND_ACTION, Action.NONE);
        Intent service = new Intent(context, CountUpService.class);
        service.putExtra(Key.SEND_ACTION, action);
        context.startService(service);
    }
}
