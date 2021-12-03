package com.ctk43.doancoso.View;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.CalendarAdapter;
import com.google.type.DateTime;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class MonthFragment extends Fragment implements CalendarAdapter.OnItemListener{

    private View view;
    private TextView monthYearText;
    private Context mContext;
    private RecyclerView calendarRCV;
    private DateTime selectedDate;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_month,container,false);
        return view;
    }
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }
    private void init(View view)
    {
        calendarRCV = view.findViewById(R.id.calendarRecyclerView);
        monthYearText = view.findViewById(R.id.monthYearTV);
    }

    @Override
    public void onItemClick(int position, String dayText) {
        
    }

 /*   private void setMonthView()
    {
        monthYearText.setText(monthYearFromDate());
        ArrayList<String> daysInMonth = daysInMonthArray(selectedDate);

        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, view.setOnClickListener(););
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 7);
        calendarRCV.setLayoutManager(layoutManager);
        calendarRCV.setAdapter(calendarAdapter);
    }

    private ArrayList<String> daysInMonthArray(Date date)
    {
        ArrayList<String> daysInMonthArray = new ArrayList<>();


        return  daysInMonthArray;
    }

    private String monthYearFromDate(Date date)
    {
        return null;
    }

    public void previousMonthAction(View view)
    {

    }

    public void nextMonthAction(View view)
    {

    }

    @Override
    public void onItemClick(int position, String dayText)
    {
        if(!dayText.equals(""))
        {
            String message = "Selected Date " + dayText + " " + monthYearFromDate(selectedDate);
            Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
        }
    }*/
}