package com.drkaiproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.drkaiproject.Notice;
import com.drkaiproject.R;

import java.util.ArrayList;

public class NoticeAdapter extends BaseAdapter {


    private Context context;
    private ArrayList<Notice> NoticeList;

    public NoticeAdapter(Context context, ArrayList<Notice> NoticeList){
        this.context = context;
        this.NoticeList = NoticeList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_item, null);
        TextView textView1 = view.findViewById(R.id.notice_title);
        TextView textView2 = view.findViewById(R.id.notice_date);

        Notice notice = NoticeList.get(position);

        textView1.setText(notice.getTitle());
        textView2.setText(notice.getDate());

        return view;
    }

    @Override
    public int getCount() {
        return NoticeList.size();
    }

    @Override
    public Object getItem(int position) {
        return NoticeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
