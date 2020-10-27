package com.drkaiproject.sqliteModi;

import androidx.appcompat.app.AppCompatActivity;


public class AlcoholModiActivity extends AppCompatActivity {//implements View.OnClickListener {

    /*
    //db
    AlcoholSQLiteHelper helper;
    SQLiteDatabase db;
    String dbName = "alcoholTBL.db";
    String tableName = "alcoholTBL";

    Button btnAdd, btnMinus, deleteBtn;
    TextView alcohol_view, dateView, alcoholModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alcohol_modi);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        btnAdd = (Button) findViewById(R.id.addButton);
        btnMinus = (Button) findViewById(R.id.minusButton);

        alcohol_view = (TextView) findViewById(R.id.alcohol_view); //입력된 데이터
        dateView = (TextView) findViewById(R.id.date_view); //입력된 날짜
        deleteBtn = (Button) findViewById(R.id.alcohol_delete); //삭제 버튼
        alcoholModify = (TextView) findViewById(R.id.alcohol_modify); //수정

        btnAdd.setOnClickListener(this);
        btnMinus.setOnClickListener(this);

        //가져온 리스트뷰의 데이터
        Alcohol selectData = (Alcohol) getIntent().getSerializableExtra("selectData");

        //위의 데이터 default값으로 보여주기
        alcohol_view.setText(String.valueOf(selectData.getAlcohol_data()));
        dateView.setText(selectData.getDate());

        //db생성
        helper = new AlcoholSQLiteHelper(this, dbName, null, 3);

        //update
        alcoholModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                int alcohol_data = Integer.parseInt(alcohol_view.getText().toString());
                String date = dateView.getText().toString();

                ContentValues values = new ContentValues();
                values.put("alcohol_data", alcohol_data);
                db.update(tableName, values, "date=?", new String[]{date + ""});
                Toast.makeText(AlcoholModiActivity.this, "데이터가 수정되었습니다",
                        Toast.LENGTH_SHORT).show();
                //수정 후 목록으로 이동
                Intent intent = new Intent(AlcoholModiActivity.this, AlcoholListActivity.class);
                startActivity(intent);
            }
        });

        //delete
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                String date = dateView.getText().toString();
                db.delete(tableName, "date=?", new String[]{date + ""});
                Toast.makeText(AlcoholModiActivity.this, "데이터가 삭제되었습니다",
                        Toast.LENGTH_SHORT).show();

                Cursor cursor = db.query(tableName, null, null, null, null, null, null);

                //레코드가 있는 경우
                if (cursor != null && cursor.moveToFirst()) {
                    //수정 후 목록으로 이동
                    Intent intent = new Intent(AlcoholModiActivity.this, AlcoholListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(AlcoholModiActivity.this, "데이터가 삭제되었습니다",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AlcoholModiActivity.this, AlcoholActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        int alcoholNum = Integer.parseInt(alcohol_view.getText().toString());

        switch (v.getId()) {
            case R.id.addButton:
                alcoholNum++;
                break;
            case R.id.minusButton:
                if (alcoholNum == 0) {
                    break;
                }
                alcoholNum--;
                break;
            default:
                break;
        }
        alcohol_view.setText(String.valueOf(alcoholNum));
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AlcoholModiActivity.this, AlcoholListActivity.class);
        startActivity(intent);
    }

     */
}
