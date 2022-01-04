package com.ctk43.doancoso.View.Activity;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.ctk43.doancoso.Library.DialogExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.View.Adapter.ViewPagerAdapter;
import com.ctk43.doancoso.View.Adapter.ViewPagerJobAdapter;
import com.ctk43.doancoso.View.Fragment.JobFragment;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobActivity extends AppCompatActivity {
    LiveData<List<Job>> jobs;
    JobViewModel jobViewModel;
    TabLayout tabLayout;
    ViewPager2 viewPager;
    String name;
    private SearchView searchView;
    private MenuItem addition_menu;
    private MenuItem notificationManagement;
    JobFragment jobFragment;
    boolean dateToDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_manager);
        intiViewModel();
        getData();
        init();
    }

    private void intiViewModel() {
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.setData(this);
    }

    private void init(){
        jobFragment = new JobFragment();
        jobFragment.setJobs(jobs);
        getSupportFragmentManager().beginTransaction().add(R.id.layout_jobs,jobFragment).commit();
        ImageButton img_btn_filter = findViewById(R.id.img_btn_filter);
        ImageButton img_btn_convert = findViewById(R.id.img_btn_convert);
        Spinner spn_category = findViewById(R.id.spn_category);
        FloatingActionButton fab_add_job =findViewById(R.id.fab_add_job);
        ArrayAdapter adapter_spn = new ArrayAdapter(JobActivity.this, android.R.layout.simple_spinner_item, Collections.singletonList((name))) ;
        adapter_spn.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_category.setAdapter(adapter_spn);
        img_btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogExtension.showDialogFilterJob(JobActivity.this, dateToDate,jobFragment.getAdapter());
            }
        });

        img_btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JobAdapter adapter = jobFragment.getAdapter();
                adapter.Revert();
            }
        });
        if(dateToDate)
        fab_add_job.setOnClickListener(v -> {
            Intent intent = new Intent(this,AddJobActivity.class);
            startActivity(intent);
        });
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
                    JobAdapter adapter = jobFragment.getAdapter();
                    adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search) {
                    JobAdapter adapter = jobFragment.getAdapter();
                    adapter.getFilter().filter(search);
                return false;
            }
        });
        addition_menu = menu.findItem(R.id.addition_menu);
        addition_menu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DialogExtension.onOpenMenuDialog(JobActivity.this);
                return true;
            }
        });
        notificationManagement = menu.findItem(R.id.menu_item_notification);
        notificationManagement.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Intent intent = new Intent(JobActivity.this, NotificationManagementActivity.class);
                startActivity(intent);
                return true;
            }
        });
        return true;
    }
    private void getData(){
        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            Job job = (Job) bundle.get(Key.SEND_JOB);
            boolean dateToDate = (boolean) bundle.get(Key.DATE_TO_DATE);
            this.dateToDate = !dateToDate;
            name = job.getName();
            setJobs(job,dateToDate);
        }
    }


    public LiveData<List<Job>> getJobs() {
        return jobs;
    }

    public void setJobs(Job job, boolean dateToDate) {
        if(dateToDate){
            if(job.getStatus() !=-1){
                jobs = jobViewModel.getJobsStatus(job.getStatus(),job.getStartDate(),job.getEndDate());
            }else{
                jobs = jobViewModel.getJobs(job.getStartDate(),job.getEndDate());
            }
        }else {
            if(job.getCategoryId() !=0){
                jobs = jobViewModel.getByCategoryId(job.getCategoryId());
            }
        }

    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
