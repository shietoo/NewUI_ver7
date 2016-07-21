package com.example.shietoo.newui;

import android.app.Application;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.SimpleCursorAdapter;

/**
 * Created by shietoo on 2016/6/28.
 */
public class MyApp extends Application {
    boolean isWifiEnable;
    String name;
    String date;
    String location,uri_plantmain;
    StringBuffer dbResult;
    int plantID = 0;
    Bitmap createbmp,profilebmp;
    int hum;
    String uri,uricreate;
    Long page;
    SimpleCursorAdapter cursorAdapter;

    @Override
    public void onCreate() {
        super.onCreate();


    }
}
