package com.drkaiproject.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.drkaiproject.Constants;
import com.drkaiproject.OnSwipeTouchListener;
import com.drkaiproject.R;
import com.drkaiproject.adapter.DiseaseAdapter;
import com.drkaiproject.card.AlcoholActivity;
import com.drkaiproject.card.ExerciseActivity;
import com.drkaiproject.card.SleepActivity;
import com.drkaiproject.card.SmokeActivity;
import com.drkaiproject.card.WaterActivity;
import com.drkaiproject.model.Disease;
import com.drkaiproject.sqliteHelper.SqliteFunction;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;
import static com.drkaiproject.sqliteHelper.SqliteFunction.db;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HLTCareFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ScrollView scrollView;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private CardView card_exercise, card_water, card_sleep, card_food, card_alcohol, card_smoke;

    //SQLite
    TextView exer_txt;
    TextView water_txt;
    TextView hour_txt;
    TextView min_txt;
    TextView food_txt;
    TextView alcohol_txt;
    TextView smoke_txt;


    static ArrayList<Disease> totalDisease2;
    ArrayList<Disease> disease = new ArrayList<Disease>();
    DiseaseAdapter adapter;

    //info_graph: 그래프 뷰
    private LineChart mChart;
    private ArrayList<Entry> y_values;
    private ArrayList<String> label_values;

    //info_graph: 그래프 뷰 end

    ListView disease_lv;

    private String id, name;
    private int testmode = 0; // 1이면 testmode
    Intent intent;

    String[] date_6days = new String[7];
    int[] result_6days = new int[7];

    JSONObject jsonObject;
    String diabetes_risk, myocardial_risk, depression_risk, hepatitisA_risk, hepatitisB_risk, hepatitisC_risk, cirrhosis_risk, stroke_risk, gastriculcer_risk, lungcancer_risk, lungdisease_risk;
    ArrayList<Disease> totalDisease;
    private ActionBar actionBar;
    private TextView textView1;
    SharedPreferences sf;

    public HLTCareFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().invalidateOptionsMenu();
        getAllHealth();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_hltcare, container, false);

        // Sqlite 기본설정 -> MainFragment에 설정
        sf = SqliteFunction.mCtx.getSharedPreferences("sfFile", MODE_PRIVATE);
        name = sf.getString("user_name", "안지원");
        id = sf.getString("user_id", "hoho1911");

        final TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(name + "님, 오늘의 상태를 기록해보세요");

        totalDisease = new ArrayList<Disease>();

        //SQLite TextView
        exer_txt = (TextView) view.findViewById(R.id.exer_txt);
        water_txt = (TextView) view.findViewById(R.id.water_txt);
        hour_txt = (TextView) view.findViewById(R.id.hour_txt);
        min_txt = (TextView) view.findViewById(R.id.min_txt);
        food_txt = (TextView) view.findViewById(R.id.food_txt);
        alcohol_txt = (TextView) view.findViewById(R.id.alcohol_txt);
        smoke_txt = (TextView) view.findViewById(R.id.smoke_txt);

        card_exercise = (CardView) view.findViewById(R.id.card_exercise);
        card_water = (CardView) view.findViewById(R.id.card_water);
        card_sleep = (CardView) view.findViewById(R.id.card_sleep);
        card_food = (CardView) view.findViewById(R.id.card_food);
        card_alcohol = (CardView) view.findViewById(R.id.card_alcohol);
        card_smoke = (CardView) view.findViewById(R.id.card_smoke);

        //리스트뷰
        disease_lv = (ListView) view.findViewById(R.id.disease_lv);

        card_exercise.setOnClickListener(this);
        card_water.setOnClickListener(this);
        card_sleep.setOnClickListener(this);
        card_food.setOnClickListener(this);
        card_alcohol.setOnClickListener(this);
        card_smoke.setOnClickListener(this);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        textView1 = (TextView) view.findViewById(R.id.app_name);

        getAllHealth(); // 오늘 6대 요소 데이터 받아

        receiveDisease(); // 서버로부터 데이터 받아옴

        //info_graph: 그래프 이벤트 지정
        mChart = (LineChart) view.findViewById(R.id.chart);
        y_values = new ArrayList<>();
        label_values = new ArrayList<>();

        showData();

        mChart.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
                //Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                SharedPreferences sf = getActivity().getSharedPreferences("sfFile", MODE_PRIVATE);
                id = sf.getString("user_id", "hoho1911");  //id만 원하는 걸로 바꾸기
                showData();
            }

            public void onSwipeLeft() {
                SharedPreferences spn = getActivity().getSharedPreferences("sfFile", MODE_PRIVATE);
                id = sf.getString("user_id", "hoho1911");  //id만 원하는 걸로 바꾸기
                showData();

            }

            public void onSwipeBottom() {
            }
        });

        scrollView = (ScrollView) view.findViewById(R.id.scrollview);
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
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onStop() {
        Log.w("fragment changed", "HLTCareFragment Stop");
        scrollView.scrollTo(scrollView.getScrollX(), 0);
        super.onStop();
    }

    public void showData() {
        y_values.clear();

        int length = result_6days.length;

        for (int i = 0; i < length; i++) {
            String tmp = date_6days[length - 1 - i];
            tmp = tmp.substring(5,7) + "/" + tmp.substring(8);
            Log.i("showData tmp",tmp);
            label_values.add(tmp);

            y_values.add(new Entry(i, result_6days[length-1-i]));
        }
        showChart(y_values, "건강지킴이"); //그래프에 넣을 값 지정, 질병이름
    }

    public void receiveDisease() {

        String url = Constants.SERVER_URL + "/user/send-all-disease";

        jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(getContext());
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
            adapter = new DiseaseAdapter(getActivity(), disease, R.layout.disease_row);
            disease_lv.setAdapter(adapter);
            setListViewHeightBasedOnChildren(disease_lv);
        }
    }

    public void showChart(ArrayList<Entry> records, String disease) { //use parameter for for,

        ArrayList<Entry> user_values = new ArrayList<>();//차트 데이터 셋에 담겨질 데이터
        for (Entry record : records) { //values에 데이터를 담는 과정
            user_values.add(record);
        }

        mChart.animateXY(3000, 3000);// 차트 생성 시 애니메이션, 시간 지정 가능

        LineDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(user_values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        } else { //그래프 처음 실행 시 여기부터
            set1 = new LineDataSet(user_values, disease); //그래프 라벨 --> 질병명을 넣어도 좋고 사용안해도 ok
            set1.setDrawIcons(false);
            set1.setColor(Color.DKGRAY); //그래프 선 색
            set1.setCircleColor(Color.DKGRAY); //그래프 가운데 점 색
            set1.setLineWidth(2f); //그래프 선 두께
            set1.setCircleRadius(3f); //그래프 가운데 점 두께
            set1.setDrawCircleHole(false); //그래프 가운데 점, 즉, 원 안에 색을 넣을것인지 말것인지, 지금은 채운 원
            set1.setValueTextSize(9f); // 그래프에 들어간 값
            set1.setDrawFilled(false); //그래프 선 아래를 채울것인지 말것인지, 지금은 안채운 상태
            set1.setFormLineWidth(1f);
            set1.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            set1.setFormSize(15.f);
            set1.setMode(LineDataSet.Mode.CUBIC_BEZIER); //뾰족뾰족 선에서 곡선로 변형

            //밑에 3줄: x축 라벨 위치 바꾸기, 디폴트는 위지만 하단으로 이동시켜놓음
            XAxis xAxis = mChart.getXAxis(); // x축에 대한 정보를 View로부터 받아온다.
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // x축 표시에 대한 위치 설정으로 아래쪽에 위치시킨다.
            xAxis.setTextColor(Color.DKGRAY); // x축 텍스트 컬러 설정
            xAxis.setSpaceMax(0.65f); // 차트의 마지막과 x축 사이의 간

            xAxis.setValueFormatter(new IAxisValueFormatter() {
                @Override
                public String getFormattedValue(float value, AxisBase axis) {
                    if (value >= 0) {
                        if (value <= label_values.size() - 1) {
                            return label_values.get((int) value);
                        }
                        return "";
                    }
                    return "";
                }
            });
            xAxis.setLabelCount(label_values.size());


            // y축 설정
            YAxis yAxis = mChart.getAxisLeft(); // y축 왼쪽 데이터 가져오기.
            yAxis.setTextColor(Color.DKGRAY); // y축 텍스트 컬러 설정FF
            yAxis.setAxisMaximum((float)101);
            // y축 오른쪽 비활성화 (디폴트는 왼쪽 오른쪽에 라벨 보임, 값의 변화가 급격하지 않다면 하나만 써도 될 듯)
            yAxis = mChart.getAxisRight();
            yAxis.setDrawLabels(false);
            yAxis.setDrawAxisLine(false);
            yAxis.setDrawGridLines(false);

            //x축, y축 배경 그리드 지우기
            mChart.getAxisLeft().setDrawGridLines(false);
            mChart.getXAxis().setDrawGridLines(false);


            mChart.setPinchZoom(true);
            // disable description text
            mChart.getDescription().setEnabled(false);
            // enable touch gestures
            mChart.setTouchEnabled(true);
            // 줌인, 줌아웃, 에뮬레이터에서는 테스트가 안되므로 실기기에서 줌인 아웃 되는지 테스트
            mChart.setDragEnabled(true);
            mChart.setScaleEnabled(true);


            if (Utils.getSDKInt() >= 18) {
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.ic_launcher_background);///??????
                set1.setFillDrawable(drawable);//????
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);

        }
    }

    public int calRisk(double prob) {
        if (prob < 0.2) return 1;
        else if (prob >= 0.2 && prob < 0.4) return 2;
        else if (prob >= 0.4) return 3;
        else return 0;
    }

    @Override
    public void onClick(View v) {
        Intent i;

        switch (v.getId()) {
            case R.id.card_exercise:
                intent = new Intent(v.getContext(), ExerciseActivity.class);
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("exercise", exer_txt.getText());
                }catch (Exception e){
                    e.printStackTrace();
                }
                intent.putExtra("data", jsonObject.toString());
                startActivity(intent);
                break;
            case R.id.card_water:
                intent = new Intent(v.getContext(), WaterActivity.class);
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("water", water_txt.getText());
                }catch (Exception e){
                    e.printStackTrace();
                }
                intent.putExtra("data", jsonObject.toString());
                startActivity(intent);
                break;
            case R.id.card_sleep:
                intent = new Intent(v.getContext(), SleepActivity.class);
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("sleep_hour", hour_txt.getText());
                    jsonObject.put("sleep_min", min_txt.getText());
                }catch (Exception e){
                    e.printStackTrace();
                }
                intent.putExtra("data", jsonObject.toString());
                startActivity(intent);
                break;
            case R.id.card_food:
                //intent = new Intent(v.getContext(), FoodActivity.class);
                //startActivity(intent);
                Toast.makeText(v.getContext(), "개발 중입니다.", Toast.LENGTH_LONG);
                break;
            case R.id.card_alcohol:
                intent = new Intent(v.getContext(), AlcoholActivity.class);
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("drinking", alcohol_txt.getText());
                }catch (Exception e){
                    e.printStackTrace();
                }
                intent.putExtra("data", jsonObject.toString());
                startActivity(intent);
                break;
            case R.id.card_smoke:
                intent = new Intent(v.getContext(), SmokeActivity.class);
                try {
                    jsonObject = new JSONObject();
                    jsonObject.put("smoking", smoke_txt.getText());
                }catch (Exception e){
                    e.printStackTrace();
                }
                intent.putExtra("data", jsonObject.toString());
                startActivity(intent);
                break;
            default:
                break;
        }
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

    public void getAllHealth(){

        if (testmode == 1) { // 데이터 없는 상태

            SqliteFunction.deleteData(20201014);
            SqliteFunction.deleteData(20201015);
            SqliteFunction.deleteData(20201016);
            SqliteFunction.deleteData(20201017);
            SqliteFunction.deleteData(20201018);
            SqliteFunction.deleteData(20201019);
            SqliteFunction.deleteData(20201020);
            SqliteFunction.deleteData(20201021);

            SqliteFunction.insertData(20201014, "2020-10-14", 7, 50, 1000, 0, 1, 0);
            SqliteFunction.insertData(20201015, "2020-10-15", 8, 420, 1000, 1, 0, 60);
            SqliteFunction.insertData(20201016, "2020-10-16", 7, 60, 1000, 1, 1, 30);
            SqliteFunction.insertData(20201017, "2020-10-17", 1, 600, 1000, 1, 0, 30);
            SqliteFunction.insertData(20201018, "2020-10-18", 8, 420, 1000, 1, 0, 60);
            SqliteFunction.insertData(20201019, "2020-10-19", 7, 60, 1000, 1, 1, 30);
            SqliteFunction.insertData(20201020, "2020-10-20", 1, 600, 1000, 1, 1, 30);
            SqliteFunction.insertData(20201021, "2020-10-21", 8, 540, 1000, 1, 0, 30);


        }



        int date_id, water, sleep, food, drinking, smoking, exercise;
        int i=0;

        Log.i("오늘 날짜 : ",""+SqliteFunction.today);
        String sql = "select * from allHealthTBL order by date_id desc limit 7" ;
        Cursor cursor = SqliteFunction.db.rawQuery(sql, null);
        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {


                    date_id = cursor.getInt(0);
                    date_6days[i] = cursor.getString(1);
                    water = cursor.getInt(2); // = cursor.getInt(cursor.getColumnIndex("water"));
                    sleep = cursor.getInt(3);
                    food = cursor.getInt(4);
                    drinking = cursor.getInt(5);
                    smoking = cursor.getInt(6);
                    exercise = cursor.getInt(7);
                    result_6days[i++] = cursor.getInt(8);

                    if (SqliteFunction.today == date_id) {
                        Log.i("오늘 날짜 설정", ""+date_id);
                        //water
                        water_txt.setText(String.valueOf(water));
                        //sleep
                        hour_txt.setText(String.valueOf(sleep/60));
                        min_txt.setText(String.valueOf(sleep%60));
                        //food
                        food_txt.setText(String.valueOf(food));
                        //smoking
                        smoke_txt.setText(String.valueOf(smoking));
                        //drinking
                        alcohol_txt.setText(String.valueOf(drinking));
                        //exercise
                        exer_txt.setText(String.valueOf(exercise));
                    }
                    Log.i("AllHealthSQLite_select", "date_id: "+date_id+", date: "+date_6days[i-1]+",water: "+water+", sleep: "+sleep+", food: "+food+", drinking: "+drinking+", smoking: "+smoking+", exercise: "+exercise + ", result: "+ result_6days[i-1]);
                }
            }
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }
}