package com.ctk43.doancoso.View.Activity;

import android.os.Bundle;
import android.view.GestureDetector;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Library.GeneralData;
import com.ctk43.doancoso.Model.NotificationModel;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.NotificationAdapter;
import com.ctk43.doancoso.ViewModel.NotificationViewModel;

import java.util.List;

public class NotificationManagementActivity extends AppCompatActivity {
    RecyclerView rcv_new;
    RecyclerView rcv_old;
    public NotificationViewModel notificationViewModel;
    NotificationAdapter notificationAdapterNew;
    NotificationAdapter notificationAdapterOld;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        innitView();
    }

    private void innitView() {
        notificationViewModel = new ViewModelProvider(this).get(NotificationViewModel.class);
        notificationViewModel.setData(this);
        rcv_new = findViewById(R.id.rcv_notification_new);
        rcv_old = findViewById(R.id.rcv_notification_old);
        rcv_new.setNestedScrollingEnabled(false);
        rcv_old.setNestedScrollingEnabled(false);
        ImageButton imageButton = findViewById(R.id.img_finish_notification);
        notificationViewModel.geListNotificationByStatus(GeneralData.STATUS_NOTIFICATION_ACTIVE).observe(this, notificationModels -> {
            notificationAdapterNew = new NotificationAdapter(this, notificationModels);
            rcv_new.setLayoutManager(new LinearLayoutManager(this));
            rcv_new.setAdapter(notificationAdapterNew);

            imageButton.setOnClickListener(v -> {
                seenAll(notificationModels);
            });
        });
        notificationViewModel.geListNotificationByStatus(GeneralData.STATUS_NOTIFICATION_SEEN).observe(this, notificationModels -> {
            notificationAdapterOld = new NotificationAdapter(this, notificationModels);
            rcv_old.setLayoutManager(new LinearLayoutManager(this));
            rcv_old.setAdapter(notificationAdapterOld);
        });
    }

    private void seenAll( List<NotificationModel> notificationViewModelList) {
        if (notificationViewModelList.size() > 0) {
            for (NotificationModel notificationModel : notificationViewModelList
            ) {
                notificationModel.setStatus(GeneralData.STATUS_NOTIFICATION_SEEN);
            }
            notificationViewModel.update(notificationViewModelList.toArray(new NotificationModel[0]));
        }
    }
}
