package com.hermawan.pendakian.api;

import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.InfoJalurResponse;
import com.hermawan.pendakian.api.response.KuotaResponse;
import com.hermawan.pendakian.api.response.KuotayJalurResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.TitikJalurResponse;
import com.hermawan.pendakian.api.response.UserResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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

    @GET("detail_jalur/buthak")
    Call<DetailJalurResponse> getDetailJalurButhak();

    @GET("detail_jalur/panderman")
    Call<DetailJalurResponse> getDetailJalurPanderman();

    @GET("detail_jalur/cek_tanggal")
    Call<DetailJalurResponse> getTanggal(
            @Query("id_info_jalur") String idInfoJalur,
            @Query("tanggal_jalur") String tanggalJalur
    );

    @GET("detail_jalur/cek_tanggal_daki")
    Call<DetailJalurResponse> getTanggalDaki(
            @Query("id_info_jalur") String idInfoJalur,
            @Query("tgl_mulai") String tanggalMulai,
            @Query("tgl_selesai") String tanggalSelesai
    );

    @GET("detail_jalur/dj_home_user")
    Call<DetailJalurResponse> getJalur(
            @Query("tanggal_jalur") String tanggalJalur
    );

    @FormUrlEncoded
    @POST("detail_jalur/atur_jalur")
    Call<BaseResponse> tambahDetailJalur(
            @Field("id_info_jalur") String idInfoJalur,
            @Field("total_kuota") String totalKuota,
            @Field("status") String status,
            @Field("tanggal_jalur") String tanggalJalur,
            @Field("keterangan") String keterangan
    );

    @FormUrlEncoded
    @POST("pendaftaran_pendakian/daftar_daki")
    Call<BaseResponse> daftarDaki(
            @Field("id_user") String idUser,
            @Field("id_info_jalur") String idInfoJalur,
            @Field("tgl_daftar_pendakian") String tglDaftarPendakian,
            @Field("tgl_mulai_pendakian") String tglMulaiPendakian,
            @Field("tgl_selesai_pendakian") String tglSelesaiPendakian,
            @Field("status_pendakian") String statusPendakian
    );

    @Multipart
    @POST("pendaki/simpan")
    Call<BaseResponse> simpanPendaki(
            @Part("no_identitas") RequestBody noIdentitas,
            @Part("id_daftar") RequestBody idDaftar,
            @Part("nama_pendaki") RequestBody namaPendaki,
            @Part("tgl_lahir") RequestBody tglLahir,
            @Part("jk_pendaki") RequestBody jkPendaki,
            @Part("alamat_pendaki") RequestBody alamatPendaki,
            @Part("no_telp") RequestBody noTelp,
            @Part("status_pendaki") RequestBody statusPendaki,
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part image1
    );

    @GET("pendaki/get_id_daftar")
    Call<PendaftaranPendakianResponse> getIdDaftar(
            @Query("id_user") String idUser
    );

    @GET("pendaftaran_pendakian/get_history")
    Call<PendaftaranPendakianResponse> getHistory(
            @Query("id_user") String idUser
    );
}