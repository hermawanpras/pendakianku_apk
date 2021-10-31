package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BlacklistResponse extends BaseResponse {
    @SerializedName("data")
    public List<BlacklistModel> data;

    public static class BlacklistModel {
        @SerializedName("id_pendaki")
        public String idPendaki;

        @SerializedName("no_identitas")
        public String noIdentitas;

        @SerializedName("id_daftar")
        public String idDaftar;

        @SerializedName("nama_pendaki")
        public String namaPendaki;

        @SerializedName("tgl_mulai")
        public String tglMulai;

        @SerializedName("tgl_selesai")
        public String tglSelesai;

        @SerializedName("keterangan")
        public String keterangan;

        @SerializedName("status")
        public String status;

        public BlacklistModel(String idPendaki, String noIdentitas, String idDaftar, String namaPendaki, String tglMulai, String tglSelesai, String keterangan, String status) {
            this.idPendaki = idPendaki;
            this.noIdentitas = noIdentitas;
            this.idDaftar = idDaftar;
            this.namaPendaki = namaPendaki;
            this.tglMulai = tglMulai;
            this.tglSelesai = tglSelesai;
            this.keterangan = keterangan;
            this.status = status;
        }

        public String getIdPendaki() {
            return idPendaki;
        }

        public void setIdPendaki(String idPendaki) {
            this.idPendaki = idPendaki;
        }

        public String getNoIdentitas() {
            return noIdentitas;
        }

        public void setNoIdentitas(String noIdentitas) {
            this.noIdentitas = noIdentitas;
        }

        public String getIdDaftar() {
            return idDaftar;
        }

        public void setIdDaftar(String idDaftar) {
            this.idDaftar = idDaftar;
        }

        public String getNamaPendaki() {
            return namaPendaki;
        }

        public void setNamaPendaki(String namaPendaki) {
            this.namaPendaki = namaPendaki;
        }

        public String getTglMulai() {
            return tglMulai;
        }

        public void setTglMulai(String tglMulai) {
            this.tglMulai = tglMulai;
        }

        public String getTglSelesai() {
            return tglSelesai;
        }

        public void setTglSelesai(String tglSelesai) {
            this.tglSelesai = tglSelesai;
        }

        public String getKeterangan() {
            return keterangan;
        }

        public void setKeterangan(String keterangan) {
            this.keterangan = keterangan;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
