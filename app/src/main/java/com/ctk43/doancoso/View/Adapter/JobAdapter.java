package com.ctk43.doancoso.View.Adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
import com.ctk43.doancoso.View.JobFragment;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.ArrayList;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {
    private List<Job> listJob;
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
    }

    @Override
    public void onBindViewHolder(JobHolder holder, @SuppressLint("RecyclerView") int position) {

        Job item = listJob.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));

        holder.tv_job_name.setText(item.getName());
        holder.tv_job_des.setText(item.getDescription());
        holder.img_priority.setImageResource(GeneralData.getImgPriority(item.getPriority()));
        holder.checkBox.setChecked(item.getStatus() == 2);
        setProcess(holder.tv_job_prg, holder.progressBar, item);
        setTextStatus(item, holder.tv_job_status, holder.tv_time_title, holder.tv_time);
        holder.layout_funcion.setOnClickListener(v -> DialogDeleteJob(item));
        holder.itemJob.setOnClickListener(v -> ViewJobDetail(item));
        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> CheckOrUncheck(holder.checkBox, item));

//        JobDetailViewModel jobDetailViewModel = new JobDetailViewModel();
//        jobDetailViewModel.setContext(context, item.getId());
//
//        holder.checkBox.setOnCheckedChangeListener((compoundButton, b) -> {
//            Toast.makeText(context, String.valueOf(b), Toast.LENGTH_SHORT).show();
//            jobDetailViewModel.getJobDetails().observe((LifecycleOwner) context, new Observer<List<JobDetail>>() {
//                @Override
//                public void onChanged(List<JobDetail> jobDetails) {
//                    for (JobDetail jobDetail : jobDetails) {
//                        jobDetail.setStatus(holder.checkBox.isChecked());
//                        jobDetailViewModel.update(jobDetail);
//                    }
////                jobDetailViewModel.update(jobDetails.toArray(new JobDetail[0]));
//                }
//            });
//        });
    }

    @Override
    public int getItemCount() {
        if (listJob == null)
            return 0;
        return listJob.size();
    }

    void setProcess(TextView tv_progress, ProgressBar progressBar, Job job) {
        int progress = (int) job.getProgress() * 100;
        String prgString = progress + " %";
        tv_progress.setText(prgString);
        progressBar.setProgress(progress);
    }

    void setTextStatus(Job job, TextView status, TextView title_Time, TextView time) {
        int color = ContextCompat.getColor(context, (GeneralData.getColorStatus(job.getStatus())));

        time.setText(Extension.TimeRemaining(job.getStartDate(), job.getEndDate()));
        time.setTextColor(color);

        status.setText(GeneralData.getStatus(job.getStatus()));
        status.setTextColor(color);

        title_Time.setText(GeneralData.getTimeTitle(job.getStatus()));
        title_Time.setTextColor(color);

    }

    void deleteItem(Job job) {
        jobViewModel.delete(job);
        notifyItemRemoved(listJob.indexOf(job));
        listJob.remove(job);
    }

    void ViewJobDetail(Job job) {
        Intent intent = new Intent(context, JobDetailActivity.class);
        intent.putExtra("JobID", job.getId());
        context.startActivity(intent);
    }

    private void updateStatus(Job job, boolean isFinish) {
        jobViewModel.checkOrUncheck(job, isFinish);
       JobDetailViewModel jobDetailViewModel = new JobDetailViewModel();
       jobDetailViewModel.setContext(context,job.getId());
       jobDetailViewModel.syncJob(job);
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
            Extension.dialogYesNo(dialogYesNo, confirm, context.getString(R.string.message_check_job));
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
            Extension.dialogYesNo(dialogYesNo, confirm, context.getString(R.string.message_finish_all_job_detail));
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

    public static class JobHolder extends RecyclerView.ViewHolder {
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
        }
    }
}
