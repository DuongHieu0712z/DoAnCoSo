package com.ctk43.doancoso.View;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ctk43.doancoso.View.MainActivity;
import com.ctk43.doancoso.R;

import java.text.DateFormat;
import java.util.Calendar;

public class AddJobFragment extends Fragment implements DatePickerDialog.OnDateSetListener {
    private Context mContext;
    private String currentDate;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_add_new_job, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    private void initView(View v) {
        Button btn_add_new_Job = v.findViewById(R.id.btn_add_new_job);
        btn_add_new_Job.setBackgroundTintMode(null);

        TextView tv_date = v.findViewById(R.id.tv_date);
        tv_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity) getActivity()).gotoShowDialogScreen();
                //tv_date.setText(((MainActivity) getActivity()).currentDate);
            }
        });
        TextView tv_time = v.findViewById(R.id.tv_time);
        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //((MainActivity) getActivity()).gotoShowTimeDialogScreen();
            }
        });
        ImageView img_back = v.findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).gotoM001Screen();
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
    }
}
