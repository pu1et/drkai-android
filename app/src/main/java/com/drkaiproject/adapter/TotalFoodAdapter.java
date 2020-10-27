package com.drkaiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drkaiproject.R;
import com.drkaiproject.model.TotalFood;

import java.util.ArrayList;


public class TotalFoodAdapter extends BaseAdapter {

    Context context;
    ArrayList<TotalFood> tf;
    ArrayList<TotalFood> displayListItem = new ArrayList<TotalFood>();
    int layout;
    LayoutInflater inf;

    public TotalFoodAdapter(Context context, ArrayList<TotalFood> tf, int layout) {
        this.context = context;
        this.tf = tf;
        this.layout = layout;
        inf = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return tf.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return tf.get(position);
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
        TextView totalfood_txt = (TextView) convertView.findViewById(R.id.totalfood_txt);

        TotalFood b = tf.get(position);
        totalfood_txt.setText(b.totalfood_txt + "");

        return convertView;
    }
}
