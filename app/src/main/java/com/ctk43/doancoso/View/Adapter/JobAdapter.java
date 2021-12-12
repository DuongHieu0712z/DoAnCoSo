package com.ctk43.doancoso.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
import com.ctk43.doancoso.View.JobFragment;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {
    private List<Job> listJob;
    private final Context context;
    private JobViewModel jobViewModel;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public JobAdapter(Context context,JobViewModel jobViewModel) {
        this.context = context;
         this.jobViewModel = jobViewModel;
        listJob = jobViewModel.getJobs().getValue();
    }

    @Override
    public JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);
        return new JobHolder(view);
    }

    public void setJob(List<Job> jobs) {
        listJob = jobs;
    }

    @Override
    public void onBindViewHolder(JobHolder holder, @SuppressLint("RecyclerView") int position) {

        Job item = listJob.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));

        holder.tv_job_name.setText(item.getName());
        holder.tv_job_des.setText(item.getDescription());
        holder.img_priority.setImageResource(GeneralData.getImgPriority(item.getPriority()));
        double prg = item.getProgress() * 100;
        holder.tv_job_prg.setText((int) prg + "%");
        holder.progressBar.setProgress((int) (prg));
        holder.checkBox.setChecked(item.getStatus()==2?true: false);
        setText(item, holder.tv_job_status, holder.tv_time_title, holder.tv_time);

        holder.layout_funcion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogDeleteJob(item);
            }
        });
        holder.itemJob.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewJobDetail(item);
            }
        });
        holder.checkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckOrUncheck(holder.checkBox,item);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listJob == null )
            return 0;
        return listJob.size();
    }

    void setText(Job job,TextView status,TextView title_Time,TextView time){

        int color = ContextCompat.getColor(context, (GeneralData.getColorStatus(job.getStatus())));
        time.setText(Extension.TimeRemaining(job.getStartDate(),job.getEndDate()));
        time.setTextColor(color);

        status.setText(GeneralData.getStatus(job.getStatus()));
        status.setTextColor(color);

        title_Time.setText(GeneralData.getTimeTitle(job.getStatus()));
        title_Time.setTextColor(color);

    }

    void DialogDeleteJob(Job job){
        final Dialog dialogYesNo = new Dialog(context);
        Button buttn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
        Button buttn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
        dialogYesNo.setCancelable(true);
        Extension.dialogYesNo(dialogYesNo,context.getString(R.string.confirm_delete), context.getString(R.string.message_delete_all_job_detail));
        buttn_yes.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem(job);
                dialogYesNo.dismiss();
            }
        });
        buttn_no.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogYesNo.dismiss();
            }
        });
        dialogYesNo.show();
    }

    void deleteItem(Job job){
        jobViewModel.delete(job);
        notifyItemRemoved(listJob.indexOf(job));
        listJob.remove(job);
    }

    void ViewJobDetail(Job job){
        Intent intent = new Intent(context, JobDetailActivity.class);
        intent.putExtra("JobID", job.getId());
        context.startActivity(intent);
    }
    void updateStatus(Job Job,boolean isFinish){
        jobViewModel.checkOrUncheck(Job,isFinish);
        new JobFragment();
    }

    void CheckOrUncheck(CheckBox checkBox,Job job){
        final Dialog dialogYesNo = new Dialog(context);
        String confirm = context.getString(R.string.confirm);
        dialogYesNo.setCancelable(true);
        if(checkBox.isChecked()){
            Extension.dialogYesNo(dialogYesNo, confirm, context.getString(R.string.message_finish_all_job_detail));
            Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
            Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
            btn_yes.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogYesNo.dismiss();
                    updateStatus(job,true);
                }
            });
            btn_no.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkBox.setChecked(false);
                    dialogYesNo.dismiss();
                }
            });
        }else{
            Extension.dialogYesNo(dialogYesNo, confirm, context.getString(R.string.message_delete_progress));
            Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
            Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
            btn_yes.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateStatus(job,false);
                    dialogYesNo.dismiss();
                }
            });
            btn_no.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogYesNo.dismiss();
                    checkBox.setChecked(true);
                }
            });
        }
        dialogYesNo.show();
    }

    public class JobHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout layout_funcion;
        LinearLayout itemJob;
        TextView tv_job_name;
        TextView tv_time_title;
        TextView tv_job_des;
        TextView tv_job_prg;
        TextView tv_time;
        TextView tv_job_status;
        ProgressBar progressBar;
        CheckBox checkBox;
        ImageView img_priority;
        public JobHolder(View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.item_topic);
            layout_funcion = view.findViewById(R.id.job_funcion);
            tv_time_title = view.findViewById(R.id.tv_time);
            itemJob = view.findViewById(R.id.item_job);
            checkBox = view.findViewById(R.id.chk_finish_job);
           img_priority = view.findViewById(R.id.img_priority);
            tv_job_name = view.findViewById(R.id.tv_job_name);
            tv_job_des = view.findViewById(R.id.tv_job_description);
            tv_job_prg = view.findViewById(R.id.tv_progress);
            tv_time = view.findViewById(R.id.tv_remainning_time);
            tv_job_status = view.findViewById(R.id.tv_Status);
            progressBar = view.findViewById(R.id.prg_progress);
           /*  itemView.setOnClickListener(v -> {
                ((MainActivity)mContext).gotoM003Screen(listJob, (StoryEntity)tvName.getTag());
            });*/
        }
    }
}
