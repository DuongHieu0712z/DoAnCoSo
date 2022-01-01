package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity(foreignKeys = @ForeignKey(entity = User.class,
        parentColumns = "Email",
        childColumns = "Email_user",
        onDelete = ForeignKey.CASCADE))
public class Category {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID")
    private int id;

    @ColumnInfo(name = "Name")
    @NonNull
    private String name;

    @ColumnInfo(name = "Email_user")
    private String email;


    public Category(@NonNull String name, @NonNull String email) {
        this.name = name;
        this.email = email;
    }

    @Ignore
    public Category(@NonNull String name, @NonNull String email, int id) {
        this.name = name;
        this.email = email;
        this.id = id;
    }

    @Ignore
    public Category(int id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Category)) return false;
        Category category = (Category) o;
        return getId() == category.getId() && getName().equals(category.getName()) && Objects.equals(getEmail(), category.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getEmail());
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }
}
