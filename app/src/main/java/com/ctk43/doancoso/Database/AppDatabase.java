package com.ctk43.doancoso.Database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
    private static String DATABASE_NAME = "JobManagement.db";
    private static AppDatabase instance;

    public abstract CategoryDAO categoryDAO();

    public abstract JobDAO jobDAO();

    public abstract JobDetailDAO jobDetailDAO();

    public abstract UserDAO userDAO();

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    AppDatabase.class, DATABASE_NAME)
                    .addCallback(callback)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    /*    public static AppDatabase getInstance(final Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, DATABASE_NAME)
                        .allowMainThreadQueries()
                        .addCallback(new Callback() {
                            @Override
                            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                                super.onCreate(db);
                                new prePopulateData(instance).execute();
                            }
                        }).build();
            }
            return instance;
        }*/
    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new prePopulateData(instance).execute();

        }

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static class prePopulateData extends AsyncTask<Void, Void, Void> {
        private JobDAO jobDAO;
        private JobDetailDAO jobDetailDAO;
        private CategoryDAO categoryDAO;


        private prePopulateData(AppDatabase db) {
            categoryDAO = db.categoryDAO();
            jobDAO = db.jobDAO();
            jobDetailDAO = db.jobDetailDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Log.e("database","đang trong databse");
            Calendar cal = Calendar.getInstance();
            String Date = "31/12/2021";
            Date date;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(Date);
                cal.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cal.set(Calendar.HOUR_OF_DAY, 6);// for 6 hour
            cal.set(Calendar.MINUTE, 0);// for 0 min
            cal.set(Calendar.SECOND, 0);// for 0 sec
            Date start = Calendar.getInstance().getTime();
            Date end = cal.getTime();
            categoryDAO.insert(new Category("default"));
            jobDAO.insertJob(new Job(1,"Làm ", "Đây là công việc đầu 1 rat nhieu chu", start,end , true, 0.0));
            jobDAO.insertJob(new Job(1,"Đi chơi", "Đây là công việc đầu 1 rat nhieu chu", start,end , false, 0.0));
            jobDAO.insertJob(new Job(1,"Bài tập", "Đây là công việc đầu 1 rat nhieu chu", start,end , true, 0.0));
            jobDAO.insertJob(new Job(1,"Cua kỳ cục", "Đây là công việc đầu 1 rat nhieu chu", start,end , false, 0.0));
            jobDAO.insertJob(new Job(1,"Android", "Đây là công việc đầu 1 rat nhieu chu", start,end , false, 0.0));
            jobDAO.insertJob(new Job(1,"Em ăn cơm chưa", "Đây là công việc đầu 1 rat nhieu chu", start,end , true, 0.0));
            jobDAO.insertJob(new Job(1,"Em ăn uống rồi", "Đây là công việc đầu 1 rat nhieu chu", start,end , false, 0.0));
            jobDAO.insertJob(new Job(1,"Thuaaaaaaa", "Đây là công việc đầu 1 rat nhieu chu", start,end , false, 0.0));
            jobDAO.insertJob(new Job(1,"Thắng", "Đây là công việc đầu 1 rat nhieu chu", start,end , true, 0.0));
            jobDAO.insertJob(new Job(1,"Daily", "Đây là công việc đầu 1 rat nhieu chu", start,end , false, 0.0));

            jobDetailDAO.insertJobDetail(new JobDetail(1,"Học lý thuyết trường","Thêm 1 xíu về gì gì đó",5 ));
            jobDetailDAO.insertJobDetail(new JobDetail(1,"Học lý trường trọng lực","Thêm 1 xíu về gì gì đó",7 ));
            jobDetailDAO.insertJobDetail(new JobDetail(1,"Học lý thuyết trường kinh tế","Thêm 1 xíu về gì gì đó",6 ));
            jobDetailDAO.insertJobDetail(new JobDetail(2,"Học lý luận cùn","Thêm 1 xíu về gì gì đó",1 ));
            jobDetailDAO.insertJobDetail(new JobDetail(3,"làm không chơi kỳ lạ","Thêm 1 xíu về gì gì đó",3 ));
            jobDetailDAO.insertJobDetail(new JobDetail(4,"Chơi không làm","Thêm 1 xíu về gì gì đó",4 ));
            return null;

        }
    }
}
