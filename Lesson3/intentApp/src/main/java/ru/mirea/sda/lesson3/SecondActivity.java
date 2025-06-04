package ru.mirea.sda.lesson3;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.second_activity);

        String curtime = getIntent().getStringExtra("CUR_TIME");
        int number = getIntent().getIntExtra("LIST_NUMBER", 0);
        int square = number * number;
        String text = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ " + square + ", а текущее время " + curtime;
        TextView textView = findViewById(R.id.textView);
        textView.setText(text);
    }
}
