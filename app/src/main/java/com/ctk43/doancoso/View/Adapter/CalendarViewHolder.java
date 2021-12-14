package com.ctk43.doancoso.View.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.R;

public class CalendarViewHolder extends RecyclerView.ViewHolder{

    public final TextView dayOfMonth;
    //private final CalendarAdapter.OnItemListener onItemListener;
    public CalendarViewHolder(@NonNull View itemView) {
        super(itemView);
        dayOfMonth = itemView.findViewById(R.id.cellDayText);
        //this.onItemListener = onItemListener;
        //itemView.setOnClickListener(this);
    }

}
