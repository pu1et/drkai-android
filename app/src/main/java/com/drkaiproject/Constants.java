package com.drkaiproject;

import android.content.AsyncQueryHandler;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Constants {

    //기압
    public final static int STN_0 = 84;
    public final static int STN_1 = 2;
    public final static int STN_2 = 43;
    public final static int STN_3 = 67;
    public final static int STN_4 = 51;
    public final static int STN_5 = 82;
    public final static int STN_6 = 22;
    public final static int STN_7 = 21;
    public final static int STN_8 = 68;
    public final static int STN_9 = 60;
    public final static int STN_10 = 63;
    public final static int STN_11 = 13;
    public final static int STN_12 = 33;
    public final static int STN_13 = 25;
    public final static int STN_14 = 74;
    public final static int STN_15 = 41;

    public final static String newsky_server = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getVilageFcst";
    public final static String newsky_serviceKey = "yli51XHsdqgKRA7JW9qYvwBP0CKUbxbvMSYX0ylJ3vBoiMEURfJYLNcMzDiqaHBGyltEUqTbaE6msFv04Jj%2FLg%3D%3D";
    public final static String kma_server = "https://www.weather.go.kr/weather/observation/currentweather.jsp?type=t99&mode=0&stn=0&reg=100&auto_man=a";
    public final static String atm_server = "http://apis.data.go.kr/1360000/AsosHourlyInfoService/getWthrDataList";
    public final static String index_server = "http://apis.data.go.kr/1360000/HealthWthrIdxService";
    public final static String index_serviceKey = "yli51XHsdqgKRA7JW9qYvwBP0CKUbxbvMSYX0ylJ3vBoiMEURfJYLNcMzDiqaHBGyltEUqTbaE6msFv04Jj%2FLg%3D%3D";


    public final static String base_time = "0500"; // 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300


    public final static String SERVER_URL = "http://ec2-54-203-95-119.us-west-2.compute.amazonaws.com:1337";
    public final static String Chatbot_svKey = "RlN3QXZuQ1hRTE5PU0pNSndzT3JJUkFKcmpvQ0dCV1k=";


    //RequestFuture Main UI Thread XXX, must create A New Thread
    public static class VolleySync extends AsyncTask<String, Void, JSONObject> {
        private Context mCtx;

        public VolleySync(Context ctx){
            mCtx = ctx;
        }

        @Override
        protected JSONObject doInBackground(String... params){
            final String url = params[0];
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(params[1]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            final RequestQueue queue = Volley.newRequestQueue(mCtx.getApplicationContext());
            RequestFuture<JSONObject> future = RequestFuture.newFuture();
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject, future, future);
            queue.add(request);
            JSONObject object = null;
            try{
                object = future.get(10, TimeUnit.SECONDS);
                if(object.get("result").equals("0")) return null;
                else return object;
            }catch (InterruptedException | ExecutionException | TimeoutException | JSONException e){
                e.printStackTrace();
            }
            return null;
        }
    }
    

    public static String areaNumtoStr(int area){
        String[] str_are = {"강원도(영서)", "강원도(영동)", "서울", "인천", "경기", "충북", "대전/충남", "대구/경북", "전북", "울산", "경남", "광주", "부산",
                "전남", "제주", "서귀포"};
        return str_are[area];
    }

}