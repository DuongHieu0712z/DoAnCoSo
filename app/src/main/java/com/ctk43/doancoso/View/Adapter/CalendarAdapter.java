package com.ctk43.doancoso.View.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>{

    private final ArrayList<String> daysOfMonth;
    private ArrayList<List<Job>> listJob;
    private Context mContext;


    private List<Job> OldJobs;
    private List<Job> jobsInDay;
    public Date currentDate;

    public CalendarAdapter(ArrayList<String> daysOfMonth,ArrayList<List<Job>> listJob, Context context) {
        this.listJob = listJob;
        this.daysOfMonth = daysOfMonth;
        this.mContext = context;
    }
    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);

        JobViewModel jobViewModel = new JobViewModel();
        try{
        OldJobs = jobViewModel.getJobs().getValue();}
        catch (Exception e){}


        return new CalendarViewHolder(view);
    }

    public void IsHaveJobsDay(Date date){
        if(OldJobs!=null){
            Calendar cal1 = new GregorianCalendar();
            cal1.setTime(date);

            Calendar cal2 = new GregorianCalendar();

            for(Job job:OldJobs){
                cal2.setTime(job.getEndDate());
                if(cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH) &&
                        cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
                        cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR))
                    jobsInDay.add(job);
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position) {
        holder.dayOfMonth.setText(daysOfMonth.get(position));
       // holder.IsCurrentDay();
        if(position>=listJob.size()) return;
        if(listJob.get(position)!=null){
            if(listJob.get(position).size()>0){
                holder.IsHaveJobsDay();
                holder.cl_cell_calendar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity)mContext).SelectBottomMenuPosition(0);
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

}
