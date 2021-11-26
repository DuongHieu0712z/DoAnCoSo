package com.ctk43.doancoso.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Sqlite_Helper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "JobManagement.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;
    private SQLiteDatabase mDatabase;
    public Sqlite_Helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
