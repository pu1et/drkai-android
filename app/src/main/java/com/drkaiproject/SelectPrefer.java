package com.drkaiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SelectPrefer extends AppCompatActivity {

    CheckBox check0, check1, check2, check3, check4, check5, check6, check7, check8, check9, check10;
    Button edit_btn;
    int prefer0, prefer1, prefer2, prefer3, prefer4, prefer5, prefer6, prefer7, prefer8, prefer9, prefer10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_prefer);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        check0 = findViewById(R.id.check0);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
        check5 = findViewById(R.id.check5);
        check6 = findViewById(R.id.check6);
        check7 = findViewById(R.id.check7);
        check8 = findViewById(R.id.check8);
        check9 = findViewById(R.id.check9);
        check10 = findViewById(R.id.check10);

        edit_btn = findViewById(R.id.edit_btn);

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getPrefer();

                Intent intent = new Intent(SelectPrefer.this, JoinActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getPrefer() {
        if (check0.isChecked()) {
            prefer0 = 1;
        } else {
            prefer0 = 0;
        }

        if (check1.isChecked()) {
            prefer1 = 1;
        } else {
            prefer1 = 0;
        }

        if (check2.isChecked()) {
            prefer2 = 1;
        } else {
            prefer2 = 0;
        }

        if (check3.isChecked()) {
            prefer3 = 1;
        } else {
            prefer3 = 0;
        }

        if (check4.isChecked()) {
            prefer4 = 1;
        } else {
            prefer4 = 0;
        }

        if (check5.isChecked()) {
            prefer5 = 1;
        } else {
            prefer5 = 0;
        }

        if (check6.isChecked()) {
            prefer6 = 1;
        } else {
            prefer6 = 0;
        }

        if (check7.isChecked()) {
            prefer7 = 1;
        } else {
            prefer7 = 0;
        }
        if (check8.isChecked()) {
            prefer8 = 1;
        } else {
            prefer8 = 0;
        }
        if (check9.isChecked()) {
            prefer9 = 1;
        } else {
            prefer9 = 0;
        }

        if (check10.isChecked()) {
            prefer10 = 1;
        } else {
            prefer10 = 0;
        }
    }
}

