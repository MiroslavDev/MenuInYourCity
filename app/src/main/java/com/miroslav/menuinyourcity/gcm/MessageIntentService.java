package com.miroslav.menuinyourcity.gcm;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.miroslav.menuinyourcity.R;
import com.miroslav.menuinyourcity.request.URLHelper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
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



    public void sendNotification(String image, String title, String text, String shop_id, String desc) {
//        NotificationCompat.Builder builder = new
//                NotificationCompat.Builder(this).setSmallIcon(R.mipmap.ic_launcher)
//                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
//                .setContentTitle(title).setContentText(text);
//        Random r = new Random();
//        int NOTIFICATION_ID = r.nextInt(9999 - 1111) + 1111;
//        PackageManager pm = getPackageManager();
//        Intent notificationIntent = pm.getLaunchIntentForPackage(getApplicationContext().getPackageName());
//        notificationIntent.putExtra("bOpenPushes", true);
//        notificationIntent.putExtra("message", text);
//        notificationIntent.putExtra("title", title);
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(contentIntent);
//        builder.setAutoCancel(true);
//        //  builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
//        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        nManager.notify(NOTIFICATION_ID, builder.build());
        handleMessage(text, image, shop_id, desc);
    }
//URLHelper.imageDomain +image
//
    @SuppressWarnings("deprecation")
    private void handleMessage(String message, String image, String shop_id, String desc) {
        Bitmap remote_picture = null;
        NotificationManager notificationManager = null;
        long when = System.currentTimeMillis();
        int icon = R.mipmap.ic_launcher;
        Random r = new Random();
        int NOTIFICATION_ID = r.nextInt(9999 - 1111) + 1111;
        //if message and image url
        if(message!=null && image!=null) {
            try {
                Log.v("TAG_IMAGE", "" + message);
                Log.v("TAG_IMAGE", "" + image);
                Log.v("TAG_IMAGE", "" + shop_id);
                NotificationCompat.BigPictureStyle notiStyle = new NotificationCompat.BigPictureStyle();
                notiStyle.setSummaryText(message);
                try {
                    remote_picture = BitmapFactory.decodeStream((InputStream)
                            new URL(URLHelper.imageDomain +image).getContent());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                notiStyle.bigPicture(remote_picture);
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                PendingIntent contentIntent = null;

                Intent gotoIntent = new Intent();
                gotoIntent.setClassName(getApplicationContext(), "com.miroslav.menuinyourcity.MainActivity");
                //Start activity when user taps on notification.
                gotoIntent.putExtra("isPush", true);
                gotoIntent.putExtra("message", message);
                gotoIntent.putExtra("image", image);
                gotoIntent.putExtra("shop_id", shop_id);
                gotoIntent.putExtra("desc", desc);
                contentIntent = PendingIntent.getActivity(getApplicationContext(),
                        (int) (Math.random() * 100), gotoIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                        getApplicationContext());
                Notification notification = mBuilder.setSmallIcon(icon).setTicker("MENU города").setWhen(0)
                        .setAutoCancel(true)
                        .setContentTitle("MENU твоего города")
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message))
                        .setContentIntent(contentIntent)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

                        .setContentText(message)
                        .setStyle(notiStyle).build();


                notification.flags = Notification.FLAG_AUTO_CANCEL;
                notificationManager.notify(NOTIFICATION_ID, notification);
                //This will generate seperate notification each time server sends.

            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }

}
