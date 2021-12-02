package com.ctk43.doancoso.View;

import android.content.Context;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class JobDetailFragment {
    private Context mContext;
    FloatingActionButton btn_Add_New_Job_detail;
    private JobDetailViewModel jobDetailViewModel;
    private Job job;

    public JobDetailFragment(Job job) {
        this.job = job;
    }

 /*   @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_job_detail, container, false);

        return rootView;
    }*/

/*    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }




    }
    @Override
    public void onClick(View view) {
        //((MainActivity) getActivity()).gotoAddNewJobScreen();
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
    }*/

}