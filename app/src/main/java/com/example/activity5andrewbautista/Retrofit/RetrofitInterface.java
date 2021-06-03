package com.example.activity5andrewbautista.Retrofit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RetrofitInterface {
    @GET("v6/6b76baaaf87d76fbedf70848/latest/{currency}")
    Call<JsonObject> getExchangeCurrency(@Path("currency") String currency);
}
