package com.example.powiadomienia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Activity2.class);
                //intencja oczekujaca
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(),
                        0,intent, PendingIntent.FLAG_UPDATE_CURRENT);

                //akcja
                NotificationCompat.Action action = new NotificationCompat.Action.Builder(
                        android.R.drawable.sym_action_chat,
                        "Otwórz", pendingIntent
                ).build();

                //dzwonek
                Uri dzwonek = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                //buduje powiadomienie
                Notification notification =  new NotificationCompat.Builder(
                        getBaseContext(), "channel_1")
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Powiadomienie")
                        .setContentText("Wiadmosć powiadomienia")
                        .setSound(dzwonek)
                        .setContentIntent(pendingIntent)
                        .addAction(action)
                        .build();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                int id = 1;
                notificationManager.notify(id, notification);

            }
        });
    }
}