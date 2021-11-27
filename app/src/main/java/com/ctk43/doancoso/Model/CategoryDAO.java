package com.ctk43.doancoso.Database;

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
    void insertCategory(Category...categoties);

    @Update
    void updateCategory(Category category);

    @Delete
    void deletaCategory(Category category);
}
