package ru.mirea.sda.firebaseauth;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.*;
import retrofit2.converter.gson.GsonConverterFactory;

public class InfoActivity extends AppCompatActivity {

    private TextView textViewFact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        textViewFact = findViewById(R.id.textViewTime);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://catfact.ninja/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        CatApi catApi = retrofit.create(CatApi.class);

        catApi.getCatFact().enqueue(new Callback<CatFactResponse>() {
            @Override
            public void onResponse(Call<CatFactResponse> call, Response<CatFactResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    textViewFact.setText("Факт о кошках: " + response.body().fact);
                } else {
                    textViewFact.setText("Ошибка: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CatFactResponse> call, Throwable t) {
                textViewFact.setText("Ошибка запроса: " + t.getMessage());
            }
        });
    }
}