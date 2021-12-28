package com.ctk43.doancoso.View;

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


public class  MonthFragment extends Fragment implements View.OnClickListener{

    private View view;
    private Context mContext;
    private TextView tv_current_month;
    private RecyclerView rcv_calendar;
    private int month, year;
    private LocalDate selectedDate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month,container,false);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        try {
            InnitView(view);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void InnitView(View view) throws ParseException {
        tv_current_month = view.findViewById(R.id.tv_current_month);
        rcv_calendar = view.findViewById(R.id.calendarRecyclerView);

        selectedDate = LocalDate.now();

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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView() throws ParseException {
        String str = "";

        tv_current_month.setText(monthYearFromDate(selectedDate));
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        ArrayList<List<Job>> listJob = new ArrayList<>();
        JobViewModel jobViewModel = new JobViewModel();
        jobViewModel.setData(mContext);
        listJob.add(jobViewModel.getJobsByCategory(1));
        listJob.add(null);
        listJob.add(null);
        listJob.add(null);
        listJob.add(jobViewModel.getJobsByCategory(1));

        /*Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        Date currentDay;

        for (int i=0;i<daysInMonth.size(); i++){
            str=daysInMonth.get(i)+"/"+month+"/"+year;
            currentDay = new SimpleDateFormat("dd/MM/yyyy").parse(str);

        }*/

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth,listJob, mContext);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 7);
        rcv_calendar.setLayoutManager(layoutManager);
        rcv_calendar.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<String> daysInMonthArray(LocalDate date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);

        int daysInMonth = yearMonth.lengthOfMonth();

        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        for(int i = 1; i <= 42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add("");
            }
            else
            {
                daysInMonthArray.add(String.valueOf(i - dayOfWeek));
            }
        }
        return  daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date)
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("L");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy");
        String str = "Tháng "+date.format(formatter);
        str+=" năm "+date.format(formatter2);
        month = Integer.parseInt(date.format(formatter));
        year = Integer.parseInt(date.format(formatter2));
        return str;
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View view) throws ParseException {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View view) throws ParseException {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @Override
    public void onClick(View view) {

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