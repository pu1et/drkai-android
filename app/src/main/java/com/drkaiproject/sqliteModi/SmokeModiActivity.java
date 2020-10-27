package com.drkaiproject.sqliteModi;

import androidx.appcompat.app.AppCompatActivity;


public class SmokeModiActivity extends AppCompatActivity {//implements View.OnClickListener {

    /*
    private static final String tableName = "smokeTBL";
    SmokeSQLiteHelper helper;
    SQLiteDatabase db;
    TextView smokeView2, dateView2, smokeModify;
    Button btnAdd2, btnMinus2, deleteBtn;

    protected int year, month, date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smoke_modi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();


        btnAdd2 = (Button) findViewById(R.id.addButton2);
        btnMinus2 = (Button) findViewById(R.id.minusButton2);
        deleteBtn = (Button) findViewById(R.id.smoke_delete);

        smokeView2 = (TextView) findViewById(R.id.smoke_view2); //입력된 담배
        dateView2 = (TextView) findViewById(R.id.date_view2); //입력된 날짜
        smokeModify = (TextView) findViewById(R.id.smokeModify); //수정 버튼

        smokeModify.setClickable(true);
        btnAdd2.setOnClickListener(this);
        btnMinus2.setOnClickListener(this);

        //가져온 리스트뷰의 데이터
        Smoke selectData = (Smoke) getIntent().getSerializableExtra("selectData");

        //위의 데이터 default값으로 보여주기
        smokeView2.setText(String.valueOf(selectData.getSmoke_data()));
        dateView2.setText(selectData.getDate());

        //db생성
        helper = new SmokeSQLiteHelper(this, "smokeTBL.db", null, 3);

        //update
        smokeModify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                int water_data = Integer.parseInt(smokeView2.getText().toString());
                String date = dateView2.getText().toString();

                ContentValues values = new ContentValues();
                values.put("smoke_data", water_data);
                db.update("smokeTBL", values, "date=?", new String[]{date + ""});
                Toast.makeText(SmokeModiActivity.this, "데이터가 수정되었습니다",
                        Toast.LENGTH_SHORT).show();
                //수정 후 목록으로 이동
                Intent intent = new Intent(SmokeModiActivity.this, SmokeListActivity.class);
                startActivity(intent);
            }
        });

        //delete
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                String date = dateView2.getText().toString();
                db.delete("smokeTBL", "date=?", new String[]{date + ""});
                Toast.makeText(SmokeModiActivity.this, "데이터가 삭제되었습니다",
                        Toast.LENGTH_SHORT).show();

                Cursor cursor = db.query("smokeTBL", null, null, null, null, null, null);

                //레코드가 있는 경우
                if (cursor != null && cursor.moveToFirst()) {
                    //수정 후 목록으로 이동
                    Intent intent = new Intent(SmokeModiActivity.this, SmokeListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SmokeModiActivity.this, "데이터가 삭제되었습니다",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SmokeModiActivity.this, SmokeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        int smokeNum = Integer.parseInt(smokeView2.getText().toString());

        switch (v.getId()) {
            case R.id.addButton2:
                smokeNum++;
                break;
            case R.id.minusButton2:
                if (smokeNum == 0) {
                    break;
                }
                smokeNum--;
                break;
            default:
                break;
        }
        smokeView2.setText(String.valueOf(smokeNum));
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SmokeModiActivity.this, SmokeListActivity.class);
        startActivity(intent);
    }

     */
}
