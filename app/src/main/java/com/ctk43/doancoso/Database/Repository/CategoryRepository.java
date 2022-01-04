package com.ctk43.doancoso.Database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.CategoryDAO;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.JobDetail;

import java.util.List;

public class CategoryRepository {
    private final CategoryDAO categoryDAO;
    private final LiveData<List<Category>> categories;

    public CategoryRepository(Context context) {
        AppDatabase data = AppDatabase.getInstance(context);
        categoryDAO = data.getCategoryDAO();
        categories = categoryDAO.getAll();
    }

    public Category get(int id) {
        return categoryDAO.get(id);
    }

    public LiveData<List<Category>> getCategories() {
        return categories;
    }

    public List<Category> getList(){
        return categoryDAO.getList();
    }

    public int countJob(int id) {
        return categoryDAO.countJob(id);
    }

    public void insert(Category... categories) {
        new CategoryInsertAsyncTask(categoryDAO).execute(categories);
    }

    public void update(Category... categories) {
        new CategoryUpdateAsyncTask(categoryDAO).execute(categories);
    }

    public void delete(Category... categories) {
        new CategoryDeleteAsyncTask(categoryDAO).execute(categories);
    }

    private static class CategoryInsertAsyncTask extends AsyncTask<Category, Void, Void> {
        private final CategoryDAO categoryDAO;

        private CategoryInsertAsyncTask(CategoryDAO CategoryDAO) {
            super();
            this.categoryDAO = CategoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.insert(categories);
            return null;
        }
    }

    private static class CategoryDeleteAsyncTask extends AsyncTask<Category, Void, Void> {
        private final CategoryDAO categoryDAO;

        private CategoryDeleteAsyncTask(CategoryDAO CategoryDAO) {
            super();
            this.categoryDAO = CategoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.delete(categories);
            return null;
        }
    }

    private static class CategoryUpdateAsyncTask extends AsyncTask<Category, Void, Void> {
        private final CategoryDAO categoryDAO;

        private CategoryUpdateAsyncTask(CategoryDAO CategoryDAO) {
            super();
            this.categoryDAO = CategoryDAO;
        }

        @Override
        protected Void doInBackground(Category... categories) {
            categoryDAO.update(categories);
            return null;
        }
    }
}
