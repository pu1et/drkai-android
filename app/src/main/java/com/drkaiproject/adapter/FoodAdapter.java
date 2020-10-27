package com.drkaiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drkaiproject.R;
import com.drkaiproject.model.Food;

import java.util.ArrayList;


public class FoodAdapter extends BaseAdapter {

    Context context;
    ArrayList<Food> wl;
    int layout;
    LayoutInflater inf;

    public FoodAdapter(Context context, ArrayList<Food> wl, int layout) {
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
        TextView food_name = (TextView) convertView.findViewById(R.id.food_name); //음식 이름
        TextView food_count = (TextView) convertView.findViewById(R.id.food_count); //개수
        TextView food_kcal = (TextView) convertView.findViewById(R.id.food_kcal); //칼로리
        TextView food_time = (TextView) convertView.findViewById(R.id.food_time); //음식 시간
        TextView food_date = (TextView) convertView.findViewById(R.id.food_date); //날짜

        Food b = wl.get(position);
        food_name.setText(b.getFood_name()+" ");
        food_count.setText(b.getFood_count()+" ");
        food_kcal.setText(b.getKcal()+" ");
        food_time.setText(b.getFood_time()+" ");
        food_date.setText(b.getDate()+" ");

        return convertView;
    }
}
