package com.ctk43.doancoso.Database.Reponsitory;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.CategoryDAO;
import com.ctk43.doancoso.Model.Category;

import java.util.List;

public class CategoryRepository {
    private CategoryDAO categoryDAO;
    private MutableLiveData<List<Category>> allCategory;

    public CategoryRepository(Context context) {
        AppDatabase data = AppDatabase.getInstance(context);
        categoryDAO = data.categoryDAO();
        allCategory = new MutableLiveData<>();
        allCategory.setValue(categoryDAO.getAllCategoryList());
    }

    public void insert(Category category) {
        new InsertCategoryAsyncTask(categoryDAO).execute(category);
    }

    public void update(Category category) {
        new UpdateCategoryAsyncTask(categoryDAO).execute(category);
    }

    public void Delete(Category category) {
        new DeleteCategoryAsyncTask(categoryDAO).execute(category);
    }

    public LiveData<List<Category>> getallCategory() {
        return allCategory;
    }

    private static class InsertCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDAO categoryDAO;

        private InsertCategoryAsyncTask(CategoryDAO CategoryDAO) {
            this.categoryDAO = CategoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.delete(categories[0]);
            return null;
        }
    }

    private static class DeleteCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDAO categoryDAO;

        private DeleteCategoryAsyncTask(CategoryDAO CategoryDAO) {
            this.categoryDAO = CategoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.delete(categories[0]);
            return null;
        }
    }

    private static class UpdateCategoryAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDAO categoryDAO;

        private UpdateCategoryAsyncTask(CategoryDAO CategoryDAO) {
            this.categoryDAO = CategoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.update(categories[0]);
            return null;
        }
    }
}
