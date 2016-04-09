package com.miroslav.menuinyourcity.gcm;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.miroslav.menuinyourcity.R;

import java.util.Random;


public class MessageIntentService extends IntentService {

    public MessageIntentService() {
        super("MessageIntentService");
        activeIntentService = this;
    }

    public static MessageIntentService activeIntentService = null;

    @Override
    protected void onHandleIntent(Intent intent) {
        activeIntentService = this;
        Bundle extras = intent.getExtras();
        // Check if we have something
        if (!extras.isEmpty()) {
            // Get message type
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
            String messageType = gcm.getMessageType(intent);
            // If this is a message
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                GCMManager.getInstance().onRecieveMessage(extras);
            }
        }

        MessageBroadcastReceiver.completeWakefulIntent(intent);
    }



    public void sendNotification(String notificationTitle, String title, String text) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher).setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                .setContentTitle(notificationTitle).setContentText(text);
        Random r = new Random();
        int NOTIFICATION_ID = r.nextInt(9999 - 1111) + 1111;
        PackageManager pm = getPackageManager();
        Intent notificationIntent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
        notificationIntent.putExtra("bOpenPushes", true);
        notificationIntent.putExtra("message", text);
        notificationIntent.putExtra("title", title);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        builder.setAutoCancel(true);
        //  builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }




}
