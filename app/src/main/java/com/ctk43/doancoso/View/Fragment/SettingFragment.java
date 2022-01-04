package com.ctk43.doancoso.View.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ctk43.doancoso.Model.Setting;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.SettingAdapter;

import java.util.ArrayList;

public class SettingFragment extends Fragment {
    private Context mContext;

    private String[] Titles ={"Notification", "Theme", "About"};
    private String[] Contents = {"Setting notification", "Setting theme", "All about you"};
    private  int[] Images = {R.drawable.ic_baseline_settings_24, R.drawable.ic_baseline_settings_24, R.drawable.ic_baseline_settings_24};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.setting_fragment, container, false);
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
        //((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Cài đặt");
        ArrayList<Setting> settings = new ArrayList<>();
        for (int i =0 ; i< Titles.length; i++){
            settings.add(new Setting(Titles[i], Contents[i], Images[i]));
        }
        SettingAdapter adapter = new SettingAdapter(settings);
        ListView lv = view.findViewById(R.id.lv_settings);
        lv.setAdapter(adapter);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    @Override
    public void onStop() {
        super.onStop();
    }
}
