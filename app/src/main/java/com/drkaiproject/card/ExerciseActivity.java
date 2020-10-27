package com.drkaiproject.card;

import android.content.Intent;
import android.graphics.Color;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.drkaiproject.R;
import com.drkaiproject.sqliteHelper.SqliteFunction;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class ExerciseActivity extends AppCompatActivity {

    TextView stepsView, steps, unit;
    PieModel sliceGoal, sliceCurrent;
    PieChart pg;
    SensorListener listener;
    SensorManager manager;
    int DEFAULT_GOAL = 10000; //목표 걸음수

    int todayOffset, goal, since_boot;
    NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
    boolean showSteps = true;

    TextView date_view;
    EditText km_length;
    JSONObject jsonObject;
    String str, exercise_data;
    Timer timer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        Intent intent = getIntent();
        str = intent.getExtras().getString("data");
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();




        date_view = (TextView) findViewById(R.id.date_view);
        unit = (TextView) findViewById(R.id.unit);
        km_length = (EditText) findViewById(R.id.km_length);

        Date today = new Date();

        SimpleDateFormat date = new SimpleDateFormat("yyyy/MM/dd");
        date_view.setText(date.format(today));

        //stepsView = (TextView) findViewById(R.id.steps);
        steps = (TextView) findViewById(R.id.steps);

        try {
            jsonObject = new JSONObject(str);
            exercise_data = jsonObject.getString("exercise");
            km_length.setText(exercise_data);
            km_length.setSelection(km_length.length());
            sliceCurrent = new PieModel("Exercise", Integer.parseInt(exercise_data), Color.parseColor("#43BFB0")); //현재 걸음수
            steps.setText(exercise_data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //listener = new SensorListener();

        pg = (PieChart) findViewById(R.id.graph);


        pg.addPieSlice(sliceCurrent);

        sliceGoal = new PieModel("", 100- Integer.parseInt(exercise_data), Color.parseColor("#C8C7C7")); //목표 걸음수
        pg.addPieSlice(sliceGoal);

        pg.setDrawValueInPie(false);
        pg.setUsePieRotation(true);
        pg.startAnimation();


        km_length.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(timer!= null){
                    timer.cancel();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                final String tmp = editable.toString();
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if(tmp.contains("\n")) {
                            tmp.replace("\n","");
                        }else{
                            if(tmp.equals("")) SqliteFunction.updateData(SqliteFunction.today,"exercise",0);
                            else SqliteFunction.updateData(SqliteFunction.today,"exercise", Integer.parseInt(tmp));
                        }
                    }
                }, 600);
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();

        /*
        if (BuildConfig.DEBUG) ;

        SharedPreferences prefs = getSharedPreferences("", Context.MODE_PRIVATE);

        goal = prefs.getInt("goal", DEFAULT_GOAL);
        int pauseDifference = since_boot - prefs.getInt("", since_boot);

        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);

        manager.registerListener(listener, sensor, SensorManager.SENSOR_DELAY_UI);
        since_boot -= pauseDifference;
         */
    }
/*
    public void updatePie() {

        int steps_today = max(todayOffset + since_boot, 0);
        float km = (float) steps_today / (float) 1000;

        sliceCurrent.setValue(steps_today);
        if (goal - steps_today > 0) {
            if (pg.getData().size() == 1) {
                pg.addPieSlice(sliceGoal);
            }
            sliceGoal.setValue(goal - steps_today);
        } else {
            // goal reached
            pg.clearChart();
            pg.addPieSlice(sliceCurrent);
        }
        pg.update();
        if (showSteps) {
            stepsView.setText(formatter.format(steps_today));
            km_length.setText(formatter.format(km));
        } else {
            SharedPreferences prefs = getSharedPreferences("", Context.MODE_PRIVATE);
        }
    }

    class SensorListener implements SensorEventListener {
        @Override
        public void onAccuracyChanged(final Sensor sensor, int accuracy) {

        }

        @Override
        public void onSensorChanged(final SensorEvent event) {

            if (event.values[0] > Integer.MAX_VALUE || event.values[0] == 0) {
                return;
            }
            if (todayOffset == Integer.MIN_VALUE) {

                todayOffset = -(int) event.values[0];

            }
            since_boot = (int) event.values[0];
            updatePie();
        }
    }

 */

    //뒤로가기
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}


