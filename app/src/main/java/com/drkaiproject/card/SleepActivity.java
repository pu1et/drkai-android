package com.drkaiproject.card;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.drkaiproject.R;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.drkaiproject.sqliteList.SleepListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class SleepActivity extends AppCompatActivity {
    //달력
    protected int year, month, date;

    ArrayList<Integer> hourList;
    ArrayList<Integer> minuteList;

    ArrayAdapter<Integer> arrayAdapter1;
    ArrayAdapter<Integer> arrayAdapter2;

    Spinner hourData, minuteData;
    Button dateBtn, sleep_submit;
    TextView date_view, go_sleep_list;
    int sleep_sum;
    JSONObject jsonObject;
    String str, hour_data, min_data ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep);
        Intent intent = getIntent();
        str = intent.getExtras().getString("data");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //달력
        final Calendar cal = Calendar.getInstance();
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);

        //spinner
        hourList = new ArrayList<Integer>(
                Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));

        minuteList = new ArrayList<Integer>();
        for (int i = 0; i <= 59; i++) {
            minuteList.add(i);
        }

        date_view = (TextView) findViewById(R.id.date_view);
        date_view.setText(year + "-" + month + "-" + date); //현재 날짜 초기화

        go_sleep_list = (TextView) findViewById(R.id.go_sleep_list); //목록 이동

        hourData = (Spinner) findViewById(R.id.hourData);
        minuteData = (Spinner) findViewById(R.id.minuteData);

        arrayAdapter1 = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, hourList);
        arrayAdapter2 = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, minuteList);

        hourData.setAdapter(arrayAdapter1);
        minuteData.setAdapter(arrayAdapter2);

        sleep_submit = (Button) findViewById(R.id.sleep_submit);
        dateBtn = (Button) findViewById(R.id.date_btn);

        try {
            jsonObject = new JSONObject(str);
            hour_data = jsonObject.getString("sleep_hour");
            min_data = jsonObject.getString("sleep_min");
            hourData.setSelection(Integer.parseInt(hour_data));
            minuteData.setSelection(Integer.parseInt(min_data));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //spinner 시간->분으로 변경
        sleep_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String hour = hourData.getSelectedItem().toString();
                String minute = minuteData.getSelectedItem().toString();
                sleep_sum = Integer.parseInt(hour) * 60 + Integer.parseInt(minute);//최종 데이터

                SqliteFunction.updateData(SqliteFunction.today,"sleep",sleep_sum);
                onBackPressed();
            }
        });

        //목록
        go_sleep_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "select * from allHealthTBL;" ;
                Cursor cursor = SqliteFunction.db.rawQuery(sql, null);

                //레코드가 있는 경우
                if (cursor != null && cursor.moveToFirst()) {
                    Intent intent = new Intent(SleepActivity.this, SleepListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SleepActivity.this, "등록된 데이터가 없습니다 ", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //날짜 설정 다이얼로그
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(SleepActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d-%d-%d", year, month + 1, date);
                        Toast.makeText(SleepActivity.this, msg, Toast.LENGTH_SHORT).show();
                        date_view.setText(year + "-" + (month + 1) + "-" + date);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();
            }
        });
    }

    //뒤로 가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
