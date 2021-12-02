package com.ctk43.doancoso.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.ViewPagerJobAdapter;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ManagerJobFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2  viewPager;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_manager_job, container, false);
        tabLayout = view.findViewById(R.id.tab_layout_job);
        viewPager = view.findViewById(R.id.job_view_pager);
        ViewPagerJobAdapter adapter = new ViewPagerJobAdapter(this);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Tuần trước");
                    break;
                case 1:
                    tab.setText("Tuần này");
                    break;
                case 2:
                    tab.setText("Tuần sau");
                    break;
            }
        }).attach();
        return  view;
    }
}