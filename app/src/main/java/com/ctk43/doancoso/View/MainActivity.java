package com.ctk43.doancoso.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.ctk43.doancoso.Model.Job;
import com.ctk43.doancoso.R;
import com.ctk43.doancoso.ViewModel.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public String currentDate;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private int dlg_mode = 0;
    public String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showFrg(new Splast_Fragment());
        //initView();
    }

    private void initView() {

    }
    private void showFrg(Fragment frg) {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, frg,
                null).commit();
    }
    public void gotoM001Screen() {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new MainFragment(), null).commit();
        //initView();
    }
    public void gotoM002Screen(Job job) {
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new JobDetailFragment(job), null).commit();
    }
    public void gotoAddNewJobScreen(){
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, new
                AddJobFragment(), null).commit();
    }
    public void gotoShowDialogScreen(int mode){
        dlg_mode=mode;
        DialogFragment dateDialog = new DatePickerFragment();
        FragmentManager fm = getSupportFragmentManager();
        dateDialog.show(getFragmentManager(), "");
    }
    public void gotoShowTimeDialogScreen(int mode){
        dlg_mode=mode;
        DialogFragment timePicker = new TimePickerFragment();
        timePicker.show(getFragmentManager(), "time picker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());

        /*TextView tv_date;
        if(dlg_mode==0){
            tv_date = findViewById(R.id.tv_dlg_date_start);
            tv_date.setText(currentDate);
        }
        else{
            tv_date = findViewById(R.id.tv_dlg_date_end);
            tv_date.setText(currentDate);
        }*/
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        /*TextView textView;
        if(dlg_mode==0)
            textView = (TextView) findViewById(R.id.tv_dlg_time_start);
        else
            textView = (TextView) findViewById(R.id.tv_dlg_time_end);
        textView.setText("Hour: " + hourOfDay + " Minute: " + minute);*/
        result="Hour: " + hourOfDay + " Minute: " + minute;
    }
}
