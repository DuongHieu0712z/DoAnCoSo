package com.ctk43.doancoso.View.Activity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.NotificationAdapter;

public class NotificationManagementActivity extends AppCompatActivity {
    RecyclerView rcv_new;
    RecyclerView rcv_old;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        innitView();
    }

    private void innitView() {
        getSupportActionBar().setTitle("Quản lý thông báo");

        rcv_new = findViewById(R.id.rcv_notification_new);
        rcv_old = findViewById(R.id.rcv_notification_old);

        //fake data
        String[] fakedatas = {"Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều...",
                "Xefiok have posted a new video: Nhân ngày đầu năm mới, mời mọi người cùng thử một quẻ bói vui vẻ xem điều"
        };
        NotificationAdapter adapter = new NotificationAdapter(NotificationManagementActivity.this, fakedatas);

        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(NotificationManagementActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(NotificationManagementActivity.this) {
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };

        rcv_new.setLayoutManager(linearLayoutManager1);
        rcv_old.setLayoutManager(linearLayoutManager2);

        rcv_new.setAdapter(adapter);
        rcv_old.setAdapter(adapter);

    }
}
