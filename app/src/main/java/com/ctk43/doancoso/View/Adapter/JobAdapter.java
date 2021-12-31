package com.ctk43.doancoso.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.ctk43.doancoso.Library.Action;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.Service.CountUpService;
import com.ctk43.doancoso.View.Activity.AddJobActivity;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
import com.ctk43.doancoso.View.Fragment.JobFragment;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> implements Filterable {
    private List<Job> listJob;
    private List<Job> mlistJobOld;
    private final Context context;
    private final JobViewModel jobViewModel;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public JobAdapter(Context context, JobViewModel jobViewModel) {
        this.context = context;
        this.jobViewModel = jobViewModel;

        listJob = jobViewModel.getJobs().getValue();
    }

    @Override
    @NonNull
    public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);
        return new JobHolder(view);
    }

    public void setJob(List<Job> jobs) {
        listJob = jobs;
        mlistJobOld = jobs;
    }

    @Override
    public void onBindViewHolder(JobHolder holder, @SuppressLint("RecyclerView") int position) {

        Job item = listJob.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(item.getId()));
        String jobName = item.getName();
        if(jobName.length() > 20)
            jobName = jobName.substring(0,15) + "...";
        holder.tv_job_name.setText(jobName);
        holder.tv_job_des.setText(item.getDescription());
        holder.img_priority.setImageResource(GeneralData.getImgPriority(item.getPriority()));
        holder.checkBox.setChecked(item.getStatus() == 3||item.getStatus() ==4);
        setProcess(holder.tv_job_prg, holder.progressBar, item);
        setTextStatus(item, holder.tv_job_status, holder.tv_time_title, holder.tv_time);

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDeleteJob(item);
            }
        });
        holder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddJobActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("JobToUpdate", (Serializable) item);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });

        holder.itemJob.setOnClickListener(v -> ViewJobDetail(item));
        holder.checkBox.setOnClickListener(v -> CheckOrUncheck(holder.checkBox, item));
    }

    @Override
    public int getItemCount() {
        if (listJob == null)
            return 0;
        return listJob.size();
    }

    void setProcess(TextView tv_progress, ProgressBar progressBar, Job job) {
        int progress = (int) (job.getProgress() * 100.0);
        String prgString = progress + " %";
        tv_progress.setText(prgString);
        progressBar.setProgress(progress);
    }

    void setTextStatus(Job job, TextView status, TextView title_Time, TextView time) {
        int color = ContextCompat.getColor(context, (GeneralData.getColorStatus(job.getStatus())));

        time.setText(CalendarExtension.TimeRemaining(Calendar.getInstance().getTime(), job.getEndDate()));
        time.setTextColor(color);

        status.setText(GeneralData.getStatus(job.getStatus()));
        status.setTextColor(color);

        title_Time.setText(GeneralData.getTimeTitle(job.getStatus()));
        title_Time.setTextColor(color);

    }

    void deleteItem(Job job) {
        listJob.remove(job);
        notifyItemRemoved(listJob.indexOf(job));
        jobViewModel.delete(job);
    }

    void ViewJobDetail(Job job) {

        Intent intent = new Intent(context, JobDetailActivity.class);
        intent.putExtra(Key.JOB_ID, job.getId());
        context.startActivity(intent);
    }

    void updateStatus(Job Job, boolean isFinish) {
        jobViewModel.checkOrUncheck(Job, isFinish);
        new JobFragment();
    }
    void DialogDeleteJob(Job job) {
        final Dialog dialogYesNo = new Dialog(context);
        Extension.dialogYesNo(dialogYesNo, context.getString(R.string.confirm_delete), context.getString(R.string.message_delete_all_job_detail));
        Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
        Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
        dialogYesNo.setCancelable(true);
        btn_yes.setOnClickListener(v -> {
            deleteItem(job);
            dialogYesNo.dismiss();
        });
        btn_no.setOnClickListener(v -> dialogYesNo.dismiss());
        dialogYesNo.show();
    }

    void CheckOrUncheck(CheckBox checkBox, Job job) {
        final Dialog dialogYesNo = new Dialog(context);
        String confirm = context.getString(R.string.confirm);
        dialogYesNo.setCancelable(true);
        if (checkBox.isChecked()) {
            Extension.dialogYesNo(dialogYesNo, confirm, context.getString(R.string.message_finish_all_job_detail));
            Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
            Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
            btn_yes.setOnClickListener(v -> {
                dialogYesNo.dismiss();
                updateStatus(job, true);
            });
            btn_no.setOnClickListener(v -> {
                checkBox.setChecked(false);
                dialogYesNo.dismiss();
            });
        } else {
            Extension.dialogYesNo(dialogYesNo, confirm, context.getString(R.string.message_delete_progress));
            Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
            Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
            btn_yes.setOnClickListener(v -> {
                updateStatus(job, false);
                dialogYesNo.dismiss();
            });
            btn_no.setOnClickListener(v -> {
                checkBox.setChecked(true);
                dialogYesNo.dismiss();
            });
        }
        dialogYesNo.show();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strsearch = charSequence.toString();
                if(strsearch.isEmpty()){
                    listJob = mlistJobOld;
                }else{
                    List<Job> list = new ArrayList<>();
                    for (Job j : mlistJobOld){
                        if(j.getName().toLowerCase().contains(strsearch.toLowerCase())){
                            list.add(j);
                        }
                    }
                    listJob = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = listJob;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listJob = (List<Job>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void SortByPriority(int priority){
        if(listJob==null) return;
        List<Job> list = new ArrayList<>();
        for(Job j:listJob){
            if(j.getPriority() == priority)
                list.add(j);
        }
        listJob = list;
        notifyDataSetChanged();
    }
    public void GetByCategoryId(int categoryId){
        List<Job> list = new ArrayList<>();
        if(categoryId != 0) {
            for (Job j : listJob) {
                if (j.getCategoryId() == categoryId)
                    list.add(j);
            }
        }
        else if(categoryId == 0) {
            list = mlistJobOld;
            System.out.println(mlistJobOld.size()+" mlist job size");
        }
        listJob = list;
        notifyDataSetChanged();
    }
    public void SortByStatus(int status){
        if(listJob==null){
            System.out.println("List is null");
            return;
        }
        List<Job> list = new ArrayList<>();
        for(Job j:listJob){
            if(j.getStatus() == status)
                list.add(j);
        }
        listJob = list;
        notifyDataSetChanged();
    }
    public void Revert(){
        listJob = mlistJobOld;
        notifyDataSetChanged();
    }
    public static class JobHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
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

        FrameLayout delete;
        FrameLayout update;


        public JobHolder(View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.item_topic);
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
            delete = view.findViewById(R.id.frm_function_delete);
            update = view.findViewById(R.id.frm_function_update);

        }
    }
}
