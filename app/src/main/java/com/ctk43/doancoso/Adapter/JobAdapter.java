package com.ctk43.doancoso.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.opengl.EGLExt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.MainActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.StoryHolder>{
    private List<Job> listJob;
    private final Context mContext;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private Job currentJob;
    public JobAdapter( Context mContext) {
        this.mContext = mContext;
    }
    @Override
    public StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.job_item, parent, false);

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Job _job=new Job();
                for (Job job : listJob){
                    //if(job.Name.compareTo(((TextView)view.findViewById(R.id.tv_job_name)).getText().toString())==0)
                    if (job.ID==currentJob.ID)
                    _job=job;
                }
                ((MainActivity)mContext).gotoM002Screen(_job);
            }
        });*/
        return new StoryHolder(view);
    }
    public void setJob(List<Job> jobs){
        listJob = jobs;
    }
    @Override
    public void onBindViewHolder(JobAdapter.StoryHolder holder, @SuppressLint("RecyclerView") int position) {

        Job item = listJob.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(position));
        holder.layoutDetele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listJob.remove(item);
                notifyItemRemoved(position);
            }
        });
        holder.itemJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)mContext).gotoM002Screen(item);
            }
        });
   /*     holder.swipeRevealLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setIcon(R.drawable.ic_delete)
                        .setTitle("Mày chắc chưa")
                        .setMessage("mày muốn xóa công việc này")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                listJob.remove(item);

                            }
                        }).setNegativeButton("No",null)
                        .show();
                return true;
            }
        });*/

        currentJob = item;
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

       // holder.tvName.setTag(item);
        //holder.tvName.setText(item.getName());
    }
    @Override
    public int getItemCount() {
        return listJob.size();
    }
    public class StoryHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout layoutDetele;
        LinearLayout itemJob;
        ImageView img_level;
        TextView tv_job_name;
        TextView tv_job_des;
        TextView tv_job_prg;
        TextView tv_job_end;
        TextView tv_job_status;
        ProgressBar progressBar;
        public StoryHolder(View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.item_topic);
            layoutDetele = view.findViewById(R.id.lnDelete);
            itemJob = view.findViewById(R.id.item_job);
            img_level = view.findViewById(R.id.img_level);
            tv_job_name = view.findViewById(R.id.tv_job_name);
             tv_job_des = view.findViewById(R.id.tv_job_description);
             tv_job_prg = view.findViewById(R.id.tv_progress);
             tv_job_end = view.findViewById(R.id.tv_remainning_time);
             tv_job_status = view.findViewById(R.id.tv_Status);
             progressBar = view.findViewById(R.id.prg_progress);
           /*  itemView.setOnClickListener(v -> {
                ((MainActivity)mContext).gotoM003Screen(listJob, (StoryEntity)tvName.getTag());
            });*/
        }
    }
}
