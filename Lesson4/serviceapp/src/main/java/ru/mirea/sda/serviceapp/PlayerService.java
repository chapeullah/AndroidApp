package ru.mirea.sda.serviceapp;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class PlayerService extends Service
{
    private MediaPlayer mediaPlayer;
    public static final String CHANNEL_ID = "ForegroundServiceChannel";

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Привязка не реализована");
    }

    @SuppressLint("ForegroundServiceType")
    @Override
    public void onCreate()
    {
        super.onCreate();

        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "Музыкальные уведомления",
                NotificationManager.IMPORTANCE_DEFAULT
        );
        channel.setDescription("Воспроизведение трека Сигма Бой");

        NotificationManagerCompat.from(this).createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Шамко Денис — Музыкальный плеер")
                .setContentText("Сейчас играет: Сигма Бой")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setStyle(new NotificationCompat.BigTextStyle().bigText("Композиция: Сигма Бой"));

        startForeground(1, builder.build());

        mediaPlayer = MediaPlayer.create(this, R.raw.sigmaboy);
        mediaPlayer.setLooping(false);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        if (mediaPlayer != null && !mediaPlayer.isPlaying())
        {
            mediaPlayer.start();
        }

        mediaPlayer.setOnCompletionListener(mp -> stopForeground(true));

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        stopForeground(true);
        if (mediaPlayer != null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        super.onDestroy();
    }
}
