package com.ctk43.doancoso.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sqlite_Helper extends SQLiteOpenHelper {
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
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
