package ru.mirea.sda.looper;

import android.os.*;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.*;

import ru.mirea.sda.looper.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    private MyLooper myLooper;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        EdgeToEdge.enable(this);

        initLooper();
        setupWindowInsets();
        setupButton();
    }

    private void initLooper()
    {
        Handler handler = new Handler(Looper.getMainLooper())
        {
            @Override
            public void handleMessage(Message msg)
            {
                String output = msg.getData().getString("result");
                Log.d(MainActivity.class.getSimpleName(), "Задача выполнена. Результат: " + output);
            }
        };

        myLooper = new MyLooper(handler);
        myLooper.start();
    }

    private void setupWindowInsets()
    {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (view, insets) ->
        {
            Insets bars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(bars.left, bars.top, bars.right, bars.bottom);
            return insets;
        });
    }

    private void setupButton()
    {
        Button myButton = findViewById(R.id.button);
        myButton.setOnClickListener(this::onClick);
    }

    public void onClick(View v)
    {
        Message msg = Message.obtain();
        Bundle data = new Bundle();
        data.putString("KEY", "Мне 23, я работаю в Яндексе");
        msg.setData(data);
        myLooper.mHandler.sendMessage(msg);
    }
}
