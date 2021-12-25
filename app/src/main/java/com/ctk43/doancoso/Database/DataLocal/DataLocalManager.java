package com.ctk43.doancoso.Database.DataLocal;

import android.content.Context;
import android.content.SharedPreferences;

public class DataLocalManager {
    private static final String PREF_EMAIL = "PREF_EMAIL";
    private static final String PREF_TIME_UP = "PREF_TIME_UP";
    private static final String KEY_TIME_UP = "KEY_TIME_UP";

    private static DataLocalManager instance;
    private SettingSharePrefence settingSharePrefence;
    Context context;

  //  private static final String Email ;
    public static void init(Context context){
        instance = new DataLocalManager();
        instance.settingSharePrefence = new SettingSharePrefence(context);
        instance.context = context;
    }

    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static int getTime(){
        SharedPreferences timeSharePrefence =  instance.context.getSharedPreferences(PREF_TIME_UP,Context.MODE_PRIVATE);
        return timeSharePrefence.getInt(KEY_TIME_UP,0);
    }
    public static void setTime(int time){
        SharedPreferences timeSharePrefence =  instance.context.getSharedPreferences(PREF_TIME_UP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = timeSharePrefence.edit();
        editor.putInt(PREF_TIME_UP,time);
        editor.apply();
    }

    public static void setEmail(String email){
        DataLocalManager.getInstance().settingSharePrefence.putString(PREF_EMAIL,email);
    }

    public static String getEmail(){
        return DataLocalManager.getInstance().settingSharePrefence.getString(PREF_EMAIL);
    }
}
