package com.example.shietoo.newui;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MyBootService extends Service {
    private  MyAppReceiver appReceiver;
    private MyApp myApp;
    private Timer timer;
    private MyDBHelper myDBHelper;
    private SQLiteDatabase db;
    static final int DB_VERSION =2;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("aaa","oncreate");
        myApp =(MyApp)getApplication();

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);

        appReceiver = new MyAppReceiver();
        registerReceiver(appReceiver,filter);

         timer = new Timer();

        myDBHelper = new MyDBHelper(this, "plantdata", null,DB_VERSION);
        //四個參數 本人:庫名:游標null:版本
        db = myDBHelper.getReadableDatabase();
        //getReadable跟getWriteable都可以用
        //差異:磁碟空間滿或是出現IOException時，getWriteable會拋出例外
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        int what = intent.getIntExtra("what",0);
        if(what == 1 && myApp.isWifiEnable){
            dotimer();
        }else if(what == 2 && myApp.isWifiEnable){
            adddata();
        }else{
            boolean isWifiEnable = intent.getBooleanExtra("wifi_enable", false);
            myApp.isWifiEnable = isWifiEnable;

        }

        return super.onStartCommand(intent, flags, startId);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void getdata(){
        new Thread(){
            @Override
            public void run() {
                try {
                    MultipartUtility mu = new MultipartUtility(
                            "http://192.168.43.161/serverTest/androidLoad.php","UTF-8");
//                            MultipartUtility mu = new MultipartUtility(
//                            "http://10.2.1.118/project1/catchdata.php","UTF-8");
                    List<String> ret = mu.finish();
                    String data = ret.get(0);
                    String [] lines = data.split(":");

                    myApp.name = lines[0];
                    myApp.date = lines[1];
                    myApp.location = lines[2];
                    Log.i("aaa",myApp.name+myApp.date+myApp.location);
//                    Intent intent = new Intent(MyBootService.this,PlantMainActivity.class);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    intent.putExtra("tname",tname);
//                    intent.putExtra("tname",tname);
//                    intent.putExtra("date",date);
//                    intent.putExtra("location",location);
//                    startActivity(intent);

                }catch (Exception e){
                    Log.i("aaa",e.toString());
                }
            }
        }.start();
    }

    private void dotimer(){
        timer.schedule(new MyGetDataTask(),3000,3000);
    }
    private class MyGetDataTask extends TimerTask{
        @Override
        public void run() {
            getdata();
        }
    }
    private void adddata(){
        new Thread(){
            @Override
            public void run() {
                try {
                    MultipartUtility mu = new MultipartUtility(
                            "http://192.168.43.161/serverTest/androidUp.php", "UTF-8");
                    mu.addFormField("com","@");
//                    MultipartUtility mu = new MultipartUtility(
//                            "http://10.2.1.118/project1/test1form.php", "UTF-8");
//                    mu.addFormField("type","1");
                    mu.finish();
                    Log.i("aaa","uploadok OK");

                }catch (Exception e){
                    Log.i("aaa",e.toString());
                }
            }
        }.start();

    }

}
