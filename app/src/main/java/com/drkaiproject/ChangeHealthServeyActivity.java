package com.drkaiproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.drkaiproject.algorithmn.Cirrhosis;
import com.drkaiproject.algorithmn.Depression;
import com.drkaiproject.algorithmn.Diabetes;
import com.drkaiproject.algorithmn.Gastriculcer;
import com.drkaiproject.algorithmn.Hepatitis;
import com.drkaiproject.algorithmn.LungDisease;
import com.drkaiproject.algorithmn.Myocardial;
import com.drkaiproject.algorithmn.Stroke;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeHealthServeyActivity extends AppCompatActivity {


    //--------------이전 설문조사에서 받을 데이터 > 그대로 사용--------------
    //나이, 사는 지역, 결핍지수, 당뇨병 가족력, 고혈압, 뇌졸중, 당뇨병, 천식, BMI
    String id, age, area_num, carstairs_level, history_diabetes, is_hypertension, is_previousStroke, is_diabetes, prior_asthma, bmi, history_coronaryArtery;
    //날씨 정보: 최저온도, 일교차, 일평균습도, 일평균기압 -> 웹 크롤링
    String MT, DTR, SP, H;
    //우울증 점수합
    String depSum;

    //--------------이전 설문조사에서 받을 데이터 > 새로 계산--------------
    //성별(0 1), 현재 흡연(0 1), 흡연 경험(0 1 2 3 4), 음주(0,1,2,3), 5년동안 80ml 음주(0,1), 운동
    String gender, current_smoking, smoking, Drinking, drinking, PhA;
    //비만(BMI로 계산)
    String is_obesity;

    //--------------건강검진에서 받은 정보--------------
    String FPG, TG, leukocyte, total_colesterol, HDL, LDL, HbA, SBP, DBP, is_atrialFibrillation, PT_INR, bilirubin, creatinine,
            ammonia, AFP, albumin, platelet, DLD_serve, is_hypercholesterolemia, is_chemicHeartDisease, history_cancer,
            meal_reg, salt_pref; //심근경색 가족력 추가


    Button go_main;

    EditText txt_TG, txt_leukocyte, txt_total_colesterol, txt_FPG, txt_HDL, txt_LDL, txt_HbA, txt_SBP, txt_DBP, txt_PT_INR, txt_bilirubin,
            txt_creatinine, txt_ammonia, txt_AFP, txt_albumin, txt_platelet;
    RadioGroup radio_is_aF, radio_hyper, radio_chemic, radio_cancer, radio_meal, radio_salt, radio_DLD;

    JSONObject jsonObject, newObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_healthservey);
        receiveData();
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        findMethod();
        //다음 페이지 이동
        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToString();
                radioToString();
                algorythm(); //알고리즘 갱신
                //디비에 새로운 값 저장

                sendData();
                Intent intent = new Intent(ChangeHealthServeyActivity.this, MainActivity.class); //메인에 넘겨줌
                startActivity(intent);
            }
        });
    }

    public void receiveData() {

        String url = "http://192.168.247.1:1337/chhealthservey_s";
        SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
        id = sp.getString("id", null);
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(ChangeHealthServeyActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<org.json.JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resObject = new JSONObject(response.toString());
                    FPG = resObject.getString("FPG");
                    TG = resObject.getString("TG");
                    leukocyte = resObject.getString("leukocyte");
                    total_colesterol = resObject.getString("total_colesterol");
                    HDL = resObject.getString("HDL");
                    LDL = resObject.getString("LDL");
                    HbA = resObject.getString("HbA");
                    SBP = resObject.getString("SBP");
                    DBP = resObject.getString("DBP");
                    is_atrialFibrillation = resObject.getString("is_atrialFibrillation");
                    PT_INR = resObject.getString("PT_INR");
                    bilirubin = resObject.getString("bilirubin");
                    creatinine = resObject.getString("creatinine");
                    ammonia = resObject.getString("ammonia");
                    AFP = resObject.getString("AFP");
                    albumin = resObject.getString("albumin");
                    platelet = resObject.getString("platelet");
                    DLD_serve = resObject.getString("DLD_serve");
                    is_hypercholesterolemia = resObject.getString("is_hypercholesterolemia");
                    is_chemicHeartDisease = resObject.getString("is_chemicHeartDisease");
                    history_cancer = resObject.getString("history_cancer");
                    meal_reg = resObject.getString("meal_reg");
                    salt_pref = resObject.getString("salt_pref");

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void sendData() {
        //String url = "https://nodejs105.azurewebsites.net";
        String url = "http://192.168.247.1:1337/join";
        try {
            newObject = new JSONObject();
            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
            id = sp.getString("id", null);            newObject.put("id",id);
            newObject.put("FPG", FPG);
            newObject.put("TG", TG);
            newObject.put("leukocyte", leukocyte);
            newObject.put("total_colesterol", total_colesterol);
            newObject.put("HDL", HDL);
            newObject.put("LDL", LDL);
            newObject.put("HbA", HbA);
            newObject.put("SBP", SBP);
            newObject.put("DBP", DBP);
            newObject.put("is_atrialFibrillation", is_atrialFibrillation);
            newObject.put("PT_INR", PT_INR);
            newObject.put("bilirubin", bilirubin);
            newObject.put("creatinine", creatinine);
            newObject.put("ammonia", ammonia);
            newObject.put("AFP", AFP);
            newObject.put("albumin", albumin);
            newObject.put("platelet", platelet);
            newObject.put("DLD_serve", DLD_serve);
            newObject.put("is_hypercholesterolemia", is_hypercholesterolemia);
            newObject.put("is_chemicHeartDisease", is_chemicHeartDisease);
            newObject.put("history_cancer", history_cancer);
            newObject.put("meal_reg", meal_reg);
            newObject.put("salt_pref", salt_pref);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(ChangeHealthServeyActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, newObject, new Response.Listener<org.json.JSONObject>() {
            @Override
            public void onResponse(JSONObject response) { }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void findMethod() {

        //EditText
        txt_FPG = (EditText) findViewById(R.id.txt_FPG);
        txt_TG = (EditText) findViewById(R.id.txt_TG);
        txt_leukocyte = (EditText) findViewById(R.id.txt_leukocyte);
        txt_total_colesterol = (EditText) findViewById(R.id.txt_total_colesterol);
        txt_HDL = (EditText) findViewById(R.id.txt_HDL);
        txt_LDL = (EditText) findViewById(R.id.txt_LDL);
        txt_HbA = (EditText) findViewById(R.id.txt_HbA);
        txt_SBP = (EditText) findViewById(R.id.txt_SBP);
        txt_DBP = (EditText) findViewById(R.id.txt_DBP);
        txt_PT_INR = (EditText) findViewById(R.id.txt_PT_INR);
        txt_bilirubin = (EditText) findViewById(R.id.txt_bilirubin);
        txt_creatinine = (EditText) findViewById(R.id.txt_creatinine);
        txt_ammonia = (EditText) findViewById(R.id.txt_ammonia);
        txt_AFP = (EditText) findViewById(R.id.txt_AFP);
        txt_albumin = (EditText) findViewById(R.id.txt_albumin);
        txt_platelet = (EditText) findViewById(R.id.txt_platelet);

        //라디오버튼
        radio_is_aF = (RadioGroup) findViewById(R.id.radio_is_aF);
        radio_DLD = (RadioGroup) findViewById(R.id.radio_DLD);
        radio_hyper = (RadioGroup) findViewById(R.id.radio_hyper);
        radio_chemic = (RadioGroup) findViewById(R.id.radio_chemic);
        radio_cancer = (RadioGroup) findViewById(R.id.radio_cancer);
        radio_meal = (RadioGroup) findViewById(R.id.radio_meal);
        radio_salt = (RadioGroup) findViewById(R.id.radio_salt);

        //버튼
        go_main = (Button) findViewById(R.id.go_main);
    }

    // EditText > String
    public void textToString() {

        FPG = txt_FPG.getText().toString();
        TG = txt_TG.getText().toString();
        leukocyte = txt_leukocyte.getText().toString();
        total_colesterol = txt_total_colesterol.getText().toString();
        HDL = txt_HDL.getText().toString();
        LDL = txt_LDL.getText().toString();
        HbA = txt_HbA.getText().toString();
        SBP = txt_SBP.getText().toString();
        DBP = txt_DBP.getText().toString();
        PT_INR = txt_PT_INR.getText().toString();
        bilirubin = txt_bilirubin.getText().toString();
        creatinine = txt_creatinine.getText().toString();
        ammonia = txt_ammonia.getText().toString();
        AFP = txt_AFP.getText().toString();
        albumin = txt_albumin.getText().toString();
        platelet = txt_platelet.getText().toString();

        Log.v("EditText 값:", FPG + "," + TG + "," + leukocyte + "," + total_colesterol + "," + HDL + "," + LDL + "," + HbA + "," + SBP + "," + DBP + ","
                + PT_INR + "," + bilirubin + "," + creatinine + "," + ammonia + "," + AFP + "," + albumin + "," + platelet);
    }

    // RadioButton > String
    public void radioToString() {

        //RadioButton > String, ui순서 동일
        is_atrialFibrillation = ((RadioButton) findViewById(radio_is_aF.getCheckedRadioButtonId())).getText().toString(); //심방세동
        DLD_serve = ((RadioButton) findViewById(radio_DLD.getCheckedRadioButtonId())).getText().toString();//DLD_serve
        is_hypercholesterolemia = ((RadioButton) findViewById(radio_hyper.getCheckedRadioButtonId())).getText().toString(); //고지혈증
        is_chemicHeartDisease = ((RadioButton) findViewById(radio_chemic.getCheckedRadioButtonId())).getText().toString(); //관상동맥질환
        history_cancer = ((RadioButton) findViewById(radio_cancer.getCheckedRadioButtonId())).getText().toString(); //암 가족력
        meal_reg = ((RadioButton) findViewById(radio_meal.getCheckedRadioButtonId())).getText().toString();//식사
        salt_pref = ((RadioButton) findViewById(radio_salt.getCheckedRadioButtonId())).getText().toString(); //짠 음식

        //"0","1"로 바꾸기
        String[] str = {is_atrialFibrillation, DLD_serve, is_hypercholesterolemia, is_chemicHeartDisease, history_cancer, meal_reg, salt_pref};
        for (int i = 0; i < 7; i++) {
            String result = radioCheck(str[i]);
            switch (i) {
                case 0:
                    is_atrialFibrillation = result;
                    break;
                case 1:
                    DLD_serve = result;
                    break;
                case 2:
                    is_hypercholesterolemia = result;
                    break;
                case 3:
                    is_chemicHeartDisease = result;
                    break;
                case 4:
                    history_cancer = result;
                    break;
                case 5:
                    meal_reg = result;
                    break;
                case 6:
                    salt_pref = result;
                    break;
                default:
                    break;
            }
        }
        Log.v("라디오버튼 값: ", is_atrialFibrillation + "," + DLD_serve + "." + is_hypercholesterolemia + "," + is_chemicHeartDisease + ","
                + history_cancer + "." + meal_reg + "," + salt_pref);
    }

    public String radioCheck(String e) {
        String result = "";

        if (e.equals("예")) {
            result = "1";
        } else if (e.equals("아니오")) {
            result = "0";
        } else if (e.equals("규칙적")) {
            result = "0";
        } else if (e.equals("가끔")) {
            result = "1";
        } else if (e.equals("불규칙적")) {
            result = "2";
        } else if (e.equals("거의 안먹음")) {
            result = "0";
        } else if (e.equals("자주 먹음")) {
            result = "2";
        } else {
            Log.v("오류", "....Error!");
        }
        return result;
    }


    public void algorythm() {


        //1. 당뇨병
        Diabetes diabetes_result = new Diabetes(Integer.parseInt(age), Integer.parseInt(history_diabetes), Integer.parseInt(current_smoking), Integer.parseInt(bmi),
                Integer.parseInt(is_hypertension), Integer.parseInt(FPG), Integer.parseInt(HDL), Integer.parseInt(TG), Integer.parseInt(HbA));
        Log.v("당뇨병 %: ", diabetes_result.cal_diabetes() + "");
        Log.v("당뇨병 단계: ", diabetes_result.get_value() + "");



        //2. 심근경색
        Myocardial mycardial_result = new Myocardial(Integer.parseInt(age), Double.parseDouble(creatinine), Integer.parseInt(is_hypertension),
                Integer.parseInt(history_coronaryArtery), Integer.parseInt(is_diabetes), Integer.parseInt(leukocyte), Integer.parseInt(current_smoking),
                Integer.parseInt(total_colesterol), Integer.parseInt(HDL), Integer.parseInt(LDL), Integer.parseInt(HbA), Double.parseDouble(SBP),
                Double.parseDouble(DBP), Integer.parseInt(is_atrialFibrillation));

        Log.v("심근경색 %: ", mycardial_result.cal_myocardial() + "");
        Log.v("심근경색 단계: ", mycardial_result.get_value() + "");




        //3. A형 간염
        Hepatitis hepatitis_result = new Hepatitis(Integer.parseInt(gender), Integer.parseInt(age), Integer.parseInt(HbA), Integer.parseInt(PT_INR),
                Integer.parseInt(bilirubin), Float.parseFloat(creatinine), Integer.parseInt(ammonia));
        Log.v("A형 간염 %: ", hepatitis_result.cal_hepatitis() + "");
        Log.v("A형 간염 단계: ", hepatitis_result.get_value() + "");


        //4. 간경변증
        Cirrhosis cirrhosis_result = new Cirrhosis(Integer.parseInt(gender), Integer.parseInt(age), Integer.parseInt(AFP), Float.parseFloat(albumin),
                Integer.parseInt(platelet), Integer.parseInt(DLD_serve), Integer.parseInt(drinking));
        Log.v("간경변증 %: ", cirrhosis_result.cal_cirrhosis() + "");
        Log.v("간경변증 단계: ", cirrhosis_result.get_value() + "");


        //5. 뇌졸중
        Stroke stroke_result = new Stroke(Integer.parseInt(gender), Integer.parseInt(age), Integer.parseInt(is_hypertension), Integer.parseInt(is_diabetes),
                Integer.parseInt(is_hypercholesterolemia), Integer.parseInt(is_atrialFibrillation), Integer.parseInt(is_chemicHeartDisease),
                Integer.parseInt(is_previousStroke), Integer.parseInt(is_obesity), Integer.parseInt(current_smoking));

        Log.v("뇌졸중 %: ", stroke_result.cal_stroke() + "");
        Log.v("뇌졸중 단계: ", stroke_result.get_value() + "");



        //6. 위궤양
        Gastriculcer gastriculcer_result = new Gastriculcer(Integer.parseInt(gender), Integer.parseInt(age), Float.parseFloat(bmi), Integer.parseInt(history_cancer),
                Integer.parseInt(meal_reg), Integer.parseInt(salt_pref), Integer.parseInt(Drinking), Integer.parseInt(smoking), Integer.parseInt(PhA));

        Log.v("위궤양 %: ", gastriculcer_result.cal_gastriculcer() + "");
        Log.v("위궤양 단계: ", gastriculcer_result.get_value() + "");



        //7. 우울증
        Depression depression_result = new Depression(Integer.parseInt(depSum));
        Log.v("우울증 %: ", depression_result.cal_depression() + "");



        //8. 폐질환
        LungDisease lungDisease_result = new LungDisease(Integer.parseInt(gender), Integer.parseInt(age), Integer.parseInt(smoking), Integer.parseInt(carstairs_level),
                Integer.parseInt(prior_asthma));
        Log.v("폐질환 %: ", lungDisease_result.cal_lungdisease() + "");
        Log.v("폐질환 %: ", lungDisease_result.get_value() + "");

    }
}
