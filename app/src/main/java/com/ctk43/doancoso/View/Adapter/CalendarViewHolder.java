package com.ctk43.doancoso.View.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.MainActivity;

import java.util.Calendar;

public class CalendarViewHolder extends RecyclerView.ViewHolder{

    public final TextView dayOfMonth;
    public final ConstraintLayout cl_cell_calendar;
    public int _month;
    public int _year;
    public int _day;
    public CalendarViewHolder(@NonNull View itemView) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        cl_cell_calendar = itemView.findViewById(R.id.cell_calendar);
        cl_cell_calendar.setBackgroundColor(Color.parseColor("#f0ebe5"));
    }
    @SuppressLint("ResourceAsColor")
    public void IsHaveJobsDay(){
        cl_cell_calendar.setBackgroundColor(Color.parseColor("#FF5722"));
    }
    public void IsCurrentDay(int month, int year){
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int m = cal.get(Calendar.MONTH);
        int y = cal.get(Calendar.YEAR);
        _month = m;
        _year =y;
        _day =day;

        if(dayOfMonth.getText() !="")
        {
            if(Integer.parseInt(dayOfMonth.getText().toString()) == day && (m+1)==month && y==year)
            {
                dayOfMonth.setTextColor(Color.BLUE);
                cl_cell_calendar.setBackgroundResource(R.drawable.bg_cell_calendar);
            }
        }
    }

}
