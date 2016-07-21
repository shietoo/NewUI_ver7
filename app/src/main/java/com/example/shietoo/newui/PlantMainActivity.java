package com.example.shietoo.newui;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ExpandedMenuView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class PlantMainActivity extends AppCompatActivity {
    private ImageView profile1, photo1, garden1, diary1, setting1, bgi,
            humiv, humairiv, tmpiv, wateringpotiv, happyiv, thirstyiv, profileiv, camera1, dayiv, nightiv;
    private TextView plantinfo, humtv, humairtv, tmptv, datetv;
    private MyApp myApp;
    private UIHandler handler;
    private Timer timer;
    private Vibrator myvibrator;
    private String plantName;

    //test
    private SQLiteDatabase db;
    private MyDBHelper myDBHelper;

    Animation an;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant_main);


        myDBHelper = new MyDBHelper(this, "plantData", null, 2);
        //四個參數 本人:庫名:游標null:版本
        db = myDBHelper.getReadableDatabase();
        //getReadable跟getWriteable都可以用
        //差異:磁碟空間滿或是出現IOException時，getWriteable會拋出例外

        myApp = (MyApp) getApplication();
        Log.i("aaaa", myApp.plantID + "");

        myvibrator = (Vibrator) getSystemService(Service.VIBRATOR_SERVICE);


        //action bar
        //  plantinfo = (TextView) findViewById(R.id.Plantmain_info_bar);
        profile1 = (ImageView) findViewById(R.id.img_profile);
        profile1.setImageResource(R.drawable.profile);
        photo1 = (ImageView) findViewById(R.id.img_photo);
        photo1.setImageResource(R.drawable.photo);
        garden1 = (ImageView) findViewById(R.id.img_garden);
        garden1.setImageResource(R.drawable.garden);
        diary1 = (ImageView) findViewById(R.id.img_diary);
        diary1.setImageResource(R.drawable.diary);
        setting1 = (ImageView) findViewById(R.id.img_setting);
        setting1.setImageResource(R.drawable.setting);
        //action bar

        camera1 = (ImageView) findViewById(R.id.img_camera);
        camera1.setImageResource(R.drawable.camera);
        //bgimage
        bgi = (ImageView) findViewById(R.id.iv_plant_bgi);
//        bgi.setImageResource(R.drawable.ex_plant);
//        setAlpha(80);

        //溫濕照度
        humiv = (ImageView) findViewById(R.id.iv_hum);
        humiv.setImageResource(R.drawable.hum);
        tmpiv = (ImageView) findViewById(R.id.iv_tmp);
        tmpiv.setImageResource(R.drawable.tmp);
        humairiv = (ImageView) findViewById(R.id.iv_hum_air);
        humairiv.setImageResource(R.drawable.hum_air);
        humtv = (TextView) findViewById(R.id.tv_hum);
        tmptv = (TextView) findViewById(R.id.tv_tmp);
        humairtv = (TextView) findViewById(R.id.tv_hum_air);
        //Intent intent = getIntent();
//        String name = intent.getStringExtra("tname");
//        String date = intent.getStringExtra("date");
//        String location = intent.getStringExtra("location");
        humtv.setText(myApp.name);
        tmptv.setText(myApp.date);
        humairtv.setText(myApp.location);
        timer = new Timer();
        handler = new UIHandler();
        timer.schedule(new MyChangestate(), 2000, 2000);
        //溫濕照度

        //澆水
        wateringpotiv = (ImageView) findViewById(R.id.iv_wateringpot);
        wateringpotiv.setImageResource(R.drawable.watering);
        wateringpotiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myvibrator.vibrate(500);
                //抖動效果
                // myvibrator.vibrate( new long[]{500,100,500,100},-1);

                getPlantName(String.valueOf(myApp.plantID));
                Toast.makeText(PlantMainActivity.this,"對< "+plantName+" >使用澆水，效果非常顯著！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PlantMainActivity.this, MyBootService.class);
                intent.putExtra("what", 2);
                startService(intent);
            }
        });
        //澆水

        //表情
        happyiv = (ImageView) findViewById(R.id.iv_happy);
        thirstyiv = (ImageView) findViewById(R.id.iv_thirsty);
        dayiv = (ImageView) findViewById(R.id.iv_day);
        nightiv = (ImageView) findViewById(R.id.iv_night);
        happyiv.setImageResource(R.drawable.happy);
        thirstyiv.setImageResource(R.drawable.thirsty);
        dayiv.setImageResource(R.drawable.day);
        nightiv.setImageResource(R.drawable.night);
        happyiv.setImageAlpha(0);
        thirstyiv.setImageAlpha(0);
        dayiv.setImageAlpha(0);
        nightiv.setImageAlpha(0);
        //表情

        photo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myvibrator.vibrate(50);
                Intent intent = new Intent(PlantMainActivity.this,GalleryView.class);
                startActivity(intent);
            }
        });

        camera1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });

        //profile
        profileiv = (ImageView) findViewById(R.id.img_profile);
        profileiv.setImageResource(R.drawable.profile);
        profileiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myvibrator.vibrate(50);
                Intent intent = new Intent(PlantMainActivity.this, Plantprofile1.class);
                startActivity(intent);
                //    finish();
            }
        });

        //profile
        if (myApp.plantID == 0) {
            profileiv.setEnabled(false);
        }

        diary1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myvibrator.vibrate(50);
                Intent intent = new Intent(PlantMainActivity.this,Diary.class);
                startActivity(intent);
            }
        });



        //mygarden
        garden1.setImageResource(R.drawable.garden);
        garden1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(PlantMainActivity.this, GridView.class);
//                startActivity(intent);
                myvibrator.vibrate(50);
                Intent intent = new Intent(PlantMainActivity.this, BradGarden.class);
                startActivity(intent);
                //finish();
            }
        });

        datetv = (TextView) findViewById(R.id.tv_date);
        datetv.setBackgroundResource(R.drawable.date);


        init();
    }

    private void init() {
        compareDate();

    }


    private class MyBootServiceReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    private class MyChangestate extends TimerTask {
        @Override
        public void run() {


            handler.sendEmptyMessage(0);


        }
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (myApp.name != null) {
                humtv.setText(myApp.name + "%");
            }else {
                humtv.setText("---");
            }
            if (myApp.date !=null) {
                tmptv.setText(myApp.date);
            }else{
                tmptv.setText("---");
            }
            if (myApp.location!=null) {
                humairtv.setText(myApp.location);
            }else {
                humairtv.setText("---");
            }
            changemotion();
        }
    }

    private Runnable changeUI = new Runnable() {
        @Override
        public void run() {

        }
    };

    @Override
    public void finish() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
        }
        super.finish();
    }

    private void changemotion() {


        Calendar calendar = Calendar.getInstance();
        int changeTime = calendar.get(Calendar.HOUR_OF_DAY);
       // tmptv.setText(changeTime + "");
        // Time t = new Time(Calendar.HOUR);

        int hum = 0;

        try {
            Log.i("aaa", "-" + myApp.name + "-");

            hum = Integer.valueOf(myApp.name);
            myApp.hum = hum;
        } catch (Exception e) {
            Log.i("aaa", "1" + e.toString());
        }
        if (changeTime > 6 && changeTime < 21) {
            happyiv.setImageAlpha(0);
            thirstyiv.setImageAlpha(0);
            dayiv.setImageAlpha(255);
            nightiv.setImageAlpha(0);
            if (hum > 80) {
                happyiv.setImageAlpha(255);
                thirstyiv.setImageAlpha(0);
                dayiv.setImageAlpha(0);
                nightiv.setImageAlpha(0);

                Log.i("aaa", " hum:-" + hum + "-");
            } else if (hum < 30) {
                happyiv.setImageAlpha(0);
                thirstyiv.setImageAlpha(255);
                dayiv.setImageAlpha(0);
                nightiv.setImageAlpha(0);
            } else {

            }
        } else {
            happyiv.setImageAlpha(0);
            thirstyiv.setImageAlpha(0);
            dayiv.setImageAlpha(0);
            nightiv.setImageAlpha(255);
        }

    }

    private void compareDate() {
        String nowdate = null;
        String position = myApp.plantID + "";
        Cursor c = db.query("plant1", null, "id=?", new String[]{position}, null, null, null);
        while (c.moveToNext()) {
            int indexBirth = c.getColumnIndex("birthday");
            // myApp.dbresult.append(c.getString(indexBirth) + "\n");
            nowdate = c.getString(indexBirth);

        }
        //    Log.i("aaa",zz);

        //Log.i("aaa",myApp.dbresult.toString());
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cc = Calendar.getInstance();
        Calendar aa = Calendar.getInstance();
        String aa1 = df.format(aa.getTime());
        Log.i("aaa", "aa1:" + aa1);
        try {
            cc.setTime(df.parse(nowdate));
        } catch (Exception e) {
        }

        Log.i("aaa", "cc: " + cc.toString());
        long aDayInMilliSecond = 60 * 60 * 24 * 1000;
        long dayDiff = (aa.getTimeInMillis() - cc.getTimeInMillis()) / aDayInMilliSecond;
        Log.i("aaa", aa + ":" + cc);
        if (dayDiff < 2) {
            datetv.setText(dayDiff + "");
        } else {
            datetv.setText(dayDiff + "");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            myApp.uri_plantmain = uri.toString();
            Log.i("zzz",myApp.uri_plantmain);
            Intent intent = new Intent(this,GalleryView.class);
            startActivity(intent);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
    private String getPlantName(String position) {
        Log.i("aaaa", "getPlantName");

        Cursor c = db.query("plant1", null, "id=?", new String[]{position}, null, null, null);
        while (c.moveToNext()) {
            plantName = c.getString(c.getColumnIndex("name"));
            Log.i("aaaa", plantName);
        }
        return plantName;
    }
}

