package ru.mirea.sda.firebaseauth;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CatApi {
    @GET("fact")
    Call<CatFactResponse> getCatFact();
}
