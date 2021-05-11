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
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static com.drkaiproject.sqliteHelper.SqliteFunction.allHealthSQLiteHelper;

public class MainActivity extends AppCompatActivity {
    private MainFragment mainFragment;
    private ChatbotFragment chatbotFragment;
    private DiagFragment diagFragment;
    private HLTCareFragment hltCareFragment;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle mToggle;
    private ExpandableListView expandableListView;
    private List<String> listDataHeader;
    private HashMap<String, List<String>> listDataChild;
    private NavigationView navigationView;
    private RSVTConfFragment rsvtConfFragment;
    private SharedPreferences sf;
    private SharedPreferences.Editor editor;

    private String cold_index, asthma_index;

    private long pressTime;
    private JSONObject HLTJSONObject, caIdxJSONObject;
    String id, name, phone, age;
    int gender, area, checked;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        JSONObject fromMain;
        if(SqliteFunction.id == null) {
            try {
                fromMain = new JSONObject(intent.getExtras().getString("user"));
                id = fromMain.getString("id");
                name = fromMain.getString("name");
                age = fromMain.getString("age");
                gender = Integer.parseInt(fromMain.getString("gender"));
                area = Integer.parseInt(fromMain.getString("area"));
                phone = fromMain.getString("phone");
                checked = Integer.parseInt(fromMain.getString("checked"));

                SqliteFunction.id = id;
                SqliteFunction.gender = gender; // 0,1 (여,남)
                SqliteFunction.mCtx = MainActivity.this;

                Date today_tmp = Calendar.getInstance().getTime();
                String today_text = new SimpleDateFormat("yyyyMMdd", Locale.getDefault()).format(today_tmp);
                SqliteFunction.today = Integer.parseInt(today_text);

                allHealthSQLiteHelper = new AllHealthSQLiteHelper(MainActivity.this, "allHealthTBL.db", null, 1);
                SqliteFunction.db = allHealthSQLiteHelper.getWritableDatabase();
                //SqliteFunction.db.execSQL("delete from allHealthTBL;");
                allHealthSQLiteHelper.onCreate(SqliteFunction.db);

                sf = SqliteFunction.mCtx.getSharedPreferences("sfFile", MODE_PRIVATE);
                editor = sf.edit();

                editor.putString("user_id", id);
                editor.putString("user_name", name);
                editor.putString("user_age", age);
                editor.putInt("user_gender", gender);
                editor.putInt("user_area", area);
                editor.putString("user_phone", phone);
                editor.putInt("user_checked", checked);
                editor.apply();


            } catch (JSONException e) {
                e.printStackTrace();

            }
            Log.w("sharedpreference", sf.getString("user_id", "0"));
        }

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


        HLTJSONObject = new JSONObject();
        caIdxJSONObject = new JSONObject();

        try {
            HLTJSONObject.put("id", id);
            HLTJSONObject.put("date_id", SqliteFunction.today);
            caIdxJSONObject.put("today", SqliteFunction.today);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Thread threadA = new Thread() {
            public void run() {
                JSONObject HLTResp = null, caIdxResp = null;

                try {
                    Constants.VolleySync threadB1 = new Constants.VolleySync(getApplicationContext());
                    HLTResp = threadB1.execute(Constants.SERVER_URL + "/user/send-day-health", HLTJSONObject.toString()).get();
                    Constants.VolleySync threadB2 = new Constants.VolleySync(getApplicationContext());
                    caIdxResp = threadB2.execute(Constants.SERVER_URL + "/api/cold", caIdxJSONObject.toString()).get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
                try {
                    if (HLTResp == null && caIdxResp == null) new Exception();
                    else {
                        Log.d("HLTResp", HLTResp.toString());
                        Log.d("caIdxResp", caIdxResp.toString());

                        String dayH_data = HLTResp.getString("data");

                        JSONArray jsonArray = new JSONArray(dayH_data);
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

                        String caIdx_data = caIdxResp.getString("data");
                        JSONObject jsonObject = new JSONObject(caIdx_data);
                        cold_index = cal_caIdx(Integer.parseInt(jsonObject.getString("cold_index")));
                        asthma_index = cal_caIdx(Integer.parseInt(jsonObject.getString("asthma_index")));
                        Log.v("cold_index, asthma_index", cold_index + ", " + asthma_index);

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainFragment = new MainFragment();
                            diagFragment = new DiagFragment();
                            rsvtConfFragment = new RSVTConfFragment();
                            chatbotFragment = new ChatbotFragment();
                            diagFragment = new DiagFragment();
                            hltCareFragment = new HLTCareFragment();

                            initFragment();

                            BottomBar bottomBar = findViewById(R.id.bottombar);
                            bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
                                @Override
                                public void onTabSelected(int tabId) {
                                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                                    if (tabId == R.id.tab_main) {
                                        Bundle args = new Bundle();
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
                    });
                } catch (Exception e) {
                    e.printStackTrace();

                }
            }
        };
        threadA.start();

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

    public String cal_caIdx(int idx) {
        if (idx == 0) return "양호";
        else if (idx == 1) return "보통";
        else if (idx == 2) return "위험";
        else if (idx == 3) return "매우위험";
        return null;
    }

}
