package com.drkaiproject.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.drkaiproject.R;
import com.drkaiproject.RMTDiagActivity;
import com.drkaiproject.menu.DepressionTest;
import com.drkaiproject.menu.UserInfoChange1;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ImgDiagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiagFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ConstraintLayout go_predig, go_imgdiag, go_modify_info, go_rmtdiag;

    public DiagFragment() {
        // Required empty public constructor
    }

    public static DiagFragment newInstance(String param1, String param2) {
        DiagFragment fragment = new DiagFragment();
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

        View view = inflater.inflate(R.layout.fragment_diag, container, false);

        go_predig = (ConstraintLayout) view.findViewById(R.id.go_prediag); // 예측 진단
        go_imgdiag = (ConstraintLayout) view.findViewById(R.id.go_imgdiag); // 영상 진단
        go_modify_info = (ConstraintLayout) view.findViewById(R.id.go_modify_info);
        go_rmtdiag = (ConstraintLayout) view.findViewById(R.id.go_rmtdiag); // 원격 진단


        go_predig.setClickable(true);
        go_imgdiag.setClickable(true);
        go_modify_info.setClickable(true);
        go_rmtdiag.setClickable(true);

        go_predig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), DepressionTest.class);
                startActivity(intent);
            }
        });

        go_imgdiag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "개발중인 기능입니다.", Toast.LENGTH_LONG).show();
            }
        });

        go_modify_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), UserInfoChange1.class);
                startActivity(intent);
            }
        });

        go_rmtdiag.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RMTDiagActivity.class);
                startActivity(intent);
            }
        });



        // Inflate the layout for this fragment
        return view;
    }
}