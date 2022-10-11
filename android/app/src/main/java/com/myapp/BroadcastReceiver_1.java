package com.myapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.facebook.react.HeadlessJsTaskService;
import com.facebook.react.jstasks.HeadlessJsTaskConfig;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class BroadcastReceiver_1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(Intent.ACTION_AIRPLANE_MODE_CHANGED)){
            Log.d("broadcast", "airplane mode changed");
            Intent serviceIntent = new Intent(context, BroadcastHeadless.class);
            Bundle bundle = new Bundle();
            bundle.putString("foo", "bar");
            serviceIntent.putExtras(bundle);
            context.startService(serviceIntent);
            //HeadlessJsTaskService.acquireWakeLockNow(context);

        }

    }
}
