package com.ctk43.doancoso.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ctk43.doancoso.Database.DAO.CategoryDAO;
import com.ctk43.doancoso.Database.DAO.JobDAO;
import com.ctk43.doancoso.Database.DAO.JobDetailDAO;
import com.ctk43.doancoso.Database.DAO.UserDAO;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.Model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Database(entities = {Category.class, Job.class, JobDetail.class, User.class},
        version = 1,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "JobManagement.db";

    private static final RoomDatabase.Callback CALLBACK = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new SampledData(instance).execute();
        }
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    private static AppDatabase instance;

    public static AppDatabase getInstance(final Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DATABASE_NAME)
                    .addCallback(CALLBACK)
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract CategoryDAO getCategoryDAO();

    public abstract JobDAO getJobDAO();

    public abstract JobDetailDAO getJobDetailDAO();

    public abstract UserDAO getUserDAO();

    private static class SampledData extends AsyncTask<Void, Void, Void> {
        private final CategoryDAO categoryDAO;
        private final JobDAO jobDAO;
        private final JobDetailDAO jobDetailDAO;

        private SampledData(AppDatabase db) {
            super();
            categoryDAO = db.getCategoryDAO();
            jobDAO = db.getJobDAO();
            jobDetailDAO = db.getJobDetailDAO();
        }

        @SuppressLint("SimpleDateFormat")
        @Override
        protected Void doInBackground(Void... voids) {
            Calendar calendar = Calendar.getInstance();
            String Date = "31/12/2021";
            Date date;
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(Date);
                assert date != null;
                calendar.setTime(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            calendar.set(Calendar.HOUR_OF_DAY, 6); // for 6 hour
            calendar.set(Calendar.MINUTE, 0); // for 0 min
            calendar.set(Calendar.SECOND, 0); // for 0 sec
            Date start = Calendar.getInstance().getTime();
            Date end = calendar.getTime();

            categoryDAO.insert(new Category("Học tập"), new Category("Giải trí"));

            jobDAO.insert(new Job(1, "Làm đồ án", start, end, "Một môn học gây trầm cảm"));
            jobDAO.insert(new Job(1, "Làm lab 8", start, end, "Không hiểu j hết á"));
            jobDAO.insert(new Job(1, "Làm lab 9", start, end, "Từ chối hiểu"));
            jobDAO.insert(new Job(1, "Làm web", start, end, "Làm kiểu j á"));

            jobDAO.insert(new Job(2, "Khóc", start, end, "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(2, "Chơi game", start, end, "Không biết nói j cả"));
            jobDAO.insert(new Job(2, "Ghi cho có", start, end, "Ghi cho có"));
            jobDAO.insert(new Job(2, "Đây là một ví dụ", start, end, "Chỉ là một ví dụ"));

            jobDetailDAO.insert(new JobDetail(1, "Tìm hiểu android", 300, "Tìm hiểu thôi"));
            jobDetailDAO.insert(new JobDetail(1, "Tìm hiểu SQLite", 300, "Tìm hiểu thôi"));
            jobDetailDAO.insert(new JobDetail(1, "Làm app demo", 300, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(1, "Ghi j bây giờ", 300, "Ghi cho có vậy"));

            return null;
        }
    }
}
