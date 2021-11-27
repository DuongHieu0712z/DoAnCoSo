package com.ctk43.doancoso.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.Model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


@Database(entities = {Category.class, Job.class, JobDetail.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static String DATABASE_NAME = "JobManagement";
    public static AppDatabase instance;

    public abstract CategoryDAO categoryDAO();

    public abstract JobDAO jobDAO();

    public abstract JobDetailDAO jobDetailDAO();

    public abstract UserDAO userDAO();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .addCallback(callback).build();
        }
            return instance;
    }
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PolateDpuBAsyncTask(instance).execute();
        }
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };
    private static class  PolateDpuBAsyncTask extends AsyncTask<Void,Void,Void>{
            private JobDAO jobDAO;
            private PolateDpuBAsyncTask(AppDatabase db){
                jobDAO = db.jobDAO();
            }

        @Override        protected Void doInBackground(Void... voids) {
            Calendar cal = Calendar.getInstance();
            String Date = "31/12/2021";
            Date date;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(Date);
                cal.setTime(date);
            }catch (ParseException e){
                e.printStackTrace();
            }
            cal.set(Calendar.HOUR_OF_DAY, 6);// for 6 hour
            cal.set(Calendar.MINUTE, 0);// for 0 min
            cal.set(Calendar.SECOND, 0);// for 0 sec
            Date start = Calendar.getInstance().getTime();
            Date end = cal.getTime();
                jobDAO.insertJob(new Job("Tên Công Việc 1", "Đây là công việc đầu 1 rat nhieu chu", start,end , true, 0.2));
                jobDAO.insertJob(new Job("Tên Công Việc 2", "Đây là công việc 3 rat nhieu chu", start,end , true, 0.0));
                jobDAO.insertJob(new Job("Tên Công Việc 3", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 0.));
                jobDAO.insertJob(new Job("Tên Công Việc 4", "Đây là công việc đên rat nhieu chu", start,end , true, 0.8));
                jobDAO.insertJob(new Job("Tên Công Việc 5", "Đây là công việc  rat nhieu chu", start,end , true, 0.4));
                jobDAO.insertJob(new Job("Tên Công Việc 6", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 0.5));
                jobDAO.insertJob(new Job("Tên Công Việc 6", "Đây là công việiên rat nhieu chu", start,end , true, 0.5));
                jobDAO.insertJob(new Job("Tên Công Việc 8", "Đây là công việiên rat nhieu chu", start,end , true, 0.5));
                jobDAO.insertJob(new Job("Tên Công Việc 8", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 0.5));
                jobDAO.insertJob(new Job("Tên Công Việc 8", "Đây là công v tiên rat nhieu chu", start,end , true, 0.7));
                jobDAO.insertJob(new Job("Tên Công Việc 9", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 0.7));
                jobDAO.insertJob(new Job("Tên Công Việc 0", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 4", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 5", "Đây là công vtiên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 6", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 7", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 8", "Đây là côngu tiên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 8", "Đây là công việc đầu tiên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 9", "Đây là công iên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 7", "Đây là cônu tiên rat nhieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc ", "Đây là công u thieu chu", start,end , true, 1.0));
                jobDAO.insertJob(new Job("Tên Công Việc 1", "Đây là công vt nhieu chu", start,end , true, 1.0));
            return null;
        }
    }


}
