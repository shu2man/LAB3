package com.example.yellow.lab3;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.widget.RemoteViews;
import android.widget.Toast;
import android.os.Bundle;

import java.util.Calendar;
import java.util.Random;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Yellow on 2017-10-28.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private NotificationManager myManager;
    private int notificationid=2;

    @Override
    public void onReceive(Context context,Intent intent){
        String name=intent.getStringExtra("package_name");
        //Toast.makeText(context,name+" received",Toast.LENGTH_SHORT).show();
        setNotification(name,context);
    }

    public void setNotification(String name,Context context) {
        DataShare ds=((DataShare)context.getApplicationContext());
        int pos=ds.getName().indexOf(name);
        myManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent=new Intent(context,ListViewActivity.class);
        Bundle mBundle=new Bundle();
        mBundle.putString("name",name);
        intent.putExtras(mBundle);
        PendingIntent ma=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);
        RemoteViews rViews=new RemoteViews(context.getPackageName(),R.layout.notification_view);
        rViews.setImageViewResource(R.id.notification_icon,R.drawable.peanut2);
        rViews.setTextViewText(R.id.notification_title,"商品热卖中");
        rViews.setTextViewText(R.id.notification_text,name+"仅售"+ds.getPrice().get(pos)+" ！");
        rViews.setTextViewText(R.id.notification_time,getTime());
        rViews.setImageViewResource(R.id.notification_img,ds.getIcon(pos));

        Notification.Builder mbuilder=new Notification.Builder(context)
                .setSmallIcon(ds.getIcon(pos))
                .setTicker("商品热卖")
                .setContentTitle("商品热卖中")
                .setContentText(name+"仅售"+ds.getPrice().get(pos)+" ！")
                .setContentIntent(ma)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContent(rViews)
                .setAutoCancel(true);
        Notification notification=mbuilder.build();
        myManager=(NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        myManager.notify(notificationid,notification);
    }

    public String getTime(){
        Calendar cal=Calendar.getInstance();
        String time=cal.get(Calendar.HOUR_OF_DAY)+":"+cal.get(Calendar.MINUTE);
        return time;
    }
}
