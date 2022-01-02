package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Database.Repository.CategoryRepository;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.R;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<List<Category>> categories;
    private List<Category> categoryList;
    private Context mContext;

    public CategoryViewModel() {

    }

    public void setContext(Context context) {
        categoryRepository = new CategoryRepository(context);
        categories = categoryRepository.getCategories();
        categoryList = categoryRepository.getList();
        this.mContext = context;
    }

    public Category get(int id) {
        return categoryRepository.get(id);
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public List<Category> getCategoryView(){
        List<Category> temp = getCategoryExtension();
        temp.addAll(getCategoryList());
        return temp;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public List<Category> getCategoryExtension(){
        Category all = new Category(GeneralData.ID_CATEGORY_ALL, mContext.getString(R.string.category_all));
        Category week = new Category(GeneralData.ID_CATEGORY_WEEK, mContext.getString(R.string.category_week));
        Category month = new Category(GeneralData.ID_CATEGORY_MONTH, mContext.getString(R.string.category_month));
        List<Category> list = new ArrayList<>();
        list.add(all);
        list.add(week);
        list.add(month);
        return  list;
    }

    public int countJob(int id) {
        return categoryRepository.countJob(id);
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
