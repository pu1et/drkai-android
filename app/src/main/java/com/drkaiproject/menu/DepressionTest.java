package com.drkaiproject.menu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Spinner;

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
import com.drkaiproject.sqliteHelper.SqliteFunction;

import org.json.JSONException;
import org.json.JSONObject;

public class DepressionTest extends AppCompatActivity {

    private int depSum;

    RadioButton radio1, radio2;
    Spinner depSpin01, depSpin02, depSpin03, depSpin04, depSpin05, depSpin06, depSpin07, depSpin08, depSpin09, depSpin10,
            depSpin11, depSpin12, depSpin13, depSpin14, depSpin15, depSpin16, depSpin17;
    RadioButton check_A, check_B;
    JSONObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deptest);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);

        //16번 문항 A,B
        check_A = findViewById(R.id.check_A);
        check_B = findViewById(R.id.check_B);

        findMethod();
        spinMethod();
    }

    public int calRisk(double prob){
        if (prob < 0.2) return 1;
        else if (prob >= 0.2 && prob < 0.4) return 2;
        else if (prob >= 0.4) return 3;
        else return 0;
    }

    //버튼
    public synchronized void btnMethod(View view) {
        depSum = sumDepression(); //우울증 합 추출
        update_depSum();
    }

    public synchronized void update_depSum(){
        String url =  Constants.SERVER_URL + "/user/data";

        try {
            jsonObject = new JSONObject();
            jsonObject.put("id", SqliteFunction.id);
            jsonObject.put("table","disease_all");
            jsonObject.put("column_arr","depression"); // disease_all의 우울증 컬럼
            jsonObject.put("value_arr",""+depSum);
        }catch (JSONException e){
            e.printStackTrace();
        }

        Log.i("depTest_update",jsonObject.toString());
        final RequestQueue requestQueue = Volley.newRequestQueue(DepressionTest.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,jsonObject, new Response.Listener<org.json.JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    final JSONObject res = response;
                    Log.d("update_data_response", res.toString());
                    if (!res.getString("result").equals("1")) throw new Exception();
                    else {
                        Intent intent = new Intent(DepressionTest.this, PreDiagResult.class);
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
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    public void findMethod() {
        //스피너
        depSpin01 = (Spinner) findViewById(R.id.depSpin01);
        depSpin02 = (Spinner) findViewById(R.id.depSpin02);
        depSpin03 = (Spinner) findViewById(R.id.depSpin03);
        depSpin04 = (Spinner) findViewById(R.id.depSpin04);
        depSpin05 = (Spinner) findViewById(R.id.depSpin05);
        depSpin06 = (Spinner) findViewById(R.id.depSpin06);
        depSpin07 = (Spinner) findViewById(R.id.depSpin07);
        depSpin08 = (Spinner) findViewById(R.id.depSpin08);
        depSpin09 = (Spinner) findViewById(R.id.depSpin09);
        depSpin10 = (Spinner) findViewById(R.id.depSpin10);
        depSpin11 = (Spinner) findViewById(R.id.depSpin11);
        depSpin12 = (Spinner) findViewById(R.id.depSpin12);
        depSpin13 = (Spinner) findViewById(R.id.depSpin13);
        depSpin14 = (Spinner) findViewById(R.id.depSpin14);
        depSpin15 = (Spinner) findViewById(R.id.depSpin15);
        depSpin16 = (Spinner) findViewById(R.id.depSpin16);
        depSpin17 = (Spinner) findViewById(R.id.depSpin17);
    }

    public void spinMethod() {

        String[] depSpinData01 = {"0. 없다.",
                "1. 물어보았을 때만 우울한 기분이라고 말한다.",
                "2. 자발적으로 우울한 기분이라고 말한다.",
                "3. 얼굴 표정, 자세, 목소리, 쉽게 우는 경향과 같은 비언어적인 표현을 통해 우울한 기분을 나타낸다.",
                "4. 오로지 우울한 기분만을, 언어적·비언어적 표현을 통해 나타낸다."};
        ArrayAdapter<String> depSpinAdapter01 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData01);
        depSpinAdapter01.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin01.setAdapter(depSpinAdapter01);

        String[] depSpinData02 = {"0. 없다.",
                "1. 자책하거나 자신이 사람들을 실망시킨다고 느낀다",
                "2. 죄를 지었다고 생각한다던가 과거의 실수나 자신이 한 나쁜 행위에 대해 반복적으로 생각한다.",
                "3. 현재의 병을 벌로 여긴다. 죄책망상이 있다.",
                "4. 비난 또는 탄핵하는 목소리를 듣거나 위협적인 환시를 경험한다."};
        ArrayAdapter<String> depSpinAdapter02 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData02);
        depSpinAdapter02.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin02.setAdapter(depSpinAdapter02);

        String[] depSpinData03 = {"0. 없다.",
                "1. 인생이 살 가치가 없다고 느낀다.",
                "2. 차라리 죽었으면 하거나 죽는 것에 대한 상상을 한다",
                "3. 현재의 병을 벌로 여긴다. 죄책망상이 있다.",
                "4. 자살 사고가 있거나 자살기도처럼 볼 수 있는 행동을 한다.",
                "5. 심각한 자살 기도를 한다."};
        ArrayAdapter<String> depSpinAdapter03 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData03);
        depSpinAdapter03.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin03.setAdapter(depSpinAdapter03);

        String[] depSpinData04 = {"0. 잠드는 데 어려움이 없다",
                "1. 간간이 잠들기가 어렵다(잠드는 데 30분 이상 걸린다).",
                "2. 매일 밤 잠들기가 어렵다."};
        ArrayAdapter<String> depSpinAdapter04 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData04);
        depSpinAdapter04.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin04.setAdapter(depSpinAdapter04);

        String[] depSpinData05 = {"0. 어려움이 없다.",
                "1. 새벽에 깨지만 다시 잠이 든다.",
                "2. 일단 깨어나면 다시 잠들 수 없다."};
        ArrayAdapter<String> depSpinAdapter05 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData05);
        depSpinAdapter05.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin05.setAdapter(depSpinAdapter05);

        String[] depSpinData06 = {""};
        ArrayAdapter<String> depSpinAdapter06 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData06);
        depSpinAdapter06.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin06.setAdapter(depSpinAdapter03);

        String[] depSpinData07 = {"0. 어려움이 없다.",
                "1. 제대로 할 수 없다고 느낀다. 일이나 취미와 같은 활동에 대해 피로하거나 기력이 떨어졌다고 느낀다.",
                "2. 일이나 취미와 같은 활동에 흥미를 잃는다 - 환자가 직접 이야기하거나 무관심, 우유부단, 망설임을 통해 간접적으로" +
                        "나타낸다(일이나 활동을 억지로 한다고 느낀다).",
                "3. 활동 시간이 줄거나 생산성이 떨어져 있다. 입원 환자의 경우, 병동생활에서의 개인적인 자질구레한 일을 제외한" +
                        "활동(원내 작업이나 취미)에 보내는 시간이 하루 3시간을 넘지 못한다.",
                "4. 현재의 병 때문에 일을 중단한다. 입원 환자의 경우, 병동생활에서의 개인적인 자질구레한 일 이외에는 전혀 활동을" +
                        "하지 않거나 도움 없이는 병동생활에서의 개인적인 자질구레한 일도 해내지 못한다."};
        ArrayAdapter<String> depSpinAdapter07 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData07);
        depSpinAdapter07.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin07.setAdapter(depSpinAdapter07);

        String[] depSpinData08 = {"0. 정상적으로 말하고 생각한다.",
                "1. 면담할 때 약간 지체되어 있다.",
                "2. 면담할 때 뚜렷이 지체되어 있다.",
                "3. 면담이 어려울 정도로 지체되어 있다.",
                "4. 완전한 혼미 상태에 있다."};
        ArrayAdapter<String> depSpinAdapter08 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData08);
        depSpinAdapter08.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin08.setAdapter(depSpinAdapter08);

        String[] depSpinData09 = {"0. 없다.",
                "1. 조금 초조한 듯하다.",
                "2. 손이나 머리카락 등을 만지작거린다.",
                "3. 가만히 앉아 있지 못하고 몸을 자꾸 움직인다.",
                "4. 손을 비비꼬거나 손톱을 물어뜯거나 머리카락을 잡아당기거나 입술을 깨문다."};
        ArrayAdapter<String> depSpinAdapter09 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData09);
        depSpinAdapter09.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin09.setAdapter(depSpinAdapter09);

        String[] depSpinData10 = {"0. 없다.",
                "1. 긴장감과 과민함을 느낀다.",
                "2. 사소한 일들에 대해 걱정을 한다.",
                "3. 얼굴 표정이나 말에서 염려하는 태도가 뚜렷하다.",
                "4. 묻지 않아도 심한 공포가 드러난다."};
        ArrayAdapter<String> depSpinAdapter10 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData10);
        depSpinAdapter10.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin10.setAdapter(depSpinAdapter10);

        String[] depSpinData11 = {"0. 없다. 위장관계 - 입마름, 방귀, 소화불량, 설사, 심한 복통, 트림",
                "1. 경도. 심혈관계 - 심계항진, 두통",
                "2. 중등도. 호흡기계 - 과호흡, 한숨",
                "3. 고도. 호흡기계 - 빈뇨, 발한",
                "4. 최고도(기능을 전혀 할 수 없을 정도이다)."};
        ArrayAdapter<String> depSpinAdapter11 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData11);
        depSpinAdapter11.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin11.setAdapter(depSpinAdapter11);

        String[] depSpinData12 = {"0. 없다.",
                "1. 입맛을 잃었지만 치료진의 격려 없이도 먹는다. 속이 더부룩하다.",
                "2. 치료진의 강요 없이는 잘 먹지 않는다. 하제나 소화제 등 위장관계 증상에 대한 약제를 요구하거나 필요로 한다"};
        ArrayAdapter<String> depSpinAdapter12 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData12);
        depSpinAdapter12.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin12.setAdapter(depSpinAdapter12);

        String[] depSpinData13 = {"0. 없다.",
                "1. 팔, 다리, 등, 머리가 무겁다. 등의 통증, 두통, 근육통. 기운이 없고 쉽게 피곤해진다.",
                "2. 매우 뚜렷한 신체증상이 있다."};
        ArrayAdapter<String> depSpinAdapter13 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData13);
        depSpinAdapter13.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin13.setAdapter(depSpinAdapter13);

        String[] depSpinData14 = {"0. 없다.",
                "1. 경도",
                "2. 고도"};
        ArrayAdapter<String> depSpinAdapter14 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData14);
        depSpinAdapter14.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin14.setAdapter(depSpinAdapter14);

        String[] depSpinData15 = {"0. 없다.",
                "1. 몸에 대해 많이 생각한다.",
                "2. 건강에 대해 집착한다.",
                "3. 건강이 나쁘다고 자주 호소하거나 도움을 청한다.",
                "4. 건강염려증적 망상이 있다."};
        ArrayAdapter<String> depSpinAdapter15 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData15);
        depSpinAdapter15.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin15.setAdapter(depSpinAdapter15);

        String[] depSpinData16 = {"0. 없다.",
                "1. 병력에 의해 평가할 때",
                "2. 체중감소가 없다.",
                "3. 현재의 병으로 인해 체중감소가 있는 것 같다",
                "4. (환자에 따르면) 확실한 체중감소가 있다."};
        ArrayAdapter<String> depSpinAdapter16 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData16);
        depSpinAdapter16.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin16.setAdapter(depSpinAdapter16);

        String[] depSpinData17 = {"0. 자신이 우울하고 병들었다는 것을 인식한다.",
                "1. 병들었다는 것을 인정하지만 음식, 날씨, 과로, 바이러스, 휴식 부족 등이 이유라고 생각한다.",
                "2. 자신의 병을 전적으로 부인한다."};
        ArrayAdapter<String> depSpinAdapter17 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData17);
        depSpinAdapter17.setDropDownViewResource(R.layout.depspin_text_style);
        depSpin17.setAdapter(depSpinAdapter17);
    }

    //16번 A,B 체크 처리
    public void radioCheck(View v) {
        if (check_A.isChecked()) {
            String[] depSpinData16_A = {"0. 없다.",
                    "1. 병력에 의해 평가할 때",
                    "2. 체중감소가 없다.",
                    "3. 현재의 병으로 인해 체중감소가 있는 것 같다",
                    "4. (환자에 따르면) 확실한 체중감소가 있다."};
            ArrayAdapter<String> depSpinAdapter16 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData16_A);
            depSpinAdapter16.setDropDownViewResource(R.layout.depspin_text_style);
            depSpin16.setAdapter(depSpinAdapter16);

        } else if (check_B.isChecked()) {
            String[] depSpinData16_B = {"0. 주당 0.5㎏ 미만의 체중감소.",
                    "1. 주당 0.5㎏ 이상, 1㎏ 미만의 체중감소",
                    "2. 주당 1㎏ 이상의 체중감소."};
            ArrayAdapter<String> depSpinAdapter16 = new ArrayAdapter<String>(this, R.layout.depspin_text_style, android.R.id.text1, depSpinData16_B);
            depSpinAdapter16.setDropDownViewResource(R.layout.depspin_text_style);
            depSpin16.setAdapter(depSpinAdapter16);
        }
    }

    //우울증 합 추출
    public int sumDepression() {
        int depSum = 0;
        String[] spinValue = new String[17];

        spinValue[0] = depSpin01.getSelectedItem().toString();
        spinValue[1] = depSpin02.getSelectedItem().toString();
        spinValue[2] = depSpin03.getSelectedItem().toString();
        spinValue[3] = depSpin04.getSelectedItem().toString();
        spinValue[4] = depSpin05.getSelectedItem().toString();
        spinValue[5] = depSpin06.getSelectedItem().toString();
        spinValue[6] = depSpin07.getSelectedItem().toString();
        spinValue[7] = depSpin08.getSelectedItem().toString();
        spinValue[8] = depSpin09.getSelectedItem().toString();
        spinValue[9] = depSpin10.getSelectedItem().toString();
        spinValue[10] = depSpin11.getSelectedItem().toString();
        spinValue[11] = depSpin12.getSelectedItem().toString();
        spinValue[12] = depSpin13.getSelectedItem().toString();
        spinValue[13] = depSpin14.getSelectedItem().toString();
        spinValue[14] = depSpin15.getSelectedItem().toString();
        spinValue[15] = depSpin16.getSelectedItem().toString();
        spinValue[16] = depSpin17.getSelectedItem().toString();

        for (int i = 0; i < spinValue.length; i++) {
            char nmb = ' ';
            nmb = spinValue[i].charAt(0); //첫번째 문자 가져오기

            switch (nmb) {
                case '0':
                    depSum += 0;
                    break;
                case '1':
                    depSum += 1;
                    break;
                case '2':
                    depSum += 2;
                    break;
                case '3':
                    depSum += 3;
                    break;
                case '4':
                    depSum += 4;
                    break;
            }
        }
        Log.i("sum", depSum + "");
        return depSum;
    }

    //뒤로 가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
