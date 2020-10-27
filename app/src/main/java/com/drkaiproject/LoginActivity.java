package com.drkaiproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity {
    ConstraintLayout login_btn, join_btn;
    JSONObject jsonObject;
    EditText user_id, user_pw;
    String id,pw,user_name, user_area, user_check;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        join_btn = findViewById(R.id.join_btn);
        login_btn = findViewById(R.id.login_btn);

        user_id = findViewById(R.id.user_id);
        user_pw = findViewById(R.id.user_pw);

        join_btn.setClickable(true); //클릭할 수 있게
        login_btn.setClickable(true); //클릭할 수 있게
        join_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
                startActivity(intent);
            }
        });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonObject = new JSONObject();
                id = user_id.getText().toString(); //아이디
                pw = user_pw.getText().toString(); //비밀번호
                try {
                    jsonObject.put("id",id);
                    jsonObject.put("pw",pw);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), jsonObject.toString(), Toast.LENGTH_LONG).show();
                sendData();
                // 로그인 검증-------------------------------------------------------
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                jsonObject.remove("pw");
                intent.putExtra("account",jsonObject.toString());
                startActivity(intent);
            }
        });
    }

    public void receiveValueFromServer(){

            String url = "http://192.168.247.1:1337/shared_s";

            id = user_id.getText().toString();
            jsonObject = new JSONObject();
            try {
                jsonObject.put("id", id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<org.json.JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject resObject = new JSONObject(response.toString());
                        user_name = resObject.getString("name");
                        user_area = resObject.getString("area");
                        user_check = resObject.getString("check");

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

            //sharedpreference 가져오기, 처음 만들경우 defValue 값으로 저장
            SharedPreferences sp = getSharedPreferences("user", MODE_PRIVATE);
            String user_id = sp.getString("id",id); // user_id_edittext.getString().toString(); 값을 null 자리에 넣기
            String name = sp.getString("name", user_name); // 서버로 부터 전달받아서 null 자리에 넣기
            String area = sp.getString("area", user_area); //서버로 부터 전달받아서 "서울" 자리에 넣기
            String check = sp.getString("check", user_check); //디폴트 N

        //저장한 값 체크
        Log.i("error_test", "user_id is " + user_id);
        Log.i("error_test", "name is " + name);
        Log.i("error_test", "area is " + area);
        Log.i("error_test", "check is " + check);
    }

    public String areaNumber(String area){
        String[] str_are = {"강원도(영서)", "강원도(영동)", "서울", "인천", "경기", "충북", "대전/충남", "대구/경북", "전북", "울산", "경남", "광주", "부산",
                "전남", "제주", "서귀포"};
        for(int i=0; i<16; i++){
            if(area.equals(str_are[i])) {
                return Integer.toString(i);
            }
        }
        return  null;
    }

    public void sendData(){
        //String url = "https://nodejs105.azurewebsites.net";
        String url = "http://192.168.247.1:1337/login";


        final RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<org.json.JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    String resultId = jsonObject.getString("approve_id");
                    String resultPassword = jsonObject.getString("approve_pw");

                    if(resultId.equals("OK") & resultPassword.equals("OK")){
                        Toast.makeText(getApplicationContext(), "OK!", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(), "False!", Toast.LENGTH_SHORT).show();
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
}
