package com.hermawan.pendakian.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiClient {
    private static ApiInterface apiInterface;

    public static ApiInterface getClient() {
        if (apiInterface == null) {
            Retrofit retrofit;

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(60, TimeUnit.SECONDS)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                    .build();

            Gson builder = new GsonBuilder().setLenient().create();

            retrofit = new Retrofit.Builder()
                    .client(okHttpClient)
//                    .baseUrl("https://poinku.my.id/api/")
                    .baseUrl("http://192.168.100.236/rest_hermawan/api/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(builder))
                    .build();

            apiInterface = retrofit.create(ApiInterface.class);
        }
        return apiInterface;
    }

}
