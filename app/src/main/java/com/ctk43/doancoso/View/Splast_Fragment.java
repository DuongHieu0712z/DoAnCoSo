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
import androidx.fragment.app.Fragment;

import com.ctk43.doancoso.Database.Database;
import com.ctk43.doancoso.Database.JobDAO;
import com.ctk43.doancoso.Database.Sqlite_Helper;
import com.ctk43.doancoso.R;

public class Splast_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initViews();
        return inflater.inflate(R.layout.activity_flat, container, false);
    }

    private void initViews() {
    //   ((MainActivity)getActivity()).progressCopyDataBase();
       //  Context context = (MainActivity)getActivity().getApplicationContext();
      //   Database.getInstance().setLast_week( JobDAO.getInstance().getAllJob(context)) ;
     //   Database.getInstance().LoadDataBase();
        new Handler().postDelayed(this::gotoM001Screen, 1000);
    }
    private void gotoM001Screen() {
        ((MainActivity) getActivity()).gotoM001Screen();
    }
}
