package com.drkaiproject.sqliteModi;

import androidx.appcompat.app.AppCompatActivity;


public class SleepModiActivity extends AppCompatActivity {

    /*

    //db
    SleepSQLiteHelper helper;
    SQLiteDatabase db;
    private static final String dbName = "sleepTBL.db";
    private static final String tableName = "sleepTBL";

    //달력
    protected int year, month, date;

    //스피너
    ArrayList<Integer> hourList;
    ArrayList<Integer> minuteList;

    ArrayAdapter<Integer> arrayAdapter1;
    ArrayAdapter<Integer> arrayAdapter2;

    Spinner hourData, minuteData;
    TextView dateView, sleep_modify;
    Button dateBtn, sleep_delete;

    int sleep_sum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sleep_modi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        //스피너
        //spinner
        hourList = new ArrayList<Integer>(
                Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20));

        minuteList = new ArrayList<Integer>();
        for (int i = 0; i <= 59; i++) {
            minuteList.add(i);
        }

        hourData = (Spinner) findViewById(R.id.hour_data);
        minuteData = (Spinner) findViewById(R.id.minute_data);

        arrayAdapter1 = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, hourList);
        arrayAdapter2 = new ArrayAdapter<Integer>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, minuteList);

        hourData.setAdapter(arrayAdapter1);
        minuteData.setAdapter(arrayAdapter2);

        sleep_delete = (Button) findViewById(R.id.sleep_delete);
        dateBtn = (Button) findViewById(R.id.date_btn);

        sleep_modify = (TextView) findViewById(R.id.sleep_modify);
        dateView = (TextView) findViewById(R.id.date_view);

        //가져온 리스트뷰의 데이터
        Sleep selectData = (Sleep) getIntent().getSerializableExtra("selectData");

        //위의 데이터 default값으로 보여주기(일단 날짜만...)
        dateView.setText(selectData.getDate());


        //db생성
        helper = new SleepSQLiteHelper(this, dbName, null, 3);

        //update
        sleep_modify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                String hour = hourData.getSelectedItem().toString();
                String minute = minuteData.getSelectedItem().toString();
                sleep_sum = Integer.parseInt(hour) * 60 + Integer.parseInt(minute);//최종 데이터
                String date = dateView.getText().toString();//시간

                ContentValues values = new ContentValues();
                values.put("sleep_hour", Integer.parseInt(hour)); //수면 시
                values.put("sleep_minute", Integer.parseInt(minute)); //수면 분
                values.put("sleep_sum", sleep_sum); //총 수면 시간(분)
                db.update(tableName, values, "date=?", new String[]{date + ""});
                Toast.makeText(SleepModiActivity.this, "데이터가 수정되었습니다",
                        Toast.LENGTH_SHORT).show();
                //수정 후 목록으로 이동
                Intent intent = new Intent(SleepModiActivity.this, SleepListActivity.class);
                startActivity(intent);
            }
        });

        //delete
        sleep_delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                db = helper.getWritableDatabase();
                String date = dateView.getText().toString();
                db.delete(tableName, "date=?", new String[]{date + ""});
                Toast.makeText(SleepModiActivity.this, "데이터가 삭제되었습니다",
                        Toast.LENGTH_SHORT).show();

                Cursor cursor = db.query(tableName, null, null, null, null, null, null);

                //레코드가 있는 경우
                if (cursor != null && cursor.moveToFirst()) {
                    //수정 후 목록으로 이동
                    Intent intent = new Intent(SleepModiActivity.this, SleepListActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(SleepModiActivity.this, "데이터가 삭제되었습니다",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SleepModiActivity.this, SleepActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(SleepModiActivity.this, MainActivity.class);
        startActivity(intent);
    }

     */
}
