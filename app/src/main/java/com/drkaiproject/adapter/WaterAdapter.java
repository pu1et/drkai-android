package com.drkaiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drkaiproject.R;
import com.drkaiproject.model.Water;

import java.util.ArrayList;

public class WaterAdapter extends BaseAdapter {

    Context context;
    ArrayList<Water> wl;
    int layout;
    LayoutInflater inf;

    public WaterAdapter(Context context, ArrayList<Water> wl, int layout) {
        this.context = context;
        this.wl = wl;
        this.layout = layout;
        inf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return wl.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return wl.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inf.inflate(layout, null);
        }
        TextView output_water = (TextView) convertView.findViewById(R.id.output_water);
        TextView output_ml = (TextView) convertView.findViewById(R.id.output_ml);
        TextView output_date = (TextView) convertView.findViewById(R.id.output_date);

        Water b = wl.get(position);
        output_water.setText(b.getWater_data() + "");
        output_ml.setText(b.getWater_ml() + "");
        output_date.setText(b.getDate());

        return convertView;
    }
}
