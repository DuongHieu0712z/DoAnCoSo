package com.ctk43.doancoso.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

public class ProfleFragment extends Fragment {
    private Context mContext;
    JobViewModel jobViewModel;
    TextView tv_in_comming;
    TextView tv_on_going;
    TextView tv_complete;
    TextView tv_over;
    TextView tv_over_complete;

    TextView tv_month_year;
    ImageView btn_prv_month;
    ImageView btn_next_month;

    int month, year;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        jobViewModel = new ViewModelProvider(this).get(JobViewModel.class);
        jobViewModel.setData(mContext);
        InnitView(view);
    }

    private void InnitView(View view) {
        tv_in_comming = view.findViewById(R.id.tv_profile_in_coming);
        tv_on_going = view.findViewById(R.id.tv_profile_on_going);
        tv_complete = view.findViewById(R.id.tv_profile_complete);
        tv_over = view.findViewById(R.id.tv_profile_over);
        tv_over_complete = view.findViewById(R.id.tv_profile_over_complete);
        FrameLayout f_onComing = view.findViewById(R.id.f_on_coming);
        FrameLayout f_onGoing = view.findViewById(R.id.f_on_going);
        FrameLayout f_complete = view.findViewById(R.id.f_complete);
        FrameLayout f_over = view.findViewById(R.id.f_over);
        FrameLayout f_complete_late = view.findViewById(R.id.f_complete_late);
        btn_prv_month = view.findViewById(R.id.btn_profile_prv_month);
        btn_next_month = view.findViewById(R.id.btn_profile_next_month);
        tv_month_year = view.findViewById(R.id.tv_profile_month);
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH);
        year = calendar.get(Calendar.YEAR);
        SetTextToMonthYear(month, year);
        JobViewModel jobViewModel = new JobViewModel();
        jobViewModel.setData(mContext);
        Statistical(month, year);

        f_onComing.setOnClickListener(v -> {
            Extension.callJobActivity(mContext,
                    setData(GeneralData.STATUS_COMING, month, year),
                    true);
        });
        f_onGoing.setOnClickListener(v -> {
            Extension.callJobActivity(mContext,
                    setData(GeneralData.STATUS_ON_GOING, month, year),
                    true);
        });
        f_complete.setOnClickListener(v -> {
            Extension.callJobActivity(mContext,
                    setData(GeneralData.STATUS_FINISH, month, year),
                    true);
        });
        f_over.setOnClickListener(v -> {
            Extension.callJobActivity(mContext,
                    setData(GeneralData.STATUS_OVER, month, year),
                    true);
        });
        f_complete_late.setOnClickListener(v -> {
            Extension.callJobActivity(mContext,
                    setData(GeneralData.STATUS_FINISH_LATE, month, year),
                    true);
        });

        btn_next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (month == 12) {
                    month = 1;
                    year += 1;
                } else {
                    month += 1;
                }
                SetTextToMonthYear(month, year);
                JobViewModel jobViewModel = new JobViewModel();
                jobViewModel.setData(mContext);
                Statistical(month, year);
            }
        });
        btn_prv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (month == 1) {
                    month = 12;
                    year -= 1;
                } else {
                    month -= 1;
                }
                SetTextToMonthYear(month, year);
                Statistical(month, year);
            }
        });
    }

    private void SetTextToMonthYear(int month, int year) {
        String str = "Tháng " + (month + 1) + " năm " + year;
        tv_month_year.setText(str);
    }

    private void Statistical(int month, int year) {
        jobViewModel.getJobsMonth(month, year).observe(requireActivity(), jobs ->
        {
            tv_in_comming.setText(String.valueOf(jobViewModel.countStatusMonth(GeneralData.STATUS_COMING, month, year)));
            tv_on_going.setText(String.valueOf(jobViewModel.countStatusMonth(GeneralData.STATUS_ON_GOING, month, year)));
            tv_complete.setText(String.valueOf(jobViewModel.countStatusMonth(GeneralData.STATUS_FINISH, month, year)));
            tv_over.setText(String.valueOf(jobViewModel.countStatusMonth(GeneralData.STATUS_OVER, month, year)));
            tv_over_complete.setText(String.valueOf(jobViewModel.countStatusMonth(GeneralData.STATUS_FINISH_LATE, month, year)));

        });

    }

    private Job setData(int status, int month, int year) {
        Job job = new Job(0,
                getString(GeneralData.getStatus(status)),
                CalendarExtension.getStartTimeOfMonth(month, year),
                CalendarExtension.getDateEndOfMonth(month, year),
                status);
        return job;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
