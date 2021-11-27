package com.ctk43.doancoso.View;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;


import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
<<<<<<< HEAD
import androidx.lifecycle.ViewModelProvider;
=======
import androidx.fragment.app.FragmentManager;
>>>>>>> parent of 1bb4a00 (thanh)
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Model.Database;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.Adapter.JobAdapter;
import com.ctk43.doancoso.ViewModel.Adapter.JobViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.time.LocalDateTime;

import java.util.ArrayList;

public class JobFragment extends Fragment implements View.OnClickListener{
    private Context mContext;
    FloatingActionButton btn_Add_New_Job;
    private JobAdapter jobListAdapter;
    private int dlg_mode=0;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_jobs, container, false);
        initViews(rootView);
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initViews(View v) {

        RecyclerView rcv = v.findViewById(R.id.rcv_display_job);
        jobListAdapter = new JobAdapter();
        jobListAdapter.setJobs(((MainActivity)getActivity()).listjob);
        rcv.setAdapter(jobListAdapter);
        rcv.setLayoutManager(new LinearLayoutManager(mContext));
        btn_Add_New_Job = (FloatingActionButton) v.findViewById(R.id.add_new_job);
        btn_Add_New_Job.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AddJobActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        ((MainActivity) getActivity()).gotoAddNewJobScreen();
    }

}
