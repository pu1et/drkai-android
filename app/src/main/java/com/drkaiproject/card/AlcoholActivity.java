package com.drkaiproject.card;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.drkaiproject.R;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.drkaiproject.sqliteList.AlcoholListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


public class AlcoholActivity extends AppCompatActivity implements View.OnClickListener {

    protected int year, month, date;
    Button btnAdd, btnMinus, dateBtn, alcohol_submit;
    TextView alcoholView, goAlcoholList, dateView;
    int count = 0;
    JSONObject jsonObject;
    String str, alcohol_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol);
        Intent intent = getIntent();
        str = intent.getExtras().getString("data");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        final Calendar cal = Calendar.getInstance();
        year = cal.get(cal.YEAR);
        month = cal.get(cal.MONTH) + 1;
        date = cal.get(cal.DATE);

        btnAdd = (Button) findViewById(R.id.addButton);
        btnMinus = (Button) findViewById(R.id.minusButton);
        dateBtn = (Button) findViewById(R.id.date_btn);
        alcohol_submit = (Button) findViewById(R.id.alcohol_submit);

        alcoholView = (TextView) findViewById(R.id.alcoholView);
        goAlcoholList = (TextView) findViewById(R.id.go_alcohol_list);
        dateView = (TextView) findViewById(R.id.date_view);
        dateView.setText(year + "-" + month + "-" + date); //현재 날짜 초기화

        btnAdd.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        alcohol_submit.setOnClickListener(this);

        try {
            jsonObject = new JSONObject(str);
            alcohol_data = jsonObject.getString("drinking");
            count = Integer.parseInt(alcohol_data);
            alcoholView.setText(alcohol_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //목록
        goAlcoholList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "select * from allHealthTBL;" ;
                Cursor cursor = SqliteFunction.db.rawQuery(sql, null);

                //레코드가 있는 경우
                if (cursor != null && cursor.moveToFirst()) {
                    Intent intent = new Intent(AlcoholActivity.this, AlcoholListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AlcoholActivity.this, "등록된 데이터가 없습니다 ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //날짜 설정 다이얼로그
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                DatePickerDialog dialog = new DatePickerDialog(AlcoholActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {
                        String msg = String.format("%d-%d-%d", year, month + 1, date);
                        Toast.makeText(AlcoholActivity.this, msg, Toast.LENGTH_SHORT).show();
                        dateView.setText(year + "-" + (month + 1) + "-" + date);
                    }
                }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));

                dialog.getDatePicker().setMaxDate(new Date().getTime());    //입력한 날짜 이후로 클릭 안되게 옵션
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                count++;
                alcoholView.setText(String.valueOf(count));
                break;
            case R.id.minusButton:
                if (count == 0) {
                    break;
                }
                count--;
                alcoholView.setText(String.valueOf(count));
                break;
            case R.id.alcohol_submit:
                SqliteFunction.updateData(SqliteFunction.today,"drinking",count);
                onBackPressed();
                //Intent intent = new Intent(AlcoholActivity.this, MainActivity.class);
                //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); finish();
                break;
            default:
                break;
        }
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
