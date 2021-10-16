package com.hermawan.pendakian.api;

import com.hermawan.pendakian.api.response.CurrentTimeResponse;
import com.hermawan.pendakian.api.response.DailyForecastResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherInterface {
    @GET("weather")
    Call<CurrentTimeResponse> getCurrentTime(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("appid") String appId
    );

    @GET("onecall")
    Call<DailyForecastResponse> getDailyForecast(
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("exclude") String exclude,
            @Query("appid") String appId
    );
}
