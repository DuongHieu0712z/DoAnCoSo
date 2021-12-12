package com.ctk43.doancoso.Database.DataLocal;

import android.content.Context;

public class DataLocalManager {
    private static final String PREF_EMAIL = "PREF_EMAIL";
    private static DataLocalManager instance;
    private SettingSharePrefence settingSharePrefence;
  //  private static final String Email ;
    public static void init(Context context){
        instance = new DataLocalManager();
        instance.settingSharePrefence = new SettingSharePrefence(context);
    }

    public static DataLocalManager getInstance(){
        if(instance == null){
            instance = new DataLocalManager();
        }
        return instance;
    }

    public static void setEmail(String email){
        DataLocalManager.getInstance().settingSharePrefence.putString(PREF_EMAIL,email);
    }

    public static String getEmail(){
        return DataLocalManager.getInstance().settingSharePrefence.getString(PREF_EMAIL);
    }
}
