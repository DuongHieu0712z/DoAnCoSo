package com.ctk43.doancoso.View.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ctk43.doancoso.Library.KeyFragment;
import com.ctk43.doancoso.View.Fragment.ManagerJobFragment;
import com.ctk43.doancoso.View.Fragment.MonthFragment;
import com.ctk43.doancoso.View.Fragment.ProfleFragment;
import com.ctk43.doancoso.View.Fragment.SettingFragment;

import java.util.HashMap;

public class ViewPagerAdapter extends FragmentStateAdapter {
    private HashMap<Integer, Fragment> hashMap = new HashMap<>();
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case KeyFragment.MONTH:
                MonthFragment monthFragment = new MonthFragment();
                hashMap.put(position, monthFragment);
                return monthFragment;
            case KeyFragment.SETTING:
                SettingFragment settingFragment = new SettingFragment();
                hashMap.put(position, settingFragment);
                return settingFragment;
            case KeyFragment.PROFILE:
                ProfleFragment profleFragment = new ProfleFragment();
                hashMap.put(position, profleFragment);
                return profleFragment;
            case KeyFragment.MANAGE_JOBS:
            default:
                ManagerJobFragment managerJobFragment = new ManagerJobFragment();
                hashMap.put(position, managerJobFragment);
                return managerJobFragment;
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public HashMap<Integer, Fragment> getHashMap() {
        return hashMap;
    }

}
