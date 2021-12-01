package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    public int ID;

    @ColumnInfo(name = "Name") @NonNull
    public String Name;

    public Category() {
    }

    public Category( String name) {
        Name = name;
    }
}
