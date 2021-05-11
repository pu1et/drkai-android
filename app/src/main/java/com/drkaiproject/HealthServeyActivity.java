package com.drkaiproject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

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
import com.drkaiproject.sqliteHelper.SqliteFunction;

import org.json.JSONException;
import org.json.JSONObject;

public class HealthServeyActivity extends AppCompatActivity {



    //--------------이전 설문조사에서 받을 데이터 > 그대로 사용--------------
    //나이, 사는 지역, 결핍지수, 당뇨병 가족력, 고혈압, 뇌졸중, 당뇨병, 천식, BMI
    String age, area_num, carstairs_level, history_diabetes, is_hypertension, is_previousStroke, is_diabetes, prior_asthma, bmi, history_coronaryArtery;
    // history_diabetes == HE_DMfh, history_coronaryArtery == HE_IHDfh, is_diabetes == DE1_dg, is_hypertension == DI1_dg, is_previousStroke == DI3_dg, bmi == bmi, age == age, gender == gender, is_obesity => 새로 생성해야됨(bmi기준), drinking(새로생성해야됨)-이전데이터기준으로, smoking(새로생성해야됨)-이전데이터기준으로, PhA(새로생성해야됨)-이전데이터기준으로, area_num == area;
    //날씨 정보: 최저온도, 일교차, 일평균습도, 일평균기압 -> 웹 크롤링
    String MT, DTR, SP, H;
    //우울증 점수합
    String depSum;

    //--------------이전 설문조사에서 받을 데이터 > 새로 계산--------------
    // gender : 성별(0-여, 1-남), current_smoking (1-안폈음,2-폈다끊음,3-약간,4-자주), smoking(몇 개피), drinking(몇 잔), drinking_5y(5년내 음주), PhA(운동 0-안함, 1-가볍, 2-격하게)
    String gender, current_smoking, smoking, drinking, drinking_5y, PhA;
    // 가공 데이터(위의 데이터 커스터마이징)
    //current_smoking_diabetes(현재 피우는지 -1,2번해당), current_smoking_gastric(폈던 적 있는지-1,2,3해당), smoking_gastric(개피수(smoking)로 나누기), drinking_gastric(잔 수(drinking)로 나누기)
    String current_smoking_diabetes, current_smoking_gastric, smoking_gastric, drinking_gastric;
    //비만(BMI로 계산)ㅎ
    String is_obesity;

    //--------------건강검진에서 받은 정보--------------
    String FPG, TG, leukocyte, total_colesterol, HDL, LDL, HbA, SBP, DBP, is_atrialFibrillation, PT_INR, bilirubin, creatinine,
            ammonia, AFP, albumin, platelet, DLD_serve, is_hypercholesterolemia, is_chemicHeartDisease, history_cancer,
            meal_reg, salt_pref; //심근경색 가족력 추가
    String id;

    Button go_main;

    EditText txt_TG, txt_leukocyte, txt_total_colesterol, txt_FPG, txt_HDL, txt_LDL, txt_HbA, txt_SBP, txt_DBP, txt_PT_INR, txt_bilirubin,
            txt_creatinine, txt_ammonia, txt_AFP, txt_albumin, txt_platelet;
    RadioGroup radio_is_aF, radio_hyper, radio_chemic, radio_cancer, radio_meal, radio_salt, radio_DLD;

    JSONObject jsonObject,diseaseObject;
    Double diabetes_risk, myocardial_risk, hepatitisA_risk, cirrhosis_risk, gastriculcer_risk, lungdisease_risk, stroke_risk;
    String depression_risk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthservey);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        findMethod();

        Intent intent = getIntent();
        String str = intent.getExtras().getString("account");
        try {
            jsonObject = new JSONObject(str); // <= 이전 intent의 모든 데이터 json 형태
            id = jsonObject.getString("id");
            age = jsonObject.getString("age");
            gender = jsonObject.getString("gender");
            area_num = jsonObject.getString("area");
            carstairs_level = jsonObject.getString("carstairs");
            history_diabetes = jsonObject.getString("HE_DMfh");
            is_hypertension = jsonObject.getString("DI1_dg");
            drinking = jsonObject.getString("BD2_1");
            drinking_5y = jsonObject.getString("drinking_5y");
            is_previousStroke = jsonObject.getString("DI3_dg");
            is_diabetes = jsonObject.getString("DE1_dg");
            prior_asthma = jsonObject.getString("DJ4_dg");
            bmi = jsonObject.getString("bmi");
            history_coronaryArtery = jsonObject.getString("HE_IHDfh");
            current_smoking = jsonObject.getString("BS3_1");
            smoking = jsonObject.getString("BS3_2");
            PhA = jsonObject.getString("exercise");
            depSum = jsonObject.getString("depSum");

            double bmi_tmp = Double.parseDouble(bmi);
            if(bmi_tmp >= 25) is_obesity = "1";
            else is_obesity = "0";

            int drinking_tmp = Integer.parseInt(drinking);
            if(drinking_tmp < 1) drinking_gastric = "0";
            else if(drinking_tmp >=1 & drinking_tmp <15) drinking_gastric = "1";
            else drinking_gastric = "2";

            // current_smoking_diabetes - 현재 피우는지
            if(current_smoking.equals("1") | current_smoking.equals("2")) current_smoking_diabetes = "1";
            else current_smoking_diabetes = "0";

            // current_smoking_gastric - 과거에 피운적 있는지
            if(current_smoking.equals("4")) current_smoking_gastric = "0";
            else current_smoking_gastric = "1";

            // smoking_gastric
            if(current_smoking.equals("4")) smoking_gastric = "0";
            else if(current_smoking.equals("3")) smoking_gastric = "1";
            else if(Integer.parseInt(smoking) < 5) smoking_gastric = "2";
            else if(Integer.parseInt(smoking) >= 5) smoking_gastric = "3";

            // exercise
            if (PhA.equals("1")) PhA = "0";
            else if (PhA.equals("2")) PhA = "1";
            else PhA = "2";

            //Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
        e.printStackTrace();
    }

        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToString()) {
                    radioToString();
                    //Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();

                    algorythm(); //알고리즘 실행
                    //임의의 데이터
                    try {
                        jsonObject.put("FPG", FPG);
                        jsonObject.put("TG", TG);
                        jsonObject.put("leukocyte", leukocyte);
                        jsonObject.put("total_colesterol", total_colesterol);
                        jsonObject.put("HDL", HDL);
                        jsonObject.put("LDL", LDL);
                        jsonObject.put("HbA", HbA);
                        jsonObject.put("SBP", SBP);
                        jsonObject.put("DBP", DBP);
                        jsonObject.put("is_atrialFibrillation", is_atrialFibrillation);
                        jsonObject.put("PT_INR", PT_INR);
                        jsonObject.put("bilirubin", bilirubin);
                        jsonObject.put("creatinine", creatinine);
                        jsonObject.put("ammonia", ammonia);
                        jsonObject.put("AFP", AFP);
                        jsonObject.put("albumin", albumin);
                        jsonObject.put("platelet", platelet);
                        jsonObject.put("DLD_serve", DLD_serve);
                        jsonObject.put("is_hypercholesterolemia", is_hypercholesterolemia);
                        jsonObject.put("is_chemicHeartDisease", is_chemicHeartDisease);
                        jsonObject.put("history_cancer", history_cancer);
                        jsonObject.put("meal_reg", meal_reg);
                        jsonObject.put("salt_pref", salt_pref);
                        jsonObject.put("is_obesity", is_obesity);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    check_user();
                }
            }
        });
    }

    public void check_user(){

         String url =  Constants.SERVER_URL + "/user/check-id";

        final RequestQueue requestQueue = Volley.newRequestQueue(HealthServeyActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<org.json.JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    final JSONObject res = response;
                    Log.d("h_checkUser_response", res.toString());
                    if (!res.getString("result").equals("1")) sendData(0);
                    else sendData(1);
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void sendData(int user_exist){
        String url;

        if (user_exist == 0) url = Constants.SERVER_URL + "/user/join";
        else url = Constants.SERVER_URL + "/user/data";

        Log.i("healthServey_sendData",jsonObject.toString());
        final RequestQueue requestQueue = Volley.newRequestQueue(HealthServeyActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<org.json.JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    final JSONObject res = response;
                    Log.d("h_sendData_response", res.toString());
                    if (!res.getString("result").equals("1")) throw new Exception();
                    else storeDisease();
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public void storeDisease(){

         String url = Constants.SERVER_URL + "/user/receive-all-disease";

        diseaseObject = new JSONObject();
        try {
            diseaseObject.put("id",jsonObject.getString("id"));
            diseaseObject.put("diabetes", Math.round((diabetes_risk*10)/10.0));
            diseaseObject.put("hepatitisA", Math.round((hepatitisA_risk*10)/10.0));
            diseaseObject.put("hepatitisB","None");
            diseaseObject.put("hepatitisC","None");
            diseaseObject.put("cirrhosis", Math.round((cirrhosis_risk*10)/10.0));
            diseaseObject.put("depression",depression_risk);
            diseaseObject.put("gastriculcer", Math.round((gastriculcer_risk*10)/10.0));
            diseaseObject.put("lungcancer","None");
            diseaseObject.put("lungdisease", Math.round((lungdisease_risk*10)/10.0));
            diseaseObject.put("myocardial", Math.round((myocardial_risk*10)/10.0));
            diseaseObject.put("stroke", Math.round((stroke_risk*10)/10.0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        final RequestQueue requestQueue = Volley.newRequestQueue(HealthServeyActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,diseaseObject, new Response.Listener<org.json.JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    final JSONObject res = response;
                    Log.d("storeDisease_response", res.toString());
                    if (!res.getString("result").equals("1")) throw new Exception();
                    else{
                        Intent intent = new Intent(HealthServeyActivity.this, MainActivity.class);
                        intent.putExtra("account", jsonObject.toString());
                        startActivity(intent);
                    }
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
    public boolean textToString() {

        FPG = txt_FPG.getText().toString();
        if (FPG.equals("")) {
            Toast.makeText(this, "공복혈당 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        TG = txt_TG.getText().toString();
        if (TG.equals("")) {
            Toast.makeText(this, "트리글리세라이드 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        leukocyte = txt_leukocyte.getText().toString();
        if (leukocyte.equals("")) {
            Toast.makeText(this, "백혈구 수치를 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        total_colesterol = txt_total_colesterol.getText().toString();
        if (total_colesterol.equals("")) {
            Toast.makeText(this, "총 콜레스테롤 수치를 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        HDL = txt_HDL.getText().toString();
        if (HDL.equals("")) {
            Toast.makeText(this, "HDL-콜레스테롤 수치를 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        LDL = txt_LDL.getText().toString();
        if (LDL.equals("")) {
            Toast.makeText(this, "LDL-콜레스테롤 수치를 ㄴ력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        HbA = txt_HbA.getText().toString();
        if (HbA.equals("")) {
            Toast.makeText(this, "글리코헤모글로빈 수치를 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        SBP = txt_SBP.getText().toString();
        if (SBP.equals("")) {
            Toast.makeText(this, "수축기 혈압을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        DBP = txt_DBP.getText().toString();
        if (DBP.equals("")) {
            Toast.makeText(this, "이완기 혈압을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        PT_INR = txt_PT_INR.getText().toString();
        if (PT_INR.equals("")) {
            Toast.makeText(this, "PT INR 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        bilirubin = txt_bilirubin.getText().toString();
        if (bilirubin.equals("")) {
            Toast.makeText(this, "빌리루빈 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        creatinine = txt_creatinine.getText().toString();
        if (creatinine.equals("")) {
            Toast.makeText(this, "크레아티닌 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        ammonia = txt_ammonia.getText().toString();
        if (ammonia.equals("")) {
            Toast.makeText(this, "암모니아 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        AFP = txt_AFP.getText().toString();
        if (AFP.equals("")) {
            Toast.makeText(this, "AFP 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        albumin = txt_albumin.getText().toString();
        if (albumin.equals("")) {
            Toast.makeText(this, "알부민 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        platelet = txt_platelet.getText().toString();
        if (platelet.equals("")) {
            Toast.makeText(this, "혈소판 수치 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }

        Log.v("EditText 값:", FPG + "," + TG + "," + leukocyte + "," + total_colesterol + "," + HDL + "," + LDL + "," + HbA + "," + SBP + "," + DBP + ","
                + PT_INR + "," + bilirubin + "," + creatinine + "," + ammonia + "," + AFP + "," + albumin + "," + platelet);
        return true;
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


    public void algorythm() { // 모든 알고리즘 수정 완료

        //1. 당뇨병
        Log.v("diabetes",age+" "+history_diabetes+" "+current_smoking_diabetes+" "+bmi+" "+is_hypertension+" "+FPG+" "+HDL+" "+TG+" "+HbA);
        Diabetes diabetes_result = new Diabetes(Integer.parseInt(age), Integer.parseInt(history_diabetes), Integer.parseInt(current_smoking_diabetes), Integer.parseInt(bmi), Integer.parseInt(is_hypertension), Integer.parseInt(FPG), Integer.parseInt(HDL), Integer.parseInt(TG), Integer.parseInt(HbA));
        Log.v("당뇨병 %: ", diabetes_result.cal_diabetes() + "");
        Log.v("당뇨병 단계: ", diabetes_result.get_value() + "");
        diabetes_risk = diabetes_result.cal_diabetes()*100;

        //2. 심근경색
        Log.v("myocardial",age+" "+creatinine+" "+is_hypertension+" "+history_coronaryArtery+" "+is_diabetes+" "+leukocyte+" "+current_smoking+" "+total_colesterol+" "+HDL+" "+LDL+" "+HbA+" "+SBP+" "+DBP+" "+is_atrialFibrillation);
        Myocardial myocardial_result = new Myocardial(Integer.parseInt(age), Double.parseDouble(creatinine), Integer.parseInt(is_hypertension),
                Integer.parseInt(history_coronaryArtery), Integer.parseInt(is_diabetes), Integer.parseInt(leukocyte), Integer.parseInt(current_smoking),
                Integer.parseInt(total_colesterol), Integer.parseInt(HDL), Integer.parseInt(LDL), Integer.parseInt(HbA), Double.parseDouble(SBP),
                Double.parseDouble(DBP), Integer.parseInt(is_atrialFibrillation));
        Log.v("심근경색 %: ", myocardial_result.cal_myocardial() + "");
        Log.v("심근경색 단계: ", myocardial_result.get_value() + "");
        myocardial_risk = myocardial_result.cal_myocardial()*100;


        //3. A형 간염
        Log.v("hepatitis",gender+" "+age+" "+HbA+" "+PT_INR+" "+bilirubin+" "+creatinine+" "+ammonia);
        Hepatitis hepatitis_result = new Hepatitis(Integer.parseInt(gender), Integer.parseInt(age), Integer.parseInt(HbA), Integer.parseInt(PT_INR),
                Integer.parseInt(bilirubin), Float.parseFloat(creatinine), Integer.parseInt(ammonia));
        Log.v("A형 간염 %: ", hepatitis_result.cal_hepatitis() + "");
        Log.v("A형 간염 단계: ", hepatitis_result.get_value() + "");

        hepatitisA_risk = hepatitis_result.cal_hepatitis()*100;

        //4. 간경변증
        Log.v("cirrhosis",AFP+" "+albumin+" "+platelet+" "+DLD_serve+" "+drinking_5y);
        Cirrhosis cirrhosis_result = new Cirrhosis(Integer.parseInt(gender), Integer.parseInt(age), Integer.parseInt(AFP), Float.parseFloat(albumin),
                Integer.parseInt(platelet), Integer.parseInt(DLD_serve), Integer.parseInt(drinking_5y));

        Log.v("간경변증 %: ", cirrhosis_result.cal_cirrhosis() + "");
        Log.v("간경변증 단계: ", cirrhosis_result.get_value() + "");
        cirrhosis_risk =  cirrhosis_result.cal_cirrhosis()*100;

        //5. 뇌졸중
        Log.v("stroke",is_hypertension+" "+is_diabetes+" "+is_hypercholesterolemia+" "+is_atrialFibrillation+" "+is_chemicHeartDisease+" "+is_previousStroke+" "+is_obesity+" "+current_smoking_diabetes);
        Stroke stroke_result = new Stroke(Integer.parseInt(gender), Integer.parseInt(age), Integer.parseInt(is_hypertension), Integer.parseInt(is_diabetes),
                Integer.parseInt(is_hypercholesterolemia), Integer.parseInt(is_atrialFibrillation), Integer.parseInt(is_chemicHeartDisease),
                Integer.parseInt(is_previousStroke), Integer.parseInt(is_obesity), Integer.parseInt(current_smoking_diabetes));

        Log.v("뇌졸중 %: ", stroke_result.cal_stroke() + "");
        Log.v("뇌졸중 단계: ", stroke_result.get_value() + "");
        stroke_risk = stroke_result.cal_stroke()*100;

        //6. 위궤양
        Log.v("gastriculcer",history_cancer+" "+meal_reg+" "+salt_pref+" "+drinking_gastric+" "+current_smoking_gastric+" "+PhA);
        Gastriculcer gastriculcer_result;
        if (gender.equals("0")){ // woman
            gastriculcer_result = new Gastriculcer(Integer.parseInt(gender), Integer.parseInt(age), Float.parseFloat(bmi), Integer.parseInt(history_cancer),
                    0, Integer.parseInt(salt_pref), Integer.parseInt(drinking_gastric), Integer.parseInt(current_smoking_gastric), 0);
        }else { // man
            gastriculcer_result = new Gastriculcer(Integer.parseInt(gender), Integer.parseInt(age), Float.parseFloat(bmi), Integer.parseInt(history_cancer),
                    Integer.parseInt(meal_reg), Integer.parseInt(salt_pref), Integer.parseInt(drinking_gastric), Integer.parseInt(smoking_gastric), Integer.parseInt(PhA));

        }
        Log.v("위궤양 %: ", gastriculcer_result.cal_gastriculcer() + "");
        Log.v("위궤양 단계: ", gastriculcer_result.get_value() + "");

        gastriculcer_risk = gastriculcer_result.cal_gastriculcer()*100;

        //7. 우울증
        Depression depression_result = new Depression(Integer.parseInt(depSum));
        Log.v("우울증 %: ", depression_result.cal_depression() + "");

        depression_risk = depression_result.cal_depression();

        //8. 폐질환
        Log.v("lungDisease",carstairs_level+" "+prior_asthma);
        LungDisease lungDisease_result = new LungDisease(Integer.parseInt(gender), Integer.parseInt(age), Integer.parseInt(smoking), Integer.parseInt(carstairs_level),
                Integer.parseInt(prior_asthma));
        Log.v("폐질환 %: ", lungDisease_result.cal_lungdisease() + "");
        Log.v("폐질환 %: ", lungDisease_result.get_value() + "");

        lungdisease_risk = lungDisease_result.cal_lungdisease()*100;


    }
}
