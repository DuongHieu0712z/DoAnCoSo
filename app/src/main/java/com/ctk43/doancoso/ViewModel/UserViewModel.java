package com.ctk43.doancoso.ViewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Database.Repository.UserRepository;
import com.ctk43.doancoso.Model.User;
import com.ctk43.doancoso.Model.User;

import java.util.List;

public class UserViewModel extends ViewModel {
    private UserRepository UserRepository;
    private User user;

    public UserViewModel() {

    }

    public void setContext(Context context) {
        UserRepository = new UserRepository(context);
        user = UserRepository.getUser(DataLocalManager.getEmail());
    }

    public User getUser() {
        return user;
    }

    public void insert(User... users) {
        UserRepository.insert(users);
    }

    public void update(User... users) {
        UserRepository.update(users);
    }

    public void delete(User... users) {
        UserRepository.delete(users);
    }
}
