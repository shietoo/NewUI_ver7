package com.example.shietoo.newui;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class DiaryShow extends AppCompatActivity {
    private ImageView show;
    private TextView name, note, id;
    private MyApp myApp;
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;
    static final int DBVERSION = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diary_show);

        myApp = (MyApp) getApplication();
        myDBHelper = new MyDBHelper(this, "plantData", null, DBVERSION);
        db = myDBHelper.getReadableDatabase();

        show = (ImageView) findViewById(R.id.m3_iv);
        id = (TextView) findViewById(R.id.m3tv0);
        name = (TextView) findViewById(R.id.m3tv1);
        note = (TextView) findViewById(R.id.m3tv2);
        Log.i("zzz", "oncreate");
        init();

    }
    private void init() {
        getPlantData(myApp.page+"");
        Log.i("zzz", "\"" + myApp.page + "\"");
    }

    private void getPlantData(String position) {

        try {
            Log.i("zzz", "getPlatData");
            Cursor c = db.query("diary", null, "id=?", new String[]{position}, null, null, null);


            while (c.moveToNext()) {
                id.setText(c.getString(c.getColumnIndex("date")));
                name.setText(c.getString(c.getColumnIndex("title")));
                note.setText(c.getString(c.getColumnIndex("desc")));
                Log.i("zzz",c.getString(c.getColumnIndex("title")));
                if (c.getString(c.getColumnIndex("image")) != null) {
                    show.setImageURI(Uri.parse(c.getString(c.getColumnIndex("image"))));
                    Log.i("zzz", "getPlantData OK");
                    c.close();
                } else {
                    show.setImageURI(null);
                    Log.i("zzz", "getPlantData OK");
                    c.close();
                }
            }

        } catch (Exception e) {
            Log.i("zzz", e.toString());

        }
    }
}
