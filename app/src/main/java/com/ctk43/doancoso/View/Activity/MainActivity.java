package com.ctk43.doancoso.View.Activity;



import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.Service.NotificationService;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
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
    private SearchView searchView;
    private MenuItem addition_menu;
    private MenuItem notificationManagement;

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
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                JobViewModel jobViewModel = new JobViewModel();
                jobViewModel.setData(MainActivity.this);
                JobAdapter jobAdapter = new JobAdapter(MainActivity.this, jobViewModel);
                jobViewModel.setData(MainActivity.this);
                RecyclerView rcv = findViewById(R.id.rcv_display_job);
                jobViewModel.getJobs().observe(MainActivity.this, jobs -> {
                    jobAdapter.setJob(jobs);
                    rcv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rcv.setAdapter(jobAdapter);
                    jobAdapter.getFilter().filter(query);
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                JobViewModel jobViewModel = new JobViewModel();
                jobViewModel.setData(MainActivity.this);
                JobAdapter jobAdapter = new JobAdapter(MainActivity.this, jobViewModel);
                jobViewModel.setData(MainActivity.this);
                RecyclerView rcv = findViewById(R.id.rcv_display_job);
                rcv.setAdapter(jobAdapter);

                jobViewModel.getJobs().observe(MainActivity.this, jobs -> {
                    jobAdapter.setJob(jobs);
                    rcv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    rcv.setAdapter(jobAdapter);
                    jobAdapter.getFilter().filter(newText);
                });
                return false;
            }
        });

        //category management
        addition_menu = menu.findItem(R.id.addition_menu);
        addition_menu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                onOpenMenuDialog();
                return true;

            }
        });

        //notification management
        notificationManagement = menu.findItem(R.id.menu_item_notification);
        notificationManagement.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(MainActivity.this, NotificationManagementActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }

    private void init() {
        StartService();
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
                    case R.id.menu_add_new_job:
                        Intent intent = new Intent(MainActivity.this, AddJobActivity.class);
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

    }
    private void StartService(){
        Intent intent = new Intent(this, NotificationService.class);
        this.startService(intent);
    }
    public void SelectBottomMenuPosition(int position){
        bottomMenu.getMenu().getItem(position).setChecked(true);
        viewPager.setCurrentItem(0);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    private void onOpenMenuDialog(){
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu);
        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.RIGHT;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(true);

        LinearLayout ln_category_management = dialog.findViewById(R.id.dialog_menu_category);
        ln_category_management.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CategoryManagementActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
