package com.ctk43.doancoso.Model;

public class Database {
    private Database instance;

    public Database getInstance(){
        if (instance==null)
            instance = new Database();
            return instance;
    }
    private Database(){}

}
