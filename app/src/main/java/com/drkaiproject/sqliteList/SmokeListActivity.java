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
import com.drkaiproject.adapter.SmokeAdapter;
import com.drkaiproject.model.Smoke;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.drkaiproject.sqliteModi.SmokeModiActivity;

import java.util.ArrayList;

public class SmokeListActivity extends AppCompatActivity {

    ArrayList<Smoke> wl = new ArrayList<Smoke>();
    SmokeAdapter adapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoke_list);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        lv = (ListView) findViewById(R.id.smoke_list);

        String sql = "select date,smoking from allHealthTBL order by date_id desc limit 7" ;
        Cursor c = SqliteFunction.db.rawQuery(sql, null);

        while(c.moveToNext()) {
            String date = c.getString(0);
            int smoke_data = c.getInt(1);

            Smoke w = new Smoke();
            w.setSmoke_data(smoke_data);
            w.setDate(date);
            wl.add(w);

            adapter = new SmokeAdapter(SmokeListActivity.this, wl, R.layout.smoke_row);
            lv.setAdapter(adapter);
        }


        //리스트 선택 시 수정페이지로 이동
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //선택한 position의 날짜
                Log.i("선택한 position의 날짜", wl.get(position).getDate() + "");

                Intent intent = new Intent(SmokeListActivity.this, SmokeModiActivity.class);

                //선택된 리스트뷰의 데이터 객체 생성
                Smoke selectData = new Smoke();
                selectData.setSmoke_data(wl.get(position).getSmoke_data());
                selectData.setDate(wl.get(position).getDate());

                intent.putExtra("selectData", selectData);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SmokeListActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
