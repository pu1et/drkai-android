package com.drkaiproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;

import com.drkaiproject.R;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RMTDiagFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RMTDiagFragment extends Fragment {
    public static Fragment mContext;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ActionBar actionBar;
    private Spinner spinner_depart, spinner_prof;
    Button rmtdiag_btn;

    EditText spinner_date;
    String[] data1 = {
            "정형외과", "심장내과", "이비인후과"
    };
    String[] data2 = {
            "김교수", "이교수", "최교수"
    };

    public RMTDiagFragment() {
        // Required empty public constructor
    }

    public static RMTDiagFragment newInstance(String param1, String param2) {
        RMTDiagFragment fragment = new RMTDiagFragment();
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
        View view = inflater.inflate(R.layout.fragment_rmtdiag, container, false);
        mContext = this;
        spinner_depart = (Spinner) view.findViewById(R.id.spinner_depart);
        spinner_prof = (Spinner) view.findViewById(R.id.spinner_prof);
        spinner_date = (EditText) view.findViewById(R.id.spinner_date);

        spinMethod();

        spinner_depart.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String str = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                String str = (String) parent.getSelectedItem();
            }
        });

        spinner_prof.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String str = (String) parent.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent){
                String str = (String) parent.getSelectedItem();
            }
        });

        spinner_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });

        rmtdiag_btn = view.findViewById(R.id.rmtdiag_btn);
        rmtdiag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "원격진단 요청중입니다.", Toast.LENGTH_LONG).show();


            }
        });

        return view;

    }
    public void setDateText(int year, int month, int day){
        spinner_date.setText(year + "/" + month + "/" + day );
    }

    public void showDatePickerDialog(View v){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getActivity().getFragmentManager(), "datePicker");

    }

    public void spinMethod() {


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_depart.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, data2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_prof.setAdapter(adapter2);
    }
}