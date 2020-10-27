package com.drkaiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.drkaiproject.fragment.DatePickerFragment;

public class RMTDiagActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rmtdiag);

        spinner_depart = (Spinner) findViewById(R.id.spinner_depart);
        spinner_prof = (Spinner) findViewById(R.id.spinner_prof);
        spinner_date = (EditText) findViewById(R.id.spinner_date);

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

        rmtdiag_btn = (Button) findViewById(R.id.rmtdiag_btn);
        rmtdiag_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "원격진단 요청중입니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RMTDiagActivity.this, RMTDiag2Activity.class);
                startActivityForResult(intent, 1);

            }
        });

    }
    public void setDateText(int year, int month, int day){
        spinner_date.setText(year + "/" + month + "/" + day );
    }

    public void showDatePickerDialog(View v){
        DatePickerFragment datePicker = new DatePickerFragment();
        datePicker.show(getFragmentManager(), "datePicker");

    }

    public void spinMethod() {


        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, data1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_depart.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, data2);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_prof.setAdapter(adapter2);
    }
}
