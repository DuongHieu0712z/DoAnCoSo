package com.ctk43.doancoso.Model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey @NonNull
    public String Email;
    @NonNull
    public String Name;
    public String Pass;

    public User(){

    }
    public User(String email, String name, String pass) {
        Email = email;
        Name = name;
        Pass = pass;
    }

}
