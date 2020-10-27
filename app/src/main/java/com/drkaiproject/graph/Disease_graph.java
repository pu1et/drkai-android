package com.drkaiproject.graph;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.drkaiproject.R;
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

import java.util.ArrayList;


public class Disease_graph {

    private Context context;
    private LineChart mChart;
    private int day_lenth;
    private ArrayList<String> y_values;

    public Disease_graph(Context context, LineChart mChart, ArrayList<String> y_values, int day_lenth){
        this.context = context;
        this.mChart = mChart;
        this.y_values = y_values;
        this.day_lenth = day_lenth;
//        this.yValues = yValues;
    }

    public void showChart(ArrayList<Entry> records, String disease){ //use parameter for for,



        ArrayList<Entry> user_values = new ArrayList<>();//차트 데이터 셋에 담겨질 데이터
        for (Entry record : records) { //values에 데이터를 담는 과정
            user_values.add(record);
            Log.i("error_test", record + "this is showChart ");
        }

        mChart.animateXY(3000,3000);// 차트 생성 시 애니메이션, 시간 지정 가능
        LineDataSet set1;

        if (mChart.getData() != null && mChart.getData().getDataSetCount() > 0) {
            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
            set1.setValues(user_values);
            mChart.getData().notifyDataChanged();
            mChart.notifyDataSetChanged();
        }
        else { //그래프 처음 실행 시 여기부터
            set1 = new LineDataSet(user_values, disease); //그래프 라벨 --> 질병명을 넣어도 좋고 사용안해도 ok
            set1.setDrawIcons(false);
//            set1.enableDashedLine(10f, 5f, 0f); //그래프 선이 점선으로 보이게
//            set1.enableDashedHighlightLine(10f, 5f, 0f); //그래프 선이 점선으로 보이게
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

            // y축 설정
            YAxis yAxis = mChart.getAxisLeft(); // y축 왼쪽 데이터 가져오기.
            yAxis.setTextColor(Color.DKGRAY); // y축 텍스트 컬러 설정
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
                Drawable drawable = ContextCompat.getDrawable(context, R.drawable.ic_launcher_background);///??????
                set1.setFillDrawable(drawable);//????
            } else {
                set1.setFillColor(Color.DKGRAY);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);
            LineData data = new LineData(dataSets);
            mChart.setData(data);

        }

        mChart.getXAxis().setLabelCount(day_lenth);
        setX();

    }


    public void setX(){

        //String x []={"2019-09-09", "2019-09-10", "2019-09-11", "2019-09-12"};
        Log.i("error_test", "test" + day_lenth);
        final ArrayList<String> xAxes = new ArrayList<>();
        for (int i=0; i < day_lenth; i++) {
            xAxes.add(i, y_values.get(i)); //Dynamic x-axis labels
            Log.i("error_test", xAxes.get(i));
        }

        mChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                return xAxes.get(index);
            }
        });
    }

}