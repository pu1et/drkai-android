package com.drkaiproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class UserIconActivity extends AppCompatActivity {
    private Button rsvt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usericon);

        rsvt = (Button) findViewById(R.id.schmod_btn);

        rsvt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(UserIconActivity.this, "예약하기 기능입니다.", Toast.LENGTH_LONG).show();
            }


        });

    }
}
