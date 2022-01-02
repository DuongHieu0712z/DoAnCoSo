package com.ctk43.doancoso.Library;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ctk43.doancoso.Database.DataLocal.DataLocalManager;
import com.ctk43.doancoso.Model.Category;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.View.Activity.CategoryManagementActivity;
import com.ctk43.doancoso.View.Activity.MainActivity;
import com.ctk43.doancoso.View.Adapter.JobAdapter;
import com.ctk43.doancoso.ViewModel.CategoryViewModel;

public class DialogExtension {

    public static Dialog dialogYesNo(Dialog dialog, String title, String content) {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_yes_no);
        Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windownAtrributes = window.getAttributes();
        windownAtrributes.gravity = Gravity.CENTER;
        window.setAttributes(windownAtrributes);
        TextView textView = dialog.findViewById(R.id.txt_dialog_string);
        textView.setText(title);
        Button btnYes = dialog.findViewById(R.id.btn_dialog_yes);
        btnYes.setBackgroundTintMode(null);
        Button btnNo = dialog.findViewById(R.id.btn_dialog_no);
        btnNo.setBackgroundTintMode(null);
        TextView tv_des = dialog.findViewById(R.id.tv_dialog_description);
        tv_des.setText(content);
        return dialog;
    }

    public static void showDialogFilterJob(Context mContext, boolean dayToDay, JobAdapter jobAdapter) {
        final int[] priority = {-1};
        final int[] status = {-1};
        final Dialog dialog = new Dialog(mContext);
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
        RadioButton rd_priority_0 = dialog.findViewById(R.id.rb_priority_0);
        RadioButton rd_priority_1 = dialog.findViewById(R.id.rb_priority_1);
        RadioButton rd_priority_2 = dialog.findViewById(R.id.rb_priority_2);
        RadioButton rd_priority_3 = dialog.findViewById(R.id.rb_priority_3);
        rd_priority_0.setText(GeneralData.getPriority(0));
        rd_priority_1.setText(GeneralData.getPriority(1));
        rd_priority_2.setText(GeneralData.getPriority(2));
        rd_priority_3.setText(GeneralData.getPriority(3));
        RadioButton rd_status_0 = dialog.findViewById(R.id.rb_status_0);
        RadioButton rd_status_1 = dialog.findViewById(R.id.rb_status_1);
        RadioButton rd_status_2 = dialog.findViewById(R.id.rb_status_2);
        RadioButton rd_status_3 = dialog.findViewById(R.id.rb_status_3);
        RadioButton rd_status_4 = dialog.findViewById(R.id.rb_status_4);
        rd_status_0.setText(GeneralData.getStatus(0));
        rd_status_1.setText(GeneralData.getStatus(1));
        rd_status_2.setText(GeneralData.getStatus(2));
        rd_status_3.setText(GeneralData.getStatus(3));
        rd_status_4.setText(GeneralData.getStatus(4));
        Button btn_filter = dialog.findViewById(R.id.btn_filter);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rd_priority_0.isChecked()) {
                    priority[0] = 0;
                } else if (rd_priority_1.isChecked()) {
                    priority[0] = 1;
                } else if (rd_priority_2.isChecked()) {
                    priority[0] = 2;
                } else if (rd_priority_3.isChecked()) {
                    priority[0] = 3;
                }
                if (rd_status_0.isChecked()) {
                    status[0] = 0;
                } else if (rd_status_1.isChecked()) {
                    status[0] = 1;
                } else if (rd_status_2.isChecked()) {
                    status[0] = 2;
                } else if (rd_status_3.isChecked()) {
                    status[0] = 3;
                } else if (rd_status_4.isChecked()) {
                    status[0] = 4;
                }
                if (!dayToDay && priority[0] == -1 && status[0] == -1) {
                    Toast.makeText(mContext, "Vui lòng chọn lọc", Toast.LENGTH_LONG).show();
                } else {
                    if (dayToDay) {
                        ///jobAdapter.FilterByDateToDate();
                    }
                    jobAdapter.FilterByPriority(priority[0]);
                    jobAdapter.FilterByStatus(status[0]);
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    @SuppressLint("RtlHardcoded")
    public static void onOpenMenuDialog(Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_menu);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.RIGHT;
        window.setAttributes(windowAttribute);

        LinearLayout ln_category_management = dialog.findViewById(R.id.dialog_menu_category);
        ln_category_management.setOnClickListener(view -> {
            Intent intent = new Intent(context, CategoryManagementActivity.class);
            context.startActivity(intent);
            if (!(context instanceof MainActivity))
                ((Activity) context).finish();
            ;
            dialog.dismiss();
        });
        dialog.show();
    }

    public static void onOpenCategoryDiaLog(Context context, CategoryViewModel categoryViewModel, Category category) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.floating_dialog_add_job_type);
        dialog.setCancelable(true);

        Window window = dialog.getWindow();
        if (window == null) return;

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        TextView tv_title = dialog.findViewById(R.id.tv_add_category_title);
        EditText edt_job_type_name = dialog.findViewById(R.id.edt_dlg_job_type);

        if (category == null) {
            tv_title.setText(R.string.add_category_title);
        } else {
            tv_title.setText(R.string.update_category_title);
            edt_job_type_name.setText(category.getName());
        }

        Button btn_add_job_type = dialog.findViewById(R.id.btn_dlg_add_job_type);
        btn_add_job_type.setOnClickListener(view -> {
            String name = edt_job_type_name.getText().toString();
            if (Extension.isEmpty(context, name,  context.getString(R.string.category_name),false)) {
                return;
            }

            if (category == null) {
                categoryViewModel.insert(new Category(name, DataLocalManager.getEmail()));
            } else {
                category.setName(name);
                categoryViewModel.update(category);
            }
            dialog.dismiss();
        });
        dialog.show();
    }
}
