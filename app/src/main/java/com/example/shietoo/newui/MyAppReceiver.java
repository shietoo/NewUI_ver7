package com.example.shietoo.newui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.util.Log;

public class MyAppReceiver extends BroadcastReceiver {
    private Context c;
    @Override
    public void onReceive(Context context, Intent intent) {
        c =context;
        String action = intent.getAction();
        if (action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
            int wifistate = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_DISABLED);
            boolean iswifiEnable =!(wifistate == WifiManager.WIFI_STATE_DISABLED);
            notifyAppService(iswifiEnable);

            Log.i("aaa","notifyAppService(iswifiEnable)");
        }

    }

    private  void notifyAppService(boolean isWifiOK){
        Intent intent = new Intent(c,MyBootService.class);
        intent.putExtra("wifi_enable",isWifiOK);
        Log.i("aaa","wifi_enable");
        c.startService(intent);
    }
}