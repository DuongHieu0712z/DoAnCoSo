package com.ctk43.doancoso.Database.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ctk43.doancoso.Model.Category;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CategoryDAO {
    @Query("SELECT * FROM Category")
    List<Category> getAllCategoryList();

    @Insert
    void insert(Category... categories);

    @Update
    void update(Category category);

    @Delete
    void delete(Category category);
}
