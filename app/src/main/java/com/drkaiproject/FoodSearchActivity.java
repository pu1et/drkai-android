package com.drkaiproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.drkaiproject.adapter.TotalFoodAdapter;
import com.drkaiproject.card.FoodActivity;
import com.drkaiproject.model.Food;
import com.drkaiproject.model.TotalFood;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


public class FoodSearchActivity extends AppCompatActivity {
    public static final String ROOT_DIR = "/data/user/0/ttokk.com.swproject/databases/";
    public SQLiteDatabase db; //adapter속성 정의
    public Cursor cursor;
    ProductDBHelper mHelper;
    ListView lv;
    ImageView food_search;
    ArrayList<TotalFood> tf = new ArrayList<TotalFood>(); //데이터를 넣은 리스트 변수
    TotalFoodAdapter adapter;
    EditText editSearch;
    private ArrayList<TotalFood> arraylist;
    private int flag = 0; //0이면 food로, 1이면 foodModi로
    private Food selectData;

    String dbName = "opendata_food.db";
    String tableName = "opendata_food";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_search);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //FoodModi에서 가져온 flag
        Intent intent = getIntent();
        flag = intent.getIntExtra("flag", 0);

        //가져온 리스트뷰의 데이터
        selectData = (Food) getIntent().getSerializableExtra("selectData");

        lv = (ListView) findViewById(R.id.totalfood_list);
        food_search = (ImageView) findViewById(R.id.food_search);
        editSearch = (EditText) findViewById(R.id.editSearch);
        food_search.setClickable(true);

        //db세팅
        setDB(this);
        mHelper = new ProductDBHelper(this); //헬퍼
        db = mHelper.getWritableDatabase();//db연결
        Log.i("현재 db url", db + "");
        Log.v("이제 칼럼을 찾아줍니다", ".");


        //db 쿼리문, adapter연결
        try {
            cursor = db.rawQuery("SELECT category FROM opendata_food", null); //쿼리문
            if (cursor != null) {
                while (cursor.moveToNext()) {//다음행으로 이동
                    String foodName = cursor.getString(cursor.getColumnIndex("category")); //category 칼럼을 찾아서
                    //Log.v("db에서 꺼낸거", foodName);
                    //cursor에서 꺼낼 때 마다 객체 생성해서
                    TotalFood f = new TotalFood();
                    f.totalfood_txt = foodName;
                    tf.add(f);
                }
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        // 리스트의 모든 데이터를 arraylist에 복사한다.
        // list 복사본을 만든다.
        arraylist = new ArrayList<TotalFood>();
        arraylist.addAll(tf);

        //리스트에 연동될 어댑터 생성
        adapter = new TotalFoodAdapter(FoodSearchActivity.this, tf, R.layout.totalfood_row);
        //리스트뷰에 어댑터를 연결한다
        lv.setAdapter(adapter);

        //글자가 입력될때마다
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = editSearch.getText().toString();
                search(text);
            }
        });

        //리스트 선택시 상세 페이지(FoodInfoActivity)로 이동
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FoodSearchActivity.this, FoodInfoActivity.class);
                intent.putExtra("flag", flag); //flag넣음

                //선택된 리스트뷰 position의 음식 이름 intent에 담기
                String selectFood = tf.get(position).getTotalfood_txt();
                Log.i("선택한 position의 음식 이름", tf.get(position).getTotalfood_txt() + "");
                intent.putExtra("selectFood", selectFood);
                intent.putExtra("selectData", selectData);
                startActivity(intent);
            }
        });
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        tf.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
            tf.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).getTotalfood_txt().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    tf.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }


    //db세팅
    public static void setDB(Context ctx) {
        Log.v("setDb", "setDb 접속");
        File folder = new File(ROOT_DIR); //파일 생성
        if (folder.exists()) {
            Log.v(" ", "해당 파일이 존재합니다");
        } else {//파일이 없으면
            Log.v(" ", "해당 파일이 존재하지 않습니다");
            folder.mkdirs();
        }
        AssetManager assetManager = ctx.getResources().getAssets();//ctx가 없으면 assets폴더를 찾지 못함
        Log.v("getAssets값: ", ctx.getResources().getAssets() + "");
        File outfile = new File(ROOT_DIR + "opendata_food.db");//asesets폴더에서 가져온 db를 넣을 주소
        Log.v("디바이스 폴더 안에 db를 넣었습니다", ".");
        InputStream is = null;
        FileOutputStream fo = null;
        long filesize = 0;
        try {
            is = assetManager.open("opendata_food.db", AssetManager.ACCESS_BUFFER);//asset에서 db를 찾아서
            filesize = is.available();
            if (outfile.length() <= 0) {
                byte[] tempdata = new byte[(int) filesize];
                is.read(tempdata);
                is.close();
                outfile.createNewFile();//디바이스의 file경로안에 db를 만들어준다
                fo = new FileOutputStream(outfile);
                fo.write(tempdata);
                fo.close();
                Log.v("db넣기 완료", ".");
            } else {
            }
        } catch (IOException e) {
        }
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

    //뒤로 가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FoodSearchActivity.this, FoodActivity.class);
        startActivity(intent);
    }
}