package com.example.kalidigitalemployee.retrofit;

import android.util.Log;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit sRetrofit;

    public static Retrofit getRetrofitInstance(){
        if (sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl("https://d24e-37-215-56-32.eu.ngrok.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return sRetrofit;
    }

    public static Retrofit getRetrofitInstanceWithAuthantificate(){
        if (sRetrofit == null){
            sRetrofit = new Retrofit.Builder()
                    .baseUrl("https://e66f-37-215-34-48.eu.ngrok.io/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        Log.d("TAG RESPONSE ", sRetrofit.toString());
        return sRetrofit;
    }
}
