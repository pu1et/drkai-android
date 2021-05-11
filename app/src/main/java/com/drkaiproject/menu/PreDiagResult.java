package com.drkaiproject.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.drkaiproject.Constants;
import com.drkaiproject.MainActivity;
import com.drkaiproject.R;
import com.drkaiproject.adapter.DiseaseAdapter;
import com.drkaiproject.model.Disease;
import com.drkaiproject.sqliteHelper.SqliteFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PreDiagResult extends AppCompatActivity {
    private ScrollView scrollView;

    static ArrayList<Disease> totalDisease2;
    ArrayList<Disease> disease = new ArrayList<Disease>();
    DiseaseAdapter adapter;
    ListView disease_lv;

    String name;

    JSONObject jsonObject;
    String diabetes_risk, myocardial_risk, depression_risk, hepatitisA_risk, hepatitisB_risk, hepatitisC_risk, cirrhosis_risk, stroke_risk, gastriculcer_risk, lungcancer_risk, lungdisease_risk;
    ArrayList<Disease> totalDisease;
    SharedPreferences sp;
    private ActionBar actionBar;
    private TextView textView1;
    ConstraintLayout go_main;
    SharedPreferences sf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediagresult);

        sf = SqliteFunction.mCtx.getSharedPreferences("sfFile", MODE_PRIVATE);
        name = sf.getString("user_name","안지원");
        final TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(name + "님, 오늘의 상태를 기록해보세요");

        totalDisease = new ArrayList<Disease>();

        //리스트뷰
        disease_lv = findViewById(R.id.disease_lv);

        actionBar = getSupportActionBar();
        textView1 = findViewById(R.id.app_name);
        go_main = findViewById(R.id.go_main);


        receiveDisease(); // 서버로부터 데이터 받아옴


        scrollView = (ScrollView) findViewById(R.id.scrollview);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {

            @Override
            public void onScrollChanged() {
                int scrollX = scrollView.getScrollX();
                int scrollY = scrollView.getScrollY();
                Log.w("scrollY", String.valueOf(scrollY));
                if (scrollY > 300) {
                    actionBar.hide();
                } else actionBar.show();
            }
        });

        go_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PreDiagResult.this, MainActivity.class);
                startActivity(intent);
            }
        });

  
    }
    public void receiveDisease() {

        String url = Constants.SERVER_URL + "/user/send-all-disease";

        jsonObject = new JSONObject();
        try {
            jsonObject.put("id", SqliteFunction.id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(PreDiagResult.this);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    int iaa = 0;
                    JSONObject resObject = new JSONObject(response.toString());
                    Log.d("receiveData", resObject.toString());
                    if (!resObject.getString("result").equals("1")) throw new Exception();
                    else {
                        resObject = resObject.getJSONObject("data");
                        Disease diabetes, myocardial, hepatitisA, hepatitisB, hepatitisC, depression, cirrhosis, stroke, gastriculcer, lungcancer, lungdisease;

                        totalDisease2 = new ArrayList<Disease>();

                        diabetes_risk = resObject.getString("diabetes");
                        if (!diabetes_risk.equals("None")) {
                            diabetes = new Disease("당뇨병", Double.parseDouble(diabetes_risk), calRisk(Double.parseDouble(diabetes_risk)));
                            totalDisease2.add(diabetes);
                            iaa += 1;
                        } else {
                            diabetes = new Disease("당뇨병", 0.0, calRisk(0.0));
                        }
                        // totalDisease2.add(diabetes);

                        myocardial_risk = resObject.getString("myocardial");
                        if (!myocardial_risk.equals("None")) {
                            myocardial = new Disease("심근경색", Double.parseDouble(myocardial_risk), calRisk(Double.parseDouble(myocardial_risk)));
                            totalDisease2.add(myocardial);
                            iaa += 1;
                        } else {
                            myocardial = new Disease("심근경색", 0.0, calRisk(0.0));
                        }


                        hepatitisA_risk = resObject.getString("hepatitisA");
                        if (!hepatitisA_risk.equals("None")) {
                            hepatitisA = new Disease("A형간염", Double.parseDouble(hepatitisA_risk), calRisk(Double.parseDouble(hepatitisA_risk)));
                            totalDisease2.add(hepatitisA);
                            iaa += 1;
                        } else {
                            hepatitisA = new Disease("A형간염", 0.0, calRisk(0.0));
                        }

                        hepatitisB_risk = resObject.getString("hepatitisB");
                        if (!hepatitisB_risk.equals("None")) {
                            hepatitisB = new Disease("B형간염", Double.parseDouble(hepatitisB_risk), calRisk(Double.parseDouble(hepatitisB_risk)));
                            totalDisease2.add(hepatitisB);
                            iaa += 1;
                        } else {
                            hepatitisB = new Disease("B형간염", 0.0, calRisk(0.0));
                        }

                        hepatitisC_risk = resObject.getString("hepatitisC");
                        if (!hepatitisC_risk.equals("None")) {
                            hepatitisC = new Disease("C형간염", Double.parseDouble(hepatitisC_risk), calRisk(Double.parseDouble(hepatitisC_risk)));
                            totalDisease2.add(hepatitisC);
                            iaa += 1;
                        } else {
                            hepatitisC = new Disease("C형간염", 0.0, calRisk(0.0));
                        }

                        cirrhosis_risk = resObject.getString("cirrhosis");
                        if (!cirrhosis_risk.equals("None")) {
                            cirrhosis = new Disease("간경화", Double.parseDouble(cirrhosis_risk), calRisk(Double.parseDouble(cirrhosis_risk)));
                            totalDisease2.add(cirrhosis);
                            iaa += 1;
                        } else {
                            cirrhosis = new Disease("간경화", 0.0, calRisk(0.0));
                        }

                        depression_risk = resObject.getString("depression");
                        if (!depression_risk.equals("None")) {
                            double dep_risk = Double.parseDouble(depression_risk);
                            String value_txt = "";
                            if (dep_risk < 20) value_txt = "매우 양호";
                            else if (dep_risk >= 20 & dep_risk < 40) value_txt = "양호";
                            else if (dep_risk >= 40 & dep_risk < 60) value_txt = "보통";
                            else if (dep_risk >= 60 & dep_risk < 80) value_txt = "주의";
                            else value_txt = "매우 주의";

                            depression = new Disease("우울증", value_txt);
                            totalDisease2.add(depression);
                            iaa += 1;
                        } else {
                            depression = new Disease("우울증", 0.0, calRisk(0.0));
                        }

                        gastriculcer_risk = resObject.getString("gastriculcer");
                        if (!gastriculcer_risk.equals("None")) {
                            gastriculcer = new Disease("위궤양", Double.parseDouble(gastriculcer_risk), calRisk(Double.parseDouble(gastriculcer_risk)));
                            totalDisease2.add(gastriculcer);
                            iaa += 1;
                        } else {
                            gastriculcer = new Disease("위궤양", 0.0, calRisk(0.0));
                        }

                        lungcancer_risk = resObject.getString("lungcancer");
                        if (!lungcancer_risk.equals("None")) {
                            lungcancer = new Disease("폐암", Double.parseDouble(lungcancer_risk), calRisk(Double.parseDouble(lungcancer_risk)));
                            totalDisease2.add(lungcancer);
                            iaa += 1;
                        } else {
                            lungcancer = new Disease("폐암", 0.0, calRisk(0.0));
                        }

                        lungdisease_risk = resObject.getString("lungdisease");
                        if (!lungdisease_risk.equals("None")) {
                            lungdisease = new Disease("폐질환", Double.parseDouble(lungdisease_risk), calRisk(Double.parseDouble(lungdisease_risk)));
                            totalDisease2.add(lungdisease);
                            iaa += 1;
                        } else {
                            lungdisease = new Disease("폐질환", 0.0, calRisk(0.0));
                        }

                        stroke_risk = resObject.getString("stroke");
                        if (!stroke_risk.equals("None")) {
                            stroke = new Disease("뇌졸중", Double.parseDouble(stroke_risk), calRisk(Double.parseDouble(stroke_risk)));
                            totalDisease2.add(stroke);
                            iaa += 1;
                        } else {
                            stroke = new Disease("뇌졸중", 0.0, calRisk(0.0));
                        }
                        Log.d("totaldisease2_num", Integer.toString(iaa));
                        set();
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
    }

    public void set() {
        //머신러닝에서 데이터 가져오면 보여주기
        for (Disease e : totalDisease2) {
            Log.d("set", e.toString());
            disease.add(e); //질병 하나씩 추가
            adapter = new DiseaseAdapter(PreDiagResult.this, disease, R.layout.disease_row);
            disease_lv.setAdapter(adapter);
            setListViewHeightBasedOnChildren(disease_lv);
        }
    }
    

    public int calRisk(double prob) {
        if (prob < 0.2) return 1;
        else if (prob >= 0.2 && prob < 0.4) return 2;
        else if (prob >= 0.4) return 3;
        else return 0;
    }
    

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        Log.d("setListView", Integer.toString(listAdapter.getCount()));
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);

        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        Log.d("totalHeight", Integer.toString(totalHeight));

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
