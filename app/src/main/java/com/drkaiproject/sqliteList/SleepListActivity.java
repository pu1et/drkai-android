package com.drkaiproject.sqliteList;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.drkaiproject.MainActivity;
import com.drkaiproject.R;
import com.drkaiproject.adapter.SleepAdapter;
import com.drkaiproject.model.Sleep;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.drkaiproject.sqliteModi.SleepModiActivity;

import java.util.ArrayList;


public class SleepListActivity extends AppCompatActivity {


    ArrayList<Sleep> wl = new ArrayList<Sleep>();
    SleepAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lv = (ListView) findViewById(R.id.water_list);

        String sql = "select date,sleep from allHealthTBL order by date_id desc limit 7" ;
        Cursor c = SqliteFunction.db.rawQuery(sql, null);

        while(c.moveToNext()) {
            int tmp = c.getInt(c.getColumnIndex("sleep"));
            int sleep_hour = tmp/60;
            int sleep_minute = tmp%60;
            int sleep_sum = tmp;
            String date = c.getString(c.getColumnIndex("date"));

            Sleep w = new Sleep();
            w.setSleep_hour(sleep_hour);
            w.setSleep_minute(sleep_minute);
            w.setSleep_sum(sleep_sum);
            w.setDate(date);
            wl.add(w);

            adapter = new SleepAdapter(SleepListActivity.this, wl, R.layout.sleep_row);
            lv.setAdapter(adapter);
        }


        //리스트 선택 시 수정페이지로 이동
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //선택한 position의 날짜
                Log.i("선택한 position의 날짜", wl.get(position).getDate() + "");

                Intent intent = new Intent(SleepListActivity.this, SleepModiActivity.class);

                //선택된 리스트뷰의 데이터 객체 생성
                Sleep selectData = new Sleep();
                selectData.setSleep_hour(wl.get(position).getSleep_hour());
                selectData.setSleep_minute(wl.get(position).getSleep_minute());
                selectData.setDate(wl.get(position).getDate());

                intent.putExtra("selectData", selectData);
                startActivity(intent);
            }
        });
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SleepListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
