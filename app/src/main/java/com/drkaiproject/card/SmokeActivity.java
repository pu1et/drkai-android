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
import com.drkaiproject.sqliteList.SmokeListActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;


public class SmokeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, btnMinus, dateBtn;
    TextView smokeView, goSmokeList, dateView, smoke_submit;

    JSONObject jsonObject;
    String str, smoke_data;

    protected int year, month, date;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoke);
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
        smoke_submit = (Button) findViewById(R.id.smoke_submit);

        smokeView = (TextView) findViewById(R.id.smokeView2); //물 입력
        dateView = (TextView) findViewById(R.id.date_view); //날짜 입력

        goSmokeList = (TextView) findViewById(R.id.go_smoke_list); //목록 보기
        dateView.setText(year + "-" + month + "-" + date); //default 현재 날짜

        btnAdd.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        smoke_submit.setOnClickListener(this);

        try {
            jsonObject = new JSONObject(str);
            smoke_data = jsonObject.getString("smoking");
            count = Integer.parseInt(smoke_data);
            smokeView.setText(smoke_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //목록
        goSmokeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sql = "select * from allHealthTBL" ;
                Cursor cursor = SqliteFunction.db.rawQuery(sql, null);
                //레코드가 있는 경우
                if (cursor != null && cursor.moveToFirst()) {
                    Intent intent = new Intent(SmokeActivity.this, SmokeListActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(SmokeActivity.this, "등록된 데이터가 없습니다 ",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //날짜 설정 다이얼로그
        dateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                DatePickerDialog dialog = new DatePickerDialog(SmokeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                        String msg = String.format("%d-%d-%d", year, month+1, date);
                        Toast.makeText(SmokeActivity.this, msg, Toast.LENGTH_SHORT).show();
                        dateView.setText(year+"-"+(month+1)+"-"+date);
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
                smokeView.setText(String.valueOf(count));
                break;
            case R.id.minusButton:
                if (count == 0) {
                    break;
                }
                count--;
                smokeView.setText(String.valueOf(count));
                break;
            case R.id.smoke_submit:
                SqliteFunction.updateData(SqliteFunction.today,"smoking",count);
                onBackPressed();
            default:
                break;
        }
    }

    //뒤로 가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
