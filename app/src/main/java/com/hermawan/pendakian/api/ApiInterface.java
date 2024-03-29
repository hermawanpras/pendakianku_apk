package com.hermawan.pendakian.api;

import com.hermawan.pendakian.api.response.BaseResponse;
import com.hermawan.pendakian.api.response.BlacklistResponse;
import com.hermawan.pendakian.api.response.DetailJalurResponse;
import com.hermawan.pendakian.api.response.InfoJalurResponse;
import com.hermawan.pendakian.api.response.JumlahPendakiResponse;
import com.hermawan.pendakian.api.response.KuotaResponse;
import com.hermawan.pendakian.api.response.KuotayJalurResponse;
import com.hermawan.pendakian.api.response.PembayaranResponse;
import com.hermawan.pendakian.api.response.PendaftaranPendakianResponse;
import com.hermawan.pendakian.api.response.PendakiResponse;
import com.hermawan.pendakian.api.response.ReportResponse;
import com.hermawan.pendakian.api.response.ReportUmurResponse;
import com.hermawan.pendakian.api.response.SOSResponse;
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

    @FormUrlEncoded
    @POST("SOS/sos")
    Call<BaseResponse> postSOS(
            @Field("id_user") String idUser,
            @Field("lat") String latitude,
            @Field("long") String longitude
    );

    @GET("pendaftaran_pendakian/cek_daftar")
    Call<PendaftaranPendakianResponse> cekDaftar(
            @Query("id_daftar") String idDaftar,
            @Query("id_info_jalur") String idInfoJalur
    );

    @GET("pendaki/get_pendaki")
    Call<PendakiResponse> getPendaki(
            @Query("id_pendaki") String idPendaki,
            @Query("id_daftar") String idDaftar
    );

    @GET("pendaki/get_pendaki_by_id_daftar")
    Call<PendakiResponse> getPendakiByIdDaftar(
            @Query("id_daftar") String idDaftar
    );

    @GET("pendaftaran_pendakian/get_jumlah")
    Call<JumlahPendakiResponse> getJumlahPendaki(
            @Query("id_daftar") String idDaftar
    );

    @GET("pendaftaran_pendakian/validasi_pendaftaran")
    Call<BaseResponse> validasiPendaftaran(
            @Query("id_daftar") String idDaftar,
            @Query("status") String status
    );

    @GET("pendaftaran_pendakian/get_id_info_jalur")
    Call<PendaftaranPendakianResponse> getIdIj(
            @Query("id_daftar") String idDaftar
    );

    @Multipart
    @POST("pembayaran/bayar")
    Call<BaseResponse> bayarPendakian(
            @Part("id_daftar") RequestBody idDaftar,
            @Part("tanggal_bayar") RequestBody tglBayar,
            @Part("nominal") RequestBody nominal,
            @Part("nama_rekening") RequestBody namaRekening,
            @Part("bank_pengirim") RequestBody bankPengirim,
            @Part MultipartBody.Part image
    );

    @GET("pembayaran")
    Call<PembayaranResponse> getPembayaran(
            @Query("id_pembayaran") String idPembayaran
    );

    @GET("pembayaran/validasi_pembayaran")
    Call<BaseResponse> validasiPembayaran(
            @Query("id_pembayaran") String idPembayaran,
            @Query("id_daftar") String idDaftar
    );

    @GET("pembayaran/tolak_pembayaran")
    Call<BaseResponse> tolakPembayaran(
            @Query("id_pembayaran") String idPembayaran,
            @Query("id_daftar") String idDaftar
    );

    @GET("blacklist/search")
    Call<PendakiResponse> search(
            @Query("no_identitas") String noIdentitas
    );

    @GET("blacklist")
    Call<BlacklistResponse> getBlacklist(
            @Query("id_pendaki") String idPendaki
    );

    @FormUrlEncoded
    @POST("blacklist/tambah")
    Call<BaseResponse> tambahBlacklist(
            @Field("id_pendaki") String idPendaki,
            @Field("tgl_mulai") String tglMulai,
            @Field("tgl_selesai") String tglSelesai,
            @Field("keterangan") String keterangan,
            @Field("status") String status
    );

    @GET("pembayaran/get_pembayaran_by_id_daftar")
    Call<PembayaranResponse> getPembayaranByIdDaftar(
            @Query("id_daftar") String idDaftar
    );

    @GET("detail_jalur")
    Call<DetailJalurResponse> getDetailJalur(
            @Query("id_detail_jalur") String idDetailJalur
    );

    @GET("report/get_report")
    Call<ReportResponse> getReport(
            @Query("bulan") String bulan
    );

    @GET("report/get_report_umur")
    Call<ReportUmurResponse> getReportUmur(
            @Query("bulan") String bulan
    );

    @GET("blacklist/cek")
    Call<BlacklistResponse> cekBlacklist(
            @Query("no_identitas") String noIdentitas
    );

    @GET("SOS/getSos")
    Call<SOSResponse> getSOS();

    @FormUrlEncoded
    @POST("user/postToken")
    Call<BaseResponse> postToken(
            @Field("id_user") String idUser,
            @Field("token") String token
    );

    @FormUrlEncoded
    @POST("SOS/update")
    Call<BaseResponse> updateIsSeenSOS(
            @Field("id_sos") String idSos
    );

    @Multipart
    @POST("user/edit_profil")
    Call<BaseResponse> editProfil(
            @Part("id_user") RequestBody idUser,
            @Part("nama_user") RequestBody namaUser,
            @Part("password") RequestBody password,
            @Part("email") RequestBody email,
            @Part("no_telp_user") RequestBody noTelp,
            @Part("alamat_user") RequestBody alamatUser,
            @Part MultipartBody.Part image
    );

    @Multipart
    @POST("user/tambah_admin")
    Call<BaseResponse> tambahAdmin(
            @Part("nama_user") RequestBody namaUser,
            @Part("password") RequestBody password,
            @Part("email") RequestBody email,
            @Part("no_telp_user") RequestBody noTelp,
            @Part("alamat_user") RequestBody alamatUser,
            @Part MultipartBody.Part image
    );

    @GET("user/get_admin")
    Call<UserResponse> getAdmin(
            @Query("role_user") String roleUser
    );

    @GET("pendaftaran_pendakian/hapus")
    Call<BaseResponse> hapusDaftar(
            @Query("id_daftar") String idDaftar
    );

    @GET("pendaki/hapus_pendaki")
    Call<BaseResponse> hapusPendaki(
            @Query("id_pendaki") String idPendaki
    );

    @GET("detail_jalur/update_detail")
    Call<BaseResponse> updateDj(
            @Query("id_detail_jalur") String idDetailJalur,
            @Query("total_kuota") String totalKuota,
            @Query("old_total_kuota") String oldTotalKuota,
            @Query("kuota_sisa") String kuotaSisa,
            @Query("status") String status,
            @Query("keterangan") String keterangan
    );
    
    @Multipart
    @POST("pendaki/update_detail")
    Call<BaseResponse> updatePendaki(
            @Part("no_identitas") RequestBody noIdentitas,
            @Part("id_pendaki") RequestBody idPendaki,
            @Part("nama_pendaki") RequestBody namaPendaki,
            @Part("tgl_lahir") RequestBody tglLahir,
            @Part("jk_pendaki") RequestBody jkPendaki,
            @Part("alamat_pendaki") RequestBody alamatPendaki,
            @Part("no_telp") RequestBody noTelp,
            @Part MultipartBody.Part image,
            @Part MultipartBody.Part image1
    );
}