package com.tenantsproject.flatmates.todolist;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import java.util.Calendar;

public class AlarmReceiver extends BroadcastReceiver {

    final int MY_NOTIFICATION_ID = (int) System.currentTimeMillis();
    NotificationManager notificationManager;
    Notification myNotification;

    @Override
    public void onReceive(Context context, Intent intent) {
        String toShow = intent.getStringExtra("temp");
        Intent myIntent = new Intent(context, OnNotificationClick.class);
        myIntent.putExtra("toShow",toShow);
        String mytext = toShow.toString();
        PendingIntent pendingIntent = PendingIntent.getActivity(
                context,
                MY_NOTIFICATION_ID ,
                myIntent,
                Intent.FLAG_ACTIVITY_NEW_TASK);

        myNotification = new NotificationCompat.Builder(context)
                .setContentTitle("Reminder!")
                .setContentText(toShow)
                .setTicker("Flatmates reminder!")
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND)
                .setAutoCancel(true)
                .setSmallIcon(android.R.drawable.btn_star)
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .build();

        notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(MY_NOTIFICATION_ID, myNotification);
    }

}
