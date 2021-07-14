package com.app.theimperialpalace;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.app.theimperialpalace.Utils.SharedPrefsUtils;
import com.danikula.videocache.HttpProxyCacheServer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

public class TheImperialPalace extends Application {


 String token;
 private HttpProxyCacheServer proxy;
 @Override
 public void onCreate() {
  super.onCreate();

  FirebaseApp.initializeApp(this);

  FirebaseInstanceId.getInstance().getInstanceId()
          .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
           @Override
           public void onComplete(@NonNull Task<InstanceIdResult> task) {
            if (!task.isSuccessful()) {
             return;
            }
            if (SharedPrefsUtils.getSharedPreferenceString(getApplicationContext(), "token_new").equals("")) {
             if (task.getResult() != null) {
              token = task.getResult().getToken();
              //Utils.showLog("==== token "+token);
              Log.w("ravi_testing_token", token);
             }
             SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(), "token_new", token);
            }
           }
          });

 }



 public static HttpProxyCacheServer getProxy(Context context) {
  TheImperialPalace app = (TheImperialPalace) context.getApplicationContext();
  return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
 }

 private HttpProxyCacheServer newProxy() {
  return new HttpProxyCacheServer.Builder(this)
          .maxCacheSize(1024 * 1024)
          .build();
 }

}