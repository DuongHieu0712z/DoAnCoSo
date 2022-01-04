package com.ctk43.doancoso.View.Fragment;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.CalendarAdapter;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;


public class MonthFragment extends Fragment implements View.OnClickListener {

    private View view;
    private Context mContext;
    private TextView tv_current_month;
    private RecyclerView rcv_calendar;
    private int month, year;
    private Date selectedDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month, container, false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            InnitView(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private void InnitView(View view) throws ParseException {
        tv_current_month = view.findViewById(R.id.tv_current_month);
        rcv_calendar = view.findViewById(R.id.calendarRecyclerView);
        monthYearFromDate(CalendarExtension.currDate());
        selectedDate = CalendarExtension.getStartTimeOfMonth(month,year);
        setMonthView();
        ImageView btn_prv_month = view.findViewById(R.id.btn_prv_month);
        ImageView btn_next_month = view.findViewById(R.id.btn_next_month);
        btn_next_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    nextMonthAction(view);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_prv_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    previousMonthAction(view);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void setMonthView() throws ParseException {

        tv_current_month.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);
        JobViewModel jobViewModel = new JobViewModel();
        jobViewModel.setData(mContext);
        ArrayList<Date> datesHaveJob = new ArrayList<>();
        jobViewModel.getJobsMonth(month, year).observe(requireActivity(), jobs ->
        {
            Date currentDay;
            String str = "";
            datesHaveJob.clear();
            for (int i = 0; i < daysInMonth.size(); i++) {
                if (daysInMonth.get(i) == "")
                    datesHaveJob.add(null);
                if (daysInMonth.get(i) != "") {
                    if (month == 1 && Integer.parseInt(daysInMonth.get(i)) > 29)
                        datesHaveJob.add(null);
                    else if ((month == 3 || month == 8 || month == 5 || month == 10) && Integer.parseInt(daysInMonth.get(i)) > 30)
                        datesHaveJob.add(null);
                    else {
                        str = daysInMonth.get(i) + "/" + (month + 1) + "/" + year;
                        try {
                            currentDay = new SimpleDateFormat("dd/MM/yyyy").parse(str);
                        } catch (ParseException e) {
                            currentDay = null;
                        }
                        int j ;
                        if (jobViewModel.getCountJobOneDate(currentDay) >0)
                        {
                            j = jobViewModel.getCountJobOneDate(currentDay);
                            datesHaveJob.add(currentDay);
                            continue;
                        }
                    }
                    datesHaveJob.add(null);
                }
            }
            CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,datesHaveJob , mContext, month, year);
            RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 7);
            rcv_calendar.setLayoutManager(layoutManager);
            rcv_calendar.setAdapter(calendarAdapter);
        });

    }


    private ArrayList<String> daysInMonthArray(Date date) {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        int daysInMonth = CalendarExtension.getDaysInMonth(date);
        int dayOfWeek = CalendarExtension.getDateOfWeek(date);
        for (int i = 1; i <= 42; i++) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            } else {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }


    private String monthYearFromDate(Date date) {
        month = CalendarExtension.getMonth(date, 0);
        year = CalendarExtension.getYear(date, 0);
        String str = "Tháng " + (month + 1);
        str += " năm " + year;
        return str;
    }

    public void previousMonthAction(View view) throws ParseException {
        selectedDate = CalendarExtension.getMonthByPosition(selectedDate, -1);
        setMonthView();
    }

    public void nextMonthAction(View view) throws ParseException {
        selectedDate = CalendarExtension.getMonthByPosition(selectedDate, 1);
        setMonthView();
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

         /*    List<Job> jobsInMonthYear = jobViewModel.getListJobMonth(month,year);
            Date st = new SimpleDateFormat("dd/MM/yyyy").parse("11/12/2021");
            Date et = new SimpleDateFormat("dd/MM/yyyy").parse("20/12/2021");

            jobsInMonthYear.add(new Job(10, 2, "Yêm Check", st, et,"Here is description", 1, 5.0, 1));

        *//*listJob.add(jobViewModel.getJobsByCategory(1));
        listJob.add(null);
        listJob.add(null);
        listJob.add(null);
        listJob.add(jobViewModel.getJobsByCategory(1));*//*

            Calendar cal1 = new GregorianCalendar();
            Calendar cal2 = new GregorianCalendar();
            Date currentDay;

            List<Job> jobsInDate = new ArrayList<>();

            for (int i=0;i<daysInMonth.size(); i++){
                jobsInDate.clear();
                if(daysInMonth.get(i)=="")listJob.add(null);
                if(daysInMonth.get(i)!=""){
                    if(month==1 && Integer.parseInt(daysInMonth.get(i))>29)
                        listJob.add(null);
                    else if((month == 3 || month == 8||month == 5||month == 10) && Integer.parseInt(daysInMonth.get(i))>30)
                        listJob.add(null);
                    else{
                        str=daysInMonth.get(i)+"/"+(month+1)+"/"+year;
                        currentDay = new SimpleDateFormat("dd/MM/yyyy").parse(str);
                        cal1.setTime(currentDay);
                        for(Job j : jobsInMonthYear){
                            cal2.setTime(j.getEndDate());
                            if(cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) &&
                                    cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                                    cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                            {
                                listJob.add(new ArrayList<>());
                                listJob.get(i).add(j);
                                jobsInDate.add(j);
                            }
                        }
                        if(jobsInDate.size()==0)
                            listJob.add(null);
                    }
                }*/


}