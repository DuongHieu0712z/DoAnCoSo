package com.ctk43.doancoso.View.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctk43.doancoso.Model.Setting;
import com.ctk43.doancoso.R;

import java.util.ArrayList;

public class SettingAdapter extends BaseAdapter {
    ArrayList<Setting> list = new ArrayList<>();

    public SettingAdapter(ArrayList<Setting> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View mainView = View.inflate(viewGroup.getContext(), R.layout.item_setting, null);
        Setting product = (Setting) getItem(i);
        ImageView img = mainView.findViewById(R.id.img_setting);
        img.setImageResource(product.getImages());
        TextView tv_title = mainView.findViewById(R.id.tv_setting_title);
        tv_title.setText(product.getTitle());
        TextView tv_content = mainView.findViewById(R.id.tv_setting_description);
        tv_content.setText(product.getContent());
        return  mainView;
    }
}
