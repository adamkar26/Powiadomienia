package com.example.powiadomienia;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.RemoteInput;
import androidx.core.content.ContextCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String CHANNEL_ID = "1" ;
    public static String klucz = "Klucz";
    public static int notificationId = 1;

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

                createNotificationChannel();

                //buduje powiadomienie
                Notification notification =  new NotificationCompat.Builder(
                        getBaseContext(), CHANNEL_ID)
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

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

                String[] zdarzenia = {"Zdarzenie 1", "Zdarzenie 2", "Zdarzenie 3"};
                for(int i=0; i< zdarzenia.length; i++){
                    inboxStyle.addLine(zdarzenia[i]);
                }

                Notification notification = new NotificationCompat.Builder(
                getBaseContext(), CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Powiadomienie")
                        .setContentText("Wiadomość powiadomienia")
                        .setStyle(inboxStyle)
                        .build();

                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(NOTIFICATION_SERVICE);
                notificationManager.notify(1, notification);

            }
        });

        Button button3 = findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String odpwiedz = "tutaj wpisz odpowiedz";
                RemoteInput remoteInput = new RemoteInput.Builder(klucz)
                        .setLabel(odpwiedz)
                        .build();

                Intent intent = new Intent(getBaseContext(),
                        Activity2.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                NotificationCompat.Action replayAction = new NotificationCompat.Action.Builder(
                        android.R.drawable.ic_dialog_info, "Odpowiedz", pendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();

                Notification notification = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                        .setContentTitle("Powiadomienie")
                        .setContentText("Treść powiadomienia")
                        .addAction(replayAction)
                        .build();

                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(notificationId, notification);
            }
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "kanal1";
            String description = "kanal glowny";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}