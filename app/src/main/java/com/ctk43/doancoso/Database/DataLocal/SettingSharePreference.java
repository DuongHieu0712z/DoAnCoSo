package com.ctk43.doancoso.Database.DataLocal;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingSharePreference {
    private static final String SETTING = "SETTING_SHARE_PREFERENCE";
    private final Context mContext;

    public SettingSharePreference(Context mContext) {
        this.mContext = mContext;
    }

    public void putBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public boolean getBoolean(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    public void putString(String key, String value) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key) {
        SharedPreferences sharedPreferences = mContext.getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "default@example.vn");
    }
}
