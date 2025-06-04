package ru.mirea.sda.serviceapp;

import static android.Manifest.permission.FOREGROUND_SERVICE;
import static android.Manifest.permission.POST_NOTIFICATIONS;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import ru.mirea.sda.serviceapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    ActivityMainBinding binding;
    private final int PermissionCode = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        checkPermissions();
        setupButtonListeners();
    }

    private void checkPermissions()
    {
        if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED)
        {
            Log.d(MainActivity.class.getSimpleName(), "Доступ разрешён");
        }
        else
        {
            Log.d(MainActivity.class.getSimpleName(), "Доступ запрещён!");
            ActivityCompat.requestPermissions(
                    this,
                    new String[]{POST_NOTIFICATIONS, FOREGROUND_SERVICE},
                    PermissionCode
            );
        }
    }

    private void setupButtonListeners()
    {
        binding.previous.setOnClickListener(v -> {
            Intent serviceIntent = new Intent(MainActivity.this, PlayerService.class);
            ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
        });

        binding.next.setOnClickListener(v -> {
            stopService(new Intent(MainActivity.this, PlayerService.class));
        });
    }
}
