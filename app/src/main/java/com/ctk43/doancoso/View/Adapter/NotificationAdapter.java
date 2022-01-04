package com.ctk43.doancoso.View.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Spannable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.NotificationModel;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.AddJobActivity;
import com.ctk43.doancoso.View.Activity.NotificationManagementActivity;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.ctk43.doancoso.ViewModel.NotificationViewModel;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private List<NotificationModel> notificationModels;
    private Context mContext;
    private JobViewModel jobViewModel;


    public NotificationAdapter(Context mContext, List<NotificationModel> notificationModels) {
        this.notificationModels = notificationModels;
        this.mContext = mContext;
        jobViewModel = new JobViewModel();
        jobViewModel.setData(mContext);
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        NotificationModel item = notificationModels.get(position);
        Job job = getJob(position);
        holder.img_priority.setImageResource(GeneralData.getImgPriority(job.getPriority()));
        holder.tv_content.setText(Extension.setContent(mContext,job));
        holder.tv_time.setText(CalendarExtension.formatDateTime(item.getDateOfRecord()));
        if(item.getStatus().equals(GeneralData.STATUS_NOTIFICATION_SEEN))
            holder.item.setBackgroundColor(ContextCompat.getColor(mContext,R.color.bg_color));
        else{
            holder.item.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_round_thin_2));
        }
        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOpenDialog(Extension.setContent(mContext,job),item);
            }
        });
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateSeen(item);
            }
        });


    }
    private Job getJob(int position){
        Job job = jobViewModel.getJobById(notificationModels.get(position).getJobId());
        return job;
    }

    private void updateSeen(NotificationModel item){
        if(item.getStatus().equals(GeneralData.STATUS_NOTIFICATION_ACTIVE)){
            item.setStatus(GeneralData.STATUS_NOTIFICATION_SEEN);
            ((NotificationManagementActivity)mContext).notificationViewModel.update(item);
        }
    }
    private void Delete(NotificationModel item){
        if(item!=null){
            item.setStatus(GeneralData.STATUS_NOTIFICATION_DELETE);
            ((NotificationManagementActivity)mContext).notificationViewModel.update(item);
        }
    }
    @Override
    public int getItemCount() {
        return notificationModels.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        ImageView img_priority;
        TextView tv_content;
        TextView tv_time;
        ImageButton btn_more;
        LinearLayout item;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            img_priority = itemView.findViewById(R.id.img_notification_priority);
            tv_content = itemView.findViewById(R.id.tv_content);
            tv_time = itemView.findViewById(R.id.tv_notification_time);
            btn_more = itemView.findViewById(R.id.img_btn_notification_more);
            item = itemView.findViewById(R.id.ln_item_notification);
        }
    }
    private void onOpenDialog(Spannable content,NotificationModel notificationModel) {
        final Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_notification_more);
        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.BOTTOM;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(true);
        TextView tv_name = dialog.findViewById(R.id.tv_dialog_notification_name);
        tv_name.setText(content);
        LinearLayout ln_remove = dialog.findViewById(R.id.ln_notification_remove);
        ln_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateSeen(notificationModel);
                Delete(notificationModel);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}