package com.drkaiproject;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

public class ChangeMemberInfoActivity extends AppCompatActivity {

    String id, pw_new, phone, area;

    Button edit_btn;
    EditText user_phone, txt_past_pw, txt_new_pw, txt_new_pw1;
    Spinner spinner_area;


    private String[] areaData = {"강원도(영서)", "강원도(영동)", "서울", "인천", "경기", "충북", "대전/충남", "대구/경북", "전북", "울산", "경남", "광주", "부산",
            "전남", "제주", "서귀포"};


    JSONObject jsonObject;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_member_ship);

        user_phone = findViewById(R.id.user_phone);
        txt_past_pw = findViewById(R.id.user_pw);
        txt_new_pw = findViewById(R.id.txt_new_pw);
        txt_new_pw1 = findViewById(R.id.txt_new_pw1);
        spinner_area = (Spinner) findViewById(R.id.spinner_area);
        edit_btn = findViewById(R.id.edit_btn);

        ArrayAdapter<String> areaAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, areaData);
        areaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_area.setAdapter(areaAdapter);

        //sharedpreferenced 사용자 고유값 임의로 생성
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        id = auto.getString("user_id", null);

        //버튼 클릭 시
        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //1. db에서 비밀번호 가져와서 txt_past_pw와 비교해서 같으면 다음 실행, 다르면 toast

                //2. 새로 입력한 비밀번호 두개가 같으면 다른 데이터도 db에 저장, 다르면 toast
                if (txt_new_pw.getText().toString().equals(txt_new_pw1.getText().toString())) {
                    phone = user_phone.getText().toString();
                    area = spinner_area.getSelectedItem().toString();
                    spinnerToNum();
                    pw_new = txt_new_pw1.getText().toString();
                    //3. db에 update
                    //sendData();
                }
            }
        });
    }

    /*
    public void sendData(){
        //String url = "https://nodejs105.azurewebsites.net";
        String url = "http://192.168.247.1:1337/chmem";

        jsonObject = new JSONObject();
        try {
            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
            id = sp.getString("id", null);            jsonObject.put("id",id);
            jsonObject.put("phone",phone);
            jsonObject.put("area",area);
            jsonObject.put("pw_new",pw_new);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final RequestQueue requestQueue = Volley.newRequestQueue(ChangeMemberInfoActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<org.json.JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
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

     */

    //지역 ===> 0~15 계산
    public void spinnerToNum() {
        for (int i = 0; i < areaData.length; i++) {
            if (area.equals(areaData[i])) {
                area = Integer.toString(i);
                break;
            }
        }
    }
}

