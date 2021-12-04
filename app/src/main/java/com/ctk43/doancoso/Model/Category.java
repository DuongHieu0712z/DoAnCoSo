package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    public Category(@NonNull String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
