package com.ctk43.doancoso.View;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
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

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.View.Adapter.ViewPagerJobAdapter;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.security.interfaces.RSAMultiPrimePrivateCrtKey;
import java.util.ArrayList;
import java.util.List;


public class ManagerJobFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager2  viewPager;
    private View view;
    public JobFragment jobFragment;

    CategoryViewModel categoryViewModel;
    JobViewModel jobViewModel;

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
        view =  inflater.inflate(R.layout.fragment_manager_job, container, false);
        tabLayout = view.findViewById(R.id.tab_layout_job);
        viewPager = view.findViewById(R.id.job_view_pager);
        ViewPagerJobAdapter adapter = new ViewPagerJobAdapter(this);
        jobFragment = adapter.jobFragment;
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

        InnitView(view);

        return  view;
    }
    private void initViewModel(){
        categoryViewModel = new CategoryViewModel();
        categoryViewModel.setContext(requireContext());
        jobViewModel = new JobViewModel();
        jobViewModel.setData(requireContext());
    }


    private void InnitView(View view) {

        ImageButton img_btn_filter = view.findViewById(R.id.img_btn_filter);
        ImageButton img_btn_convert = view.findViewById(R.id.img_btn_convert);
        Spinner spn_filter = view.findViewById(R.id.spn_filter);
        List<Category> categories = categoryViewModel.getCategoryList();
        categories = categoryViewModel.getCategoryList();
        categories.add(new Category("Tất cả",DataLocalManager.getEmail(), 0));


        ArrayAdapter adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_filter.setAdapter(adapter);

        spn_filter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Category category = (Category) spn_filter.getSelectedItem();
                int categoryId = category.getId();

                System.out.println(categoryId+" category id");

                JobViewModel jobViewModel = new JobViewModel();
                jobViewModel.setData(getContext());
                JobAdapter jobAdapter = new JobAdapter(getContext(), jobViewModel);
                jobViewModel.setData(getContext());
                RecyclerView rcv = getActivity().findViewById(R.id.rcv_display_job);
                rcv.setAdapter(jobAdapter);
                jobViewModel.getJobs().observe(getActivity(), jobs -> {
                    jobAdapter.setJob(jobs);
                    rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcv.setAdapter(jobAdapter);
                    jobAdapter.GetByCategoryId(categoryId);
                });

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
            @Override
            public void onClick(View view) {
                JobViewModel jobViewModel = new JobViewModel();
                jobViewModel.setData(getContext());
                JobAdapter jobAdapter = new JobAdapter(getContext(), jobViewModel);
                jobViewModel.setData(getContext());
                RecyclerView rcv = getActivity().findViewById(R.id.rcv_display_job);
                rcv.setAdapter(jobAdapter);
                jobViewModel.getJobs().observe(getActivity(), jobs -> {
                    jobAdapter.setJob(jobs);
                    rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                    rcv.setAdapter(jobAdapter);

                });
            }
        });
    }


    private void showDialogFilter(){
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.filter_dialog);
        Window window = dialog.getWindow();
        if(window==null)return;
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity= Gravity.LEFT;
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
                if(rd_priority_0.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByPriority(0);
                        dialog.dismiss();
                    });
                }
                else if(rd_priority_1.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByPriority(1);
                        dialog.dismiss();
                    });
                }
                else if(rd_priority_2.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByPriority(2);
                        dialog.dismiss();
                    });
                }
                else if(rd_priority_3.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByPriority(3);
                        dialog.dismiss();
                    });
                }
                else if(rd_status_0.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(0);
                        dialog.dismiss();
                    });
                }else if(rd_status_1.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(1);
                        dialog.dismiss();
                    });
                }else if(rd_status_2.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(2);
                        dialog.dismiss();
                    });
                }else if(rd_status_3.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(3);
                        dialog.dismiss();
                    });
                }else if(rd_status_4.isChecked())
                {
                    jobViewModel.getJobs().observe(getActivity(), jobs -> {
                        jobAdapter.setJob(jobs);
                        rcv.setLayoutManager(new LinearLayoutManager(getContext()));
                        rcv.setAdapter(jobAdapter);
                        jobAdapter.SortByStatus(4);
                        dialog.dismiss();
                    });
                }
                else{
                    Toast.makeText(getContext(),"Vui lòng chọn lọc", Toast.LENGTH_LONG).show();
                }

            }
        });

        dialog.show();
    }
    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

}