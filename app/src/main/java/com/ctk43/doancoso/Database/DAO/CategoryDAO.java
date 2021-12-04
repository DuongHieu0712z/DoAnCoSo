package com.ctk43.doancoso.Database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ctk43.doancoso.Model.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM Category")
    LiveData<List<Category>> getAll();

    @Insert
    void insert(Category... categories);

    @Update
    void update(Category... categories);

    @Delete
    void delete(Category... categories);
}
