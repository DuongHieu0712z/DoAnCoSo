package com.ctk43.doancoso.View.Adapter;

import static android.provider.Settings.System.getString;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.chauthai.swipereveallayout.ViewBinderHelper;
import com.ctk43.doancoso.Library.Extension;
import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.Model.JobDetail;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.JobDetailActivity;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.View.JobFragment;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;
import com.ctk43.doancoso.ViewModel.JobDetailViewModel;
import com.ctk43.doancoso.ViewModel.JobViewModel;

import java.util.Calendar;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobHolder> {
    private List<Job> listJob;
    private final Context context;
    private JobViewModel jobViewModel;
    private ViewBinderHelper viewBinderHelper = new ViewBinderHelper();
    Job currentJob;
    private JobHolder holder;
    private int position;

    public JobAdapter(Context context, JobViewModel jobViewModel) {
        this.context = context;
        this.jobViewModel = jobViewModel;
        listJob = jobViewModel.getJobs().getValue();
    }

    @Override
    public JobHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.job_item, parent, false);

        /*view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Job _job=new Job();
                for (Job job : listJob){
                    //if(job.Name.compareTo(((TextView)view.findViewById(R.id.tv_job_name)).getText().toString())==0)
                    if (job.ID==currentJob.ID)
                    _job=job;
                }
                ((MainActivity)mContext).gotoM002Screen(_job);
            }
        });*/
        return new JobHolder(view);
    }

    public void setJob(List<Job> jobs) {
        listJob = jobs;
    }

    @Override
    public void onBindViewHolder(JobHolder holder, @SuppressLint("RecyclerView") int position) {
        this.holder = holder;
        this.position = position;
        Job item = listJob.get(position);
        viewBinderHelper.bind(holder.swipeRevealLayout, String.valueOf(position));
        holder.layout_funcion.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialogYesNo = new Dialog(context);
                Extension.dialogYesNo(dialogYesNo, null, "Xác nhận xóa?", "Sau khi xóa sẽ mất toàn bộ dữ liệu");
                //dialogYesNo.setContentView(R.layout.floating_dialog_add_new_job);
                Button buttn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
                Button buttn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
                dialogYesNo.setCancelable(true);
                buttn_yes.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jobViewModel.delete(item);
                        notifyItemRemoved(listJob.indexOf(item));
                        listJob.remove(item);
                        dialogYesNo.dismiss();
                    }
                });
                buttn_no.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogYesNo.dismiss();
                    }
                });
                dialogYesNo.show();
            }
        });
        holder.itemJob.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, JobDetailActivity.class);
                intent.putExtra("JobID", item.getId());
                context.startActivity(intent);
            }
        });


        currentJob = item;
        holder.tv_job_name.setText(item.getName());
        holder.tv_job_des.setText(item.getDescription());
     /*   if(item.Priority ==true)
            holder.img_level.setImageResource(R.drawable.ic_baseline_star_24);
        else
            holder.img_level.setImageResource(R.drawable.ic_baseline_star_outline_24);
*/
        holder.tv_job_end.setText(Extension.TimeRemaining(Calendar.getInstance().getTime(), item.getEndDate()));
        double prg = item.getProgress() * 100;
        holder.tv_job_prg.setText((int) prg + "%");
        holder.progressBar.setProgress((int) (prg));
        String status = "";
        switch (item.getStatus()) {
            case 0:
                status = "On going";
                break;
            case 1:
                status = "Completed";
                break;
            case -1:
                status = "Dropped";
                break;
            case 2:
                status = "Over";
                break;
        }
        holder.checkBox.setChecked(item.getStatus()==1?true: false  );
        holder.checkBox.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    final Dialog dialogYesNo = new Dialog(context);
                    Extension.dialogYesNo(dialogYesNo, null, "Xác nhận ", "Công việc sẽ hoàn thành tất cả chi tiết công việc");
                    //dialogYesNo.setContentView(R.layout.floating_dialog_add_new_job);
                    Button buttn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
                    Button buttn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
                    dialogYesNo.setCancelable(true);
                    buttn_yes.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jobViewModel.checkOrUncheck(item,true);
                            dialogYesNo.dismiss();
                            new JobFragment();
                        }
                    });
                    buttn_no.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogYesNo.dismiss();
                        }
                    });
                    dialogYesNo.show();
                }else{
                    final Dialog dialogYesNo = new Dialog(context);
                    Extension.dialogYesNo(dialogYesNo, null, "Xác nhận ", "Sẽ hủy tiến độ các chi tiết chi tiết công việc đã hoàn thành");
                    Button buttn_yes = dialogYesNo.findViewById(R.id.btn_dialog_yes);
                    Button buttn_no = dialogYesNo.findViewById(R.id.btn_dialog_no);
                    dialogYesNo.setCancelable(true);
                    buttn_yes.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            jobViewModel.checkOrUncheck(item,false);
                            dialogYesNo.dismiss();
                            new JobFragment();
                        }
                    });
                    buttn_no.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialogYesNo.dismiss();
                        }
                    });
                    dialogYesNo.show();
                }
            }
        });
        holder.tv_job_status.setText(status);
        if(Extension.Remaning_minute(Calendar.getInstance().getTime(),item.getEndDate())>0){
            holder.tv_time.setText(R.string.time_remaining);
        }else{
            holder.tv_time.setText(R.string.time_over);
        }

        // holder.tvName.setTag(item);
        //holder.tvName.setText(item.getName());
    }


    @Override
    public int getItemCount() {
        if (listJob == null )
            return 0;
        return listJob.size();
    }

    public class JobHolder extends RecyclerView.ViewHolder {
        SwipeRevealLayout swipeRevealLayout;
        LinearLayout layout_funcion;
        LinearLayout itemJob;
        TextView tv_job_name;
        TextView tv_time;
        TextView tv_job_des;
        TextView tv_job_prg;
        TextView tv_job_end;
        TextView tv_job_status;
        ProgressBar progressBar;
        CheckBox checkBox;

        public JobHolder(View view) {
            super(view);
            swipeRevealLayout = view.findViewById(R.id.item_topic);
            layout_funcion = view.findViewById(R.id.job_funcion);
            tv_time = view.findViewById(R.id.tv_time);
            itemJob = view.findViewById(R.id.item_job);
            checkBox = view.findViewById(R.id.chk_finish_job);
            //      img_level = view.findViewById(R.id.img_level);
            tv_job_name = view.findViewById(R.id.tv_job_name);
            tv_job_des = view.findViewById(R.id.tv_job_description);
            tv_job_prg = view.findViewById(R.id.tv_progress);
            tv_job_end = view.findViewById(R.id.tv_remainning_time);
            tv_job_status = view.findViewById(R.id.tv_Status);
            progressBar = view.findViewById(R.id.prg_progress);
           /*  itemView.setOnClickListener(v -> {
                ((MainActivity)mContext).gotoM003Screen(listJob, (StoryEntity)tvName.getTag());
            });*/
        }
    }
}
