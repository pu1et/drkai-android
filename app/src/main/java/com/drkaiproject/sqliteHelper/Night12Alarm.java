package com.drkaiproject.sqliteHelper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Night12Alarm extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {

        SimpleDateFormat format1 = new SimpleDateFormat("YYYY-MM-dd");
        Date time = new Date();
        String today_str = format1.format(time); //오늘
        String[] tmp = today_str.split("-");
        SqliteFunction.today = Integer.parseInt(tmp[0] + tmp[1] + tmp[2]);
        Log.v("날짜", today_str + ", int: " + SqliteFunction.today);

        SqliteFunction.insertData(SqliteFunction.today, today_str, 0, 0, 0, 0, 0, 0);
    }
}
