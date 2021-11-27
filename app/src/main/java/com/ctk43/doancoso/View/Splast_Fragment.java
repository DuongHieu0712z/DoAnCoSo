package com.ctk43.doancoso.View;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

<<<<<<< HEAD
import com.ctk43.doancoso.Database.Database;
import com.ctk43.doancoso.Database.JobDAO;
import com.ctk43.doancoso.Database.Sqlite_Helper;
=======
import com.ctk43.doancoso.Model.Database;
>>>>>>> parent of 1bb4a00 (thanh)
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.MainActivity;

public class Splast_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return inflater.inflate(R.layout.activity_flat, container, false);
    }

    private void initViews() {
<<<<<<< HEAD
    //   ((MainActivity)getActivity()).progressCopyDataBase();
       //  Context context = (MainActivity)getActivity().getApplicationContext();
      //   Database.getInstance().setLast_week( JobDAO.getInstance().getAllJob(context)) ;
     //   Database.getInstance().LoadDataBase();
=======
        Database.getInstance().LoadDataBase();
>>>>>>> parent of 1bb4a00 (thanh)
        new Handler().postDelayed(this::gotoM001Screen, 1000);
    }
    private void gotoM001Screen() {
        ((MainActivity) getActivity()).gotoM001Screen();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
