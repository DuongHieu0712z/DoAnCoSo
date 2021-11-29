package com.ctk43.doancoso.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Update;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public String currentDate;
    public List<Job> listjob = new ArrayList<>();
    private static final String DATABASE_NAME = "databases/JobManagement.db";
    private static final String DB_PATH_SUFFIX  = "/databases/";
    private TabLayout tabLayout;
    private JobViewModel jobViewModel;
    private ViewPager viewPager;
    private int dlg_mode = 0;
    public String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //   Job[] PopulateMovieData =populateMovieData();
        JobRepository jobRepository = new JobRepository(this);
        // jobRepository.insert(PopulateMovieData[0]);
        listjob = jobRepository.getAlljob();
        showFrg(new Splast_Fragment());

    }
    public static Job[] populateMovieData(){
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
        return new Job[]{

                new Job(1,"Tên Công Việc 2", "Đây là công việc 3 rat nhieu chu", start,end , true, 0.0),
        };
    }
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
    public void gotoAddNewJobScreen(){
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new
                AddJobFragment(), null).commit();
    }
    public void gotoShowDialogScreen(int mode){
        dlg_mode=mode;
        DialogFragment dateDialog = new DatePickerFragment();
        FragmentManager fm = getSupportFragmentManager();
        dateDialog.show(getFragmentManager(), "");
    }
    public void gotoShowTimeDialogScreen(int mode){
        dlg_mode=mode;
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "time picker");
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        /*TextView tv_date;
        if(dlg_mode==0){
            tv_date = findViewById(R.id.tv_dlg_date_start);
            tv_date.setText(currentDate);
        }
        else{
            tv_date = findViewById(R.id.tv_dlg_date_end);
            tv_date.setText(currentDate);
        }*/
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        /*TextView textView;
        if(dlg_mode==0)
            textView = (TextView) findViewById(R.id.tv_dlg_time_start);
        else
            textView = (TextView) findViewById(R.id.tv_dlg_time_end);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);*/
        result="Hour: " + hourOfDay + " Minute: " + minute;
    }
    public String getDatabasePathstring(){
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX +DATABASE_NAME;
    }
    private void CoppyDataBaseFormAsset(){
        try{
            InputStream myInput;
            myInput = getAssets().open(DATABASE_NAME);
            String outputFileName = getDatabasePathstring();
            File f = new File(getApplicationInfo().dataDir+DB_PATH_SUFFIX+DATABASE_NAME);
            if(!f.exists())
                f.mkdir();
            OutputStream myOutput = new FileOutputStream(outputFileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer))>0){
                myOutput.write(buffer,0,length);
                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
        } catch (IOException e) {
            Log.e("error_coppy_database",e.toString());
        }
    }
    public void progressCopyDataBase(){
        File dbFile = getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists())
            CoppyDataBaseFormAsset();
        Toast.makeText(this,"ALOOOOOOOOO coppy_database",Toast.LENGTH_LONG).show();

    }
}
