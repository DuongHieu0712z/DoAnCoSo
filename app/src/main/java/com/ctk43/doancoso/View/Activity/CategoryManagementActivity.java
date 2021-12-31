package com.ctk43.doancoso.View.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.CalendarAdapter;
import com.ctk43.doancoso.View.Adapter.CategoryManagementAdapter;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;

import java.util.List;

public class CategoryManagementActivity extends AppCompatActivity {
    RecyclerView rcv_categories;
    CategoryViewModel categoryViewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_management);
        innitView();
    }

    private void innitView() {
        rcv_categories = findViewById(R.id.rcv_categories);
        categoryViewModel = new CategoryViewModel();
        categoryViewModel.setContext(CategoryManagementActivity.this);
        List<Category> categories = categoryViewModel.getCategoryList();

        CategoryManagementAdapter adapter = new CategoryManagementAdapter(CategoryManagementActivity.this, categories);
        rcv_categories.setLayoutManager(new LinearLayoutManager(CategoryManagementActivity.this));

        rcv_categories.setAdapter(adapter);

    }
}
