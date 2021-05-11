package com.drkaiproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.drkaiproject.adapter.FirstLevelAdapter;
import com.drkaiproject.algorithmn.Asthma;
import com.drkaiproject.algorithmn.Cold;
import com.drkaiproject.fragment.ChatbotFragment;
import com.drkaiproject.fragment.DiagFragment;
import com.drkaiproject.fragment.HLTCareFragment;
import com.drkaiproject.fragment.MainFragment;
import com.drkaiproject.fragment.RMTDiagFragment;
import com.drkaiproject.fragment.RSVTConfFragment;
import com.drkaiproject.sqliteHelper.AllHealthSQLiteHelper;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.google.android.material.navigation.NavigationView;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.drkaiproject.sqliteHelper.SqliteFunction.allHealthSQLiteHelper;

public class PastMainActivity extends AppCompatActivity {
    private MainFragment mainFragment;
    private RMTDiagFragment rmtDiagFragment;
    private ChatbotFragment chatbotFragment;
    private DiagFragment diagFragment;
    private HLTCareFragment hltCareFragment;
    private StringBuilder param_X, param_Y, param_STN;
    private String id, area_num, name;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ExpandableListView expandableListView;
    private FirstLevelAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private NavigationView navigationView;
    private RSVTConfFragment rsvtConfFragment;
    private SharedPreferences sf;
    private SharedPreferences.Editor editor;

    private float sub = 0, humidity = 0, min_temp = 0, pressure = 0; //일교차, 평균습도, 최저기온
    SQLiteDatabase db;
    private Cursor cursor;
    private String result_cold, result_asthma;

    private long pressTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SqliteFunction.id = "1";
        SqliteFunction.gender = 0; // 여자
        SqliteFunction.mCtx = PastMainActivity.this;

        Date today_tmp = Calendar.getInstance().getTime();
        String today_text = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(today_tmp);
        SqliteFunction.today = Integer.parseInt(today_text);

        allHealthSQLiteHelper = new AllHealthSQLiteHelper(PastMainActivity.this, "allHealthTBL.db", null, 1);
        SqliteFunction.db = allHealthSQLiteHelper.getWritableDatabase();
        //db.execSQL("delete from allHealthTBL;");
        allHealthSQLiteHelper.onCreate(SqliteFunction.db);


        sf = getSharedPreferences("sfFile", MODE_PRIVATE);
        editor = sf.edit();
        if (sf.getString("user_id", null) == null) {
            //editor.clear();
            editor.putString("user_name", "안지원");
            editor.putString("user_email", "default@gmail.com");
            editor.putString("user_id", "1");
            editor.putString("user_area", "1");
            editor.commit();
        }
        Log.w("sharedpreference", sf.getString("user_id", "null"));
        area_num = sf.getString("user_area", "1");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        expandableListView = (ExpandableListView) findViewById(R.id.expandable_list);

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        if (navigationView != null) {
            setUpDrawerContent(navigationView);
        }

        prepareListData();

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;


        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.w("Expandable_first", "group: " + groupPosition);

                return false;
            }
        });


        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        mainFragment = new MainFragment();
        diagFragment = new DiagFragment();
        rsvtConfFragment = new RSVTConfFragment();
        chatbotFragment = new ChatbotFragment();
        diagFragment = new DiagFragment();
        hltCareFragment = new HLTCareFragment();

        initFragment();
        editor = sf.edit();

        if (sf.getString("user_id", null) == null) {
            //editor.clear();
            editor.putString("user_name", "안지원");
            editor.putString("user_email", "default@gmail.com");
            editor.putString("user_id", "1");
            editor.putString("user_area", "1");
            editor.commit();
        }
        Log.w("sharedpreference_main", sf.getString("user_id", "null"));
        area_num = sf.getString("user_area", "1");


        StringBuilder urlBuilder = new StringBuilder(Constants.newsky_server);
        try {
            //Constant에 있는 변수를 가져오기 위한 문자로 동일한 부분 미리 초기화
            param_X = new StringBuilder("X_");
            param_Y = new StringBuilder("Y_");
            param_STN = new StringBuilder("STN_");

            //사용자 지역 번호 넣기
            param_X.append(area_num);
            param_Y.append(area_num);
            param_STN.append(area_num);

            Calendar calendar = Calendar.getInstance();
            //calendar.add(Calendar.DATE, 1);
            Date today = calendar.getTime();


            String dateformat = "yyyyMMdd";
            String dateformat2 = "HH";
            String todaystr = new SimpleDateFormat(dateformat).format(today);
            int todaystr2 = Integer.parseInt(new SimpleDateFormat(dateformat2).format(today));
            Log.w("today is : ", todaystr + todaystr2);
            if (todaystr2 < 05) todaystr = String.valueOf(Integer.parseInt(todaystr) - 1);
            Log.w("[setting] today is : ", todaystr + todaystr2);

            String param_x, param_y;
            param_x = (String) Constants.class.getField(param_X.toString()).get(null);
            param_y = (String) Constants.class.getField(param_Y.toString()).get(null);
            urlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + Constants.newsky_serviceKey); //Service Key
            urlBuilder.append("&" + URLEncoder.encode("base_date", "UTF-8") + "=" + URLEncoder.encode(todaystr, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("base_time", "UTF-8") + "=" + URLEncoder.encode(Constants.base_time, "UTF-8")); //05시 발표 * 기술문서 참조
            urlBuilder.append("&" + URLEncoder.encode("nx", "UTF-8") + "=" + URLEncoder.encode(param_x, "UTF-8")); //예보지점의 X 좌표값
            urlBuilder.append("&" + URLEncoder.encode("ny", "UTF-8") + "=" + URLEncoder.encode(param_y, "UTF-8")); //예보지점의 Y 좌표값
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("70", "UTF-8")); //한 페이지 결과 수
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); //페이지 번호
            urlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); //xml(기본값), json

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        Log.w("error_test", urlBuilder.toString());


        AsyncTask<String, String, String> finish_check = new JSONTask().execute(urlBuilder.toString());//AsyncTask 시작시킴

        try {
            String s = finish_check.get();
            Log.w("JSONTask", "finished!!!");
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if (tabId == R.id.tab_main) {
                    Bundle args = new Bundle();
                    Log.w("result_cold", result_cold);
                    Log.w("result_asthma", result_asthma);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("cold_index", result_cold);
                        jsonObject.put("asthma_index", result_asthma);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    args.putString("args", jsonObject.toString());
                    mainFragment.setArguments(args);
                    transaction.replace(R.id.contentContainer, mainFragment).commit();
                } else if (tabId == R.id.tab_diag) {
                    transaction.replace(R.id.contentContainer, diagFragment).commit();
                } else if (tabId == R.id.tab_rsvtconf) {
                    transaction.replace(R.id.contentContainer, rsvtConfFragment).commit();
                } else if (tabId == R.id.tab_hltcare) {
                    transaction.replace(R.id.contentContainer, hltCareFragment).commit();
                } else if (tabId == R.id.tab_chatbot) {
                    transaction.replace(R.id.contentContainer, chatbotFragment).commit();
                }
            }
        });

    }


    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();
        String[] mItemHeaders = getResources().getStringArray(R.array.items_array_expandable_level_one);
        Collections.addAll(listDataHeader, mItemHeaders);

        if (expandableListView != null) {
            FirstLevelAdapter firstLevelAdapter = new FirstLevelAdapter(this, listDataHeader);
            expandableListView.setAdapter(firstLevelAdapter);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);


        return super.onCreateOptionsMenu(menu);
    }

    //탭메뉴
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) return true;
        Log.w("onOptionsItemSelected", String.valueOf(item.getItemId()));

        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.item0:

                Toast.makeText(this, "회원정보 수정 기능 입니다.", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, UserIconActivity.class);

                JSONObject jsonObject_tmp = new JSONObject();
                try {
                    jsonObject_tmp.put("id", "1");
                    jsonObject_tmp.put("pw", "1");
                    jsonObject_tmp.put("name", "Jiwon");
                    jsonObject_tmp.put("gender", "0");
                    jsonObject_tmp.put("area", "1");
                    jsonObject_tmp.put("age", "25");
                    jsonObject_tmp.put("phone", "01029315201");
                    jsonObject_tmp.put("carstairs", "4");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                intent.putExtra("account", jsonObject_tmp.toString());
                startActivity(intent);
                break;


                /*
            case R.id.item1: //회원정보 수정
                Intent intent = new Intent(this, ChangeMemberInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.item2: //설문지 수정
                Intent intent2 = new Intent(MainActivity.this, ChangeServeyActivity.class);
                startActivity(intent2);
                break;
            case R.id.item3: //건강검진 수정
                Intent intent3 = new Intent(MainActivity.this, ChangeHealthServeyActivity.class);
                startActivity(intent3);
                break;
            case R.id.item4: //개발자 정보
                Intent intent4 = new Intent(MainActivity.this, DeveloperActivity.class);
                startActivity(intent4);
                break;

                 */
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    public void initFragment() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.contentContainer, mainFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (pressTime == 0) {
            Toast.makeText(PastMainActivity.this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressTime);

            if (seconds > 2000) {
                pressTime = 0;
            } else {
                finish();
            }
        }
    }

    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... urls) {
            String url_dayhealth = Constants.SERVER_URL + "/user/send-day-health";

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("id", "1");
                jsonObject.put("date_id", SqliteFunction.today);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final RequestQueue requestQueue = Volley.newRequestQueue(SqliteFunction.mCtx);
            final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url_dayhealth, jsonObject, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject resObject = new JSONObject(response.toString());
                        Log.d("receiveData", resObject.toString());
                        if (!resObject.getString("result").equals("1"))
                            throw new Exception();
                        else {
                            String str = resObject.getString("data");
                            JSONArray jsonArray = new JSONArray(str);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.d("receiveData", "i의값: " + i);
                                int date_id, water, sleep, food, drinking, smoking, exercise, result;
                                JSONObject tmp = jsonArray.getJSONObject(i);
                                date_id = Integer.parseInt(tmp.getString("date_id"));
                                String date = tmp.getString("date");
                                water = Integer.parseInt(tmp.getString("water"));
                                sleep = Integer.parseInt(tmp.getString("sleep"));
                                food = Integer.parseInt(tmp.getString("food"));
                                drinking = Integer.parseInt(tmp.getString("drinking"));
                                smoking = Integer.parseInt(tmp.getString("smoking"));
                                exercise = Integer.parseInt(tmp.getString("exercise"));
                                result = Integer.parseInt(tmp.getString("result"));
                                SqliteFunction.insertData(date_id, date, water, sleep, food, drinking, smoking, exercise, result);
                                Log.i("data[" + i + "]_insert", tmp.toString());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(10000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);

            if (SqliteFunction.today > sf.getInt("today", 20200000)) {

                try {
                    URL url = new URL(urls[0]);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(100000);
                    con.setReadTimeout(100000);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoInput(true); //  HERE
                    con.connect();

                    //서버로 부터 데이터를 받음
                    BufferedReader rd;
                    if (con.getResponseCode() >= 200 && con.getResponseCode() <= 300) {
                        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    } else {
                        rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }

                    rd.close();
                    con.disconnect();
                    System.out.println(sb.toString());

                    String result = sb.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                    Log.w("stage1", "onPostexecute");

                    JSONObject obj = null;
                    //습도 : REH , 최저: TMN, 최고: TMX
                    try {
                        obj = new JSONObject(result).getJSONObject("response").getJSONObject("body").getJSONObject("items"); //객체 내에서 객체 가져오기
                        Log.w("REH,TMN,TMX JSONObject", new JSONObject(result).getJSONObject("response").getJSONObject("body").getJSONObject("items").toString());
                        String temp_string = obj.getString("item"); //실제 값을 찾을 곳
                        JSONArray array = new JSONArray(temp_string);

                        int max_temp = 0; //수분, 최고온도, 최저온도

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);
                            if (object.getString("category").equals("REH"))
                                humidity += object.getInt("fcstValue"); //수분은 시간대 별로 총 7번 있음

                            if (object.getString("category").equals("TMN"))
                                min_temp = object.getInt("fcstValue");

                            if (object.getString("category").equals("TMX"))
                                max_temp = object.getInt("fcstValue");

                        }
                        humidity = humidity / 7; //평균습도
                        sub = max_temp - min_temp; //일교차

                        Log.w("error_test", "" + humidity + ", " + sub);

                        /* 평균습도, 일교차, 최저기온을 다 가져오면
                         * 기압정보 가져오기 위한 api 호출
                         */


                        Document doc = Jsoup.connect(Constants.kma_server).post();

                        Log.w("stage3", doc.toString());

                        //테스트1
                        Elements titles = doc.select("table.table_develop3 tr");

                        System.out.println("-------------------------------------------------------------");

                        Elements pressure_elmt = titles.get((int) Constants.class.getField(param_STN.toString()).get(null)).select("td:eq(12)");

                        String result_pressure = pressure_elmt.text();

                        //기압 받아오기
                        pressure = Float.parseFloat(result_pressure);
                        Cold cold = new Cold(0, min_temp, sub, humidity, pressure);
                        result_cold = cold.cal_cold();
                        Asthma asthma = new Asthma(0, min_temp, sub, humidity, pressure);
                        result_asthma = asthma.cal_asthma();

                        editor.putInt("today", SqliteFunction.today);
                        editor.putString("result_cold", result_cold);
                        editor.putString("result_asthma", result_asthma);
                        editor.commit();
                        Log.w("result_cold sf compl: ", SqliteFunction.today + ": result_cold - " + sf.getString("result_cold", "null"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (NoSuchFieldException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }

                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                result_cold = sf.getString("result_cold", "변동");
                result_asthma = sf.getString("result_asthma", "변동");
            }
            return null;
        }
    }
}
