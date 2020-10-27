package com.drkaiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drkaiproject.R;
import com.drkaiproject.model.Sleep;

import java.util.ArrayList;

public class SleepAdapter extends BaseAdapter {

    Context context;
    ArrayList<Sleep> wl;
    int layout;
    LayoutInflater inf;

    public SleepAdapter(Context context, ArrayList<Sleep> wl, int layout) {
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
        TextView output_hour = (TextView) convertView.findViewById(R.id.output_hour);
        TextView output_minute = (TextView) convertView.findViewById(R.id.output_minute);
        TextView sleep_date = (TextView) convertView.findViewById(R.id.sleep_date);

        Sleep b = wl.get(position);
        output_hour.setText(b.getSleep_hour() + "");
        output_minute.setText(b.getSleep_minute() + "");
        sleep_date.setText(b.getDate());

        return convertView;
    }
}
