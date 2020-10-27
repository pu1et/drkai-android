package com.drkaiproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SleepReceiver extends BroadcastReceiver {

    Context context;
    Intent intent;
    StringBuilder content;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;

        this.content = new StringBuilder("");
        notification();
    }

    public void notification(){
        //db 값 보여주기
    }
}