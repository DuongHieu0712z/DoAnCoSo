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
import android.widget.Adapter;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.ctk43.doancoso.Library.DialogExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.Service.NotificationService;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.View.Adapter.ViewPagerAdapter;
import com.ctk43.doancoso.View.Fragment.JobFragment;
import com.ctk43.doancoso.View.Fragment.ManagerJobFragment;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomMenu;
    private JobViewModel jobViewModel;
    private ViewPager2 viewPager;
    private int dlg_mode = 0;
    ViewPagerAdapter viewPagerAdapter;
    private SearchView searchView;
    private MenuItem addition_menu;
    private MenuItem notificationManagement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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
                if(viewPager.getCurrentItem() == 0){
                    Extension.filterSearch(viewPager,viewPagerAdapter,query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String search) {
                if(viewPager.getCurrentItem() == 0){
                    Extension.filterSearch(viewPager,viewPagerAdapter,search);
                }
                return false;
            }
        });
        addition_menu = menu.findItem(R.id.addition_menu);
        addition_menu.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                DialogExtension.onOpenMenuDialog(MainActivity.this);
                return true;
            }
        });
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
        viewPagerAdapter= new ViewPagerAdapter(this);
        viewPager.setAdapter(viewPagerAdapter);
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
                        SelectBottomMenuPosition(0);
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

}
