

package com.ctk43.doancoso.View.Adapter;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.JobFragment;
import com.ctk43.doancoso.View.ManagerJobFragment;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;

import java.util.List;

public class JobDetailAdapter extends RecyclerView.Adapter<JobDetailAdapter.StoryHolder> {
    private List<JobDetail> listJobDetail;
    private final Context mContext;
    private JobDetailViewModel jobDetailViewModel;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();

    public JobDetailAdapter(Context mContext,JobDetailViewModel jobDetailViewModel) {
        this.jobDetailViewModel = jobDetailViewModel;
        this.mContext = mContext;
    }
    public void setData(List<JobDetail> jobDetails){
        listJobDetail = jobDetails;
    }

    @Override
    public JobDetailAdapter.StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.job_detail_item, parent, false);
        return new JobDetailAdapter.StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobDetailAdapter.StoryHolder holder, int position) {
        JobDetail item = listJobDetail.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(position));
        holder.tvJdName.setText(item.Name);
        holder.tvJdDes.setText(item.Description);
        holder.tvEstimatedTime.setText(String.valueOf(item.EstimatedCompletedTime));
        holder.tvActualTime.setText(String.valueOf(item.ActualCompletedTime));
        viewBinderHelper.bind(holder.swipeRevealLayout,String.valueOf(position));
        holder.layout_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogYesNo = new Dialog(mContext);
                Extension.dialogYesNo(dialogYesNo,null,"Hay không");
                Button buttn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
                Button buttn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
                dialogYesNo.setCancelable(true);
                buttn_yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jobDetailViewModel.DeleteJobDetail(item);
                        notifyItemRemoved(listJobDetail.indexOf(item));
                         dialogYesNo.dismiss();
                        new JobFragment();
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

        if (item.Priority == true)
            holder.img_Priority.setImageResource(R.drawable.ic_baseline_star_24);
        else if (item.Priority == false)
            holder.img_Priority.setImageResource(R.drawable.ic_baseline_star_outline_24);

        if (item.Status == 1) holder.cb_status.setChecked(true);
        else if (item.Status != 1) holder.cb_status.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return listJobDetail.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
        TextView tvJdName;
        TextView tvJdDes;
        TextView tvEstimatedTime;
        TextView tvActualTime;
        FrameLayout layout_delete;
        //SeekBar sb_Progress = vJobDetail.findViewById(R.id.sb_jd_progress);
        ImageView img_Priority;
        CheckBox cb_status;
        @SuppressLint("ResourceType")
        public StoryHolder(View itemView) {
            super(itemView);
            swipeRevealLayout = itemView.findViewById(R.id.item_job_detail);
            layout_delete = itemView.findViewById(R.id.layout_delete_item);
            tvJdName = itemView.findViewById(R.id.tv_jd_name);
            tvJdDes = itemView.findViewById(R.id.tv_jd_description);
            tvEstimatedTime = itemView.findViewById(R.id.tv_jd_estimated_time);
            tvActualTime = itemView.findViewById(R.id.tv_jd_actual_time);
            //SeekBar sb_Progress = vJobDetail.findViewById(R.id.sb_jd_progress);
            img_Priority = itemView.findViewById(R.id.img_jd_level);
            cb_status = itemView.findViewById(R.id.chk_status);
        }
    }
    private void deleteItem(){

    }
}
