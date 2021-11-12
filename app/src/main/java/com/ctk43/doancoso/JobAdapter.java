package com.ctk43.doancoso;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.StoryHolder>{
    private final ArrayList<JobEnitity> listJob;
    private final Context mContext;
    public JobAdapter(ArrayList<JobEnitity> listJob, Context mContext) {
        this.listJob = listJob;
        this.mContext = mContext;
    }
    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.job_item, parent, false);
        return new StoryHolder(view);
    }
    @Override
    public void onBindViewHolder(JobAdapter.StoryHolder holder, int position) {
        JobEnitity item = listJob.get(position);
       // holder.tvName.setTag(item);
        //holder.tvName.setText(item.getName());
    }
    @Override
    public int getItemCount() {
        return listJob.size();
    }
    public class StoryHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public StoryHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_job_name);
            //itemView.setOnClickListener(v -> {
             //   ((MainActivity)mContext).gotoM003Screen(listJob, (StoryEntity)tvName.getTag());
            //});
        }
    }
}