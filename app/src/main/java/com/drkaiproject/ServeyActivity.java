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
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ServeyActivity extends AppCompatActivity {

    //데이터
    String HE_fh, HE_HPfh, HE_HLfh, HE_IHDfh, HE_STRfh, HE_HBfh, HE_DMfh, BH2_61,
            DI1_dg, DI2_dg, DI3_dg, DJ4_dg, DI4_dg, DJ2_dg, DE1_dg, DE1_32, DC1_dg, DC2_dg,
            DC6_dg, DJ8_dg, DJ6_dg, DK8_dg, DK9_dg, DK4_dg, drinking_5y;
    String BO1_1, BP1, D_1_1, BE5_1, BS3_1, DI1_2, DI2_2, exercise;
    String HE_ht, HE_wt, EC_wht_23, HE_sput2, BS3_2, Total_slp_wk, Total_slp_wd, BD2_1, BE3_33;
    String bmi; //BMI

    //라디오그룹
    RadioGroup radio_HE_fh, radio_HE_HPfh, radio_HE_HLfh, radio_HE_IHDfh, radio_HE_STRfh, radio_HE_HBfh, radio_HE_DMfh, radio_BH2_61,
            radio_DI1_dg, radio_DI2_dg, radio_DI3_dg, radio_DJ4_dg, radio_DI4_dg, radio_DJ2_dg, radio_DE1_dg, radio_DE1_32, radio_DC1_dg, radio_DC2_dg,
            radio_DC6_dg, radio_DJ8_dg, radio_DJ6_dg, radio_DK8_dg, radio_DK9_dg, radio_DK4_dg, radio_drinking_5y;

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

    //버튼
    Button go_main;

    JSONObject jsonObject;
    String str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_servey);

        findMethod();
        spinMethod();

        Intent intent = getIntent();
        str = intent.getExtras().getString("account");
        try {
            jsonObject = new JSONObject(str);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textToString()) {
                    radioToString();
                    spinToString();
                    try {
                        jsonObject.put("HE_fh", HE_fh);
                        jsonObject.put("HE_HPfh", HE_HPfh);
                        jsonObject.put("HE_HLfh", HE_HLfh);
                        jsonObject.put("HE_IHDfh", HE_IHDfh);
                        jsonObject.put("HE_STRfh", HE_STRfh);
                        jsonObject.put("HE_HBfh", HE_HBfh);
                        jsonObject.put("HE_DMfh", HE_DMfh);
                        jsonObject.put("BH2_61", BH2_61);
                        jsonObject.put("DI1_dg", DI1_dg);
                        jsonObject.put("DI2_dg", DI2_dg);
                        jsonObject.put("DI3_dg", DI3_dg);
                        jsonObject.put("DJ4_dg", DJ4_dg);
                        jsonObject.put("DI4_dg", DI4_dg);
                        jsonObject.put("DJ2_dg", DJ2_dg);
                        jsonObject.put("DE1_dg", DE1_dg);
                        jsonObject.put("DE1_32", DE1_32);
                        jsonObject.put("DC1_dg", DC1_dg);
                        jsonObject.put("DC2_dg", DC2_dg);
                        jsonObject.put("DC6_dg", DC6_dg);
                        jsonObject.put("DJ8_dg", DJ8_dg);
                        jsonObject.put("DJ6_dg", DJ6_dg);
                        jsonObject.put("DK8_dg", DK8_dg);
                        jsonObject.put("DK9_dg", DK9_dg);
                        jsonObject.put("DK4_dg", DK4_dg);
                        jsonObject.put("exercise", exercise);
                        jsonObject.put("BO1_1", BO1_1);
                        jsonObject.put("BP1", BP1);
                        jsonObject.put("D_1_1", D_1_1);
                        jsonObject.put("BE5_1", BE5_1);
                        jsonObject.put("BS3_1", BS3_1);
                        jsonObject.put("DI1_2", DI1_2);
                        jsonObject.put("DI2_2", DI2_2);
                        jsonObject.put("HE_ht", HE_ht);
                        jsonObject.put("HE_wt", HE_wt);
                        jsonObject.put("EC_wht_23", EC_wht_23);
                        jsonObject.put("HE_sput2", HE_sput2);
                        jsonObject.put("BS3_2", BS3_2);
                        jsonObject.put("Total_slp_wk", Total_slp_wk);
                        jsonObject.put("Total_slp_wd", Total_slp_wd);
                        jsonObject.put("BD2_1", BD2_1); // 음주량
                        if (drinking_5y.equals("예")) drinking_5y = "1";
                        else drinking_5y = "0";
                        jsonObject.put("drinking_5y", drinking_5y); // 5년내 음주50ml 이상
                        jsonObject.put("BE3_33", BE3_33);
                        jsonObject.put("bmi", bmi);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    Intent intent = new Intent(ServeyActivity.this, DepressionActivity.class);
                    intent.putExtra("account", jsonObject.toString());
                    startActivity(intent);
                }
            }
        });
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
        radio_drinking_5y = (RadioGroup) findViewById(R.id.radio_drinking_5y);

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
    public boolean textToString() {
        HE_ht = txt_HE_ht.getText().toString();
        if (HE_ht.equals("")) {
            Toast.makeText(this, "키 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        HE_wt = txt_HE_wt.getText().toString(); //몸무게
        if (HE_wt.equals("")) {
            Toast.makeText(this, "몸무 값을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        EC_wht_23 = txt_EC_wht_23.getText().toString(); //주당 평균 근로 시간
        if (EC_wht_23.equals("")) {
            Toast.makeText(this, "근로시간(시간/주)을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        HE_sput2 = txt_HE_sput2.getText().toString(); //연속 3개월 이상 가래 경험
        if (HE_sput2.equals("")) {
            Toast.makeText(this, "가래 경험 을 입력해주세요." , Toast.LENGTH_LONG).show();
            return false;
        }
        BS3_2 = txt_BS3_2.getText().toString(); //하루 평균 흡연량
        if (BS3_2.equals("")) {
            Toast.makeText(this, "하루 평 흡연을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        Total_slp_wk = txt_Total_slp_wk.getText().toString(); //주중 하루 평균 수면시간
        if (Total_slp_wk.equals("")) {
            Toast.makeText(this, "주중 수면 시간을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        Total_slp_wd = txt_Total_slp_wd.getText().toString(); //주말 하루 평균 수면 시간
        if (Total_slp_wd.equals("")) {
            Toast.makeText(this, "주말 수면 시간을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        BD2_1 = txt_BD2_1.getText().toString(); //음주량
        if (BD2_1.equals("")) {
            Toast.makeText(this, "음주량을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }
        BE3_33 = txt_BE3_33.getText().toString(); //걷기 지속시간
        if (BE3_33.equals("")) {
            Toast.makeText(this, "걷기 시간을 입력해주세요.", Toast.LENGTH_LONG).show();
            return false;
        }

        //bmi계산
        if (!HE_ht.equals("") & !HE_wt.equals("")) {
            bmi = String.valueOf(Integer.parseInt(HE_ht) * Integer.parseInt(HE_ht) / Integer.parseInt(HE_wt)); //BMI
        }

        Log.v("EditText 값", HE_ht + "," + HE_wt + "," + EC_wht_23 + "," + HE_sput2 + "," + "걷기 지속 시간: " + BE3_33 + "," + BS3_2 + "," + "음주량: " + BD2_1 + "," + Total_slp_wk + "," + Total_slp_wd);
        Log.v("BMI ", bmi + "");

        return true;
    }

    //RadioButton > String
    //RadioButton > String
    public void radioToString() {
        //RadioGroup > RadioButton > String
        HE_fh = ((RadioButton) findViewById(radio_HE_fh.getCheckedRadioButtonId())).getText().toString();//만성질환 가족
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
        drinking_5y = ((RadioButton) findViewById(radio_drinking_5y.getCheckedRadioButtonId())).getText().toString(); // 5년내 50ml 음주 여부

        //"0","1"로 바꾸기
        String[] str = {HE_fh, HE_HPfh, HE_HLfh, HE_IHDfh, HE_STRfh, HE_HBfh, HE_DMfh, BH2_61,
                DI1_dg, DI2_dg, DI3_dg, DJ4_dg, DI4_dg, DJ2_dg, DE1_dg, DE1_32, DC1_dg, DC2_dg,
                DC6_dg, DJ8_dg, DJ6_dg, DK8_dg, DK9_dg, DK4_dg, drinking_5y};

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
                case 24:
                    drinking_5y = result;
                    break;
                default:
                    break;
            }
        }

        Log.i("라디오버튼 값", HE_fh + "," + HE_HPfh + "," + HE_HLfh + "," + HE_IHDfh + "," + HE_STRfh + "," + HE_HBfh + "," + HE_DMfh + "," + BH2_61 + ","
                + DI1_dg + "," + DI2_dg + "," + DI3_dg + "," + DJ4_dg + "," + DI4_dg + "," + DJ2_dg + "," + DE1_dg + "," + DE1_32 + ","
                + DC1_dg + "," + DC2_dg + "," + DC6_dg + "," + DJ8_dg + "," + DJ6_dg + "," + DK8_dg + "," + DK9_dg + "," + DK4_dg + "," + drinking_5y);
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
        BO1_1 = spin_BO1_1.getSelectedItem().toString();//1년간 체중변화 여부
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
        Log.i("스피너 값", BO1_1 + "," + BP1 + "," + D_1_1 + "," + BE5_1 + "," + "평균운동정도: " + exercise + "," + BS3_1 + "," + DI1_2 + "," + DI2_2);
    }
}