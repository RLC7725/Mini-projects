package com.mypackage.servicesandmedia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnPlay, btnStop;
    Intent mediaIntent;
    private static String CHANNEL_ID= "MEDIA_AND_SERVICE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btn_play);
        btnStop = findViewById(R.id.btn_stop);

        // define an intent to a service class
        mediaIntent = new Intent(MainActivity.this, MyMedia.class);

        btnStop.setOnClickListener(v -> {
            // code to stop media
            stopService(mediaIntent);
            invokeNotification("Media stopped");
        });


        btnPlay.setOnClickListener(v -> {
            // code to start media
            startService(mediaIntent);
            invokeNotification("Playing media");
        });

    }

    private void createChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "MyMediaChannel";
            String description = "My Media Disc";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void invokeNotification(String contentBody) {
        // creating the channel
        createChannel();

        Intent intent= new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        // setting up the notification
        NotificationCompat.Builder notifybuilder = new NotificationCompat.Builder(this, CHANNEL_ID);
        notifybuilder.setSmallIcon(android.R.drawable.stat_notify_more)
                .setContentTitle(CHANNEL_ID)
                .setContentText(contentBody)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Much longer text that cannot fit one line..."))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        //.setSound(Uri.parse());
        // firing the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(5, notifybuilder.build());



    }
}