package ru.mirea.sda.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText ed_group, ed_number, ed_serial;
    private Button save_button;
    private SharedPreferences sharedPref;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_GROUP = "group_number";
    private static final String KEY_LIST_NUMBER = "list_number";
    private static final String KEY_CINEMA = "favorite_cinema";
    private static final String KEY_LAUNCH_COUNT = "launch_count";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        int launchCount = sharedPref.getInt(KEY_LAUNCH_COUNT, 0) + 1;
        sharedPref.edit().putInt(KEY_LAUNCH_COUNT, launchCount).apply();

        if (launchCount > 1) loadPreferences();

        save_button.setOnClickListener(v -> {
            savePreferences();
            Toast.makeText(this, "Данные сохранены", Toast.LENGTH_SHORT).show();
        });
    }

    private void initViews() {
        ed_group = findViewById(R.id.ed_group);
        ed_number = findViewById(R.id.ed_number);
        ed_serial = findViewById(R.id.ed_serial);
        save_button = findViewById(R.id.save_button);
    }

    private void savePreferences() {
        sharedPref.edit()
                .putString(KEY_GROUP, ed_group.getText().toString())
                .putString(KEY_LIST_NUMBER, ed_number.getText().toString())
                .putString(KEY_CINEMA, ed_serial.getText().toString())
                .apply();
    }

    private void loadPreferences() {
        ed_group.setText(sharedPref.getString(KEY_GROUP, ""));
        ed_number.setText(sharedPref.getString(KEY_LIST_NUMBER, ""));
        ed_serial.setText(sharedPref.getString(KEY_CINEMA, ""));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPref.edit().putInt(KEY_LAUNCH_COUNT, 0).apply();
    }
}