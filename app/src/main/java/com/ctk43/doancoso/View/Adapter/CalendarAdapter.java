package com.ctk43.doancoso.View.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder> {

    private final ArrayList<String> daysOfMonth;
    private Context mContext;
    int month;
    int year;
    private ArrayList<Date> listDate;

    public CalendarAdapter(ArrayList<String> daysOfMonth, ArrayList<Date> listDate, Context context, int month, int year) {
        this.listDate = listDate;
        this.daysOfMonth = daysOfMonth;
        this.mContext = context;
        this.month = month;
        this.year = year;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int hetght = (parent.getWidth() / 7 + 20);
        layoutParams.height = hetght;
        return new CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
        if (position >= listDate.size()) return;

        if (listDate.get(position) != null) {
            if (CalendarExtension.timeRemaining(CalendarExtension.getStartTimeOfDate(CalendarExtension.currDate()), listDate.get(position)) >= 0)
                holder.cl_cell_calendar.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.Have_job, null));
            else {
                holder.cl_cell_calendar.setBackgroundColor(ResourcesCompat.getColor(mContext.getResources(), R.color.Have_job_pass, null));
            }
            holder.cl_cell_calendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String day = (String) holder.dayOfMonth.getText();
                    int index = daysOfMonth.indexOf(day);
                    if (day != "" && listDate.get(index) != null) {
                        Job job = new Job(0,
                                CalendarExtension.getString(listDate.get(index)),
                                CalendarExtension.getStartTimeOfDate(listDate.get(index)),
                                CalendarExtension.getEndTimeOfDate(listDate.get(index)),
                                -1);
                        Extension.callJobActivity(mContext, job, true);
                    }
                }
            });
        }
        holder.IsCurrentDay(month, year, mContext);
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

}
