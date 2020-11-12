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
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.drkaiproject.fragment.ChatbotFragment;
import com.drkaiproject.fragment.DiagFragment;
import com.drkaiproject.fragment.HLTCareFragment;
import com.drkaiproject.fragment.MainFragment;
import com.drkaiproject.fragment.RSVTConfFragment;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;


public class LoginActivity extends AppCompatActivity {
    ConstraintLayout login_btn, join_btn;
    EditText user_id, user_pw;
    String id,pw,user_name, user_area, user_check;
    JSONObject loginObject , loginResp;
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
                loginObject = new JSONObject();
                id = user_id.getText().toString(); //아이디
                pw = user_pw.getText().toString(); //비밀번호
                try {
                    loginObject.put("id",id);
                    loginObject.put("pw",pw);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Log.v("/login send : ",loginObject.toString());

                Thread threadA = new Thread() {
                    public void run() {
                        loginResp = null;

                        try {
                            Constants.VolleySync threadB1 = new Constants.VolleySync(getApplicationContext());
                            loginResp = threadB1.execute(Constants.SERVER_URL + "/login", loginObject.toString()).get();
                        } catch (InterruptedException | ExecutionException e) {
                            e.printStackTrace();
                        }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (loginResp == null) new Exception();
                                        else {
                                            Log.d("loginResp", loginResp.toString());
                                            int ret = 0;

                                            ret = Integer.parseInt(loginResp.getString("result"));

                                            if (ret == 1) {
                                                JSONObject login_data = new JSONObject(loginResp.getString("data"));
                                                JSONObject toMain = new JSONObject();
                                                toMain.put("id", id);
                                                toMain.put("name", login_data.getString("name"));
                                                toMain.put("gender", login_data.getString("gender"));
                                                toMain.put("area", login_data.getString("area"));
                                                toMain.put("age", login_data.getString("age"));
                                                toMain.put("phone", login_data.getString("phone"));
                                                toMain.put("checked", login_data.getString("phone"));
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.putExtra("user", toMain.toString());
                                                startActivity(intent);

                                            } else if (ret == -1) {
                                                Toast.makeText(getApplicationContext(), "아이디/비밀번호를 확인해주세요.", Toast.LENGTH_LONG);
                                            } else if (ret == -2) {
                                                Toast.makeText(getApplicationContext(), "아이디/비밀번호 불일치 5회 초과로 로그인이 불가합니다.", Toast.LENGTH_LONG);
                                            }
                                        }
                                    }catch(Exception e){
                                        e.printStackTrace();
                                    }
                                }
                            });
                    }
                };
                threadA.start();
            }
        });
    }
}
