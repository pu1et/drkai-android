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
import com.drkaiproject.adapter.WaterAdapter;
import com.drkaiproject.model.Water;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.drkaiproject.sqliteModi.WaterModiActivity;

import java.util.ArrayList;


public class WaterListActivity extends AppCompatActivity {

    ArrayList<Water> wl = new ArrayList<Water>();
    WaterAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lv = (ListView) findViewById(R.id.water_list);

        String sql = "select date,water from allHealthTBL order by date_id desc limit 7" ;
        Cursor c = SqliteFunction.db.rawQuery(sql, null);


        while(c.moveToNext()) {
            int water_data = c.getInt(1);
            String date = c.getString(0);
            int water_ml = water_data*250;

            Water w = new Water();
            w.setWater_data(water_data);
            w.setWater_ml(water_ml);
            w.setDate(date);
            wl.add(w);

            adapter = new WaterAdapter(WaterListActivity.this, wl, R.layout.water_row);
            lv.setAdapter(adapter);
        }


        //리스트 선택 시 수정페이지로 이동
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //선택한 position의 날짜
                Log.i("선택한 position의 날짜", wl.get(position).getDate() + "");

                Intent intent = new Intent(WaterListActivity.this, WaterModiActivity.class);

                //선택된 리스트뷰의 데이터 객체 생성
                Water selectData = new Water();
                selectData.setWater_data(wl.get(position).getWater_data());
                selectData.setDate(wl.get(position).getDate());
                selectData.setWater_ml(wl.get(position).getWater_ml());

                intent.putExtra("selectData", selectData);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WaterListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
