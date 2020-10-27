package com.drkaiproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class AlarmHATT {

    private Context context;

    public AlarmHATT(Context context) {
        this.context=context;


    }

    public void Alarm() {
        Intent sleepintent = new Intent(context, SleepReceiver.class);

        PendingIntent sleepsender = PendingIntent.getBroadcast(context,
                0,
                sleepintent,
                0);

        Calendar sleep_calendar = Calendar.getInstance();
        sleep_calendar.set(Calendar.HOUR_OF_DAY, 2);
        sleep_calendar.set(Calendar.MINUTE, 38);
        sleep_calendar.set(Calendar.SECOND, 59);

        if(sleep_calendar.before(Calendar.getInstance())){ // if it's in the past, increment
            sleep_calendar.add(Calendar.DATE, 1);
        }

        AlarmManager sleep_am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        sleep_am.setRepeating(AlarmManager.RTC_WAKEUP, sleep_calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sleepsender);
    }



}