package com.ctk43.doancoso.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sqlite_Helper extends SQLiteOpenHelper {
<<<<<<< HEAD:app/src/main/java/com/ctk43/doancoso/Database/Sqlite_Helper.java
    private static final String DATABASE_NAME = "databases/JobManagement.db";
    private static final int DATABASE_VERSION = 1;
    private static Context mcontext;
    private static String mpathDatabase;
    private SQLiteDatabase db;
    private static Sqlite_Helper instance;
    public static Sqlite_Helper getInstance(@Nullable Context context){
        if (instance == null){
            instance = new Sqlite_Helper(context);
            mcontext = context;
        }
        return instance;
    }
    public void getPath (String pathDatabase){
        mpathDatabase = pathDatabase;
    }

    private Sqlite_Helper(@Nullable Context context) {
=======
    private static final String DATABASE_NAME = "JobManagement";
    private static final int DATABASE_VERSION = 1;

    public Sqlite_Helper(@Nullable Context context) {
>>>>>>> parent of 1bb4a00 (thanh):app/src/main/java/com/ctk43/doancoso/Model/Sqlite_Helper.java
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
