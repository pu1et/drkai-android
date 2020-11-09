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

public class MainActivity extends AppCompatActivity {
    private MainFragment mainFragment;
    private RMTDiagFragment rmtDiagFragment;
    private ChatbotFragment chatbotFragment;
    private DiagFragment diagFragment;
    private HLTCareFragment hltCareFragment;
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
    private String cold_index, asthma_index;

    private long pressTime;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SqliteFunction.id = "1";
        SqliteFunction.gender = 0; // 여자
        SqliteFunction.mCtx = MainActivity.this;

        Date today_tmp = Calendar.getInstance().getTime();
        String today_text = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(today_tmp);
        SqliteFunction.today = Integer.parseInt(today_text);

        allHealthSQLiteHelper = new AllHealthSQLiteHelper(MainActivity.this, "allHealthTBL.db", null, 1);
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


        StringBuilder coldUrlBuilder = new StringBuilder(Constants.index_server + "/getColdIdx");
        StringBuilder asthmaUrlBuilder = new StringBuilder(Constants.index_server + "/getAsthmaIdx");

        try {

            Calendar calendar = Calendar.getInstance();
            //calendar.add(Calendar.DATE, 1);
            Date today = calendar.getTime();


            String dateformat = "yyyyMMdd";
            String dateformat2 = "HH";
            String todaystr = new SimpleDateFormat(dateformat).format(today);
            int todaystr2 = Integer.parseInt(new SimpleDateFormat(dateformat2).format(today));
            Log.w("today is : ", todaystr + todaystr2);
            if (todaystr2 < 06) todaystr = String.valueOf(Integer.parseInt(todaystr) - 1);
            Log.w("[setting] today is : ", todaystr + todaystr2);

            coldUrlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + Constants.index_serviceKey); //Service Key
            coldUrlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); //페이지 번호
            coldUrlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("70", "UTF-8")); //한 페이지 결과 수
            coldUrlBuilder.append("&" + URLEncoder.encode("areaNo", "UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); //05시 발표 * 기술문서 참조
            coldUrlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); //xml(기본값), json
            coldUrlBuilder.append("&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(todaystr + todaystr2, "UTF-8"));

            asthmaUrlBuilder.append("?" + URLEncoder.encode("ServiceKey", "UTF-8") + "=" + Constants.index_serviceKey); //Service Key
            asthmaUrlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); //페이지 번호
            asthmaUrlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("70", "UTF-8")); //한 페이지 결과 수
            asthmaUrlBuilder.append("&" + URLEncoder.encode("areaNo", "UTF-8") + "=" + URLEncoder.encode("1100000000", "UTF-8")); //05시 발표 * 기술문서 참조
            asthmaUrlBuilder.append("&" + URLEncoder.encode("dataType", "UTF-8") + "=" + URLEncoder.encode("JSON", "UTF-8")); //xml(기본값), json
            asthmaUrlBuilder.append("&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(todaystr + todaystr2, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.w("error_test", coldUrlBuilder.toString());


        AsyncTask<String, String, String> finish_check = new JSONTask().execute(coldUrlBuilder.toString(), asthmaUrlBuilder.toString());//AsyncTask 시작시킴

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
                    Log.w("result_cold", cold_index);
                    Log.w("result_asthma", asthma_index);
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("cold_index", cold_index);
                        jsonObject.put("asthma_index", asthma_index);
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
            Toast.makeText(MainActivity.this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
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
            String url_dayhealth = Constants.SERVER_URL + "/dayHealth_s";

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

                    ///////////////////////////////////////////////////////////////////////////////////////
                    URL url = new URL(urls[0]);

                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(50000);
                    con.setReadTimeout(50000);
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

                    obj = new JSONObject(result).getJSONObject("response").getJSONObject("body").getJSONObject("items"); //객체 내에서 객체 가져오기

                    JSONArray temp_string = obj.getJSONArray("item"); //실제 값을 찾을 곳
                    JSONObject tmp = temp_string.getJSONObject(0);
                    Log.w("index_url_result", tmp.toString());

                    cold_index = tmp.get("today").toString();
                    Log.w("index_cold_index", cold_index);
                    if (cold_index.equals("")) cold_index = "0";

                    switch (Integer.parseInt(cold_index)) {
                        case 0:
                            cold_index = "양호";
                            break;
                        case 1:
                            cold_index = "보통";
                            break;
                        case 2:
                            cold_index = "위험";
                            break;
                        case 3:
                            cold_index = "매우위험";
                            break;
                    }


                    editor.putInt("today", SqliteFunction.today);
                    editor.putString("cold_index", cold_index);
                    Log.w("cold sf compl: ", SqliteFunction.today + ": cold_index - " + sf.getString("cold_index", "null"));


                    ///////////////////////////////////////////////////////////////////////////////////////
                    url = new URL(urls[1]);

                    con = (HttpURLConnection) url.openConnection();
                    con.setRequestMethod("GET");
                    con.setConnectTimeout(100000);
                    con.setReadTimeout(100000);
                    con.setRequestProperty("Content-Type", "application/json");
                    con.setRequestProperty("Accept", "application/json");
                    con.setDoInput(true); //  HERE
                    con.connect();

                    //서버로 부터 데이터를 받음

                    if (con.getResponseCode() >= 200 && con.getResponseCode() <= 300) {
                        rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    } else {
                        rd = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    }
                    sb = new StringBuilder();

                    while ((line = rd.readLine()) != null) {
                        sb.append(line);
                    }

                    rd.close();
                    con.disconnect();
                    System.out.println(sb.toString());

                    result = sb.toString();//서버로 부터 받은 값을 리턴해줌 아마 OK!!가 들어올것임

                    Log.w("stage1", "onPostexecute");

                    obj = new JSONObject(result).getJSONObject("response").getJSONObject("body").getJSONObject("items"); //객체 내에서 객체 가져오기
                    temp_string = obj.getJSONArray("item"); //실제 값을 찾을 곳
                    tmp = temp_string.getJSONObject(0);
                    Log.w("index_url_result", tmp.toString());

                    asthma_index = tmp.get("today").toString();
                    Log.w("index_asthma_index", asthma_index);
                    if (asthma_index.equals("")) asthma_index = "0";

                    switch (Integer.parseInt(asthma_index)) {
                        case 0:
                            asthma_index = "양호";
                            break;
                        case 1:
                            asthma_index = "보통";
                            break;
                        case 2:
                            asthma_index = "위험";
                            break;
                        case 3:
                            asthma_index = "매우위험";
                            break;
                    }


                    editor.putString("asthma_index", asthma_index);
                    editor.commit();
                    Log.w("asthma sf compl: ", SqliteFunction.today + ": asthma_index - " + sf.getString("asthma_index", "null"));


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                cold_index = sf.getString("cold_index", "변동");
                asthma_index = sf.getString("asthma_index", "변동");
            }
            return null;
        }
    }
}
