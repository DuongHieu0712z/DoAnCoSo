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

public class CalendarViewHolder extends RecyclerView.ViewHolder{

    public final TextView dayOfMonth;
    public final ConstraintLayout cl_cell_calendar;
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

}
