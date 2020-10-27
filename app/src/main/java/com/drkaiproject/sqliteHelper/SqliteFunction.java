package com.drkaiproject.sqliteHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SqliteFunction {
    public static AllHealthSQLiteHelper allHealthSQLiteHelper;
    public static SQLiteDatabase db;
    public static int gender;
    public static int today;
    public static Context mCtx;
    public static String url = "http://ec2-54-203-95-119.us-west-2.compute.amazonaws.com:1337" + "/dayHealth_r";
    public static JSONObject jsonObject;
    public static String id;

    public static synchronized void insertData(int date_id, String date, int water, int sleep, int food, int drinking, int smoking, int exercise) {

        int result = cal_result(water, sleep, food, drinking, smoking, exercise);

        ContentValues values = new ContentValues();
        values.put("date_id", date_id);
        values.put("date", date);
        values.put("water", water);
        values.put("sleep", sleep);
        values.put("food", food);
        values.put("drinking", drinking);
        values.put("smoking", smoking);
        values.put("exercise", exercise);
        values.put("result", result);

        Log.i("AllHealthSQLite_insert", "date_id: " + date_id + ", date: " + date + ",water: " + water + ", sleep: " + sleep + ", food: " + food + ", drinking: " + drinking + ", smoking" + smoking + ", exercise: " + exercise + ", result: " + result);
        long ret = db.insert("allHealthTBL", null, values);
        Log.i("insert_ret",""+ret);

        if (ret != -1) {
            insert_dayhealth_to_server(date_id, date, water, sleep, food, drinking, smoking, exercise, result);
        }
    }
    public static synchronized void insertData(int date_id, String date, int water, int sleep, int food, int drinking, int smoking, int exercise, int result) {

        ContentValues values = new ContentValues();
        values.put("date_id", date_id);
        values.put("date", date);
        values.put("water", water);
        values.put("sleep", sleep);
        values.put("food", food);
        values.put("drinking", drinking);
        values.put("smoking", smoking);
        values.put("exercise", exercise);
        values.put("result", result);

        Log.i("AllHealthSQLite_insert", "date_id: " + date_id + ", date: " + date + ",water: " + water + ", sleep: " + sleep + ", food: " + food + ", drinking: " + drinking + ", smoking" + smoking + ", exercise: " + exercise + ", result: " + result);
        long ret = db.insert("allHealthTBL", null, values);
        Log.i("insert_ret",""+ret);

        if (ret != -1) {}
    }

    public static synchronized void updateData(int date_id, String data_name, int update_data){
        int water=0, sleep=0, food=0, drinking=0, smoking=0, exercise=0, result=0;
        String sql = "select * from allHealthTBL where date_id="+date_id+";";
        Cursor cursor = SqliteFunction.db.rawQuery(sql, null);

        try {
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    water = cursor.getInt(2); // = cursor.getInt(cursor.getColumnIndex("water"));
                    sleep = cursor.getInt(3);
                    food = cursor.getInt(4);
                    drinking = cursor.getInt(5);
                    smoking = cursor.getInt(6);
                    exercise = cursor.getInt(7);
                }
            }
        }finally {
                if (cursor != null) {
                    cursor.close();
                }
        }

        if(data_name.equals("water")) {
            water = update_data;
            result = cal_result(water, sleep, food, drinking, smoking, exercise);
        } else if(data_name.equals("sleep")){
            sleep = update_data;
            result = cal_result(water, sleep, food, drinking, smoking, exercise);
        } else if(data_name.equals("food")) {
            food = update_data;
            result = cal_result(water, sleep, food, drinking, smoking, exercise);
        } else if(data_name.equals("drinking")) {
            drinking = update_data;
            result = cal_result(water, sleep, food, drinking, smoking, exercise);
        } else if(data_name.equals("smoking")) {
            smoking = update_data;
            result = cal_result(water, sleep, food, drinking, smoking, exercise);
        } else if(data_name.equals("exercise")) {
            exercise = update_data;
            result = cal_result(water, sleep, food, drinking, smoking, exercise);
        }
        ContentValues values = new ContentValues();
        values.put(data_name, update_data);

        Log.i("AllHealthSQLite_update", "date_id: "+date_id+", "+data_name+": "+update_data);

        if(db.update("allHealthTBL",values,"date_id="+date_id, null) > 0){
            update_dayhealth_to_server(date_id, data_name, result, update_data);
        };

    }

    public static synchronized void deleteData(int date_id){
        Log.i("AllHealthSQLite_delete", "date_id: " + date_id);
        long ret = db.delete("allHealthTBL","date_id="+date_id,null);
        Log.i("delete_ret",""+ret);
        if (ret > 0) {
        delete_dayhealth_to_server(date_id);
        }
    }

    public static void insert_dayhealth_to_server(int date_id, String date, int water, int sleep, int food, int drinking, int smoking, int exercise, int result){
        jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("flag","-1");
            jsonObject.put("date_id",""+date_id);
            jsonObject.put("date",date);
            jsonObject.put("water",""+water);
            jsonObject.put("sleep",""+sleep);
            jsonObject.put("food",""+food);
            jsonObject.put("drinking",""+drinking);
            jsonObject.put("smoking",""+smoking);
            jsonObject.put("exercise",""+exercise);
            jsonObject.put("result",""+result);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resObject = new JSONObject(response.toString());
                    Log.d("return_insert_dayhealth", resObject.toString());
                    if (!resObject.getString("result").equals("1")) throw new Exception();
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

    public static void update_dayhealth_to_server(int date_id, String data_name, int result, int new_value){
        jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("flag","-1");
            jsonObject.put("date_id",""+date_id);

            if(data_name.equals("water")) jsonObject.put("flag",2);
            else if(data_name.equals("sleep")) jsonObject.put("flag",3);
            else if(data_name.equals("food")) jsonObject.put("flag",4);
            else if(data_name.equals("drinking")) jsonObject.put("flag",5);
            else if(data_name.equals("smoking")) jsonObject.put("flag",6);
            else if(data_name.equals("exercise")) jsonObject.put("flag",7);
            else throw new Exception();

            jsonObject.put("result",""+result);
            jsonObject.put("new_value",""+new_value);

        } catch (Exception e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resObject = new JSONObject(response.toString());
                    Log.d("return_update_dayhealth", resObject.toString());
                    if (!resObject.getString("result").equals("1")) throw new Exception();
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


    public static void delete_dayhealth_to_server(int date_id){
        jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("flag","-2");
            jsonObject.put("date_id",""+date_id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        final RequestQueue requestQueue = Volley.newRequestQueue(mCtx);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject resObject = new JSONObject(response.toString());
                    Log.d("return_delete_dayhealth", resObject.toString());
                    if (!resObject.getString("result").equals("1")) ;//throw new Exception();
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

    public static int cal_result(int water, int sleep, int food, int drinking, int smoking, int exercise) {
        int count = 0, result;

        if (water >= 8) count += 1;
        if (sleep >= 420 && sleep <= 540) count += 1;
        if (food > -1) count += 1; // 수정필요
        if (gender == 0) {
            if (drinking <= 1) count += 1;
        } else {
            if (drinking <= 2) count += 1;
        }
        if (smoking == 0) count += 1;
        if (exercise >= 60) count += 1;

        result = (int) (Math.round(16.0 * count / 10.0) * 10);
        return result;
    }
}
