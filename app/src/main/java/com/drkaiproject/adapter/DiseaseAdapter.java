package com.drkaiproject.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.drkaiproject.R;
import com.drkaiproject.R.*;
import com.drkaiproject.model.Disease;

import java.util.ArrayList;


public class DiseaseAdapter extends BaseAdapter {

    Context context;
    ArrayList<Disease> wl;
    int layout;
    LayoutInflater inf;

    public DiseaseAdapter(Context context, ArrayList<Disease> wl, int layout) {
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

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inf.inflate(layout, null);
        }

        //카드뷰
        CardView cv = (CardView) convertView.findViewById(id.cv);

        int low_color = cv.getContext().getResources().getColor(R.color.low_color);
        int medium_color = cv.getContext().getResources().getColor(R.color.medium_color);
        int high_color = cv.getContext().getResources().getColor(R.color.high_color);

        //공통
        TextView name = (TextView) convertView.findViewById(id.name);//질병 이름
        ImageView img = (ImageView) convertView.findViewById(id.img);//이미지
        TextView percent = (TextView) convertView.findViewById(id.percent);//확률

        Disease d = wl.get(position);

        //질병 이름
        name.setText(d.getName() + "");

        if (d.getName().equals("우울증")) {
            //이미지, 배경색
            if (d.getValue_txt().equals("매우 양호")) {
                percent.setText("매우 양호");
                cv.setCardBackgroundColor(low_color); //배경 색상
                img.setImageDrawable(img.getResources().getDrawable(drawable.low)); //이미지
            } else if (d.getValue_txt().equals("양호")) {
                percent.setText("양호");
                cv.setCardBackgroundColor(low_color); //배경 색상
                img.setImageDrawable(img.getResources().getDrawable(drawable.low)); //이미지
            } else if (d.getValue_txt().equals("보통")) {
                percent.setText("보통");
                cv.setCardBackgroundColor(medium_color); //배경 색상
                img.setImageDrawable(img.getResources().getDrawable(drawable.medium)); //이미지
            } else if (d.getValue_txt().equals("주의")) {
                percent.setText("주의");
                cv.setCardBackgroundColor(high_color); //배경 색상
                img.setImageDrawable(img.getResources().getDrawable(drawable.high)); //이미지
            } else {
                percent.setText("매우 주의");
                cv.setCardBackgroundColor(high_color); //배경 색상
                img.setImageDrawable(img.getResources().getDrawable(drawable.high)); //이미지
            }
        } else {
            //확률
            percent.setText(d.getPercent() + "%");

            //이미지, 배경색
            if (d.getValue() == 1) {
                cv.setCardBackgroundColor(low_color); //배경 색상
                img.setImageDrawable(img.getResources().getDrawable(drawable.low)); //이미지
            } else if (d.getValue() == 2) {
                cv.setCardBackgroundColor(medium_color);
                img.setImageDrawable(img.getResources().getDrawable(drawable.medium));
            } else {
                cv.setCardBackgroundColor(high_color);
                img.setImageDrawable(img.getResources().getDrawable(drawable.high));
            }
        }

        return convertView;
    }
}

