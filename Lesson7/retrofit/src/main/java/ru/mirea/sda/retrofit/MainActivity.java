package ru.mirea.sda.retrofit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private TextView textViewResult;
    private Button buttonLoad;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.textViewResult);
        buttonLoad = findViewById(R.id.buttonLoad);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);

        buttonLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadUsers();
            }
        });
    }

    private void loadUsers() {
        Call<List<User>> call = apiService.getUsers();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("Ошибка: " + response.code());
                    return;
                }
                List<User> users = response.body();
                StringBuilder sb = new StringBuilder();
                for (User user : users) {
                    sb.append(user.toString()).append("\n\n");
                }
                textViewResult.setText(sb.toString());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                textViewResult.setText("Сбой: " + t.getMessage());
                Log.e("Retrofit", "Ошибка", t);
            }
        });
    }
}
