package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendaftaranPendakianResponse extends BaseResponse {
    @SerializedName("data")
    public List<PendaftaranPendakianModel> data;

    public static class PendaftaranPendakianModel {
        @SerializedName("id_daftar")
        public String idDaftar;

        @SerializedName("id_user")
        public String idUser;

        @SerializedName("id_info_jalur")
        public String idInfoJalur;

        @SerializedName("tgl_daftar_pendakian")
        public String tglDaftarPendakian;

        @SerializedName("tgl_mulai_pendakian")
        public String tglMulaiPendakian;

        @SerializedName("tgl_selesai_pendakian")
        public String tglSelesaiPendakian;

        @SerializedName("status_pendakian")
        public String statusPendakian;

        @SerializedName("nama_user")
        public String namaUser;

        @SerializedName("nama_gunung")
        public String namaGunung;

        public PendaftaranPendakianModel(String idDaftar, String idUser, String idInfoJalur, String tglDaftarPendakian, String tglMulaiPendakian, String tglSelesaiPendakian, String statusPendakian, String namaUser, String namaGunung) {
            this.idDaftar = idDaftar;
            this.idUser = idUser;
            this.idInfoJalur = idInfoJalur;
            this.tglDaftarPendakian = tglDaftarPendakian;
            this.tglMulaiPendakian = tglMulaiPendakian;
            this.tglSelesaiPendakian = tglSelesaiPendakian;
            this.statusPendakian = statusPendakian;
            this.namaUser = namaUser;
            this.namaGunung = namaGunung;
        }

        public String getIdDaftar() {
            return idDaftar;
        }

        public void setIdDaftar(String idDaftar) {
            this.idDaftar = idDaftar;
        }

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public String getIdInfoJalur() {
            return idInfoJalur;
        }

        public void setIdInfoJalur(String idInfoJalur) {
            this.idInfoJalur = idInfoJalur;
        }

        public String getTglDaftarPendakian() {
            return tglDaftarPendakian;
        }

        public void setTglDaftarPendakian(String tglDaftarPendakian) {
            this.tglDaftarPendakian = tglDaftarPendakian;
        }

        public String getTglMulaiPendakian() {
            return tglMulaiPendakian;
        }

        public void setTglMulaiPendakian(String tglMulaiPendakian) {
            this.tglMulaiPendakian = tglMulaiPendakian;
        }

        public String getTglSelesaiPendakian() {
            return tglSelesaiPendakian;
        }

        public void setTglSelesaiPendakian(String tglSelesaiPendakian) {
            this.tglSelesaiPendakian = tglSelesaiPendakian;
        }

        public String getStatusPendakian() {
            return statusPendakian;
        }

        public void setStatusPendakian(String statusPendakian) {
            this.statusPendakian = statusPendakian;
        }

        public String getNamaUser() {
            return namaUser;
        }

        public void setNamaUser(String namaUser) {
            this.namaUser = namaUser;
        }

        public String getNamaGunung() {
            return namaGunung;
        }

        public void setNamaGunung(String namaGunung) {
            this.namaGunung = namaGunung;
        }
    }
}
