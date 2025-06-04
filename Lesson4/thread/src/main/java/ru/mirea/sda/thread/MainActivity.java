package ru.mirea.sda.thread;

import android.os.Bundle;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;

import ru.mirea.sda.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity
{
    private ActivityMainBinding binding;
    private int counter = 0;
    private TextView infoTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        infoTextView = findViewById(R.id.textView2);
        Thread mainThread = Thread.currentThread();

        infoTextView.setText("Имя текущего потока: " + mainThread.getName());
        mainThread.setName("МОЙ НОМЕР ГРУППЫ: 03, НОМЕР ПО СПИСКУ: 23, МОЙ ЛЮБИМЫЙ ФИЛЬМ: Интерстеллар");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());

        Log.d(MainActivity.class.getSimpleName(), "Стек: " + Arrays.toString(mainThread.getStackTrace()));

        binding.buttonshow.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String pairsString = binding.editTextDays.getText().toString();
                String daysString = binding.editTextPairs.getText().toString();
                int Pairs = Integer.parseInt(pairsString);
                int Days = Integer.parseInt(daysString);
                new AveragePairsPerDay().execute(Pairs, Days);
            }
        });

        EdgeToEdge.enable(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) ->
        {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public class AveragePairsPerDay extends AsyncTask<Integer, Void, Double>
    {
        @Override
        protected Double doInBackground(Integer... arrays)
        {
            int days = arrays[0];
            int pairs = arrays[1];
            return (double) pairs / days;
        }

        @Override
        protected void onPostExecute(Double result)
        {
            binding.textViewResult.setText("Среднее количество пар в день: " + result);
        }
    }
}
