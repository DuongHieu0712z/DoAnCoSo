package com.ctk43.doancoso.View.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
import com.ctk43.doancoso.View.Activity.JobDetail_CountUp;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.List;

public class JobDetailAdapter extends RecyclerView.Adapter<JobDetailAdapter.JobDetailHolder> {
    private final Context mContext;
    private List<JobDetail> listJobDetail;
    private JobDetailViewModel jobDetailViewModel;
    private JobViewModel jobViewModel;
    private Job job;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public JobDetailAdapter(Context mContext, JobDetailViewModel jobDetailViewModel, JobViewModel jobViewModel) {
        this.jobDetailViewModel = jobDetailViewModel;
        this.jobViewModel = jobViewModel;
        this.mContext = mContext;
    }

    public void setData(List<JobDetail> jobDetails) {
        listJobDetail = jobDetails;
    }

    @Override
    public JobDetailHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.job_detail_item, parent, false);
        return new JobDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobDetailHolder holder, int position) {
        JobDetail item = listJobDetail.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));
        holder.tvJdName.setText(item.getName());
        holder.tvJdDes.setText(item.getDescription());
        holder.tvEstimatedTime.setText(String.valueOf(item.getEstimatedCompletedTime()));
        holder.tvActualTime.setText(String.valueOf(item.getActualCompletedTime()));
        if (item.isPriority() == true)
            holder.img_Priority.setImageResource(R.drawable.ic_baseline_star_24);
        else if (item.isPriority() == false)
            holder.img_Priority.setImageResource(R.drawable.ic_baseline_star_outline_24);
        holder.checkBox.setChecked(item.getStatus());
        holder.jobDetailItem.setOnClickListener(v -> {
            JobClock(item.getId());
        });
        holder.checkBox.setOnClickListener(v ->  {
            IsFinish(holder.checkBox,item);
        });
    }

    public void IsFinish(CheckBox checkBox, JobDetail jobDetail){
        if(checkBox.isChecked()){
            jobDetail.setStatus(true);
            jobDetailViewModel.update(jobDetail);
            notifyDataSetChanged();
        }else {
            DialogUnCheckJobDetail(jobDetail,checkBox);
        }
    }

    public void JobClock(int id){
        Intent intent = new Intent(mContext, JobDetail_CountUp.class);
        intent.putExtra("id",id);
        mContext.startActivity(intent);

    }

    public Job getJob() {
        return job;
    }

    void DialogDeleteJobDetail(JobDetail jobDetail) {
        final Dialog dialogYesNo = new Dialog(mContext);
        Extension.dialogYesNo(dialogYesNo, mContext.getString(R.string.confirm_delete), mContext.getString(R.string.message_uncheck_job_detail));
        Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
        Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
        dialogYesNo.setCancelable(true);
        btn_yes.setOnClickListener(v -> {
            jobDetailViewModel.delete(jobDetail);
            dialogYesNo.dismiss();
        });
        btn_no.setOnClickListener(v -> dialogYesNo.dismiss());
        dialogYesNo.show();
    }

    void DialogUnCheckJobDetail(JobDetail jobDetail,CheckBox checkBox) {
        final Dialog dialogYesNo = new Dialog(mContext);
        Extension.dialogYesNo(dialogYesNo, mContext.getString(R.string.confirm_delete), mContext.getString(R.string.message_uncheck_job_detail));
        Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
        Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
        dialogYesNo.setCancelable(true);
        btn_yes.setOnClickListener(v -> {
            jobDetail.setStatus(false);
            jobDetailViewModel.update(jobDetail);
            notifyDataSetChanged();
            dialogYesNo.dismiss();
        });
        btn_no.setOnClickListener(v -> {
            dialogYesNo.dismiss();
            checkBox.setChecked(true);
        });
        dialogYesNo.show();
    }

    @Override
    public int getItemCount() {
        return listJobDetail.size();
    }

    public class JobDetailHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout jobDetailItem;
        TextView tvJdName;
        TextView tvJdDes;
        TextView tvEstimatedTime;
        TextView tvActualTime;
        ImageView img_Priority;
        CheckBox checkBox;

        public JobDetailHolder(View itemView) {
            super(itemView);
            jobDetailItem = itemView.findViewById(R.id.ln_item_job_detail);
            swipeRevealLayout = itemView.findViewById(R.id.item_job_detail);
            tvJdName = itemView.findViewById(R.id.tv_jd_name);
            tvJdDes = itemView.findViewById(R.id.tv_jd_description);
            tvEstimatedTime = itemView.findViewById(R.id.tv_jd_estimated_time);
            tvActualTime = itemView.findViewById(R.id.tv_jd_actual_time);
            //SeekBar sb_Progress = vJobDetail.findViewById(R.id.sb_jd_progress);
            img_Priority = itemView.findViewById(R.id.img_jd_level);
            checkBox = itemView.findViewById(R.id.chk_finish_job_detail);
        }
    }

}
