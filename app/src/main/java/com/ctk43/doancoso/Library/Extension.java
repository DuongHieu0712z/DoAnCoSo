package com.ctk43.doancoso.Library;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.View.Adapter.ViewPagerAdapter;
import com.ctk43.doancoso.View.Fragment.ManagerJobFragment;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Extension {

    public static boolean isEmpty(Context context, String value, String name, boolean isDefault) {
        if (value.isEmpty() || isDefault) {
            Toast.makeText(context, "Không được để " + name + " trống, vui lòng nhập " + name + "!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    public static int CheckStatus(Job job) {
        if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(),job.getStartDate() ) > 0) {
            return GeneralData.STATUS_COMING;
        } else if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate()) >= 0 && job.getProgress() != 1) {
            long test= CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate());
            return GeneralData.STATUS_ON_GOING;
        } else if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate()) < 0 && job.getProgress() != 1) {
            return GeneralData.STATUS_OVER;
        } else if (CalendarExtension.Remaining_minute(Calendar.getInstance().getTime(), job.getEndDate()) >= 0 && job.getProgress() == 1) {
            return GeneralData.STATUS_FINISH;
        } else {
            return GeneralData.STATUS_FINISH_LATE;
        }
    }

    public static boolean statusIsChange(Job job) {
        int status = job.getStatus();
        job.setStatus(CheckStatus(job));
        if (status != job.getStatus())
            return true;
        return false;
    }

    public static List<Job> getJobsChange(List<Job> jobList) {
        List<Job> jobs = new ArrayList<>();
        for (Job job : jobList) {
            if (statusIsChange(job)) {
                jobs.add(job);
            }
        }
        return jobs;
    }
    public static boolean canCheck(Context context,CheckBox checkBox, Job job){
        if(job.getStatus() == GeneralData.STATUS_COMING){
            Toast.makeText(context,R.string.toast_can_not_do_that,Toast.LENGTH_SHORT).show();
            checkBox.setChecked(false);
            return true;
        }
        return true;
    }

   public static void CheckOrUncheckJob(Context context,CheckBox checkBox,Job job,TextView tv_progress, ProgressBar progressBar) {
        Dialog dialogYesNo = new Dialog(context);
        String confirm = context.getString(R.string.confirm);
        dialogYesNo.setCancelable(false);
        if (checkBox.isChecked()) {
            DialogExtension.dialogYesNo(dialogYesNo, confirm, context.getString(R.string.message_finish_all_job_detail));
            Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
            Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
            btn_yes.setOnClickListener(v -> {
                updateStatus(context,job, true);
                checkBox.setChecked(Extension.isFinishJob(job));
                if(tv_progress !=null && progressBar !=null)
                    setProgress(tv_progress,progressBar,job);
                dialogYesNo.dismiss();
            });
            btn_no.setOnClickListener(v -> {
                checkBox.setChecked(Extension.isFinishJob(job));
                dialogYesNo.dismiss();
            });
        } else {
            DialogExtension.dialogYesNo(dialogYesNo, confirm, context.getString(R.string.message_delete_progress));
            Button btn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
            Button btn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
            btn_yes.setOnClickListener(v -> {
                updateStatus(context,job, false);
                checkBox.setChecked(Extension.isFinishJob(job));
                if(tv_progress !=null && progressBar !=null)
                setProgress(tv_progress,progressBar,job);
                dialogYesNo.dismiss();
            });
            btn_no.setOnClickListener(v -> {
                checkBox.setChecked(Extension.isFinishJob(job));
                dialogYesNo.cancel();
                dialogYesNo.dismiss();
            });
        }
        dialogYesNo.show();
    }

    public static void updateStatus(Context context, Job job, boolean isFinish) {
        JobViewModel jobViewModel = new JobViewModel();
        jobViewModel.setData(context);
        jobViewModel.checkOrUncheck(job, isFinish);
        JobDetailViewModel jobDetailViewModel = new JobDetailViewModel();
        jobDetailViewModel.setContext(context,job.getId());
        jobDetailViewModel.syncJob(job);
    }
    public static boolean isFinishJob(Job job){
        if(job.getStatus() == GeneralData.STATUS_FINISH || job.getStatus() == GeneralData.STATUS_FINISH_LATE)
            return true;
        return false;
    }
    public static void setProgress(TextView tv_progress, ProgressBar progressBar, Job job) {
        int progress = (int) (job.getProgress() * 100.0);
        String prgString = progress + " %";
        tv_progress.setText(prgString);
        progressBar.setProgress(progress);
    }
    public static void filterSearch(ViewPager2 viewPager, ViewPagerAdapter viewPagerAdapter , String text){
        if(viewPager.getCurrentItem() == 0){
            ManagerJobFragment managerJobFragment = (ManagerJobFragment) viewPagerAdapter.getHashMap().get(0);
            JobAdapter jobAdapter = managerJobFragment.getJobFragment().getAdapter();
            jobAdapter.getFilter().filter(text);
        }
    }

}
