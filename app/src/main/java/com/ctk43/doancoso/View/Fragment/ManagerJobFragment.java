package com.ctk43.doancoso.View.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.ctk43.doancoso.Library.CalendarExtension;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.View.Adapter.ViewPagerJobAdapter;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class ManagerJobFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private View view;
    public JobFragment jobFragment;
    public boolean isAll;

    CategoryViewModel categoryViewModel;
    JobViewModel jobViewModel;
    List<Category> categories;

    RadioButton rd_priority_0;
    RadioButton rd_priority_1;
    RadioButton rd_priority_2;
    RadioButton rd_priority_3;

    RadioButton rd_status_0;
    RadioButton rd_status_1;
    RadioButton rd_status_2;
    RadioButton rd_status_3;
    RadioButton rd_status_4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initViewModel();

        // Inflate the layout for this fragment
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
        spn_category.setSelection(1);
        spn_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(isAll){
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
                }else {

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        img_btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialogFilter();
            }
        });

        img_btn_convert.setOnClickListener(new View.OnClickListener() {
            final ArrayList<Category>[] categories = new ArrayList[]{new ArrayList<>()};

            @Override
            public void onClick(View view) {
                JobViewModel jobViewModel = new JobViewModel();
                jobViewModel.setData(getContext());
                JobAdapter jobAdapter = new JobAdapter(getContext(), jobViewModel);
                jobViewModel.setData(getContext());
                RecyclerView rcv = getActivity().findViewById(R.id.rcv_display_job);
                jobViewModel.getJobs().observe(getActivity(), jobs -> {
                    jobAdapter.setJob(jobs);
                    rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcv.setAdapter(jobAdapter);
                    jobAdapter.Revert();
                });
            }
        });
    }

    private void showDialogFilter() {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.filter_dialog);
        Window window = dialog.getWindow();
        if (window == null) return;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.LEFT;
        window.setAttributes(windowAttribute);
        dialog.setCancelable(true);

        rd_priority_0 = dialog.findViewById(R.id.rb_priority_0);
        rd_priority_1 = dialog.findViewById(R.id.rb_priority_1);
        rd_priority_2 = dialog.findViewById(R.id.rb_priority_2);
        rd_priority_3 = dialog.findViewById(R.id.rb_priority_3);

        rd_status_0 = dialog.findViewById(R.id.rb_status_0);
        rd_status_1 = dialog.findViewById(R.id.rb_status_1);
        rd_status_2 = dialog.findViewById(R.id.rb_status_2);
        rd_status_3 = dialog.findViewById(R.id.rb_status_3);
        rd_status_4 = dialog.findViewById(R.id.rb_status_4);

        Button btn_filter = dialog.findViewById(R.id.btn_filter);

        JobViewModel jobViewModel = new JobViewModel();
        jobViewModel.setData(getContext());
        JobAdapter jobAdapter = new JobAdapter(getContext(), jobViewModel);
        jobViewModel.setData(getContext());
        RecyclerView rcv = getActivity().findViewById(R.id.rcv_display_job);
        rcv.setAdapter(jobAdapter);

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rd_priority_0.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByPriority(0);
                        dialog.dismiss();
                    });
                } else if (rd_priority_1.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByPriority(1);
                        dialog.dismiss();
                    });
                } else if (rd_priority_2.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByPriority(2);
                        dialog.dismiss();
                    });
                } else if (rd_priority_3.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByPriority(3);
                        dialog.dismiss();
                    });
                } else if (rd_status_0.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(0);
                        dialog.dismiss();
                    });
                } else if (rd_status_1.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(1);
                        dialog.dismiss();
                    });
                } else if (rd_status_2.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(2);
                        dialog.dismiss();
                    });
                } else if (rd_status_3.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(3);
                        dialog.dismiss();
                    });
                } else if (rd_status_4.isChecked()) {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(4);
                        dialog.dismiss();
                    });
                } else {
                    Toast.makeText(getContext(), "Vui lòng chọn lọc", Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }

    private void showWeek() {
        ViewPagerJobAdapter adapter = new ViewPagerJobAdapter(this);
        tabLayout.setVisibility(View.VISIBLE);
        Date date1 =CalendarExtension.getStartTimePreviousWeek();
        Date date2 =Calendar.getInstance().getTime();
        Date date3 =CalendarExtension.getStartTimeNextWeek();

        adapter.setJobs(
                jobViewModel.getJobsWeek(CalendarExtension.getStartTimePreviousWeek()),
                jobViewModel.getJobsWeek(Calendar.getInstance().getTime()),
                jobViewModel.getJobsWeek(CalendarExtension.getStartTimeNextWeek()));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        jobFragment = adapter.jobFragment;
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.week_ago);
                    break;
                case 1:
                    tab.setText(R.string.week_current);
                    break;
                case 2:
                    tab.setText(R.string.next_week);
                    break;
            }
        }).attach();
    }

    private void showMonth() {
        ViewPagerJobAdapter adapter = new ViewPagerJobAdapter(this);
        tabLayout.setVisibility(View.VISIBLE);
        int currMonth = CalendarExtension.getMonth(Calendar.getInstance().getTime(),0);
        int previousMonth = CalendarExtension.getMonth(Calendar.getInstance().getTime(),-1);
        int nextMonth = CalendarExtension.getMonth(Calendar.getInstance().getTime(),1);
        adapter.setJobs(
                jobViewModel.getJobsMonth(previousMonth,
                        CalendarExtension.getYear(Calendar.getInstance().getTime(), 0)),
                jobViewModel.getJobsMonth(currMonth,
                        CalendarExtension.getYear(Calendar.getInstance().getTime(), 0)),
                jobViewModel.getJobsMonth(nextMonth,
                        CalendarExtension.getYear(Calendar.getInstance().getTime(), 0)));
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        jobFragment = adapter.jobFragment;
        viewPager.setUserInputEnabled(false);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(getContext().getString(R.string.month)+" "+(previousMonth+1));
                    break;
                case 1:
                    tab.setText(getContext().getString(R.string.month)+" "+(currMonth+1));
                    break;
                case 2:
                    tab.setText(getContext().getString(R.string.month)+" "+(nextMonth+1));
                    break;

            }
        }).attach();
    }


    private void showDate() {
        tabLayout.setVisibility(View.GONE);
        ViewPagerJobAdapter adapter = new ViewPagerJobAdapter(this);
        adapter.setJobs(jobViewModel.getJobs());
        jobFragment = adapter.jobFragment;
        adapter.createFragment(0);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setUserInputEnabled(false);
    }

    private void showAll() {
        tabLayout.setVisibility(View.GONE);
        ViewPagerJobAdapter adapter = new ViewPagerJobAdapter(this);
        adapter.setJobs(jobViewModel.getJobs());
        jobFragment = adapter.jobFragment;
        adapter.createFragment(0);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setUserInputEnabled(false);
    }
    private void showCategory(int idCategory){
        tabLayout.setVisibility(View.GONE);
        ViewPagerJobAdapter adapter = new ViewPagerJobAdapter(this);
        adapter.setJobs(jobViewModel.getByCategoryId(idCategory));
        adapter.createFragment(0);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        viewPager.setUserInputEnabled(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

}