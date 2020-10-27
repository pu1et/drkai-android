package com.drkaiproject.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.drkaiproject.R;

import java.util.List;
import java.util.Map;

public class SecondLevelAdapter extends BaseExpandableListAdapter {
    private final Context context;
    private final List<String> listDataHeader;
    private final Map<String, List<String>> listDataChild;

    SecondLevelAdapter(Context context, List<String> listDataHeader, Map<String, List<String>> listDataChild){
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listDataChild;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        try{
            return this.listDataChild.get(this.listDataHeader.get(groupPosition)).size();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group_child,null);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.nl_header);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        View indicator = convertView.findViewById(R.id.explist_indicator);

        if(indicator != null){
            ImageView indicatorImage = (ImageView) indicator;
            if(getChildrenCount(groupPosition) == 0){
                indicatorImage.setVisibility(View.INVISIBLE);
            }else{
                indicatorImage.setVisibility(View.VISIBLE);

                if(isExpanded){
                    indicatorImage.setImageResource(R.drawable.ic_action_expand);
                }else{
                    indicatorImage.setImageResource(R.drawable.ic_action_collapse);
                }
            }
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item,null);
        }
        TextView lblListHeader = (TextView) convertView.findViewById(R.id.nl_item);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(childText);


        return  convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
