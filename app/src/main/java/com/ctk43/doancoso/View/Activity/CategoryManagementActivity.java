package com.ctk43.doancoso.View.Activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.DialogExtension;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.CategoryManagementAdapter;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class CategoryManagementActivity extends AppCompatActivity {
    RecyclerView rcv_categories;
    CategoryViewModel categoryViewModel;
    SearchView searchView;
    CategoryManagementAdapter adapter;
    FloatingActionButton fab_add_cate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management);
        initView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.category_management_menu, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_category_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        initView();

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

    private void initView() {
        rcv_categories = findViewById(R.id.rcv_categories);
        rcv_categories.setLayoutManager(new LinearLayoutManager(this));

        categoryViewModel = new CategoryViewModel();
        categoryViewModel.setContext(this);
        categoryViewModel.getCategories().observe(this, categories -> {
            adapter = new CategoryManagementAdapter(this, categories);
            rcv_categories.setAdapter(adapter);
        });

        fab_add_cate = findViewById(R.id.fab_add_category);
        fab_add_cate.setOnClickListener(v -> DialogExtension.onOpenCategoryDiaLog(this, categoryViewModel, null));
    }
}
