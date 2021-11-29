

package com.ctk43.doancoso.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;

import java.util.ArrayList;

public class JobDetailAdapter extends RecyclerView.Adapter<JobDetailAdapter.StoryHolder> {
    private final ArrayList<JobDetail> listJobDetail;
    private final Context mContext;

    public JobDetailAdapter(ArrayList<JobDetail> listJobDetail, Context mContext) {
        this.listJobDetail = listJobDetail;
        this.mContext = mContext;
    }

    @Override
    public JobDetailAdapter.StoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.job_detail_item, parent, false);
        return new JobDetailAdapter.StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobDetailAdapter.StoryHolder holder, int position) {
        JobDetail item = listJobDetail.get(position);
        holder.tvJdName.setText(item.Name);
        holder.tvJdDes.setText(item.Description);
        holder.tvEstimatedTime.setText(String.valueOf(item.EstimatedCompletedTime));
        holder.tvActualTime.setText(String.valueOf(item.ActualCompletedTime));

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
        TextView tvJdName;
        TextView tvJdDes;
        TextView tvEstimatedTime;
        TextView tvActualTime;
        //SeekBar sb_Progress = vJobDetail.findViewById(R.id.sb_jd_progress);
        ImageView img_Priority;
        CheckBox cb_status;
        public StoryHolder(View itemView) {
            super(itemView);
            tvJdName = itemView.findViewById(R.id.tv_jd_name);
            tvJdDes = itemView.findViewById(R.id.tv_jd_description);
            tvEstimatedTime = itemView.findViewById(R.id.tv_jd_estimated_time);
            tvActualTime = itemView.findViewById(R.id.tv_jd_actual_time);
            //SeekBar sb_Progress = vJobDetail.findViewById(R.id.sb_jd_progress);
            img_Priority = itemView.findViewById(R.id.img_jd_level);
            cb_status = itemView.findViewById(R.id.chk_status);
        }
    }
}
