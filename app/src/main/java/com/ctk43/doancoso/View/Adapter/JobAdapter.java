package com.ctk43.doancoso.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
<<<<<<< HEAD
import com.ctk43.doancoso.View.Activity.JobDeltailActivity;
=======
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.Calendar;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {
    private List<Job> listJob;
    private final Context context;
    private JobViewModel jobViewModel;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    Job currentJob;
    private JobHolder holder;
    private int position;

    public JobAdapter(Context context, JobViewModel jobViewModel) {
        this.context = context;
        this.jobViewModel = jobViewModel;
        listJob = jobViewModel.getJobs().getValue();
    }

    @Override
    public JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);

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
        return new JobHolder(view);
    }

    public void setJob(List<Job> jobs) {
        listJob = jobs;
    }

    @Override
    public void onBindViewHolder(JobHolder holder, @SuppressLint("RecyclerView") int position) {
        this.holder = holder;
        this.position = position;
        Job item = listJob.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));
        holder.layout_funcion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogYesNo = new Dialog(context);
                Extension.dialogYesNo(dialogYesNo, null, "Xác nhận xóa?", "Sau khi xóa sẽ mất toàn bộ dữ liệu");
                //dialogYesNo.setContentView(R.layout.floating_dialog_add_new_job);
                Button buttn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
                Button buttn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
                dialogYesNo.setCancelable(true);
                buttn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jobViewModel.delete(item);
                        notifyItemRemoved(listJob.indexOf(item));
                        listJob.remove(item);
                        dialogYesNo.dismiss();
                    }
                });
                buttn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogYesNo.dismiss();
                    }
                });
                dialogYesNo.show();
            }
        });
        holder.itemJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
                Intent intent = new Intent(context, JobDeltailActivity.class);
=======
                Intent intent = new Intent(context, JobDetailActivity.class);
>>>>>>> 92793a4312b9bd423a942bf644bcf3abf4f19813
                intent.putExtra("JobID", item.getId());
                context.startActivity(intent);
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
        holder.tv_job_name.setText(item.getName());
        holder.tv_job_des.setText(item.getDescription());
     /*   if(item.Priority ==true)
            holder.img_level.setImageResource(R.drawable.ic_baseline_star_24);
        else
            holder.img_level.setImageResource(R.drawable.ic_baseline_star_outline_24);
*/
        holder.tv_job_end.setText(Extension.TimeRemaining(Calendar.getInstance().getTime(), item.getEndDate()));
        double prg = item.getProgress() * 100;
        holder.tv_job_prg.setText((int) prg + "%");
        holder.progressBar.setProgress((int) (prg));
        String status = "";
        switch (item.getStatus()) {
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
        if (listJob == null )
            return 0;
        return listJob.size();
    }

    public class JobHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout layout_funcion;
        LinearLayout itemJob;
        ImageView img_level;
        TextView tv_job_name;
        TextView tv_job_des;
        TextView tv_job_prg;
        TextView tv_job_end;
        TextView tv_job_status;
        ProgressBar progressBar;

        public JobHolder(View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.item_topic);
            layout_funcion = view.findViewById(R.id.job_funcion);
            itemJob = view.findViewById(R.id.item_job);
            //      img_level = view.findViewById(R.id.img_level);
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
