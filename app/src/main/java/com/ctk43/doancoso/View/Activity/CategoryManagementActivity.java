package com.ctk43.doancoso.View.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.CalendarAdapter;
import com.ctk43.doancoso.View.Adapter.CategoryManagementAdapter;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.List;

public class CategoryManagementActivity extends AppCompatActivity {
    RecyclerView rcv_categories;
    CategoryViewModel categoryViewModel;
    SearchView searchView;
    CategoryManagementAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management);
        innitView();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_management_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_category_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        innitView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    private void innitView() {
        getSupportActionBar().setTitle("Quản lý danh mục");
        rcv_categories = findViewById(R.id.rcv_categories);
        categoryViewModel = new CategoryViewModel();
        categoryViewModel.setContext(CategoryManagementActivity.this);
        List<Category> categories = categoryViewModel.getCategoryList();

        adapter = new CategoryManagementAdapter(CategoryManagementActivity.this, categories);
        rcv_categories.setLayoutManager(new LinearLayoutManager(CategoryManagementActivity.this));

        rcv_categories.setAdapter(adapter);


    }
}
