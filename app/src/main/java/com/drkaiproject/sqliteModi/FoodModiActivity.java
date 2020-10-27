package com.drkaiproject.sqliteModi;

import androidx.appcompat.app.AppCompatActivity;


public class FoodModiActivity extends AppCompatActivity {

    /*
    //db
    FoodSQLiteHelper helper;
    SQLiteDatabase db;
    String dbName = "foodTBL.db";
    String tableName = "foodTBL";

    ArrayList foodArray;
    ArrayAdapter foodAdapter;
    Spinner food_time;
    Button dateBtn, food_delete;
    TextView dateView, inputFoodBtn, food_name, food_count, food_kcal, foodModify;
    protected int year, month, date;
    FoodExcel foodData;
    int foodCount = 0; //최초 접속시 0으로 초기화
    LinearLayout lv;

    private int flag = 0;
    private Food selectData;
    private Food selectData2; //리스트에서 선택한 구데이터


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_modi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        food_time = (Spinner) findViewById(R.id.food_time);
        dateBtn = (Button) findViewById(R.id.date_btn);
        food_delete = (Button) findViewById(R.id.food_delete);
        dateView = (TextView) findViewById(R.id.date_view);
        foodModify = (TextView) findViewById(R.id.food_modify);//수정 버튼
        dateView.setText(year + "-" + month + "-" + date); //현재 날짜 초기화

        //FoodInfo에서 가져온 리스트뷰의 데이터
        Intent intent = getIntent();
        foodCount = intent.getIntExtra("foodCount", 0); //음식 개수
        flag = intent.getIntExtra("flag", 0); //flag
        Log.i("foodcount", "" + foodCount);
        foodData = (FoodExcel) intent.getSerializableExtra("foodData");//음식 객체

        //가져온 리스트뷰의 데이터
        selectData = (Food) getIntent().getSerializableExtra("selectData");

        //앞의 리스트에서 선택한 구데이터
        selectData2 = (Food) getIntent().getSerializableExtra("selectData2");

        if (flag == 1) { //데이터를 수정 후 search>info 경로로 온거라면
            //앞에서 사용자가 선택한 날짜 default
            dateView.setText(String.valueOf(selectData2.getDate()));
            dateView.setText(selectData2.getDate());
        } else { //foodList에서 바로 온거라면
            //앞에서 사용자가 선택한 날짜 default
            dateView.setText(String.valueOf(selectData.getDate()));
            dateView.setText(selectData.getDate());
        }

        //앞에서 사용자가 선택한 식사 시간(spinner에서 default로 설정 찾아보기)
        final String selectTime = String.valueOf(selectData.getFood_time());

        //스피너 등록
        foodArray = new ArrayList<>();
        foodArray.add("아침");
        foodArray.add("점심");
        foodArray.add("저녁");

        food_time = (Spinner) findViewById(R.id.food_time); //시간
        foodAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, foodArray);
        food_time.setAdapter(foodAdapter);

        //음식 등록 리니어
        lv = (LinearLayout) findViewById(R.id.layout);

        food_name = (TextView) findViewById(R.id.food_name); //음식 이름
        food_count = (TextView) findViewById(R.id.food_count);//음식 개수
        food_kcal = (TextView) findViewById(R.id.food_kcal);//음식 칼로리
        inputFoodBtn = (TextView) findViewById(R.id.input_food_btn);

        //앞에서 사용자가 선택한 음식 이름, 개수, 칼로리 default
        final String selectName = selectData.getFood_name();
        final int selectCount = selectData.getFood_count();
        final int selectKcal = selectData.getKcal();

        food_name.setText(selectName);
        food_count.setText(String.valueOf(selectCount)); //ing>string
        food_kcal.setText(String.valueOf(selectKcal)); //int>string

        //음식을 새로 선택하고 오면
        if (foodCount != 0) {
            food_name.setText(foodData.getName());
            food_count.setText(String.valueOf(foodCount)); //ing>string
            food_kcal.setText(String.valueOf(foodData.getKcal())); //int>string

            //버튼 안보이게
            inputFoodBtn.setVisibility(View.INVISIBLE);
        }

        //db연결
        helper = new FoodSQLiteHelper(this, dbName, null, 3);

        //update
        foodModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                //칼럼 명칭과 짝
                ContentValues values = new ContentValues();

                String date = dateView.getText().toString();
                String foodTime = food_time.getSelectedItem().toString();
                values.put("food_name", food_name.getText().toString());//음식 이름
                values.put("food_count", Integer.parseInt(food_count.getText().toString()));//음식 개수
                values.put("kcal", Integer.parseInt(food_kcal.getText().toString()));//음식 칼로리
                values.put("food_time", foodTime);//음식 시간

                int update = db.update(tableName, values, "date=? and food_time=?", new String[]{date, selectTime});
                Toast.makeText(FoodModiActivity.this, "데이터가 수정되었습니다",
                        Toast.LENGTH_SHORT).show();
                //수정 후 목록으로 이동
                Intent intent = new Intent(FoodModiActivity.this, FoodListActivity.class);
                startActivity(intent);
            }
        });

        //delete
        food_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                String date = dateView.getText().toString(); //날짜
                String foodTime = food_time.getSelectedItem().toString(); //음식 시간
                db.delete(tableName, "date=? and food_time=?", new String[]{date, selectTime});
                Toast.makeText(FoodModiActivity.this, "데이터가 삭제되었습니다",
                        Toast.LENGTH_SHORT).show();

                Cursor cursor = db.query(tableName, null, null, null, null, null, null);

                //레코드가 있는 경우
                if (cursor != null && cursor.moveToFirst()) {
                    //수정 후 목록으로 이동
                    Intent intent = new Intent(FoodModiActivity.this, FoodListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(FoodModiActivity.this, "데이터가 삭제되었습니다",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FoodModiActivity.this, FoodActivity.class);
                    startActivity(intent);
                }
            }
        });


        //기록 버튼
        inputFoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FoodModiActivity.this, FoodSearchActivity.class);
                //기록에서 이동한 search액티비티임을 알려줌
                int flag = 1;
                intent.putExtra("flag", flag);
                intent.putExtra("selectData", selectData);
                startActivity(intent);
            }
        });
    }

    //뒤로 가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(FoodModiActivity.this, FoodListActivity.class);
        startActivity(intent);
    }

     */
}
