package com.ctk43.doancoso.Database.DataLocal;

import android.content.Context;


public class DataLocalManager {
    private static final String PREF_EMAIL = "PREF_EMAIL";
    private static final String PREF_TIME_UP = "PREF_TIME_UP";
    private static final String KEY_TIME_UP = "KEY_TIME_UP";

    private static DataLocalManager instance;
    public SettingSharePreference settingSharePreference;

    //  private static final String Email ;
    public static void init(Context context) {
        instance = new DataLocalManager();
        instance.settingSharePreference = new SettingSharePreference(context);
    }

    public static DataLocalManager getInstance() {
        if (instance == null) {
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static String getEmail() {
        return getInstance().settingSharePreference.getString(PREF_EMAIL);
    }

    public static void setEmail(String email) {
        getInstance().settingSharePreference.putString(PREF_EMAIL, email);
    }
}
