package ru.mirea.sda.timeservice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "TimeService";
    private static final String HOST = "time.nist.gov";
    private static final int PORT = 13;

    private TextView textViewTime;
    private Button buttonGetTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewTime = findViewById(R.id.textViewTime);
        buttonGetTime = findViewById(R.id.buttonGetTime);

        buttonGetTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetTimeTask().execute();
            }
        });
    }

    private class GetTimeTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... voids) {
            String result = "";
            try {
                Socket socket = new Socket(HOST, PORT);
                BufferedReader reader = SocketUtils.getReader(socket);
                reader.readLine(); // игнорируем первую строку
                result = reader.readLine(); // считываем вторую
                socket.close();
            } catch (IOException e) {
                Log.e(TAG, "Ошибка при получении времени", e);
                result = "Ошибка подключения";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String time) {
            textViewTime.setText("Точное время:\n" + time);
        }
    }
}
