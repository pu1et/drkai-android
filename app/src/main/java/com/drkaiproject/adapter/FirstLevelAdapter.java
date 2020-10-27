package com.drkaiproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.drkaiproject.R;
import com.drkaiproject.menu.UserInfoChange1;
import com.drkaiproject.menu.UserInfoConf1;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FirstLevelAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> listDataHeader;
    private Map<String, List<String>> listData_SecondLevel_Child;
    private Map<String, List<String>> listData_ThirdLevel_Child;

    public FirstLevelAdapter(Context context, List<String> listDataHeader) {
        this.context = context;
        this.listDataHeader = new ArrayList<>();
        this.listDataHeader.addAll(listDataHeader);

        // Init second level data
        String[] mItemHeaders;
        listData_SecondLevel_Child = new HashMap<>();
        int parentCount = listDataHeader.size();
        for (int i = 0; i < parentCount; i++) {
            switch (i) {
                case 0:
                    mItemHeaders = context.getResources().getStringArray(R.array.items_array_expandable_level_one_one_child);
                    break;
                case 1:
                    mItemHeaders = context.getResources().getStringArray(R.array.items_array_expandable_level_one_two_child);
                    break;
                default:
                    mItemHeaders = context.getResources().getStringArray(R.array.items_array_expandable_level_one_three_child);
                    break;
            }
            listData_SecondLevel_Child.put(listDataHeader.get(i), Arrays.asList(mItemHeaders));
        }

        // third level
        String[] mItemChildOfChild;
        List<String> listChild;
        listData_ThirdLevel_Child = new HashMap<>();
        for (Object o : listData_SecondLevel_Child.entrySet()) {
            Map.Entry entry = (Map.Entry) o;
            Object object = entry.getValue();
            if (object instanceof List) {
                List<String> stringList = new ArrayList<>();
                Collections.addAll(stringList, (String[]) ((List) object).toArray());
                for (int i = 0; i < stringList.size(); i++) {
                    if (stringList.get(i).equals("더보기")) {
                        mItemChildOfChild = context.getResources().getStringArray(R.array.items_array_expandable_level_one_one_child_one_child);
                        listChild = Arrays.asList(mItemChildOfChild);
                        listData_ThirdLevel_Child.put(stringList.get(i), listChild);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childPosition;
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
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }
        View indicator = convertView.findViewById(R.id.explist_indicator);

        //Log.w("Indicator",indicator.toString() + ", " + isExpanded);
        ImageView indicatorImage = (ImageView) indicator;
        if (getChildrenCount(groupPosition) == 0) {
            indicatorImage.setVisibility(View.INVISIBLE);
        } else {
            indicatorImage.setVisibility(View.VISIBLE);

            if (isExpanded) {
                indicatorImage.setImageResource(R.drawable.ic_action_expand);
            } else {
                indicatorImage.setImageResource(R.drawable.ic_action_collapse);
            }
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.nl_header);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final CustomExpListView customExpListView = new CustomExpListView(this.context);

        final String parentNode = (String) getGroup(groupPosition);
        final long parentNodeId = getGroupId(groupPosition);
        Log.w("Expandable_second", "parent: "+getGroupId(groupPosition)+", "+parentNode);


        customExpListView.setAdapter(new SecondLevelAdapter(this.context, listData_SecondLevel_Child.get(parentNode), listData_ThirdLevel_Child));
        customExpListView.setGroupIndicator(null);
        customExpListView.setDivider(null);
        customExpListView.setOnGroupClickListener(new CustomExpListView.OnGroupClickListener(){

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.w("Expandable_second", "parent: " + parentNodeId + "group: " + groupPosition);
                if (parentNodeId == 0) {
                    switch (groupPosition) {
                        case 0: // 정형외과
                            Toast.makeText(context, "정형외과입니다.", Toast.LENGTH_LONG).show();
                            break;
                        case 1: // 심장내과
                            Toast.makeText(context, "심장내과입니다.", Toast.LENGTH_LONG).show();
                            break;
                        case 2: // 이비인후과
                            Toast.makeText(context, "이비인후과입니다.", Toast.LENGTH_LONG).show();
                            break;
                    }
                } else if (parentNodeId == 1) {
                    Intent intent;
                    JSONObject jsonObject_tmp;
                    switch (groupPosition) {
                        case 0: // 즐겨찾기
                            Toast.makeText(context, "즐겨찾기입니다.", Toast.LENGTH_LONG).show();
                            break;
                        case 1: // 회원정보 확인
                            intent = new Intent(context, UserInfoConf1.class);
                            jsonObject_tmp = new JSONObject();
                            try {
                                jsonObject_tmp.put("id", "1");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            intent.putExtra("account", jsonObject_tmp.toString());
                            context.startActivity(intent);
                            break;
                        case 2: // 회원정보 수정
                            intent = new Intent(context, UserInfoChange1.class);
                            jsonObject_tmp = new JSONObject();
                            try {
                                jsonObject_tmp.put("id", "1");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            intent.putExtra("account", jsonObject_tmp.toString());
                            context.startActivity(intent);
                            break;
                        default:
                            break;
                    }
                } else if(parentNodeId == 2){
                    switch (groupPosition){
                        case 0:
                            Toast.makeText(context, "병원 정보입니다.", Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(context, "병원 약도입니다.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            break;
                    }
                }
                return false;

            }
        });

        customExpListView.setOnChildClickListener(new CustomExpListView.OnChildClickListener(){

            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.w("Expandable_third", "group: " + groupPosition + ", child: " + childPosition);
                if (groupPosition == 3) { // 더보기
                    switch (childPosition) {
                        case 0:
                            Toast.makeText(context, "일반내과입니다.", Toast.LENGTH_LONG).show();
                            break;
                        case 1:
                            Toast.makeText(context, "신장내과입니다.", Toast.LENGTH_LONG).show();
                            break;
                        case 2:
                            Toast.makeText(context, "피부과입니다.", Toast.LENGTH_LONG).show();
                            break;
                        default:
                            break;
                    }
                }

                return false;
            }

        });
        return customExpListView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
