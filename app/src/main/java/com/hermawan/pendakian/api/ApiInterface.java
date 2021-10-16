package com.hermawan.pendakian.api;

import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.InfoJalurResponse;
import com.hermawan.pendakian.api.response.KuotaResponse;
import com.hermawan.pendakian.api.response.KuotayJalurResponse;
import com.hermawan.pendakian.api.response.TitikJalurResponse;
import com.hermawan.pendakian.api.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("user/login")
    Call<UserResponse> login(
            @Query("email") String email,
            @Query("password") String password
    );

    @GET("InfoJalur/infojalur")
    Call<InfoJalurResponse> infojalur();

    @GET("InfoJalur/kuota")
    Call<KuotaResponse> getKuota(
            @Query("id_info_jalur") String id_info_jalur
    );

    @GET("PendaftaranPendakian/daftar")
    Call<KuotayJalurResponse> getKuotayInfoJalur(
            @Query("id_info_jalur") String id_info_jalur
    );

    @FormUrlEncoded //jika tidak upload gamar
    @POST("user/register")
    Call<BaseResponse> register(
            @Field("username") String username,
            @Field("password") String password,
            @Field("email") String email
    );

    @GET("TitikJalur/getTitikJalur")
    Call<TitikJalurResponse> getTitikJalur(
            @Query("id") String id
    );
}