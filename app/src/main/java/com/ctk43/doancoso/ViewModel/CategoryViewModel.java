package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.Repository.CategoryRepository;
import com.ctk43.doancoso.Model.Category;

import java.util.List;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> categories;
    private List<Category> categoryList;

    public CategoryViewModel() {

    }

    public void setContext(Context context) {
        categoryRepository = new CategoryRepository(context);
        categories = categoryRepository.getCategories();
        categoryList = categoryRepository.getList();
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void insert(Category... categories) {
        categoryRepository.insert(categories);
    }

    public void update(Category... categories) {
        categoryRepository.update(categories);
    }

    public void delete(Category... categories) {
        categoryRepository.delete(categories);
    }
}
