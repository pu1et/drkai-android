package com.drkaiproject;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.drkaiproject.card.FoodActivity;
import com.drkaiproject.model.Food;
import com.drkaiproject.model.FoodExcel;
import com.drkaiproject.sqliteModi.FoodModiActivity;


public class FoodInfoActivity extends AppCompatActivity implements View.OnClickListener {

    //db
    public SQLiteDatabase db;
    //헬퍼
    public ProductDBHelper mHelper;
    public Cursor cursor;
    TextView output_kcal, output_carbo, output_protein, output_fat, output_sugar, output_salt, output_chole, output_saturfat, output_transfat;
    TextView food_title, food_count, go_food;
    Button btnAdd, btnMinus;
    private int flag = 0;//0이면 food로, 1이면 foodModi로
    private Food selectData2;

    int count = 1;
    FoodExcel f;
    int one_kcal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_info);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //FoodModi에서 가져온 flag
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);
        Log.v("현재 flag값", flag + "");

        //가져온 리스트뷰의 데이터
        selectData2 = (Food) getIntent().getSerializableExtra("selectData");

        output_kcal = (TextView) findViewById(R.id.output_kcal); //열량
        output_carbo = (TextView) findViewById(R.id.output_carbo); //탄수화물
        output_protein = (TextView) findViewById(R.id.output_protein); //단백질
        output_fat = (TextView) findViewById(R.id.output_fat); //지방
        output_sugar = (TextView) findViewById(R.id.output_sugar); //당류
        output_salt = (TextView) findViewById(R.id.output_salt); //나트륨
        output_chole = (TextView) findViewById(R.id.output_chole); //콜레스테롤
        output_saturfat = (TextView) findViewById(R.id.output_saturfat); //포화지방산
        output_transfat = (TextView) findViewById(R.id.output_transfat); //트랜스지방산
        food_count = (TextView) findViewById(R.id.food_count); //음식 개수
        food_title = (TextView) findViewById(R.id.food_title); //음식 타이틀
        go_food = (TextView) findViewById(R.id.go_food); //food activity로 이동

        btnAdd = (Button) findViewById(R.id.addButton);
        btnMinus = (Button) findViewById(R.id.minusButton);

        btnAdd.setOnClickListener(this);
        btnMinus.setOnClickListener(this);
        go_food.setClickable(true);

        //가져온 리스트뷰의 데이터
        String selectFood = intent.getStringExtra("selectFood");
        food_title.setText(selectFood);
        Log.v("가져온 데이터", selectFood + "");

        //db 연동
        mHelper = new ProductDBHelper(this); //헬퍼
        db = mHelper.getWritableDatabase();//db연결
        Log.i("현재 db url", db + "");
        Log.v("이제 칼럼을 찾아줍니다", ".");

        //db 쿼리문, adapter연결
        try {
            cursor = db.rawQuery("SELECT * FROM opendata_food where category = ?", new String[]{selectFood});
            if (cursor != null) {
                while (cursor.moveToNext()) {//다음행으로 이동

                    int kcal = cursor.getInt(cursor.getColumnIndex("kcal"));
                    int carbo = cursor.getInt(cursor.getColumnIndex("carbo"));
                    float protein = cursor.getFloat(cursor.getColumnIndex("protein"));
                    int fat = cursor.getInt(cursor.getColumnIndex("fat"));
                    String sugar = cursor.getString(cursor.getColumnIndex("sugar"));
                    String salt = cursor.getString(cursor.getColumnIndex("salt"));
                    String chole = cursor.getString(cursor.getColumnIndex("chole"));
                    String saturfat = cursor.getString(cursor.getColumnIndex("saturfat"));
                    int transfat = cursor.getInt(cursor.getColumnIndex("transfat"));

                    //String형 데이터는 null검사
                    String[] s = {sugar, salt, chole, saturfat};
                    for (int i = 0; i < 4; i++) {
                        if (s[i] == null || s[i].trim().length() == 0) {
                            s[i] = "0";
                            Log.v("널값", s[i]);
                        }
                    }

                    f = new FoodExcel();
                    f.name = selectFood;
                    f.kcal = kcal;
                    f.carbo = carbo;
                    f.protein = protein;
                    f.fat = fat;
                    f.sugar = s[0];
                    f.salt = s[1];
                    f.chole = s[2];
                    f.saturfat = s[3];
                    f.transfat = transfat;

                    //한개당 열량
                    one_kcal = f.kcal;
                    output_kcal.setText(String.valueOf(f.kcal));
                    output_carbo.setText(String.valueOf(f.carbo));
                    output_protein.setText(String.valueOf(f.protein));
                    output_fat.setText(String.valueOf(f.fat));
                    output_sugar.setText(String.valueOf(f.sugar));
                    output_salt.setText(String.valueOf(f.salt));
                    output_chole.setText(String.valueOf(f.chole));
                    output_saturfat.setText(String.valueOf(f.saturfat));
                    output_transfat.setText(String.valueOf(f.transfat));
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        Log.v("최종 열량", f.kcal + "");

        //저장 버튼 이벤트
        go_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == 0) { //flag가 0이면
                    Intent intent = new Intent(FoodInfoActivity.this, FoodActivity.class);
                    intent.putExtra("foodCount", count); //음식 개수
                    intent.putExtra("foodData", f); //음식 객체
                    startActivity(intent);
                } else { //flag가 1이면 즉, foodmodiactivity에서 넘어온거라면
                    Intent intent = new Intent(FoodInfoActivity.this, FoodModiActivity.class);
                    intent.putExtra("foodCount", count); //음식 개수
                    intent.putExtra("foodData", f); //음식 객체
                    intent.putExtra("flag", flag);
                    intent.putExtra("selectData2", selectData2);
                    startActivity(intent);
                }
            }
        });
    }

    //헬퍼
    class ProductDBHelper extends SQLiteOpenHelper {
        public ProductDBHelper(Context context) {
            super(context, "opendata_food.db", null, 3);    // db명과 버전만 정의 한다.
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addButton:
                count++;
                food_count.setText(String.valueOf(count));
                break;
            case R.id.minusButton:
                if (count == 1) {
                    break;
                }
                count--;
                food_count.setText(String.valueOf(count));
                break;
            default:
                break;
        }
        output_kcal.setText(String.valueOf(one_kcal * count));
        f.kcal = one_kcal * count;
    }

    //뒤로 가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FoodInfoActivity.this, FoodSearchActivity.class);
        intent.putExtra("flag", flag);
        startActivity(intent);
    }
}
