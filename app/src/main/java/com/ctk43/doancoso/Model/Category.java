package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true) @NonNull
    public int ID;

    @ColumnInfo(name = "Name")
    public String Name;

    public Category() {
    }
    public Category(int ID, String name) {
        this.ID = ID;
        Name = name;
    }
}
