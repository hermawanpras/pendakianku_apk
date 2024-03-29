package com.hermawan.pendakian.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.hermawan.pendakian.R;
import com.hermawan.pendakian.SplashscreenActivity;

import java.util.Random;

public class MyFireBaseMessagingService extends FirebaseMessagingService {
    private final String ADMIN_CHANNEL_ID ="admin_channel";
    String title,message;

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title = remoteMessage.getNotification().getTitle();
        message = remoteMessage.getNotification().getBody();

        Log.d("msg", "onMessageReceived: " + remoteMessage.getNotification().toString());
        Intent intent = new Intent(this, SplashscreenActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        String channelId = String.valueOf(new Random().nextInt(3000));
        NotificationCompat.Builder builder = new  NotificationCompat.Builder(this, ADMIN_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notif)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notif))
                .setVibrate(new long[] { 1000, 1000, 1000, 1000, 1000 })
                .setLights(Color.WHITE, 3000, 3000)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_ALL)
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody()).setAutoCancel(true).setContentIntent(pendingIntent);

        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(ADMIN_CHANNEL_ID, "Default channel", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
        manager.notify(Integer.parseInt(channelId), builder.build());
    }
}
