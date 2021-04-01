package com.drkaiproject.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.drkaiproject.Notice;
import com.drkaiproject.R;
import com.drkaiproject.adapter.NoticeAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private ActionBar actionBar;
    private TextView asthma_title, asthma_index, cold_title, cold_index;
    private ListView listView;
    private NoticeAdapter noticeAdapter;
    private String args;
    private JSONObject jsonObject;
    private String id, area_num, name;


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        args = getArguments().getString("args");
        actionBar = actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        try {
            jsonObject = new JSONObject(args);
            Log.w("MainActivity -> MainFragment",args);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.w("fragment changed","oncreateview");

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        listView = (ListView) view.findViewById(R.id.notice_listview);
        cold_index = (TextView) view.findViewById(R.id.cold_index);
        cold_title = (TextView) view.findViewById(R.id.cold_title);
        asthma_index = (TextView) view.findViewById(R.id.asthma_index);
        asthma_title = (TextView) view.findViewById(R.id.asthma_title);

        ArrayList<Notice> noticeList = new ArrayList<Notice>();
        noticeList.add(new Notice("1. 오픈 이벤트", LocalDate.now()));
        noticeList.add(new Notice("2. 추석 휴일 안내", LocalDate.of(2020, 9, 1)));

        noticeAdapter = new NoticeAdapter(getContext(), noticeList);
        listView.setAdapter(noticeAdapter);

        cold_title.setText("😄 오늘의 감기 지수 😄");
        asthma_title.setText("😄 오늘의 천식 지수 😄");

        try {
            cold_index.setText("감기 지수 : " + jsonObject.get("cold_index"));
            asthma_index.setText("천식 지수 : " + jsonObject.get("asthma_index"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onStop(){
        Log.w("fragment changed","MainFragment Stop");
        super.onStop();
    }

    @Override
    public void onResume(){
        Log.w("fragment changed","MainFragment Resume");
        super.onResume();
    }

    @Override
    public void onDestroy(){
        Log.w("fragment changed","MainFragment Destroy");
        super.onDestroy();
    }

}