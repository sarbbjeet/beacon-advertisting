package com.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class BroadcastReceiver_1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
   if(intent.getAction().matches("android.location.PROVIDERS_CHANGED")){
       //if(intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
       final LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            Log.d("broadcast", "location mode change");
            Intent serviceIntent = new Intent(context, BroadcastHeadless.class);
            Bundle bundle = new Bundle();
       if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
           bundle.putString("location", "off");
       }
       else{
           bundle.putString("location", "on");
       }
            serviceIntent.putExtras(bundle);
            context.startService(serviceIntent);
            //HeadlessJsTaskService.acquireWakeLockNow(context);

        }

    }
}
