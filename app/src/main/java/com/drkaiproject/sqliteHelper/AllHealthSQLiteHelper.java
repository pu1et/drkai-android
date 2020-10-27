package com.drkaiproject.sqliteHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class AllHealthSQLiteHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    public static String mDB;
    private SQLiteDatabase.CursorFactory mFc;
    private Context mCtx;

    public AllHealthSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context, name, factory, version);
        mCtx = context;
        mDB = name;
        mFc = factory;
        Log.i("==========================AllHealthSQLiteHelper=============================","생성자: " + name);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("drop table if exists allHealthTBL");
        String sql = "create table if not exists allHealthTBL" +
                "(date_id integer primary key, " + // date_id : date를 int로 변환한것
                "date text, " + // 날짜
                "water integer, " + // 잔 >= 8
                "sleep integer, " +
                "food integer, " +
                "drinking integer, " +
                "smoking integer, " +
                "exercise integer, " +
                "result integer);";
        db.execSQL(sql);
        Log.i("AllHealthSQL_onCreate","onCreate");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}
