package com.drkaiproject.menu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.drkaiproject.Constants;
import com.drkaiproject.R;

import org.json.JSONException;
import org.json.JSONObject;

public class UserInfoConf1 extends AppCompatActivity {

    private String str;
    JSONObject jsonObject, resObject;
    String id;
    Button go_userinfoconf2;

    // 43개
    TextView info_HE_fh, info_HE_HPfh, info_HE_HLfh, info_HE_IHDfh, info_HE_STRfh, info_HE_HBfh, info_HE_DMfh, info_BH2_61,
            info_DI1_dg, info_DI2_dg, info_DI3_dg, info_DJ4_dg, info_DI4_dg, info_DJ2_dg, info_DE1_dg, info_DE1_32, info_DC1_dg,
            info_DC2_dg, info_DC6_dg, info_DJ8_dg, info_DJ6_dg, info_DK8_dg, info_DK9_dg, info_DK4_dg, info_drinking_5y,
            info_BS3_2, info_HE_sput2, info_EC_wht_23, info_Total_slp_wk, info_Total_slp_wd, info_HE_ht, info_HE_wt,
            info_BD2_1, info_BE3_33, info_D_1_1, info_BO1_1, info_BS3_1, info_BE5_1, info_DI1_2, info_DI2_2, info_BP1, info_exercise, info_depSum;


    String[] data1 = {
            "1. 매우좋음", "2. 좋음", "3. 보통", "4. 나쁨", "5. 매우나쁨"
    };

    String[] data2 = {
            "1. 변화없음", "2. 체중감소", "3. 증가"
    };
    String[] data3 = { // 현재 흡연
            "1. 매일", "2. 가끔", "3. 과거에피웠으나현재안피움", "4. 피운적이 없음"
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_userinfoconf1);

        Intent intent = getIntent();
        str = intent.getExtras().getString("account");

        try {
            jsonObject = new JSONObject(str);
            id = jsonObject.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        findMethod();

        go_userinfoconf2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    jsonObject.put("FPG", resObject.getString("AFP"));
                    jsonObject.put("TG", resObject.getString("TG"));
                    jsonObject.put("leukocyte", resObject.getString("leukocyte"));
                    jsonObject.put("total_colesterol", resObject.getString("total_colesterol"));
                    jsonObject.put("HDL", resObject.getString("HDL"));
                    jsonObject.put("LDL", resObject.getString("LDL"));
                    jsonObject.put("HbA", resObject.getString("HbA"));
                    jsonObject.put("SBP", resObject.getString("SBP"));
                    jsonObject.put("DBP", resObject.getString("DBP"));
                    jsonObject.put("is_atrialFibrillation", resObject.getString("is_atrialFibrillation"));
                    jsonObject.put("PT_INR", resObject.getString("PT_INR"));
                    jsonObject.put("bilirubin", resObject.getString("bilirubin"));
                    jsonObject.put("creatinine", resObject.getString("creatinine"));
                    jsonObject.put("ammonia", resObject.getString("ammonia"));
                    jsonObject.put("AFP", resObject.getString("AFP"));
                    jsonObject.put("albumin", resObject.getString("albumin"));
                    jsonObject.put("platelet", resObject.getString("platelet"));
                    jsonObject.put("DLD_serve", resObject.getString("DLD_serve"));
                    jsonObject.put("is_hypercholesterolemia", resObject.getString("is_hypercholesterolemia"));
                    jsonObject.put("is_chemicHeartDisease", resObject.getString("is_chemicHeartDisease"));
                    jsonObject.put("history_cancer", resObject.getString("history_cancer"));
                    jsonObject.put("meal_reg", resObject.getString("meal_reg"));
                    jsonObject.put("salt_pref", resObject.getString("salt_pref"));
                }catch (JSONException e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(UserInfoConf1.this, UserInfoConf2.class);
                intent.putExtra("account", jsonObject.toString());
                startActivity(intent);
            }
        });

        receiveAlldata();





    }

    public void receiveAlldata() {
         String url = Constants.SERVER_URL + "/alldata_s";

        jsonObject = new JSONObject();
        try {
            id="1";
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    resObject = new JSONObject(response.toString());
                    Log.d("receiveAlldata", resObject.toString());
                    if (!resObject.getString("result").equals("1")) throw new Exception();
                    else {
                        resObject = resObject.getJSONObject("data");
                        setMethod();

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


    public void findMethod(){

        //Radio Group to TextView
        info_HE_fh = findViewById(R.id.info_HE_fh);
        info_HE_HPfh = findViewById(R.id.info_HE_HPfh);
        info_HE_HLfh = findViewById(R.id.info_HE_HLfh);
        info_HE_IHDfh = findViewById(R.id.info_HE_IHDfh);
        info_HE_STRfh = findViewById(R.id.info_HE_STRfh);
        info_HE_HBfh = findViewById(R.id.info_HE_HBfh);
        info_HE_DMfh = findViewById(R.id.info_HE_DMfh);
        info_BH2_61 = findViewById(R.id.info_BH2_61);
        info_DI1_dg = findViewById(R.id.info_DI1_dg);
        info_DI2_dg = findViewById(R.id.info_DI2_dg);
        info_DI3_dg = findViewById(R.id.info_DI3_dg);
        info_DJ4_dg = findViewById(R.id.info_DJ4_dg);
        info_DI4_dg = findViewById(R.id.info_DI4_dg);
        info_DJ2_dg = findViewById(R.id.info_DJ2_dg);
        info_DE1_dg = findViewById(R.id.info_DE1_dg);
        info_DE1_32 = findViewById(R.id.info_DE1_32);
        info_DC1_dg = findViewById(R.id.info_DC1_dg);
        info_DC2_dg = findViewById(R.id.info_DC2_dg);
        info_DC6_dg = findViewById(R.id.info_DC6_dg);
        info_DJ8_dg = findViewById(R.id.info_DJ8_dg);
        info_DJ6_dg = findViewById(R.id.info_DJ6_dg);
        info_DK8_dg = findViewById(R.id.info_DK8_dg);
        info_DK9_dg = findViewById(R.id.info_DK9_dg);
        info_DK4_dg = findViewById(R.id.info_DK4_dg);
        info_drinking_5y = findViewById(R.id.info_drinking_5y);

        //EditText to TextView
        info_BS3_2 = findViewById(R.id.info_BS3_2);
        info_HE_sput2 = findViewById(R.id.info_HE_sput2);
        info_EC_wht_23 = findViewById(R.id.info_EC_wht_23);
        info_Total_slp_wk = findViewById(R.id.info_Total_slp_wk);
        info_Total_slp_wd = findViewById(R.id.info_Total_slp_wd);
        info_HE_ht = findViewById(R.id.info_HE_ht);
        info_HE_wt = findViewById(R.id.info_HE_wt);
        info_BD2_1 = findViewById(R.id.info_BD2_1);
        info_BE3_33 = findViewById(R.id.info_BE3_33);

        //Spinner to TextView
        info_D_1_1 = findViewById(R.id.info_D_1_1);
        info_BO1_1 = findViewById(R.id.info_BO1_1);
        info_BS3_1 = findViewById(R.id.info_BS3_1);
        info_BE5_1 = findViewById(R.id.info_BE5_1);
        info_DI1_2 = findViewById(R.id.info_DI1_2);
        info_DI2_2 = findViewById(R.id.info_DI2_2);
        info_BP1 = findViewById(R.id.info_BP1);
        info_exercise = findViewById(R.id.info_exercise);

        info_depSum = findViewById(R.id.info_depSum);

        //Button
        go_userinfoconf2 = (Button) findViewById(R.id.go_userinfoconf2);
    }

    public void setMethod(){

        //Radio Group to TextView
        try {
            info_HE_fh.setText(resObject.getString("HE_fh").equals("1")? "예":"아니오");
            info_HE_HPfh.setText(resObject.getString("HE_HPfh").equals("1")? "예":"아니오");
            info_HE_HLfh.setText(resObject.getString("HE_HLfh").equals("1")? "예":"아니오");
            info_HE_IHDfh.setText(resObject.getString("HE_IHDfh").equals("1")? "예":"아니오");
            info_HE_STRfh.setText(resObject.getString("HE_STRfh").equals("1")? "예":"아니오");
            info_HE_HBfh.setText(resObject.getString("HE_HBfh").equals("1")? "예":"아니오");
            info_HE_DMfh.setText(resObject.getString("HE_DMfh").equals("1")? "예":"아니오");
            info_BH2_61.setText(resObject.getString("BH2_61").equals("1")? "예":"아니오");
            info_DI1_dg.setText(resObject.getString("DI1_dg").equals("1")? "예":"아니오");
            info_DI2_dg.setText(resObject.getString("DI2_dg").equals("1")? "예":"아니오");
            info_DI3_dg.setText(resObject.getString("DI3_dg").equals("1")? "예":"아니오");
            info_DJ4_dg.setText(resObject.getString("DJ4_dg").equals("1")? "예":"아니오");
            info_DI4_dg.setText(resObject.getString("DI4_dg").equals("1")? "예":"아니오");
            info_DJ2_dg.setText(resObject.getString("DJ2_dg").equals("1")? "예":"아니오");
            info_DE1_dg.setText(resObject.getString("DE1_dg").equals("1")? "예":"아니오");
            info_DE1_32.setText(resObject.getString("DE1_32").equals("1")? "예":"아니오");
            info_DC1_dg.setText(resObject.getString("DC1_dg").equals("1")? "예":"아니오");
            info_DC2_dg.setText(resObject.getString("DC2_dg").equals("1")? "예":"아니오");
            info_DC6_dg.setText(resObject.getString("DC6_dg").equals("1")? "예":"아니오");
            info_DJ8_dg.setText(resObject.getString("DJ8_dg").equals("1")? "예":"아니오");
            info_DJ6_dg.setText(resObject.getString("DJ6_dg").equals("1")? "예":"아니오");
            info_DK8_dg.setText(resObject.getString("DK8_dg").equals("1")? "예":"아니오");
            info_DK9_dg.setText(resObject.getString("DK9_dg").equals("1")? "예":"아니오");
            info_DK4_dg.setText(resObject.getString("DK4_dg").equals("1")? "예":"아니오");
            info_drinking_5y.setText(resObject.getString("dr_5y").equals("1")? "예":"아니오");

            //EditText to TextView
            info_BS3_2.setText(resObject.getString("BS3_2"));
            info_HE_sput2.setText(resObject.getString("HE_sput2"));
            info_EC_wht_23.setText(resObject.getString("EC_wht_23"));
            info_Total_slp_wk.setText(resObject.getString("Total_slp_wk"));
            info_Total_slp_wd.setText(resObject.getString("Total_slp_wd"));
            info_HE_ht.setText(resObject.getString("HE_ht"));
            info_HE_wt.setText(resObject.getString("HE_wt"));
            info_BD2_1.setText(resObject.getString("BD2_1"));
            info_BE3_33.setText(resObject.getString("BE3_33"));

            //Spinner to TextView
            info_D_1_1.setText(data1[Integer.parseInt(resObject.getString("D_1_1"))-1]);
            info_BO1_1.setText(data2[Integer.parseInt(resObject.getString("BO1_1"))-1]);
            info_BS3_1.setText(data3[Integer.parseInt(resObject.getString("BP1"))-1]);
            info_BE5_1.setText(data4[Integer.parseInt(resObject.getString("BE5_1"))-1]);
            info_DI1_2.setText(data6[Integer.parseInt(resObject.getString("DI1_2"))-1]);
            info_DI2_2.setText(data7[Integer.parseInt(resObject.getString("DI1_2"))-1]);
            info_BP1.setText(data8[Integer.parseInt(resObject.getString("BP1"))-1]);
            info_exercise.setText(data5[Integer.parseInt(resObject.getString("exercise"))-1]);

            info_depSum.setText("양호");

        } catch (JSONException e) {
        e.printStackTrace();
    }
    }

}
