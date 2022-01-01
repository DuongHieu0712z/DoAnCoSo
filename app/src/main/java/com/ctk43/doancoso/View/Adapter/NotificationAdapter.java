package com.ctk43.doancoso.View.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.NotificationModel;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.AddJobActivity;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {
    private List<NotificationModel> notificationModels;
    private Context mContext;
    private String[] fakeData;

    public NotificationAdapter(Context mContext, String[] fakeData) {
        this.mContext = mContext;
        this.fakeData = fakeData;
    }

    public NotificationAdapter(Context mContext, List<NotificationModel> notificationModels) {
        this.notificationModels = notificationModels;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_notification, parent, false);
        return new NotificationAdapter.NotificationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        String test = fakeData[position];
        //holder.img_priority.setImageResource();
        if(test.length() >150)
            holder.tv_name.setText(test.substring(0,145)+"...");
        //holder.tv_time.setText();
        holder.btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOpenDialog(fakeData[position]);
            }
        });

    }

    @Override
    public int getItemCount() {
        return fakeData.length;
        //return notificationModels.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        ImageView img_priority;
        TextView tv_name;
        TextView tv_time;
        ImageButton btn_more;

        LinearLayout item;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            img_priority = itemView.findViewById(R.id.img_notification_priority);
            tv_name = itemView.findViewById(R.id.tv_notification_name);
            tv_time = itemView.findViewById(R.id.tv_notification_time);
            btn_more = itemView.findViewById(R.id.img_btn_notification_more);

            item = itemView.findViewById(R.id.ln_item_notification);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    item.setBackgroundColor(Color.parseColor("#3e4556"));
                }
            });
        }
    }
    private void onOpenDialog(String notificationName) {
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
        tv_name.setText(notificationName);

        LinearLayout ln_remove = dialog.findViewById(R.id.ln_notification_remove);
        ln_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //----
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}