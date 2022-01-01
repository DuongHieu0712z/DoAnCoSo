package com.ctk43.doancoso.View.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ctk43.doancoso.View.Fragment.ManagerJobFragment;
import com.ctk43.doancoso.View.Fragment.MonthFragment;
import com.ctk43.doancoso.View.Fragment.ProfleFragment;
import com.ctk43.doancoso.View.Fragment.SettingFragment;

public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
                  return new ManagerJobFragment();
            case 1:
               return new MonthFragment();
            case 2:
                return new SettingFragment();
            case 3:
                return new ProfleFragment();
            default:
                return new ManagerJobFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }

    /*public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new JobFragment();
            default:
                return  new JobFragment();
        }
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Công Việc";
                break;
        }
        return title;
    }*/
}
