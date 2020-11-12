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

    public final static String X_0 = "73";
    public final static String X_1 = "92";
    public final static String X_2 = "60";
    public final static String X_3 = "55";
    public final static String X_4 = "60";
    public final static String X_5 = "69";
    public final static String X_6 = "67";
    public final static String X_7 = "89";
    public final static String X_8 = "63";
    public final static String X_9 = "102";
    public final static String X_10 = "91";
    public final static String X_11 = "58";
    public final static String X_12 = "98";
    public final static String X_13 = "51";
    public final static String X_14 = "53";
    public final static String X_15 = "52";

    public final static String Y_0 = "134";
    public final static String Y_1 = "131";
    public final static String Y_2 = "127";
    public final static String Y_3 = "124";
    public final static String Y_4 = "120";
    public final static String Y_5 = "107";
    public final static String Y_6 = "100";
    public final static String Y_7 = "90";
    public final static String Y_8 = "89";
    public final static String Y_9 = "84";
    public final static String Y_10 = "77";
    public final static String Y_11 = "74";
    public final static String Y_12 = "76";
    public final static String Y_13 = "67";
    public final static String Y_14 = "38";
    public final static String Y_15 = "33";


    public final static String base_time = "0500"; // 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300

    //개선 알고리즘 가중치
    public final static int lung_gravity [] = {2, 2, 2, 2, 3, 2, 1, 2};
    public final static int diabetes_gravity [] = {3, 3, 3, 3, 2, 2, 1, 1};
    public final static int strok_gravity [] = {2, 2, 2, 2, 3, 2, 1, 1};
    public final static int myocardial_gravity [] = {3, 3, 3, 3, 3, 2, 1, 1};
    public final static int depress_gravity [] = {3, 3, 3, 3, 1, 2, 1, 2};
    public final static int stomach_gravity [] = {2, 3, 3, 3, 3, 3, 1, 2};
    public final static int hepatitis_gravity [] = {1, 3, 3, 3, 2, 2, 1, 3};
    public final static int cirrhosis_gravity [] = {2, 3, 3, 3, 1, 3, 1, 2};

    public final static String SERVER_URL = "http://ec2-54-203-95-119.us-west-2.compute.amazonaws.com:1337";
    // public final static String SERVER_URL = "http://192.168.43.204:1337";


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
                if(object.get("result").equals("1")) return object;
                else return null;
            }catch (InterruptedException | ExecutionException | TimeoutException | JSONException e){
                e.printStackTrace();
            }
            return null;
        }
    }

}