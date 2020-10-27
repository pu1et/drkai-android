package com.drkaiproject.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.drkaiproject.R;
import com.drkaiproject.RSVTActivity;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RSVTConfFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RSVTConfFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActionBar actionBar;
    private Button rsvt_btn, rsvtcan_btn, rsvtmod_btn, schcan_btn, schmod_btn;

    public RSVTConfFragment() {
        // Required empty public constructor
    }

    public static RSVTConfFragment newInstance(String param1, String param2) {
        RSVTConfFragment fragment = new RSVTConfFragment();
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

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_rsvtconf, container, false);

        rsvtcan_btn = (Button) view.findViewById(R.id.rsvtcan_btn);
        rsvtmod_btn = (Button) view.findViewById(R.id.rsvtmod_btn);
        schcan_btn = (Button) view.findViewById(R.id.schcan_btn);
        schmod_btn = (Button) view.findViewById(R.id.schmod_btn);
        rsvt_btn = (Button) view.findViewById(R.id.rsvt_btn);

        rsvt_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "예약하기 기능입니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getContext(), RSVTActivity.class);
                startActivity(intent);

            }

        });

        rsvtcan_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "진료예약 취소기능입니다.", Toast.LENGTH_LONG).show();
            }
        });

        rsvtmod_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "진료예약 변경기능입니다.", Toast.LENGTH_LONG).show();
            }


        });

        schcan_btn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "검사예약 취소기능입니다.", Toast.LENGTH_LONG).show();
            }
        });

        schmod_btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(view.getContext(), "검사예약 변경기능입니다.", Toast.LENGTH_LONG).show();
            }


        });

        return view;
    }
}