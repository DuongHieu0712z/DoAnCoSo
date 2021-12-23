package com.ctk43.doancoso.View.Adapter;

import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.R;

import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarHolder>{
    private final ArrayList<String> daysOfMonth;

    public CalendarAdapter(ArrayList<String> daysOfMonth) {
        this.daysOfMonth = daysOfMonth;
    }

    @NonNull
    @Override
    public CalendarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarHolder holder, int position) {
        holder.day.setText(daysOfMonth.get(position));
        holder.setOnClickListener(view -> {
            holder.day.setTextColor(holder.getResources().getColor(R.color.orange));
        });
    }

    @Override
    public int getItemCount() {
        return daysOfMonth.size();
    }

    static class CalendarHolder extends RecyclerView.ViewHolder {
        private final View view;
        TextView day;

        public CalendarHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            day = itemView.findViewById(R.id.cellDayText);
        }

        public Resources getResources() {
            return view.getResources();
        }

        public void setOnClickListener(View.OnClickListener clickListener) {
            view.setOnClickListener(clickListener);
        }
    }
}
