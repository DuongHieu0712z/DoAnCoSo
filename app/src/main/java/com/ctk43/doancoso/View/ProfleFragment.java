package com.ctk43.doancoso.View;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.zip.Inflater;

public class ProfleFragment extends Fragment {
    private Context mContext;
    JobViewModel jobViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        InnitView(view);
    }

    private void InnitView(View view) {
        TextView tv_in_comming = view.findViewById(R.id.tv_profile_in_coming);
        TextView tv_on_going = view.findViewById(R.id.tv_profile_on_going);
        TextView tv_complete = view.findViewById(R.id.tv_profile_complete);
        TextView tv_over = view.findViewById(R.id.tv_profile_over);
        TextView tv_over_complete = view.findViewById(R.id.tv_profile_over_complete);

        JobViewModel jobViewModel = new JobViewModel();
        jobViewModel.setData(mContext);

        tv_in_comming.setText(String.valueOf( jobViewModel.sumStatus(0)));
        tv_on_going.setText(String.valueOf( jobViewModel.sumStatus(1)));
        tv_complete.setText(String.valueOf( jobViewModel.sumStatus(2)));
        tv_over.setText(String.valueOf( jobViewModel.sumStatus(3)));
        tv_over_complete.setText(String.valueOf( jobViewModel.sumStatus(4)));
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}
