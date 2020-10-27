package com.drkaiproject.menu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.drkaiproject.MainActivity;
import com.drkaiproject.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoConf2 extends AppCompatActivity {
    private String str;
    JSONObject jsonObject;

    TextView info_TG, info_leukocyte, info_total_colesterol, info_FPG, info_HDL, info_LDL, info_HbA, info_SBP, info_DBP, info_PT_INR,
            info_bilirubin, info_creatinine, info_ammonia, info_AFP, info_albumin, info_platelet, info_is_aF, info_hyper, info_chemic,
            info_cancer, info_meal, info_salt, info_DLD;

    Button go_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_userinfoconf2);

        Intent intent = getIntent();
        str = intent.getExtras().getString("account");

        try {
            jsonObject = new JSONObject(str);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        findMethod();
        setMethod();

        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserInfoConf2.this, MainActivity.class);
                intent.putExtra("account", jsonObject.toString());
                startActivity(intent);
            }
        });


    }

    public void setMethod(){
        try {
            info_TG.setText(jsonObject.getString("TG"));
            info_leukocyte.setText(jsonObject.getString("leukocyte"));
            info_total_colesterol.setText(jsonObject.getString("total_colesterol"));
            info_FPG.setText(jsonObject.getString("FPG"));
            info_HDL.setText(jsonObject.getString("HDL"));
            info_LDL.setText(jsonObject.getString("LDL"));
            info_HbA.setText(jsonObject.getString("HbA"));
            info_SBP.setText(jsonObject.getString("SBP"));
            info_DBP.setText(jsonObject.getString("DBP"));
            info_PT_INR.setText(jsonObject.getString("PT_INR"));
            info_bilirubin.setText(jsonObject.getString("bilirubin"));
            info_creatinine.setText(jsonObject.getString("creatinine"));
            info_ammonia.setText(jsonObject.getString("ammonia"));
            info_AFP.setText(jsonObject.getString("AFP"));
            info_albumin.setText(jsonObject.getString("albumin"));
            info_platelet.setText(jsonObject.getString("platelet"));
            info_is_aF.setText(jsonObject.getString("is_atrialFibrillation").equals("1")? "예":"아니오");
            info_hyper.setText(jsonObject.getString("is_hypercholesterolemia").equals("1")? "예":"아니오");
            info_chemic.setText(jsonObject.getString("is_chemicHeartDisease").equals("1")? "예":"아니오");
            info_cancer.setText(jsonObject.getString("history_cancer").equals("1")? "예":"아니오");
            info_DLD.setText(jsonObject.getString("DLD_serve").equals("1")? "예":"아니오");
            String tmp = jsonObject.getString("meal_reg");
            switch (tmp){
                case "0":
                    tmp = "규칙적";
                    break;
                case "1":
                    tmp = "가끔";
                    break;
                case "2":
                    tmp = "불규칙";
                    break;
                default:
                    break;
            }
            info_meal.setText(tmp);
            tmp = jsonObject.getString("salt_pref");
            switch (tmp){
                case "0":
                    tmp = "거의 안먹음";
                    break;
                case "1":
                    tmp = "가끔";
                    break;
                case "2":
                    tmp = "자주 먹음";
                    break;
                default:
                    break;
            }
            info_salt.setText(tmp);
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void findMethod(){
        info_TG = findViewById(R.id.info_TG);
        info_leukocyte = findViewById(R.id.info_leukocyte);
        info_total_colesterol = findViewById(R.id.info_total_colesterol);
        info_FPG = findViewById(R.id.info_FPG);
        info_HDL = findViewById(R.id.info_HDL);
        info_LDL = findViewById(R.id.info_LDL);
        info_HbA = findViewById(R.id.info_HbA);
        info_SBP = findViewById(R.id.info_SBP);
        info_DBP = findViewById(R.id.info_DBP);
        info_PT_INR = findViewById(R.id.info_PT_INR);
        info_bilirubin = findViewById(R.id.info_bilirubin);
        info_creatinine = findViewById(R.id.info_creatinine);
        info_ammonia = findViewById(R.id.info_ammonia);
        info_AFP = findViewById(R.id.info_AFP);
        info_albumin = findViewById(R.id.info_albumin);
        info_platelet = findViewById(R.id.info_platelet);
        info_is_aF = findViewById(R.id.info_is_aF);
        info_hyper = findViewById(R.id.info_hyper);
        info_chemic = findViewById(R.id.info_chemic);
        info_cancer = findViewById(R.id.info_cancer);
        info_meal = findViewById(R.id.info_meal);
        info_salt = findViewById(R.id.info_salt);
        info_DLD = findViewById(R.id.info_DLD);

        go_main = (Button) findViewById(R.id.go_main);
    }
}
