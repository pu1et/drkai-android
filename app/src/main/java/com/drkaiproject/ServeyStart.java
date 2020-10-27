package com.drkaiproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONException;
import org.json.JSONObject;

public class ServeyStart extends AppCompatActivity {
    ConstraintLayout go_servey;
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serveystart);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //Intent intent = getIntent();
        //str = intent.getExtras().getString("account");



        go_servey = findViewById(R.id.go_servey);

        go_servey.setClickable(true);
        go_servey.setOnClickListener(new View.OnClickListener() {

            // 임시
            @Override
            public void onClick(View v){

                Intent intent = new Intent(ServeyStart.this, ServeyActivity.class);

                JSONObject jsonObject = new JSONObject();
                try {
                    // 임시로 설
                    jsonObject.put("id","1");
                    jsonObject.put("pw","1");
                    jsonObject.put("name","Jiwon");
                    jsonObject.put("gender","0");
                    jsonObject.put("area","1");
                    jsonObject.put("age","25");
                    jsonObject.put("phone","01029315201");
                    jsonObject.put("carstairs","4");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                intent.putExtra("account",jsonObject.toString());
                startActivity(intent);
            }
        });

    }

}
