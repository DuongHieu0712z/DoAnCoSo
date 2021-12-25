package com.ctk43.doancoso.View.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.ctk43.doancoso.View.JobFragment;
import com.ctk43.doancoso.View.MonthFragment;

public class ViewPagerJobAdapter extends FragmentStateAdapter {
    public JobFragment jobFragment = new JobFragment();

    public ViewPagerJobAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position)
        {
            case 0:
               //   return new JobFragment();
            case 1:
               return jobFragment = new JobFragment();

            case 2:
         //       return new JobFragment();
            case 3:
           //     return new JobFragment();
            default:
                return jobFragment =new JobFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 3;
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
