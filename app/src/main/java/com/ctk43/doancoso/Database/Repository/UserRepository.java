package com.ctk43.doancoso.Database.Repository;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.ctk43.doancoso.Database.AppDatabase;
import com.ctk43.doancoso.Database.DAO.UserDAO;
import com.ctk43.doancoso.Model.User;

import java.util.List;

public class UserRepository {

    private final UserDAO UserDAO;

    public UserRepository(Context context) {
        AppDatabase data = AppDatabase.getInstance(context);
        UserDAO = data.getUserDAO();
    }

    public User getUser(String email) {
        return UserDAO.getUser(email);
    }

    public void insert(User... Users) {
        new UserRepository.UserInsertAsyncTask(UserDAO).execute(Users);
    }

    public void update(User... Users) {
        new UserRepository.UserUpdateAsyncTask(UserDAO).execute(Users);
    }

    public void delete(User... Users) {
        new UserRepository.UserDeleteAsyncTask(UserDAO).execute(Users);
    }

    private static class UserInsertAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDAO UserDAO;

        private UserInsertAsyncTask(UserDAO UserDAO) {
            super();
            this.UserDAO = UserDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            UserDAO.insert(users);
            return null;
        }
    }

    private static class UserUpdateAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDAO UserDAO;

        private UserUpdateAsyncTask(UserDAO UserDAO) {
            super();
            this.UserDAO = UserDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            UserDAO.update(users);
            return null;
        }
    }

    private static class UserDeleteAsyncTask extends AsyncTask<User, Void, Void> {
        private final UserDAO UserDAO;

        private UserDeleteAsyncTask(UserDAO UserDAO) {
            super();
            this.UserDAO = UserDAO;
        }

        @Override
        protected Void doInBackground(User... users) {
            UserDAO.delete(users);
            return null;
        }
    }
}
