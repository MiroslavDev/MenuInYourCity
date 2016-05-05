package com.miroslav.menuinyourcity.gcm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.miroslav.menuinyourcity.MainActivity;

import java.io.IOException;


public class GCMManager {
    int index = 0;

    public static final String EXTRA_MESSAGE = "message";
    public static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "1.0";

    public static final String SENDER_ID = "381294291923";

    public Context appContext = null;
    private GoogleCloudMessaging gcmInstance = null;
    public String registrationId = null;

    private static GCMManager instance = new GCMManager();

    public static GCMManager getInstance() {
        return instance;
    }



    // sent_at, receiver,  sender, community;
    public void onRecieveMessage(Bundle extras) {
        String message = extras.getString("message");
        String image = extras.getString("image");
        String shop_id = extras.getString("shop_id");
        String desc = extras.getString("description");
         Log.d("receive push", message+" "+image);
          MessageIntentService.activeIntentService.sendNotification(image, "MENU города", message, shop_id, desc);
    }


    public void initialize(Context context) {
        this.appContext = context;

        gcmInstance = GoogleCloudMessaging.getInstance(appContext);
        registrationId = getRegistrationId(appContext);

        if (registrationId.isEmpty() || registrationId == null) {
            registerInBackground();
        }

    }


    private static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private String getRegistrationId(Context context) {
        final SharedPreferences prefs = MainActivity.rootAcvitityInstance.sharedPreferences();
        String regId = prefs.getString(PROPERTY_REG_ID, "");
        if (regId.isEmpty()) {
            Log.i("Error", "Registration not found.");
            return "";
        }
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(context);

        if (registeredVersion != currentVersion) {
            Log.i("Error", "App version changed.");
            return "";
        }

        return regId;
    }

    //
    //
    //
    //
    public void storeRegistrationId(String regId) {
        final SharedPreferences prefs = MainActivity.rootAcvitityInstance.sharedPreferences();
        int appVersion = getAppVersion(appContext);
        Log.d("Error", "Saving regId on app version " + appVersion);
        Log.d("regId", "Saving regId " + regId);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }

    //
    //
    //
    //
    private void registerInBackground() {
        new RegistrationAsyncTask().execute(null, null, null);
    }

    //
    //
    //
    //
	public void registerUserOnServer(final String reg_id)
	{
        MainActivity.rootAcvitityInstance.registerApp(reg_id);
	}

    //
    //
    //
    //
    public void onAppCloseAction() {
//        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(GCMManager.getInstance().appContext);
//        try
//        {
//            gcm.unregister();
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
    }
}

//
//
//
//
class RegistrationAsyncTask extends AsyncTask<Object, Object, String> {
    @Override
    protected String doInBackground(Object... params) {
        String msg = "";
        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(GCMManager.getInstance().appContext);
            String regId = gcm.register(GCMManager.SENDER_ID);
            GCMManager.getInstance().registrationId = regId;
            GCMManager.getInstance().storeRegistrationId(regId);
        } catch (IOException ex) {
            msg = "Error :" + ex.getMessage();
        }

        return msg;
    }

    protected void onPostExecute(Bitmap result) {
        // send id to server
         GCMManager.getInstance().registerUserOnServer(GCMManager.getInstance().registrationId );
    }
}