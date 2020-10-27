package com.drkaiproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangeServeyActivity extends AppCompatActivity {

    //데이터
    String HE_fh, HE_HPfh, HE_HLfh, HE_IHDfh, HE_STRfh, HE_HBfh, HE_DMfh, BH2_61,
            DI1_dg, DI2_dg, DI3_dg, DJ4_dg, DI4_dg, DJ2_dg, DE1_dg, DE1_32, DC1_dg, DC2_dg,
            DC6_dg, DJ8_dg, DJ6_dg, DK8_dg, DK9_dg, DK4_dg;
    String BO1_1, BP1, D_1_1, BE5_1, BS3_1, DI1_2, DI2_2, exercise;
    String HE_ht, HE_wt, EC_wht_23, HE_sput2, BS3_2, Total_slp_wk, Total_slp_wd, BD2_1, BE3_33;
    String bmi; //BMI

    //라디오그룹
    RadioGroup radio_HE_fh, radio_HE_HPfh, radio_HE_HLfh, radio_HE_IHDfh, radio_HE_STRfh, radio_HE_HBfh, radio_HE_DMfh, radio_BH2_61,
            radio_DI1_dg, radio_DI2_dg, radio_DI3_dg, radio_DJ4_dg, radio_DI4_dg, radio_DJ2_dg, radio_DE1_dg, radio_DE1_32, radio_DC1_dg, radio_DC2_dg,
            radio_DC6_dg, radio_DJ8_dg, radio_DJ6_dg, radio_DK8_dg, radio_DK9_dg, radio_DK4_dg;

    //텍스트뷰
    TextView txt_BS3_2, txt_HE_sput2, txt_EC_wht_23, txt_Total_slp_wk, txt_Total_slp_wd, txt_HE_ht, txt_HE_wt, txt_BD2_1, txt_BE3_33;

    //스피너
    Spinner spin_D_1_1, spin_BO1_1, spin_BS3_1, spin_BE5_1, spin_DI1_2, spin_DI2_2, spin_BP1, spin_exercise;

    String[] data1 = {
            "1. 매우좋음", "2. 좋음", "3. 보통", "4. 나쁨", "5. 매우나쁨"
    };

    String[] data2 = {
            "1. 변화없음", "2. 체중감소", "3. 증가"
    };
    String[] data3 = {
            "1. 매일", "2. 가끔", "3. 과거에피웠으나현재안피움"
    };
    String[] data4 = {
            "1. 안한다", "2. 1일", "3. 2일", "4. 3일", "5. 4일", "6. 5일 이상"
    };
    String[] data5 = { //평균 운동 정도
            "1. 안한다", "2. 가볍", "3. 적당"
    };
    String[] data6 = {
            "1. 매일 복용함", "2. 한달에 20일 이상 복용한다", "3. 한달에 15일 이상 복용한다", "4. 한달에 15일 미만 복용한다", "5. 복용하지 않는다"
    };
    String[] data7 = {
            "1. 매일 복용함", "2. 한달에 20일 이상 복용한다", "3. 한달에 15일 이상 복용한다", "4. 한달에 15일 미만 복용한다", "5. 복용하지 않는다"
    };
    String[] data8 = {
            "1. 대단히 많이 느낀다.", "2. 많이 느끼는 편이다.", "3. 조금 느끼는 편이다.", "4. 거의 느끼지 않는다"
    };

    //버튼
    Button go_main;
    String id;
    JSONObject jsonObject, newObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_change_servey);
        receiveData();
        findMethod();
        spinMethod();

        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToString();
                radioToString();
                spinToString();
                //디비에 새로운 값 저장
                sendData();

                Intent intent = new Intent(ChangeServeyActivity.this, MainActivity.class); //메인으로 이동
                startActivity(intent);
            }
        });
    }

    public void sendData() {
        //String url = "https://nodejs105.azurewebsites.net";
        String url = "http://192.168.247.1:1337/chservey_r";
        try {
            newObject = new JSONObject();
            String id="aa"; // 하드코딩(추후삭제)
            newObject.put("HE_fh",HE_fh);
            newObject.put("HE_HPfh",HE_HPfh);
            newObject.put("HE_HLfh",HE_HLfh);
            newObject.put("HE_IHDfh",HE_IHDfh);
            newObject.put("HE_STRfh",HE_STRfh);
            newObject.put("HE_HBfh",HE_HBfh);
            newObject.put("HE_DMfh",HE_DMfh);
            newObject.put("BH2_61",BH2_61);
            newObject.put("DI1_dg",DI1_dg);
            newObject.put("DI2_dg",DI2_dg);
            newObject.put("DI3_dg",DI3_dg);
            newObject.put("DJ4_dg",DJ4_dg);
            newObject.put("DI4_dg",DI4_dg);
            newObject.put("DJ2_dg",DJ2_dg);
            newObject.put("DE1_dg",DE1_dg);
            newObject.put("DE1_32",DE1_32);
            newObject.put("DC1_dg",DC1_dg);
            newObject.put("DC2_dg",DC2_dg);
            newObject.put("DC6_dg",DC6_dg);
            newObject.put("DJ8_dg",DJ8_dg);
            newObject.put("DJ6_dg",DJ6_dg);
            newObject.put("DK8_dg",DK8_dg);
            newObject.put("DK9_dg",DK9_dg);
            newObject.put("DK4_dg",DK4_dg);
            newObject.put("BO1_1",BO1_1);
            newObject.put("BP1",BP1);
            newObject.put("D_1_1",D_1_1);
            newObject.put("BE5_1",BE5_1);
            newObject.put("BS3_1",BS3_1);
            newObject.put("DI1_2",DI1_2);
            newObject.put("DI2_2",DI2_2);
            newObject.put("exercise",exercise);
            newObject.put("HE_ht",HE_ht);
            newObject.put("HE_wt",HE_wt);
            newObject.put("EC_wht_23",EC_wht_23);
            newObject.put("HE_sput2",HE_sput2);
            newObject.put("BS3_2",BS3_2);
            newObject.put("Total_slp_wk",Total_slp_wk);
            newObject.put("Total_slp_wd",Total_slp_wd);
            newObject.put("BD2_1",BD2_1);
            newObject.put("BE3_33",BE3_33);
            newObject.put("bmi",bmi);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(ChangeServeyActivity.this);
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

    public void receiveData() {

        String url = "http://192.168.247.1:1337/chservey_s";
        id = "aa"; // 하드코딩(추후삭제)
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(ChangeServeyActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<org.json.JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resObject = new JSONObject(response.toString());
                    HE_fh = resObject.getString("HE_fh");
                    HE_HPfh = resObject.getString("HE_HPfh");
                    HE_HLfh = resObject.getString("HE_HLfh");
                    HE_IHDfh = resObject.getString("HE_IHDfh");
                    HE_STRfh = resObject.getString("HE_STRfh");
                    HE_HBfh = resObject.getString("HE_HBfh");
                    HE_DMfh = resObject.getString("HE_DMfh");
                    BH2_61 = resObject.getString("BH2_61");
                    DI1_dg = resObject.getString("DI1_dg");
                    DI2_dg = resObject.getString("DI2_dg");
                    DI3_dg = resObject.getString("DI3_dg");
                    DJ4_dg = resObject.getString("DJ4_dg");
                    DI4_dg = resObject.getString("DI4_dg");
                    DJ2_dg = resObject.getString("DJ2_dg");
                    DE1_dg = resObject.getString("DE1_dg");
                    DE1_32 = resObject.getString("DE1_32");
                    DC1_dg = resObject.getString("DC1_dg");
                    DC2_dg = resObject.getString("DC2_dg");
                    DC6_dg = resObject.getString("DC6_dg");
                    DJ8_dg = resObject.getString("DJ8_dg");
                    DJ6_dg = resObject.getString("DJ6_dg");
                    DK8_dg = resObject.getString("DK8_dg");
                    DK9_dg = resObject.getString("DK9_dg");
                    DK4_dg = resObject.getString("DK4_dg");
                    BO1_1 = resObject.getString("BO1_1");
                    BP1 = resObject.getString("BP1");
                    D_1_1 = resObject.getString("D_1_1");
                    BE5_1 = resObject.getString("BE5_1");
                    BS3_1 = resObject.getString("BS3_1");
                    DI1_2 = resObject.getString("DI1_2");
                    DI2_2 = resObject.getString("DI2_2");
                    exercise = resObject.getString("exercise");
                    HE_ht = resObject.getString("HE_ht");
                    HE_wt = resObject.getString("HE_wt");
                    EC_wht_23 = resObject.getString("EC_wht_23");
                    HE_sput2 = resObject.getString("HE_sput2");
                    BS3_2 = resObject.getString("BS3_2");
                    Total_slp_wk = resObject.getString("Total_slp_wk");
                    Total_slp_wd = resObject.getString("Total_slp_wd");
                    BD2_1 = resObject.getString("BD2_1");
                    BE3_33 = resObject.getString("BE3_33");
                    bmi = resObject.getString("bmi");

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
        //RadioGroup
        radio_HE_fh = (RadioGroup) findViewById(R.id.radio_HE_fh);
        radio_HE_HPfh = (RadioGroup) findViewById(R.id.radio_HE_HPfh);
        radio_HE_HLfh = (RadioGroup) findViewById(R.id.radio_HE_HLfh);
        radio_HE_IHDfh = (RadioGroup) findViewById(R.id.radio_HE_IHDfh);
        radio_HE_STRfh = (RadioGroup) findViewById(R.id.radio_HE_STRfh);
        radio_HE_HBfh = (RadioGroup) findViewById(R.id.radio_HE_HBfh);
        radio_HE_DMfh = (RadioGroup) findViewById(R.id.radio_HE_DMfh);
        radio_BH2_61 = (RadioGroup) findViewById(R.id.radio_BH2_61);
        radio_DI1_dg = (RadioGroup) findViewById(R.id.radio_DI1_dg);
        radio_DI2_dg = (RadioGroup) findViewById(R.id.radio_DI2_dg);
        radio_DI3_dg = (RadioGroup) findViewById(R.id.radio_DI3_dg);
        radio_DJ4_dg = (RadioGroup) findViewById(R.id.radio_DJ4_dg);
        radio_DI4_dg = (RadioGroup) findViewById(R.id.radio_DI4_dg);
        radio_DJ2_dg = (RadioGroup) findViewById(R.id.radio_DJ2_dg);
        radio_DE1_dg = (RadioGroup) findViewById(R.id.radio_DE1_dg);
        radio_DE1_32 = (RadioGroup) findViewById(R.id.radio_DE1_32);
        radio_DC1_dg = (RadioGroup) findViewById(R.id.radio_DC1_dg);
        radio_DC2_dg = (RadioGroup) findViewById(R.id.radio_DC2_dg);
        radio_DC6_dg = (RadioGroup) findViewById(R.id.radio_DC6_dg);
        radio_DJ8_dg = (RadioGroup) findViewById(R.id.radio_DJ8_dg);
        radio_DJ6_dg = (RadioGroup) findViewById(R.id.radio_DJ6_dg);
        radio_DK8_dg = (RadioGroup) findViewById(R.id.radio_DK8_dg);
        radio_DK9_dg = (RadioGroup) findViewById(R.id.radio_DK9_dg);
        radio_DK4_dg = (RadioGroup) findViewById(R.id.radio_DK4_dg);

        //EditText
        txt_BS3_2 = findViewById(R.id.txt_BS3_2);
        txt_HE_sput2 = findViewById(R.id.txt_HE_sput2);
        txt_EC_wht_23 = findViewById(R.id.txt_EC_wht_23);
        txt_Total_slp_wk = findViewById(R.id.txt_Total_slp_wk);
        txt_Total_slp_wd = findViewById(R.id.txt_Total_slp_wd);
        txt_HE_ht = findViewById(R.id.txt_HE_ht);
        txt_HE_wt = findViewById(R.id.txt_HE_wt);
        txt_BD2_1 = findViewById(R.id.txt_BD2_1);
        txt_BE3_33 = findViewById(R.id.txt_BE3_33);

        //Spinner
        spin_D_1_1 = findViewById(R.id.spin_D_1_1);
        spin_BO1_1 = findViewById(R.id.spin_BO1_1);
        spin_BS3_1 = findViewById(R.id.spin_BS3_1);
        spin_BE5_1 = findViewById(R.id.spin_BE5_1);
        spin_DI1_2 = findViewById(R.id.spin_DI1_2);
        spin_DI2_2 = findViewById(R.id.spin_DI2_2);
        spin_BP1 = findViewById(R.id.spin_BP1);
        spin_exercise = findViewById(R.id.spin_exercise);

        //Button
        go_main = (Button) findViewById(R.id.go_main);
    }

    public void spinMethod() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data1);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_D_1_1.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data2);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_BO1_1.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data3);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_BS3_1.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data4);
        adapter4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_BE5_1.setAdapter(adapter4);

        ArrayAdapter<String> adapter5 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data5);
        adapter5.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_exercise.setAdapter(adapter5);

        ArrayAdapter<String> adapter6 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data6);
        adapter6.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_DI1_2.setAdapter(adapter6);

        ArrayAdapter<String> adapter7 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data7);
        adapter7.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_DI2_2.setAdapter(adapter7);

        ArrayAdapter<String> adapter8 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data8);
        adapter8.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_BP1.setAdapter(adapter8);
    }

    //EditText > String
    public void textToString() {
        HE_ht = txt_HE_ht.getText().toString(); //키
        HE_wt = txt_HE_wt.getText().toString(); //몸무게
        EC_wht_23 = txt_EC_wht_23.getText().toString(); //주당 평균 근로 시간
        HE_sput2 = txt_HE_sput2.getText().toString(); //연속 3개월 이상 가래 경험
        BS3_2 = txt_BS3_2.getText().toString(); //하루 평균 흡연량
        Total_slp_wk = txt_Total_slp_wk.getText().toString(); //주중 하루 평균 수면시간
        Total_slp_wd = txt_Total_slp_wd.getText().toString(); //주말 하루 평균 수면 시간
        BD2_1 = txt_BD2_1.getText().toString(); //음주량
        BE3_33 = txt_BE3_33.getText().toString(); //걷기 지속시간

        //bmi계산
        if (!HE_ht.equals("") || !HE_wt.equals("")) {
            bmi = String.valueOf(Integer.parseInt(HE_ht) * Integer.parseInt(HE_ht) / Integer.parseInt(HE_wt)); //BMI
        }

        Log.v("EditText 값", HE_ht + "," + HE_wt + "," + EC_wht_23 + "," + HE_sput2 + "," + "걷기 지속 시간: " + BE3_33 + "," + BS3_2 + "," + "음주량: " + BD2_1 + "," + Total_slp_wk + "," + Total_slp_wd);
        Log.v("BMI ", bmi + "");
    }

    //RadioButton > String
    public void radioToString() {
        //RadioGroup > RadioButton > String
        HE_fh = ((RadioButton) findViewById(radio_HE_fh.getCheckedRadioButtonId())).getText().toString();//만성질환
        HE_HPfh = ((RadioButton) findViewById(radio_HE_HPfh.getCheckedRadioButtonId())).getText().toString();//고혈압 가족력
        HE_HLfh = ((RadioButton) findViewById(radio_HE_HLfh.getCheckedRadioButtonId())).getText().toString();//고지혈증 가족력
        HE_IHDfh = ((RadioButton) findViewById(radio_HE_IHDfh.getCheckedRadioButtonId())).getText().toString();//허혈성심장질환 가족력
        HE_STRfh = ((RadioButton) findViewById(radio_HE_STRfh.getCheckedRadioButtonId())).getText().toString();//뇌졸중 가족력
        HE_HBfh = ((RadioButton) findViewById(radio_HE_HBfh.getCheckedRadioButtonId())).getText().toString();//b형간염 가족력
        HE_DMfh = ((RadioButton) findViewById(radio_HE_DMfh.getCheckedRadioButtonId())).getText().toString();//당뇨병 가족력
        BH2_61 = ((RadioButton) findViewById(radio_BH2_61.getCheckedRadioButtonId())).getText().toString();//2년간 암검진 여부
        DI1_dg = ((RadioButton) findViewById(radio_DI1_dg.getCheckedRadioButtonId())).getText().toString();//고혈압 의사진단 여부
        DI2_dg = ((RadioButton) findViewById(radio_DI2_dg.getCheckedRadioButtonId())).getText().toString();//이상지질혈증 의사진단 여부
        DI3_dg = ((RadioButton) findViewById(radio_DI3_dg.getCheckedRadioButtonId())).getText().toString();//뇌졸중 의사진단 여부
        DJ4_dg = ((RadioButton) findViewById(radio_DJ4_dg.getCheckedRadioButtonId())).getText().toString();//천식 의사진단 여부
        DI4_dg = ((RadioButton) findViewById(radio_DI4_dg.getCheckedRadioButtonId())).getText().toString();//심근경색증 또는 협심증
        DJ2_dg = ((RadioButton) findViewById(radio_DJ2_dg.getCheckedRadioButtonId())).getText().toString();//폐결핵 의사진단 여부
        DE1_dg = ((RadioButton) findViewById(radio_DE1_dg.getCheckedRadioButtonId())).getText().toString();//당뇨병 의사진단 여부
        DE1_32 = ((RadioButton) findViewById(radio_DE1_32.getCheckedRadioButtonId())).getText().toString();//당뇨병혈당관리치료
        DC1_dg = ((RadioButton) findViewById(radio_DC1_dg.getCheckedRadioButtonId())).getText().toString();//위암 의사진단 여부
        DC2_dg = ((RadioButton) findViewById(radio_DC2_dg.getCheckedRadioButtonId())).getText().toString();//간암 의사진단 여부
        DC6_dg = ((RadioButton) findViewById(radio_DC6_dg.getCheckedRadioButtonId())).getText().toString();//폐암 의사진단 여부
        DJ8_dg = ((RadioButton) findViewById(radio_DJ8_dg.getCheckedRadioButtonId())).getText().toString();//알레르기비염 의사진단 여부
        DJ6_dg = ((RadioButton) findViewById(radio_DJ6_dg.getCheckedRadioButtonId())).getText().toString();//부비동염 의사진단 여부
        DK8_dg = ((RadioButton) findViewById(radio_DK8_dg.getCheckedRadioButtonId())).getText().toString();//B형간염 의사진단 여부
        DK9_dg = ((RadioButton) findViewById(radio_DK9_dg.getCheckedRadioButtonId())).getText().toString();//C형간염 의사진단 여부
        DK4_dg = ((RadioButton) findViewById(radio_DK4_dg.getCheckedRadioButtonId())).getText().toString();//간경변증 의사진단 여부

        //"0","1"로 바꾸기
        String[] str = {HE_fh, HE_HPfh, HE_HLfh, HE_IHDfh, HE_STRfh, HE_HBfh, HE_DMfh, BH2_61,
                DI1_dg, DI2_dg, DI3_dg, DJ4_dg, DI4_dg, DJ2_dg, DE1_dg, DE1_32, DC1_dg, DC2_dg,
                DC6_dg, DJ8_dg, DJ6_dg, DK8_dg, DK9_dg, DK4_dg};

        for (int i = 0; i < 24; i++) {
            String result = radioCheck(str[i]);
            switch (i) {
                case 0:
                    HE_fh = result;
                    break;
                case 1:
                    HE_HPfh = result;
                    break;
                case 2:
                    HE_HLfh = result;
                    break;
                case 3:
                    HE_IHDfh = result;
                    break;
                case 4:
                    HE_STRfh = result;
                    break;
                case 5:
                    HE_HBfh = result;
                    break;
                case 6:
                    HE_DMfh = result;
                    break;
                case 7:
                    BH2_61 = result;
                    break;
                case 8:
                    DI1_dg = result;
                    break;
                case 9:
                    DI2_dg = result;
                    break;
                case 10:
                    DI3_dg = result;
                    break;
                case 11:
                    DJ4_dg = result;
                    break;
                case 12:
                    DI4_dg = result;
                    break;
                case 13:
                    DJ2_dg = result;
                    break;
                case 14:
                    DE1_dg = result;
                    break;
                case 15:
                    DE1_32 = result;
                    break;
                case 16:
                    DC1_dg = result;
                    break;
                case 17:
                    DC2_dg = result;
                    break;
                case 18:
                    DC6_dg = result;
                    break;
                case 19:
                    DJ8_dg = result;
                    break;
                case 20:
                    DJ6_dg = result;
                    break;
                case 21:
                    DK8_dg = result;
                    break;
                case 22:
                    DK9_dg = result;
                    break;
                case 23:
                    DK4_dg = result;
                    break;
                default:
                    break;
            }
        }

        Log.v("라디오버튼 값", HE_fh + "," + HE_HPfh + "," + HE_HLfh + "," + HE_IHDfh + "," + HE_STRfh + "," + HE_HBfh + "," + HE_DMfh + "," + BH2_61 + ","
                + DI1_dg + "," + DI2_dg + "," + DI3_dg + "," + DJ4_dg + "," + DI4_dg + "," + DJ2_dg + "," + DE1_dg + "," + DE1_32 + ","
                + DC1_dg + "," + DC2_dg + "," + DC6_dg + "," + DJ8_dg + "," + DJ6_dg + "," + DK8_dg + "," + DK9_dg + "," + DK4_dg);
    }

    public String radioCheck(String e) {
        String result = "";

        if (e.equals("예")) {
            result = "1";
        } else if (e.equals("아니오")) {
            result = "0";
        } else if (e.equals("비해당")) {
            result = "8";
        } else {
            Log.v("오류", "....Error!");
        }
        return result;
    }

    //Spinner > String
    public void spinToString() {
        //Spinner > String
        BO1_1 = spin_BO1_1.getSelectedItem().toString();//1년간 최중변화 여부
        BP1 = spin_BP1.getSelectedItem().toString();//평소 스트레스 인지 정도
        D_1_1 = spin_D_1_1.getSelectedItem().toString(); //주관적 건강 상태
        BE5_1 = spin_BE5_1.getSelectedItem().toString();//1주일간 근력운동 일수
        BS3_1 = spin_BS3_1.getSelectedItem().toString();//현재 흡연 여부
        DI1_2 = spin_DI1_2.getSelectedItem().toString();//혈압조절제 복용
        DI2_2 = spin_DI2_2.getSelectedItem().toString();//이상지질혈증 약복용
        exercise = spin_exercise.getSelectedItem().toString();//평균 운동 정도

        //"0","1"로 바꾸기
        String[] str = {BO1_1, BP1, D_1_1, BE5_1, BS3_1, DI1_2, DI2_2, exercise};
        for (int i = 0; i < 8; i++) {
            String result = Character.toString(str[i].charAt(0));
            switch (i) {
                case 0:
                    BO1_1 = result;
                    break;
                case 1:
                    BP1 = result;
                    break;
                case 2:
                    D_1_1 = result;
                    break;
                case 3:
                    BE5_1 = result;
                    break;
                case 4:
                    BS3_1 = result;
                    break;
                case 5:
                    DI1_2 = result;
                    break;
                case 6:
                    DI2_2 = result;
                    break;
                case 7:
                    exercise = result;
                    break;
                default:
                    break;
            }
        }
        Log.v("스피너 값", BO1_1 + "," + BP1 + "," + D_1_1 + "," + BE5_1 + "," + "평균운동정도: " + exercise + "," + BS3_1 + "," + DI1_2 + "," + DI2_2);
    }
}
