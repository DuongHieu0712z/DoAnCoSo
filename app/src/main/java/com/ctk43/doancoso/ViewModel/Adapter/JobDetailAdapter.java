

package com.ctk43.doancoso.ViewModel.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return new StoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobDetailAdapter.StoryHolder holder, int position) {
        JobDetail item = listJobDetail.get(position);
    }

    @Override
    public int getItemCount() {
        return listJobDetail.size();
    }

    public class StoryHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        public StoryHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_jd_name);
            //itemView.setOnClickListener(v -> {
            //   ((MainActivity)mContext).gotoM003Screen(listJob, (StoryEntity)tvName.getTag());
            //});
        }
    }
}
