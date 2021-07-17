package com.app.theimperialpalace.Utils;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.app.theimperialpalace.BuildConfig;
import com.app.theimperialpalace.ListOfOrderActivity;
import com.app.theimperialpalace.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONObject;

public class MyFirebaseMessaging extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessaging.class.getSimpleName();



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        NotificationManager notificationManager = (NotificationManager) getApplicationContext().
                getSystemService(Context.NOTIFICATION_SERVICE);
        try {


            JSONObject jsonObject = new JSONObject(remoteMessage.getData());
            Log.w("=== onMessageReceived " , jsonObject.toString());
            //JSONObject data = jsonObject.getJSONObject("data");

            String title = jsonObject.getString("title");
            String message = jsonObject.getString("body");
            Log.e("Spider", "From: " + remoteMessage.getFrom() + " " );
            int notificationId = 1;
            String channelId = "channel-01";
            String channelName = "Channel Name";
            // Log.e("From: ",   jsonObject.getString("title"));
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant") NotificationChannel mChannel = new NotificationChannel(
                        channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(mChannel);
            }

            AudioAttributes att = new AudioAttributes.Builder()

                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)

                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)

                    .build();
            Uri soundUri = Uri.parse("android.resource://" + getApplicationContext().getPackageName() + "/" + R.raw.notificationrington);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message);

          /*  Notification notification = mBuilder.build();
            notification.sound = Uri.parse("android.resource://"
                    + getApplicationContext().getPackageName() + "/" + R.raw.notificationrington);*/

            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notificationrington");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();

         /*   MediaPlayer mp;
            mp =MediaPlayer.create(getApplicationContext(), R.raw.notificationrington);
            mp.start();*/

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            Intent intent = new Intent(getApplicationContext(), ListOfOrderActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setAutoCancel(true);
            notificationManager.notify(notificationId, mBuilder.build());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}