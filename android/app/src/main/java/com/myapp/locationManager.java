package com.myapp;

import static java.lang.Thread.sleep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.logging.Handler;

public class locationManager extends ReactContextBaseJavaModule {

    private static ReactApplicationContext reactContext;
    locationManager(ReactApplicationContext context) {
        super(context);
        reactContext = context;
        statusCheck();
        reactContext.registerReceiver(new BroadcastReceiver_1(), new IntentFilter("android.location.PROVIDERS_CHANGED"));

    }


    @ReactMethod
    public void toggleGpsButton(){
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            reactContext.startActivity(intent);
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) reactContext.getSystemService(Context.LOCATION_SERVICE);

        Intent serviceIntent = new Intent(reactContext, BroadcastHeadless.class);
        Bundle bundle = new Bundle();
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER))
            bundle.putString("location", "off");
        else
        bundle.putString("location", "on");
        serviceIntent.putExtras(bundle);
        reactContext.startService(serviceIntent);
    }



//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            reactContext.startActivity(intent);




    @NonNull
    @Override
    public String getName() {
        return "LocationModule";
    }
}
