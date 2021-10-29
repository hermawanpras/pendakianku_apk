package com.hermawan.pendakian.api.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailJalurResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailJalurModel> data;

    public static class DetailJalurModel {
        @SerializedName("id_info_jalur")
        public String idInfoJalur;

        @SerializedName("nama_gunung")
        public String namagunung;

        @SerializedName("id_detail_jalur")
        public String idDetailJalur;

        @SerializedName("kuota_sisa")
        public String kuotaSisa;

        @SerializedName("total_kuota")
        public String totalKuota;

        @SerializedName("status")
        public String status;

        @SerializedName("tanggal_jalur")
        public String tanggalJalur;

        @SerializedName("keterangan")
        public String keterangan;

        public DetailJalurModel(String idInfoJalur, String namagunung, String idDetailJalur, String kuotaSisa, String totalKuota, String status, String tanggalJalur, String keterangan) {
            this.idInfoJalur = idInfoJalur;
            this.namagunung = namagunung;
            this.idDetailJalur = idDetailJalur;
            this.kuotaSisa = kuotaSisa;
            this.totalKuota = totalKuota;
            this.status = status;
            this.tanggalJalur = tanggalJalur;
            this.keterangan = keterangan;
        }

        public String getIdInfoJalur() {
            return idInfoJalur;
        }

        public void setIdInfoJalur(String idInfoJalur) {
            this.idInfoJalur = idInfoJalur;
        }

        public String getNamagunung() {
            return namagunung;
        }

        public void setNamagunung(String namagunung) {
            this.namagunung = namagunung;
        }

        public String getIdDetailJalur() {
            return idDetailJalur;
        }

        public void setIdDetailJalur(String idDetailJalur) {
            this.idDetailJalur = idDetailJalur;
        }

        public String getKuotaSisa() {
            return kuotaSisa;
        }

        public void setKuotaSisa(String kuotaSisa) {
            this.kuotaSisa = kuotaSisa;
        }

        public String getTotalKuota() {
            return totalKuota;
        }

        public void setTotalKuota(String totalKuota) {
            this.totalKuota = totalKuota;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTanggalJalur() {
            return tanggalJalur;
        }

        public void setTanggalJalur(String tanggalJalur) {
            this.tanggalJalur = tanggalJalur;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        @NonNull
        @Override
        public String toString() {
            return namagunung;
        }
    }
}
