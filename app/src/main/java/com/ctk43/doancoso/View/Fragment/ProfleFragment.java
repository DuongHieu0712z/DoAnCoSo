package com.ctk43.doancoso.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.zip.Inflater;

public class ProfleFragment extends Fragment {
    private Context mContext;
    JobViewModel jobViewModel;

    TextView tv_in_comming;
    TextView tv_on_going;
    TextView tv_complete ;
    TextView tv_over ;
    TextView tv_over_complete ;

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
        InnitView(view);
    }

    private void InnitView(View view) {
        tv_in_comming = view.findViewById(R.id.tv_profile_in_coming);
        tv_on_going = view.findViewById(R.id.tv_profile_on_going);
        tv_complete = view.findViewById(R.id.tv_profile_complete);
        tv_over = view.findViewById(R.id.tv_profile_over);
        tv_over_complete = view.findViewById(R.id.tv_profile_over_complete);

        btn_prv_month = view.findViewById(R.id.btn_profile_prv_month);
        btn_next_month = view.findViewById(R.id.btn_profile_next_month);

        tv_month_year =view.findViewById(R.id.tv_profile_month);
        Calendar calendar = Calendar.getInstance();
        month = calendar.get(Calendar.MONTH) +1;
        year = calendar.get(Calendar.YEAR);
        SetTextToMonthYear(month, year);
        JobViewModel jobViewModel = new JobViewModel();
        jobViewModel.setData(mContext);
        Statistical(jobViewModel, month, year);

        btn_next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(month == 12){
                    month =1;
                    year +=1;
                }else{
                    month +=1;
                }
                SetTextToMonthYear(month, year);
                JobViewModel jobViewModel = new JobViewModel();
                jobViewModel.setData(mContext);
                Statistical(jobViewModel, month, year);
            }
        });
        btn_prv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(month ==1){
                    month =12;
                    year-=1;
                }else{
                    month-=1;
                }
                SetTextToMonthYear(month,year);
                JobViewModel jobViewModel = new JobViewModel();
                jobViewModel.setData(mContext);
                Statistical(jobViewModel, month, year);
            }
        });





        //Statistical(jobViewModel);

    }
    private void SetTextToMonthYear(int month, int year){
        String str = "Tháng "+month+" năm "+year;
        tv_month_year.setText(str);
    }
    private void Statistical(JobViewModel jobViewModel, int month, int year){
        tv_in_comming.setText(String.valueOf( jobViewModel.countStatusMonth(0, month-1, year)));
        tv_on_going.setText(String.valueOf( jobViewModel.countStatusMonth(1, month-1, year)));
        tv_complete.setText(String.valueOf( jobViewModel.countStatusMonth(2, month-1, year)));
        tv_over.setText(String.valueOf( jobViewModel.countStatusMonth(3, month-1, year)));
        tv_over_complete.setText(String.valueOf( jobViewModel.countStatusMonth(4, month-1, year)));
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
