package com.ctk43.doancoso.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
//import androidx.room.Update;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.Reponsitory.JobRepository;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.Adapter.JobAdapter;
import com.ctk43.doancoso.ViewModel.Adapter.JobViewModel;
import com.ctk43.doancoso.ViewModel.Adapter.MyApplication;
import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity  {
    public String currentDate;
    public List<Job> listjob = new ArrayList<>();
//    public LiveData<List<Job>> listjob ;
    private static final String DATABASE_NAME = "databases/JobManagement.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    private TabLayout tabLayout;
    private JobViewModel jobViewModel;
    private ViewPager viewPager;
    private int dlg_mode = 0;
    public String result = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   Job[] PopulateMovieData =populateMovieData();
        // jobRepository.insert(PopulateMovieData[0]);
       // listjob = jobRepository.getAlljob();
       // jobViewModel.setData(jobRepository.getAlljob());
       jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        showFrg(new Splast_Fragment());
    }
<<<<<<< HEAD
  /*  public static Job[] populateMovieData(){
=======

    public static Job[] populateMovieData() {
>>>>>>> c73e58aa893e6c809d4afaaba9c88be8f71da0b5
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
        return new Job[]{

                new Job(1, "Tên Công Việc 2", "Đây là công việc 3 rat nhieu chu", start, end, true, 0.0),
        };
    }*/
   /* private void initViewMobdel() {
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.getAllJob().observe(this, new Observer<List<Job>>() {
            @Override
            public void onChanged(List<Job> jobs) {
                listjob = jobs;
            }
        });
    }*/

    private void showFrg(Fragment frg) {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, frg,
                null).commit();
    }

    public void gotoM001Screen() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new MainFragment(), null).commit();
    }

    public void gotoM002Screen(Job job) {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new JobDetailFragment(job), null).commit();
    }

    public void gotoAddNewJobScreen() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new
                AddJobFragment(), null).commit();
    }

    public void gotoShowDialogScreen(int mode) {
        dlg_mode = mode;
        DialogFragment dateDialog = new DatePickerFragment();
        FragmentManager fm = getSupportFragmentManager();
        dateDialog.show(getFragmentManager(), "");
    }

    public void gotoShowTimeDialogScreen(int mode) {
        dlg_mode = mode;
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "time picker");
    }
    public String getDatabasePathstring() {
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME;
    }

    private void CoppyDataBaseFormAsset() {
        try {
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outputFileName = getDatabasePathstring();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX + DATABASE_NAME);
            if (!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        } catch (IOException e) {
            Log.e("error_coppy_database", e.toString());
        }
    }

    public void progressCopyDataBase() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())
            CoppyDataBaseFormAsset();
        Toast.makeText(this, "ALOOOOOOOOO coppy_database", Toast.LENGTH_LONG).show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }
}
