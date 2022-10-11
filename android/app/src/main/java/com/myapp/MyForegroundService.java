package com.myapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.RequiresApi;

public class MyForegroundService extends Service {
    public MyForegroundService() {
    }
    Thread t1;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        t1=  new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            try{
                                Intent serviceIntent = new Intent(getApplicationContext(), BroadcastHeadless.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("foo", "bar");
                                serviceIntent.putExtras(bundle);
                                getApplicationContext().startService(serviceIntent);
                                Log.d("Service", "service is nunning");
                                Thread.sleep(2000);
                            }
                            catch(InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
        );
        t1.start();
        final  String CHANNELID = "Foreground Service ID";
        NotificationChannel channel = new NotificationChannel(
                CHANNELID,CHANNELID,
                NotificationManager.IMPORTANCE_LOW
        );

        getSystemService(NotificationManager.class).createNotificationChannel(channel);
        Notification.Builder notification = new Notification.Builder(this, CHANNELID)
                .setContentText("Service is running")
                .setContentTitle("Doorstep advertising")
                .setSmallIcon(R.drawable.redbox_top_border_background);

        startForeground(1001,notification.build() );

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        throw new UnsupportedOperationException("Not yet implemented");
    }
}