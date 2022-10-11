package com.myapp;


import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class CalendarModule extends ReactContextBaseJavaModule {
    private static ReactApplicationContext reactContext;
    CalendarModule(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        reactContext.registerReceiver(new BroadcastReceiver_1(), new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
        Intent intent = new Intent(reactContext,MyForegroundService.class);
        //check if service is already running
        if(!foregroundServiceRunning()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                reactContext.startForegroundService(intent);
            }
        }
        else{
            Log.d("Service", "service is already running");
        }

    }

    public boolean foregroundServiceRunning(){
        ActivityManager activityManager = (ActivityManager)  reactContext.getSystemService(Context.ACTIVITY_SERVICE);
        for(ActivityManager.RunningServiceInfo service: activityManager.getRunningServices(Integer.MAX_VALUE)){
            if(MyForegroundService.class.getName().equals(service.service.getClassName())){
                return true;
            }
        }
        return false;
    }

//    private final BroadcastReceiver bcr = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            // int level = intent.getIntExtra("level", 0);
//            Log.d("battery level", "mode changed..");
//        }
//    };

    public void sendEvent() {
        WritableMap params = Arguments.createMap();
        params.putString("id", "1567892");
        reactContext
                .getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                .emit("eventA", params);
    }

    @ReactMethod
    public void addListener(String eventName) {

        // Set up any upstream listeners or background tasks as necessary
    }

    @ReactMethod
    public void removeListeners(Integer count) {
        // Remove upstream listeners, stop unnecessary background tasks
    }


    @ReactMethod
    public void createCalendarEvent(String name, String location, Callback callback) {

        if(name.equals("university"))

            callback.invoke(null, "create data is saved successfully");
        else
            callback.invoke("wrong name..");
//
//        Log.d("CalendarModule", "Create event called with name: " + name
//                + " and location: " + location);
    }
    @NonNull
    @Override
    public String getName() {
        return "CalendarModule";
    }

    @Override
    public void initialize() {
        super.initialize();
        sendEvent();
//        reactContext.registerReceiver(bcr, new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED));
    }
}
