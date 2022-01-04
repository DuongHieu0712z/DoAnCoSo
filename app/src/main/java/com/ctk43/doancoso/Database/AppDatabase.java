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
import com.ctk43.doancoso.Database.DAO.NotificationModelDAO;
import com.ctk43.doancoso.Database.DAO.UserDAO;
import com.ctk43.doancoso.Database.Repository.NoticationRepository;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.Model.NotificationModel;
import com.ctk43.doancoso.Model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Database(entities = {Category.class, Job.class, JobDetail.class, User.class , NotificationModel.class},
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

    public abstract NotificationModelDAO getNotificationModelDAO();

    private static class SampledData extends AsyncTask<Void, Void, Void> {
        private final CategoryDAO categoryDAO;
        private final JobDAO jobDAO;
        private final JobDetailDAO jobDetailDAO;
        private final UserDAO userDAO;

        private SampledData(AppDatabase db) {
            super();
            categoryDAO = db.getCategoryDAO();
            jobDAO = db.getJobDAO();
            userDAO = db.getUserDAO();
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
            userDAO.insert(new User("default@example.vn","Người dùng"));

            categoryDAO.insert(new Category("Mặc định","default@example.vn"),
                    new Category("Học tập","default@example.vn"),
                    new Category("Giải trí","default@example.vn"));

            jobDAO.insert(new Job(1, "Làm đồ án", CalendarExtension.getEndDateOfWeek(start,-1), CalendarExtension.getEndDateOfWeek(start,0), "Một môn học gây trầm cảm"));
            jobDAO.insert(new Job(1, "Làm lab 8", CalendarExtension.getDateStartOfMonth(2,2022),CalendarExtension.getDateEndOfMonth(5,2022) , "Không hiểu j hết á"));
            jobDAO.insert(new Job(1, "Làm lab 9", CalendarExtension.getDateStartOfMonth(3,2022), CalendarExtension.getDateEndOfMonth(3,2022), "Từ chối hiểu"));
            jobDAO.insert(new Job(1, "Làm web", CalendarExtension.getDateStartOfMonth(4,2022), CalendarExtension.getDateEndOfMonth(4,2022), "Làm kiểu j á"));
            jobDAO.insert(new Job(1, "Genshin +1", CalendarExtension.getEndDateOfWeek(start,0), CalendarExtension.getStartTimeOfWeek(start,1), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(1, "Nấu cum",CalendarExtension.getEndDateOfWeek(start,1), CalendarExtension.getStartTimeOfWeek(start,1), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(1, "Không như mơ ước", CalendarExtension.getStartTimeOfWeek(start,-1), CalendarExtension.getEndTimeOfWeek(start,0), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(1, "Kho cá", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,0), "Vì trầm cảm do làm đồ án"));

            jobDAO.insert(new Job(3, "Cá không", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,1), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",0));
            jobDAO.insert(new Job(3, "Ăn muối", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,2), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",1));
            jobDAO.insert(new Job(3, "cá xem ươn", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,1), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",3));
            jobDAO.insert(new Job(3, "Tìm kiếm theo", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,0), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",2));
            jobDAO.insert(new Job(3, "Code android thú zị", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,1), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",1));
            jobDAO.insert(new Job(3, "Tiệm cận trầm cảm", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,1), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",0));
            jobDAO.insert(new Job(3, "Job management nè", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,1), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",1));
            jobDAO.insert(new Job(3, "Mang hương xuân điều hoà", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,2), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",2));
            jobDAO.insert(new Job(3, "Thú zị", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,4), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",1));
            jobDAO.insert(new Job(3, "Quên hết rồi", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,4), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",3));
            jobDAO.insert(new Job(3, "Vậy là chấm hết rồi đúng không", CalendarExtension.getStartTimeOfWeek(start,-3), CalendarExtension.getEndTimeOfWeek(start,4), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",3));
            jobDAO.insert(new Job(3, "5 phút là quên mất tiuuuu", CalendarExtension.getStartTimeOfWeek(start,-2), CalendarExtension.getEndTimeOfWeek(start,4), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",1));
            jobDAO.insert(new Job(3, "kiến thức vào chuồng gà", CalendarExtension.getStartTimeOfWeek(start,1), CalendarExtension.getEndTimeOfWeek(start,3), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",0));
            jobDAO.insert(new Job(3, "Nằm góc nhà", CalendarExtension.getStartTimeOfWeek(start,-1), CalendarExtension.getEndTimeOfWeek(start,1), "Cá không ăn muối cá ươn, con không nghe lời được gói mang về hehe",0));

            jobDAO.insert(new Job(2, "Đồ án", CalendarExtension.getEndDateOfWeek(start,-1), CalendarExtension.getStartTimeOfWeek(start,1), "Vì trầm cảm do làm đồ án",1));
            jobDAO.insert(new Job(2, "Cơm thừa canh cạn", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,0), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(2, "Kho mém", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,0), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(2, "Nếu không yêu đừng gây thương nhớ, Vì con tim không nghe theo lý trí chẳng thể phía sauuuuuuuu", CalendarExtension.getStartTimeOfWeek(start,-3), CalendarExtension.getEndTimeOfWeek(start,0), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(2, "Anh thuộc về ai mất rồi", CalendarExtension.getStartTimeOfWeek(start,0), CalendarExtension.getEndTimeOfWeek(start,-1), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(2, "Tan vỡ rồi", CalendarExtension.getStartTimeOfWeek(start,-3), CalendarExtension.getEndTimeOfWeek(start,-1), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(2, "Con tim chẳng thể theo lý trí", CalendarExtension.getStartTimeOfWeek(start,-2), CalendarExtension.getEndTimeOfWeek(start,-1), "Vì trầm cảm do làm đồ án"));
            jobDAO.insert(new Job(2, "Chơi game", CalendarExtension.getStartTimeOfWeek(start,-4),CalendarExtension.getEndTimeOfWeek(start,1) , "Không biết nói j cả"));
            jobDAO.insert(new Job(2, "Ghi cho có", CalendarExtension.getStartTimeOfWeek(start,-2), CalendarExtension.getEndTimeOfWeek(start,0), "Ghi cho có"));
            jobDAO.insert(new Job(2, "Đây là một ví dụ",  CalendarExtension.getStartTimeOfWeek(start,-3), CalendarExtension.getEndTimeOfWeek(start,1), "Chỉ là một ví dụ"));

            jobDetailDAO.insert(new JobDetail(1, "Tìm hiểu android", 270, "Android là gì? tôi là ai ai là tôi, thế gian kiếm android android kiếm ios, window phone is dead like a wind"));
            jobDetailDAO.insert(new JobDetail(1, "Tìm hiểu SQLite", 270, "Tìm hiểu thôi"));
            jobDetailDAO.insert(new JobDetail(1, "Làm app demo", 270, "Làm kiểu j á",true));
            jobDetailDAO.insert(new JobDetail(1, "Ghi j bây giờ", 270, "Ghi cho có vậy"));
            jobDetailDAO.insert(new JobDetail(3, "Câu gì á kì lạ", 50, "Tìm hiểu thôi",true));
            jobDetailDAO.insert(new JobDetail(3, "Câu gì á kì lạ", 550, "Tìm hiểu thôi"));
            jobDetailDAO.insert(new JobDetail(3, "Câu gì á kì lạ-1", 1100, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(3, "Cây gì á kì lạày thú vị", 200, "GhiKhơn học được có cái bản chưa nâng cấp nghi lắm"));
            jobDetailDAO.insert(new JobDetail(3, "Câu gì á kì lạ", 100, "Tìm hiểu thôi",true));
            jobDetailDAO.insert(new JobDetail(3, "Khơn học được ", 200, "Tìm hiểu thôi",true));
            jobDetailDAO.insert(new JobDetail(4, "Khơn học được -1", 50, "Làm kiểu j á",true));
            jobDetailDAO.insert(new JobDetail(4, "Khơn học được ày thú vị", 70, "Ghi cho có cái bản chưa nâng cấp nghi lắm"));
            jobDetailDAO.insert(new JobDetail(4, "Khơn học được ", 80, "Tìm hiểu thôi"));
            jobDetailDAO.insert(new JobDetail(4, "Khơn học được ", 40, "Tìm hiểu thôi"));
            jobDetailDAO.insert(new JobDetail(4, "con cá là con cá zàng N-1", 1100, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(4, "con cá là con cá zàng này thú vị", 350, "Ghi cho có cái bản chưa nâng cấp nghi lắm",true));
            jobDetailDAO.insert(new JobDetail(5, "con cá là con cá zàng 2", 123, "Chẳng qua là thú zị kìa"));
            jobDetailDAO.insert(new JobDetail(5, "con cá là con cá zàng 3", 231, "Chẳng qua là thú zị kìa"));
            jobDetailDAO.insert(new JobDetail(5, "con cá là con cá zàng N-1", 157, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(5, "con cá là con cá zàng này thú vị", 3587, "Ghi cho có cái bản chưa nâng cấp nghi lắm",true));
            jobDetailDAO.insert(new JobDetail(5, "con cá là con cá zàng 2", 300, "Chẳng qua là thú zị kìa"));
            jobDetailDAO.insert(new JobDetail(5, "con cá là con cá zàng 3", 300, "Chẳng qua là thú zị kìa"));
            jobDetailDAO.insert(new JobDetail(6, "Người đừng hòng thú zị thế nhở-1", 300, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(6, "Người đừng hòng thú zị thế nhởày thú vị", 300, "Ghi cho có vậy"));
            jobDetailDAO.insert(new JobDetail(6, "Người đừng hòng thú zị thế nhở", 300, "Khi trầm cảm online lên tiếng"));
            jobDetailDAO.insert(new JobDetail(6, "Người đừng hòng thú zị thế nhở", 300, "Khi trầm cảm online lên tiếng",true));
            jobDetailDAO.insert(new JobDetail(6, "Người đừng hòng thú zị thế nhở-1", 300, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(6, "Người đừng hòng thú zị thế nhởày thú vị", 300, "Ghi cho có vậy"));
            jobDetailDAO.insert(new JobDetail(7, "Người đừng hòng thú zị thế nhở", 300, "Khi trầm cảm online lên tiếng"));
            jobDetailDAO.insert(new JobDetail(7, "Người đừng hòng thú zị thế nhở", 300, "Khi trầm cảm online lên tiếng",true));
            jobDetailDAO.insert(new JobDetail(7, "C-1", 300, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(7, "Cày thú vị", 300, "Ghi cho có vậy"));
            jobDetailDAO.insert(new JobDetail(7, "C", 300, "Mũi tên trái phải trên dưới alo",true));
            jobDetailDAO.insert(new JobDetail(7, "C", 300, "Mũi tên trái phải trên dưới alo",true));
            jobDetailDAO.insert(new JobDetail(8, "C-1", 300, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(8, "Cày thú vị", 300, "Ghi cho có vậy"));
            jobDetailDAO.insert(new JobDetail(8, "C", 300, "Mũi tên trái phải trên dưới alo",true));
            jobDetailDAO.insert(new JobDetail(8, "3", 300, "Mũi tên trái phải trên dưới alo",true));
            jobDetailDAO.insert(new JobDetail(8, "Câu N-1", 300, "Làm kiểu j á"));
            jobDetailDAO.insert(new JobDetail(8, "Cây này thú vị", 300, "Ghi cho có vậy"));

            return null;
        }
    }
}
