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
import com.drkaiproject.adapter.AlcoholAdapter;
import com.drkaiproject.model.Alcohol;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.drkaiproject.sqliteModi.AlcoholModiActivity;

import java.util.ArrayList;


public class AlcoholListActivity extends AppCompatActivity {

    //db
    ArrayList<Alcohol> wl = new ArrayList<Alcohol>();
    ListView lv;
    AlcoholAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lv = (ListView) findViewById(R.id.alcohol_list);

        String sql = "select date,drinking from allHealthTBL order by date_id desc limit 7" ;
        Cursor c = SqliteFunction.db.rawQuery(sql, null);

        c.moveToLast();
        do {
            String date = c.getString(0);
            int alcohol_data = c.getInt(1);

            Alcohol w = new Alcohol();
            w.setAlcohol_data(alcohol_data);
            w.setDate(date);
            wl.add(w);

            adapter = new AlcoholAdapter(AlcoholListActivity.this, wl, R.layout.alcohol_row);
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

                Intent intent = new Intent(AlcoholListActivity.this, AlcoholModiActivity.class);

                //선택된 리스트뷰의 데이터 객체 생성
                Alcohol selectData = new Alcohol();
                selectData.setAlcohol_data(wl.get(position).getAlcohol_data());
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
        Intent intent = new Intent(AlcoholListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
