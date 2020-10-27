package com.drkaiproject.sqliteModi;

import androidx.appcompat.app.AppCompatActivity;

public class WaterModiActivity extends AppCompatActivity {//implements View.OnClickListener {
    /*

    private static final String tableName = "waterTBL";
    WaterSQLiteHelper helper;
    SQLiteDatabase db;
    TextView waterView2, water_ml2, dateView2, waterModify;
    Button btnAdd2, btnMinus2, deleteBtn;

    protected int year, month, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_modi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        btnAdd2 = (Button) findViewById(R.id.addButton2);
        btnMinus2 = (Button) findViewById(R.id.minusButton2);
        deleteBtn = (Button) findViewById(R.id.water_delete);

        waterView2 = (TextView) findViewById(R.id.water_view2); //입력된 물
        dateView2 = (TextView) findViewById(R.id.date_view2); //입력된 날짜
        water_ml2 = (TextView) findViewById(R.id.waterml2); //입력된 ml
        waterModify = (TextView) findViewById(R.id.water_modify); //수정 버튼

        waterModify.setClickable(true);
        btnAdd2.setOnClickListener(this);
        btnMinus2.setOnClickListener(this);

        //가져온 리스트뷰의 데이터
        Water selectData = (Water) getIntent().getSerializableExtra("selectData");

        //위의 데이터 default값으로 보여주기
        waterView2.setText(String.valueOf(selectData.getWater_data()));
        dateView2.setText(selectData.getDate());
        water_ml2.setText(String.valueOf(selectData.getWater_ml()));

        //db생성
        helper = new WaterSQLiteHelper(this, "waterTBL.db", null, 3);

        //update
        waterModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                int water_data= Integer.parseInt(waterView2.getText().toString());
                int water_ml = Integer.parseInt(water_ml2.getText().toString());
                String date = dateView2.getText().toString();

                ContentValues values = new ContentValues();
                values.put("water_data", water_data);
                values.put("water_ml", water_ml);
                db.update("waterTBL", values, "date=?", new String[]{date+""});
                Toast.makeText(WaterModiActivity.this, "데이터가 수정되었습니다",
                        Toast.LENGTH_SHORT).show();
                //수정 후 목록으로 이동
                Intent intent = new Intent(WaterModiActivity.this, WaterListActivity.class);
                startActivity(intent);
            }
        });

        //delete
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                String date = dateView2.getText().toString();
                db.delete("waterTBL", "date=?", new String[]{date+""});
                Toast.makeText(WaterModiActivity.this, "데이터가 삭제되었습니다",
                        Toast.LENGTH_SHORT).show();

                Cursor cursor = db.query("waterTBL", null, null, null, null, null, null);

                //레코드가 있는 경우
                if (cursor != null && cursor.moveToFirst()) {
                    //수정 후 목록으로 이동
                    Intent intent = new Intent(WaterModiActivity.this, WaterListActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(WaterModiActivity.this, "데이터가 삭제되었습니다",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(WaterModiActivity.this, WaterActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int waterNum = Integer.parseInt(waterView2.getText().toString());

        switch (v.getId()) {
            case R.id.addButton2:
                waterNum++;
                water_ml2.setText(String.valueOf(waterNum * 250));
                break;
            case R.id.minusButton2:
                if (waterNum == 0) {
                    break;
                }
                waterNum--;
                water_ml2.setText(String.valueOf(waterNum * 250));
                break;
            default:
                break;
        }
        waterView2.setText(String.valueOf(waterNum));
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(WaterModiActivity.this, WaterListActivity.class);
        startActivity(intent);
    }

     */
}
