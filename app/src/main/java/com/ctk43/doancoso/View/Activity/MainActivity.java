package com.ctk43.doancoso.View.Activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.ViewPagerAdapter;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    //    public LiveData<List<Job>> listjob ;
    private static final String DATABASE_NAME = "databases/JobManagement.db";
    private static final String DB_PATH_SUFFIX = "/databases/";
    public String currentDate;
    public List<Job> listjob = new ArrayList<>();
    public String result = "";
    private TabLayout tabLayout;
    private BottomNavigationView bottomMenu;
    private JobViewModel jobViewModel;
    private ViewPager2 viewPager;
    private int dlg_mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        // jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        //   Job[] PopulateMovieData =populateMovieData();
        // jobRepository.insert(PopulateMovieData[0]);
        // listjob = jobRepository.getJobs();
        // jobViewModel.setContext(jobRepository.getJobs());

        // showFrg(new Splast_Fragment());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    private void init() {
        viewPager = findViewById(R.id.view_pager_main);
        bottomMenu = findViewById(R.id.bottom_Menu);
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setUserInputEnabled(false);
        bottomMenu.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id) {
                    case R.id.menu_job:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.menu_month:
                        viewPager.setCurrentItem(1);
                        break;
                    case R.id.menu_setting:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.menu_account:
                        viewPager.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });

    }
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
        jobViewModel.getJobs().observe(this, new Observer<List<Job>>() {
            @Override
            public void onChanged(List<Job> jobs) {
                listjob = jobs;
            }
        });
    }*/
/*
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
    }*/
}
