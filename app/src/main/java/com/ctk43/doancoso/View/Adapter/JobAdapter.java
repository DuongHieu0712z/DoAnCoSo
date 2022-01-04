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
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.DialogExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Library.Key;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.AddJobActivity;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> implements Filterable {
    private final Context context;
    private final JobViewModel jobViewModel;
    private final ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    private List<Job> jobsShow;
    private List<Job> jobs;

    public JobAdapter(Context context, JobViewModel jobViewModel) {
        this.context = context;
        this.jobViewModel = jobViewModel;
    }

    @Override
    @NonNull
    public JobHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);
        return new JobHolder(view);
    }

    public void setJob(List<Job> jobs) {
        jobsShow = jobs;
        this.jobs = jobs;
    }

    @Override
    public void onBindViewHolder(JobHolder holder, @SuppressLint("RecyclerView") int position) {
        Job item = jobsShow.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(item.getId()));
        if(item.getName().length()>20){
            holder.tv_job_name.setText(item.getName().substring(0,17)+"...");
        }else{
            holder.tv_job_name.setText(item.getName());
        }

        if(item.getDescription().length()>30){
            holder.tv_job_des.setText(item.getDescription().substring(0,27)+"...");
        }else{
            holder.tv_job_des.setText(item.getDescription());
        }
        holder.img_priority.setImageResource(GeneralData.getImgPriority(item.getPriority()));
        holder.checkBox.setChecked(item.getStatus() == 3 || item.getStatus() == 4);
        Extension.setProgress(holder.tv_job_prg, holder.progressBar, item);
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
        holder.checkBox.setOnClickListener(v -> {
            if (Extension.canCheck(context, holder.checkBox, item))
                Extension.CheckOrUncheckJob(context, holder.checkBox, item, null, null);
        });

    }

    @Override
    public int getItemCount() {
        if (jobsShow == null)
            return 0;
        return jobsShow.size();
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
        jobs.remove(job);
        notifyItemRemoved(jobs.indexOf(job));
        jobViewModel.delete(job);
    }

    void ViewJobDetail(Job job) {
        Intent intent = new Intent(context, JobDetailActivity.class);
        intent.putExtra(Key.JOB_ID, job.getId());
        context.startActivity(intent);
    }


    void DialogDeleteJob(Job job) {
        final Dialog dialogYesNo = new Dialog(context);
        DialogExtension.dialogYesNo(dialogYesNo, context.getString(R.string.confirm_delete), context.getString(R.string.message_delete_all_job_detail));
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


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String strsearch = charSequence.toString();
                if (strsearch.isEmpty()) {
                    jobsShow = jobs;
                } else {
                    List<Job> list = new ArrayList<>();
                    for (Job j : jobs) {
                        if (j.getName().toLowerCase().contains(strsearch.toLowerCase())) {
                            list.add(j);
                        }
                    }
                    jobsShow = list;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = jobsShow;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                jobsShow = (List<Job>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void FilterByPriority(int priority) {
        if (jobsShow == null) return;
        else if (priority == -1)
            return;
        List<Job> list = new ArrayList<>();
        for (Job j : jobsShow) {
            if (j.getPriority() == priority)
                list.add(j);
        }
        jobsShow = list;
        notifyDataSetChanged();
    }

    public void GetByCategoryId(int categoryId) {
        List<Job> list = new ArrayList<>();
        if (categoryId != 0) {
            for (Job j : jobsShow) {
                if (j.getCategoryId() == categoryId)
                    list.add(j);
            }
        } else if (categoryId == 0) {
            list = jobsShow;
            System.out.println(jobsShow.size() + " mlist job size");
        }
        jobsShow = list;
        notifyDataSetChanged();
    }

    public void FilterByStatus(int status) {
        if (jobsShow == null)
            return;
        else if (status == -1)
            return;
        List<Job> list = new ArrayList<>();
        for (Job j : jobsShow) {
            if (j.getStatus() == status)
                list.add(j);
        }
        jobsShow = list;
        notifyDataSetChanged();
    }

    public void FilterByDateToDate(Date start, Date end) {
        if (jobsShow == null)
            return;
        List<Job> list = new ArrayList<>();
        for (Job job : jobsShow) {
            if (CalendarExtension.Remaining_minute(start, job.getEndDate()) >= 0 && CalendarExtension.Remaining_minute(end, job.getEndDate()) >= 0)
                list.add(job);
        }
        jobsShow = list;
        notifyDataSetChanged();
    }

    public void Revert() {
        jobsShow = jobs;
        notifyDataSetChanged();
    }

    public int getNumJob(int status) {
        int temp;
        switch (status) {
            case GeneralData.NON_STATUS:
                return jobs.size();
            default:
                return numJobsByStatus(status);
        }
    }

    private int numJobsByStatus(int status) {
        int num = 0;
        for (Job job : jobs
        ) {
            if (job.getStatus() == status)
                num++;
        }
        return num;
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
