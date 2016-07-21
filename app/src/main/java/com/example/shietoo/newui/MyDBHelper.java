package com.example.shietoo.newui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by shietoo on 2016/6/18.
 * 處理的方法，回到你要控制的ACTIVITY，有可能是前端有可能是service
 * 99%都寫在service
 *
 */

//繼承SQLiteOpenHelper
public class MyDBHelper extends SQLiteOpenHelper {
 //兩個抽象方法實作 onCreate onUpgrade 建構式

    private static final String createTable =
         "CREATE TABLE plant1 (id integer primary key autoincrement," +
                 "name TEXT, birthday DATE,note TEXT,image TEXT)";

    private static final String createTable_DIARY =
            "CREATE TABLE diary (id integer primary key autoincrement," +
                    "title TEXT,date TEXT,desc TEXT,image TEXT)";
    //建立庫與表
    //primary key autoincrement 主鍵自動遞增

     public MyDBHelper(Context context, String dbname,
                       SQLiteDatabase.CursorFactory cf, int version){
        super(context,dbname,cf,version);
         //沒有無傳參數建構式
         //context 是誰在玩我
         //name 資料庫名
         //CursorFactory 游標移動的因素，一般給null
         //資料庫版本數
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //沒有庫會自動建
        db.execSQL(createTable);
        db.execSQL(createTable_DIARY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
