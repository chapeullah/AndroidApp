package ru.mirea.sda.httpurlconnection;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private Button buttonGetData;
    private TextView textViewIp, textViewCity, textViewRegion, textViewCoords, textViewWeather;
    private String latitude = "", longitude = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonGetData = findViewById(R.id.buttonGetData);
        textViewIp = findViewById(R.id.textViewIp);
        textViewCity = findViewById(R.id.textViewCity);
        textViewRegion = findViewById(R.id.textViewRegion);
        textViewCoords = findViewById(R.id.textViewCoords);
        textViewWeather = findViewById(R.id.textViewWeather);

        buttonGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo ni = cm.getActiveNetworkInfo();
                if (ni != null && ni.isConnected()) {
                    new GetIpInfoTask().execute("https://ipinfo.io/json");
                } else {
                    Toast.makeText(MainActivity.this, "Нет интернета", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class GetIpInfoTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return downloadUrl(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                String ip = json.getString("ip");
                String city = json.getString("city");
                String region = json.getString("region");
                String loc = json.getString("loc");

                textViewIp.setText("IP: " + ip);
                textViewCity.setText("Город: " + city);
                textViewRegion.setText("Регион: " + region);
                textViewCoords.setText("Координаты: " + loc);

                String[] parts = loc.split(",");
                latitude = parts[0];
                longitude = parts[1];

                String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                        + "&longitude=" + longitude + "&current_weather=true";

                new GetWeatherTask().execute(weatherUrl);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetWeatherTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            return downloadUrl(urls[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONObject current = json.getJSONObject("current_weather");
                String temp = current.getString("temperature");
                String wind = current.getString("windspeed");
                textViewWeather.setText("Погода: " + temp + "°C, Ветер: " + wind + " км/ч");
            } catch (JSONException e) {
                textViewWeather.setText("Ошибка парсинга погоды");
            }
        }
    }

    private String downloadUrl(String address) {
        InputStream inputStream = null;
        try {
            URL url = new URL(address);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.connect();
            inputStream = conn.getInputStream();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int read;
            while ((read = inputStream.read()) != -1) {
                bos.write(read);
            }
            return bos.toString();
        } catch (IOException e) {
            return "Ошибка подключения";
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException ignored) {}
        }
    }
}
