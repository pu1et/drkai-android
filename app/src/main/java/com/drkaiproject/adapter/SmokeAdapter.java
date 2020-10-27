package com.drkaiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drkaiproject.R;
import com.drkaiproject.model.Smoke;

import java.util.ArrayList;


public class SmokeAdapter extends BaseAdapter {

    Context context;
    ArrayList<Smoke> wl;
    int layout;
    LayoutInflater inf;

    public SmokeAdapter(Context context, ArrayList<Smoke> wl, int layout) {
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
        TextView output_smoke = (TextView) convertView.findViewById(R.id.output_smoke);
        TextView output_date = (TextView) convertView.findViewById(R.id.output_date);

        Smoke b = wl.get(position);
        output_smoke.setText(b.getSmoke_data());
        output_date.setText(b.getDate());

        return convertView;
    }
}
