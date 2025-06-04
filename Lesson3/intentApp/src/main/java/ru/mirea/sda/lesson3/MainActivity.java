package ru.mirea.sda.lesson3;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        long dateInMillis = System.currentTimeMillis();

        String format = "yyyy-MM-dd HH:mm:ss";
        final SimpleDateFormat sdf = new SimpleDateFormat(format);
        String dateString = sdf.format(new Date(dateInMillis));

        Intent transfer = new Intent(MainActivity.this, SecondActivity.class);
        transfer.putExtra("CUR_TIME", dateString);
        transfer.putExtra("LIST_NUMBER", 23);
        startActivity(transfer);
    }
}
