package com.ctk43.doancoso.View.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Library.DialogExtension;
import com.ctk43.doancoso.Library.KeyFragment;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.ViewPagerJobAdapter;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ManagerJobFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private View view;
    private JobFragment jobFragment;
    public boolean isAll;
    CategoryViewModel categoryViewModel;
    JobViewModel jobViewModel;
    ViewPagerJobAdapter adapterManager;
    List<Category> categories;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initViewModel();
        view = inflater.inflate(R.layout.fragment_manager_job, container, false);
        InnitView(view);
        tabLayout = view.findViewById(R.id.tab_layout_job);
        viewPager = view.findViewById(R.id.job_view_pager);
        return view;
    }

    private void initViewModel() {
        categoryViewModel = new CategoryViewModel();
        categoryViewModel.setContext(requireContext());
        jobViewModel = new ViewModelProvider(this.getActivity()).get(JobViewModel.class);
        jobViewModel.setData(requireContext());
    }

    public void getCategories(List<Category> categories){
        this.categories = categories;
    }

    private void InnitView(View view) {
        ImageButton img_btn_filter = view.findViewById(R.id.img_btn_filter);
        ImageButton img_btn_convert = view.findViewById(R.id.img_btn_convert);
        Spinner spn_category = view.findViewById(R.id.spn_category);
        categories = categoryViewModel.getCategoryView();
        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item,categories) ;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_category.setAdapter(adapter);
        spn_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                    switch (position) {
                        case 0:
                            showAll();
                            break;
                        case 1:
                            showWeek();
                            break;
                        case 2:
                            showMonth();
                            break;
                        default:
                            showCategory(categories.get(position).getId());
                            break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn_category.setSelection(1);
        img_btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJobFragment();
                DialogExtension.showDialogFilterJob(getContext(),false,jobFragment.getAdapter());
            }
        });

        img_btn_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJobFragment();
                jobFragment.getAdapter().Revert();
            }
        });
    }
    public JobFragment getJobFragment(){
        return jobFragment = (JobFragment) adapterManager.getHashMap().get(viewPager.getCurrentItem());
    }


    private void showWeek() {
        adapterManager = new ViewPagerJobAdapter(this);
        tabLayout.setVisibility(View.VISIBLE);
        adapterManager.setJobs(
                jobViewModel.getJobsWeek(CalendarExtension.currDate(),-1),
                jobViewModel.getJobsWeek(CalendarExtension.currDate(),0),
                jobViewModel.getJobsWeek(CalendarExtension.currDate(),1));
        viewPager.setAdapter(adapterManager);
        viewPager.setCurrentItem(1);
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case KeyFragment.MANAGE_JOBS_PREVIOUS:
                    tab.setText(R.string.week_ago);
                    break;
                case KeyFragment.MANAGE_JOBS_CURR:
                    tab.setText(R.string.week_current);
                    break;
                case KeyFragment.MANAGE_JOBS_NEXT:
                    tab.setText(R.string.next_week);
                    break;
            }
        }).attach();
    }

    private void showMonth() {
        adapterManager = new ViewPagerJobAdapter(this);
        tabLayout.setVisibility(View.VISIBLE);
        int currMonth = CalendarExtension.getMonth(Calendar.getInstance().getTime(),0);
        int previousMonth = CalendarExtension.getMonth(Calendar.getInstance().getTime(),-1);
        int nextMonth = CalendarExtension.getMonth(Calendar.getInstance().getTime(),1);
        int curr = 0;
        int previous =0;
        int next =0;
        if(CalendarExtension.isJanuary(currMonth))
            previous = -1;
        else if(CalendarExtension.isDecember(currMonth)){
            next =1;
        }
        adapterManager.setJobs(
                jobViewModel.getJobsMonth(previousMonth,
                        CalendarExtension.getYear(Calendar.getInstance().getTime(), previous)),
                jobViewModel.getJobsMonth(currMonth,
                        CalendarExtension.getYear(Calendar.getInstance().getTime(), curr)),
                jobViewModel.getJobsMonth(nextMonth,
                        CalendarExtension.getYear(Calendar.getInstance().getTime(), next)));
        viewPager.setAdapter(adapterManager);
        viewPager.setCurrentItem(1);
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case KeyFragment.MANAGE_JOBS_PREVIOUS:
                    tab.setText(getContext().getString(R.string.month)+" "+(previousMonth+1));
                    break;
                case KeyFragment.MANAGE_JOBS_CURR:
                    tab.setText(getContext().getString(R.string.month)+" "+(currMonth+1));
                    break;
                case KeyFragment.MANAGE_JOBS_NEXT:
                    tab.setText(getContext().getString(R.string.month)+" "+(nextMonth+1));
                    break;

            }
        }).attach();
    }


    private void showDate() {
        tabLayout.setVisibility(View.GONE);
        adapterManager = new ViewPagerJobAdapter(this);
        adapterManager.setJobs(jobViewModel.getJobs());
        adapterManager.createFragment(0);
        viewPager.setAdapter(adapterManager);
        viewPager.setCurrentItem(0);
        viewPager.setUserInputEnabled(false);
    }

    private void showAll() {
        tabLayout.setVisibility(View.GONE);
        adapterManager = new ViewPagerJobAdapter(this);
        adapterManager.setJobs(jobViewModel.getJobs());
        adapterManager.createFragment(0);
        viewPager.setAdapter(adapterManager);
        viewPager.setCurrentItem(0);
        viewPager.setUserInputEnabled(false);
    }
    private void showCategory(int idCategory){
        tabLayout.setVisibility(View.GONE);
        adapterManager = new ViewPagerJobAdapter(this);
        adapterManager.setJobs(jobViewModel.getByCategoryId(idCategory));
        adapterManager.createFragment(0);
        viewPager.setAdapter(adapterManager);
        viewPager.setCurrentItem(0);
        viewPager.setUserInputEnabled(false);
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