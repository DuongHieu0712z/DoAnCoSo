package com.ctk43.doancoso.View.Adapter;

import com.ctk43.doancoso.Model.Job;

public interface OnItemClickListener {
    void onItemClick(Job job);
    void deleteItemClick(Job job);
}
