package com.ctk43.doancoso.ViewModel.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.StoryHolder>{
    private Context mContext;
    private List<Job> listJob = new ArrayList<Job>();


    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.job_item, parent, false);
        return new StoryHolder(view);
    }
    public void setJobs(List<Job> jobs){
        this.listJob = jobs;
        notifyDataSetChanged();
    }
    @Override
    public void onBindViewHolder(JobAdapter.StoryHolder holder, int position) {
        Job item = listJob.get(position);
        holder.tv_job_name.setText(item.Name);
        holder.tv_job_des.setText(item.Description);
        if(item.Priority ==true)
            holder.img_level.setImageResource(R.drawable.ic_baseline_star_24);
        else
            holder.img_level.setImageResource(R.drawable.ic_baseline_star_outline_24);

        holder.tv_job_end.setText(Extension.TimeRemaining(Calendar.getInstance().getTime(),item.EndDate));
        double prg = item.Progress*100;
        holder.tv_job_prg.setText(String.valueOf((int)prg)+"%");
        holder.progressBar.setProgress((int) (prg));
        String status ="";
        switch (item.Status){
            case 0:
                status = "On going";
                break;
            case 1:
                status = "Completed";
                break;
            case -1:
                status = "Dropped";
                break;
            case 2:
                status = "Over";
                break;
        }
        holder.tv_job_status.setText(status);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

       // holder.tvName.setTag(item);
        //holder.tvName.setText(item.getName());
    }
    @Override
    public int getItemCount() {
        if(listJob == null ||listJob.size()==0)
            return 0;
        return listJob.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder {
        ImageView img_level;
        TextView tv_job_name;
        TextView tv_job_des;
        TextView tv_job_prg;
        TextView tv_job_end;
        TextView tv_job_status;
        ProgressBar progressBar;
        public StoryHolder(View view) {
            super(view);
             img_level = (ImageView) view.findViewById(R.id.img_level);
             tv_job_name = (TextView) view.findViewById(R.id.tv_job_name);
             tv_job_des = (TextView) view.findViewById(R.id.tv_job_description);
             tv_job_prg = (TextView) view.findViewById(R.id.tv_progress);
             tv_job_end = (TextView) view.findViewById(R.id.tv_remainning_time);
             tv_job_status = (TextView) view.findViewById(R.id.tv_Status);
             progressBar = (ProgressBar) view.findViewById(R.id.prg_progress);
           /*  itemView.setOnClickListener(v -> {
                ((MainActivity)mContext).gotoM003Screen(listJob, (StoryEntity)tvName.getTag());
            });*/
        }
    }
    public interface HandleCategoryClick{
        void itemClick(Job job);
    }
}
