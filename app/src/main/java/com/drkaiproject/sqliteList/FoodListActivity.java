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
import com.drkaiproject.adapter.FoodAdapter;
import com.drkaiproject.model.Food;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.drkaiproject.sqliteModi.FoodModiActivity;

import java.util.ArrayList;

public class FoodListActivity extends AppCompatActivity {

    FoodAdapter adapter;

    ListView lv;

    ArrayList<Food> wl = new ArrayList<Food>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        lv = (ListView) findViewById(R.id.food_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        //selectAll
        String sql = "select date,food from allHealthTBL order by date_id desc limit 7" ;
        Cursor c = SqliteFunction.db.rawQuery(sql, null);

        c.moveToLast();
        do {
            String food_name = c.getString(c.getColumnIndex("food_name"));
            int food_count = c.getInt(c.getColumnIndex("food_count"));
            int food_kcal = c.getInt(c.getColumnIndex("kcal"));
            String food_time = c.getString(c.getColumnIndex("food_time"));
            String date = c.getString(c.getColumnIndex("date"));

            Food w = new Food();
            w.setFood_name(food_name);
            w.setFood_count(food_count);
            w.setKcal(food_kcal);
            w.setFood_time(food_time);
            w.setDate(date);
            wl.add(w);

            adapter = new FoodAdapter(FoodListActivity.this, wl, R.layout.food_row);
            lv.setAdapter(adapter);
        }
        while (c.moveToPrevious());
        Log.i("DB select", "완료");

        //리스트 선택 시 수정페이지로 이동
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //선택한 position의 날짜
                Log.i("선택한 position의 날짜", wl.get(position).getDate() + "");

                Intent intent = new Intent(FoodListActivity.this, FoodModiActivity.class);

                //선택된 리스트뷰의 데이터 객체 생성
                Food selectData = new Food();
                selectData.setDate(wl.get(position).getDate()); //선택한 날짜
                selectData.setFood_time(wl.get(position).getFood_time()); //선택한 식사 시간
                selectData.setFood_name(wl.get(position).getFood_name()); //선택한 음식 이름
                selectData.setFood_count(wl.get(position).getFood_count()); //선택한 음식 개수
                selectData.setKcal(wl.get(position).getKcal()); //선택한 음식 칼로리

                intent.putExtra("selectData", selectData);
                startActivity(intent);
            }
        });

    }

    //뒤로 가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FoodListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
