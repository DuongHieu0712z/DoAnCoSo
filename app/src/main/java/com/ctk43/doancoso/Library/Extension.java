package com.ctk43.doancoso.Library;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobActivity;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.View.Adapter.ViewPagerAdapter;
import com.ctk43.doancoso.View.Fragment.ManagerJobFragment;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.ArrayList;
import java.util.Calendar;
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
        if (CalendarExtension.timeRemaining(Calendar.getInstance().getTime(),job.getStartDate()) > 0) {
            return GeneralData.STATUS_COMING;
        } else if (CalendarExtension.timeRemaining(Calendar.getInstance().getTime(), job.getEndDate()) > 0 && job.getProgress() != 1) {
            return GeneralData.STATUS_ON_GOING;
        } else if (CalendarExtension.timeRemaining(Calendar.getInstance().getTime(), job.getEndDate()) <= 0 && job.getProgress() != 1) {
            return GeneralData.STATUS_OVER;
        } else if (CalendarExtension.timeRemaining(Calendar.getInstance().getTime(), job.getEndDate()) >= 0 && job.getProgress() == 1) {
            return GeneralData.STATUS_FINISH;
        } else {
            return GeneralData.STATUS_FINISH_LATE;
        }
    }



    public static List<Job> getJobsChange(List<Job> jobList,int statusTarget) {
        List<Job> jobs = new ArrayList<>();
        for (Job job : jobList) {
            job.setStatus(CheckStatus(job));
            if (job.getStatus()== statusTarget) {
                jobs.add(job);
            }
        }
        return jobs;
    }
    public static boolean canCheck(Context context,CheckBox checkBox, Job job){
        if(job.getStatus() == GeneralData.STATUS_COMING){
            Toast.makeText(context,R.string.toast_can_not_do_that,Toast.LENGTH_SHORT).show();
            checkBox.setChecked(false);
            return false;
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
    public static void callJobActivity(Context context, Job job, Boolean dateToDate){
        Intent intent = new Intent(context, JobActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Key.SEND_JOB,job);
        bundle.putSerializable(Key.DATE_TO_DATE,dateToDate);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    public static String getStringTotalJob(int count, int status, Context context){
        String str = "";
        if(count !=0){
            str =  " "+count+ " " + context.getString(R.string.job_non_cap)+" "+context.getString(GeneralData.getStatus(status));
        }
        return str;
    }

    public static Spannable setContent(Context context,Job job) {
        String strStart = context.getString(R.string.notification_job_show) + " " + context.getString(R.string.job);
        int start = strStart.length() + 1;
        String name = job.getName() + " " + context.getString(GeneralData.getStatus(job.getStatus()));
        strStart = strStart + " " + name +" ";
        SpannableString spannable = new SpannableString(strStart +job.getDescription());
        ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(ResourcesCompat.getColor(context.getResources(), GeneralData.getColorStatus(job.getStatus()), null));
        spannable.setSpan(foregroundColorSpan, start, strStart.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannable;
    }

}
