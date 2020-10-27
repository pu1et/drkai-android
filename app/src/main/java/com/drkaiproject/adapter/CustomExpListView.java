package com.drkaiproject.adapter;

import android.content.Context;
import android.widget.ExpandableListView;

public class CustomExpListView extends ExpandableListView {
    public CustomExpListView(Context context){
        super(context);
    }
    protected  void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(1260, MeasureSpec.AT_MOST);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(1000, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
