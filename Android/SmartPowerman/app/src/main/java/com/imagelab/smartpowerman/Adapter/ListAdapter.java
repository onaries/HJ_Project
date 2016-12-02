package com.imagelab.smartpowerman.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.imagelab.smartpowerman.R;

import java.util.List;

/**
 * Created by ksw89 on 2016-11-29.
 */

public class ListAdapter extends ArrayAdapter<ListData> {

    public ListAdapter(Context context, int resource) {
        super(context, resource);
    }

    public ListAdapter(Context context, int resource, List<ListData> objects) {
        super(context, resource, objects);
    }

    public ListAdapter(Context context, int resource, ListData[] objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            v = li.inflate(R.layout.list_settings, null);
        }

        ListData p = getItem(position);

        if (p != null){
            ImageView img1 = (ImageView) v.findViewById(R.id.custom_img);
            TextView txt1 = (TextView) v.findViewById(R.id.custom_text);
            Switch swi1 = (Switch) v.findViewById(R.id.custom_switch);
            ImageView img2 = (ImageView) v.findViewById(R.id.custom_img2);

            if (img1 != null) {
                img1.setBackgroundResource(p.getCustom_img());
            }
            if (txt1 != null) {
                txt1.setText(p.getCustom_txt());
            }
            if (swi1 != null) {
                if (p.getCustom_switch() == 0){
                    swi1.setVisibility(View.GONE);
                }
                else {
                    swi1.setVisibility(View.VISIBLE);
                }
            }
            if (img2 != null) {
                if (p.getCustom_img2() == 0){
                    img2.setVisibility(View.GONE);
                }
                else {
                    img2.setVisibility(View.VISIBLE);
                }
            }

        }

        return v;
    }


}
