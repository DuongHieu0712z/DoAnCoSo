package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.ctk43.doancoso.Database.Reponsitory.CategoryRepository;
import com.ctk43.doancoso.Model.Category;

import java.util.List;

public class CategoryViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> categories;

    public CategoryViewModel() {
    }
    public void setData(Context context) {
        categoryRepository = new CategoryRepository(context);
        categories = categoryRepository.getallCategory();
    }

    public void InsertCategory(Category category) {
        categoryRepository.insert(category);
    }

    public void UpdateCategory(Category category) {
        categoryRepository.update(category);
    }

    public void DeleteCategory(Category category) {
        categoryRepository.Delete(category);
    }

    public LiveData<List<Category>> getAllCategory() {
        return categories;
    }
}
