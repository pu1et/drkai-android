package com.drkaiproject;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {
    public static int REQUEST_CODE = 99;
    Button go_servey_start, check_id;
    EditText user_id, user_pw1, user_pw, user_name, user_age, user_phone;
    Spinner spin_area;
    RadioGroup user_gender;
    CheckBox level1, level2, level3, level4;

    String id, pw, name, gender, area;
    String age, phone, carstairs_level;
    RadioButton gender_value;

    private String[] areaData = {"강원도(영서)", "강원도(영동)", "서울", "인천", "경기", "충북", "대전/충남", "대구/경북", "전북", "울산", "경남", "광주", "부산",
            "전남", "제주", "서귀포"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        user_id = findViewById(R.id.user_id);
        user_pw = findViewById(R.id.user_pw);
        user_name = findViewById(R.id.user_name);
        user_age = findViewById(R.id.user_age);
        spin_area = (Spinner) findViewById(R.id.spin_area);
        user_gender = findViewById(R.id.user_gender);
        user_phone = findViewById(R.id.user_phone);
        level1 = findViewById(R.id.level1);
        level2 = findViewById(R.id.level2);
        level3 = findViewById(R.id.level3);
        level4 = findViewById(R.id.level4);

        check_id = findViewById(R.id.button1);
        check_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("error_test", "this is click event");
            }
        });

/*
        user_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = user_id.getText().toString();
                check(text, "id");
            }
        });

        user_pw1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = user_pw1.getText().toString();
                check(text, "pwd");
            }
        });

        user_pw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = user_pw.getText().toString();
                if(user_pw1.getText().toString().equals(text))
                    Toast.makeText(JoinActivity.this, "pwd is same", Toast.LENGTH_SHORT).show();
            }
        });
        */

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, areaData);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_area.setAdapter(adapter1);

        gender_value = (RadioButton) findViewById(user_gender.getCheckedRadioButtonId());
        go_servey_start = findViewById(R.id.go_servey_start);

        //회원정보 입력 완료, 서버 송신
        go_servey_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calCarstairs();
                id = user_id.getText().toString(); //아이디

                pw = user_pw.getText().toString(); //비밀번호
                name = user_name.getText().toString(); //이름
                area = spin_area.getSelectedItem().toString(); //사는 지역
                spinnerToNum();
                age = user_age.getText().toString(); //나이
                phone = user_phone.getText().toString(); //번호
                if (gender_value.getText().toString() == "남") gender = "1";
                else gender = "0";
                login(LoginType.PHONE);
            }
        });
    }

    public void check(String text, String flag){

        if(flag.equals("id")){
            if(text.length()>5){
                // 영문만 허용 (숫자 포함)
                if(!Pattern.matches("^[a-zA-Z]{1,28}[0-9][0-9a-zA-Z]{0,28}$", text)){
                    user_id.setTextColor(Color.RED);
                }else{
                    user_id.setTextColor(Color.BLACK);
                    check_id.setEnabled(true);
                }
            }else{
                user_id.setTextColor(Color.RED);
            }

        }else{
            if(text.length()>10){
                // 영문만 허용 (숫자 포함)
                if(!Pattern.matches("^[a-zA-Z]{1,28}[0-9][0-9a-zA-Z]{0,28}$", text)){
                    user_id.setTextColor(Color.RED);
                }else{
                    user_id.setTextColor(Color.BLACK);
                }
            }else{
                user_id.setTextColor(Color.RED);
            }
        }
    }

    public void btnMethod(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button:
                //Intent intent1 = new Intent(JoinActivity.this, informationActivity.class);
                //startActivity(intent1);
                break;
            case R.id.button1:
                //아이디 검증
                break;
            case R.id.button2:
               // Intent intent2 = new Intent(JoinActivity.this, information2Activity.class);
               // startActivity(intent2);
                break;
        }
    }

    //핸드폰 인증
    private void login(LoginType loginType) {
        final Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        loginType,
                        AccountKitActivity.ResponseType.CODE);
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, REQUEST_CODE);
    }

    //핸드폰 인증 후
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = loginResult.getError().getErrorType().getMessage();
            } else {
                toastMessage = "회원가입에 성공하셨습니다";
                goToProfileInActivity();
            }
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
        }
    }

    private void goToProfileInActivity() {

        Intent intent = new Intent(JoinActivity.this, ServeyStart.class);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id",id);
            jsonObject.put("pw",pw);
            jsonObject.put("name",name);
            jsonObject.put("gender",gender);
            jsonObject.put("area",area);
            jsonObject.put("age",age);
            jsonObject.put("phone",phone);
            jsonObject.put("carstairs",carstairs_level);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        intent.putExtra("account",jsonObject.toString());
        startActivityForResult(intent, 1);
    }
    public void calCarstairs() {
        int score = 1;
        if (level1.isChecked()) {
            score++;
        }
        if (level2.isChecked()) {
            score++;
        }
        if (level3.isChecked()) {
            score++;
        }
        if (level4.isChecked()) {
            score++;
        }
        carstairs_level = String.valueOf(score);
        Log.v("결핍지수: ",carstairs_level);
    }

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
