package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PendakiResponse extends BaseResponse {
    @SerializedName("data")
    public List<PendakiModel> data;

    public static class PendakiModel {
        @SerializedName("id_daftar")
        public String idDaftar;

        @SerializedName("id_pendaki")
        public String idPendaki;

        @SerializedName("nama_pendaki")
        public String namaPendaki;

        @SerializedName("tgl_lahir")
        public String tglLahir;

        @SerializedName("jk_pendaki")
        public String jkPendaki;

        @SerializedName("alamat_pendaki")
        public String alamatPendaki;

        @SerializedName("no_telp")
        public String noTelp;

        @SerializedName("no_identitas")
        public String noIdentitas;

        @SerializedName("status_pendaki")
        public String statusPendaki;

        @SerializedName("identitas_pendaki")
        public String identitasPendaki;

        @SerializedName("srt_ket_kesehatan")
        public String suratKetKes;

        public PendakiModel(String idDaftar, String idPendaki, String namaPendaki, String tglLahir, String jkPendaki, String alamatPendaki, String noTelp, String noIdentitas, String statusPendaki, String identitasPendaki, String suratKetKes) {
            this.idDaftar = idDaftar;
            this.idPendaki = idPendaki;
            this.namaPendaki = namaPendaki;
            this.tglLahir = tglLahir;
            this.jkPendaki = jkPendaki;
            this.alamatPendaki = alamatPendaki;
            this.noTelp = noTelp;
            this.noIdentitas = noIdentitas;
            this.statusPendaki = statusPendaki;
            this.identitasPendaki = identitasPendaki;
            this.suratKetKes = suratKetKes;
        }

        public String getIdDaftar() {
            return idDaftar;
        }

        public void setIdDaftar(String idDaftar) {
            this.idDaftar = idDaftar;
        }

        public String getIdPendaki() {
            return idPendaki;
        }

        public void setIdPendaki(String idPendaki) {
            this.idPendaki = idPendaki;
        }

        public String getNamaPendaki() {
            return namaPendaki;
        }

        public void setNamaPendaki(String namaPendaki) {
            this.namaPendaki = namaPendaki;
        }

        public String getTglLahir() {
            return tglLahir;
        }

        public void setTglLahir(String tglLahir) {
            this.tglLahir = tglLahir;
        }

        public String getJkPendaki() {
            return jkPendaki;
        }

        public void setJkPendaki(String jkPendaki) {
            this.jkPendaki = jkPendaki;
        }

        public String getAlamatPendaki() {
            return alamatPendaki;
        }

        public void setAlamatPendaki(String alamatPendaki) {
            this.alamatPendaki = alamatPendaki;
        }

        public String getNoTelp() {
            return noTelp;
        }

        public void setNoTelp(String noTelp) {
            this.noTelp = noTelp;
        }

        public String getNoIdentitas() {
            return noIdentitas;
        }

        public void setNoIdentitas(String noIdentitas) {
            this.noIdentitas = noIdentitas;
        }

        public String getStatusPendaki() {
            return statusPendaki;
        }

        public void setStatusPendaki(String statusPendaki) {
            this.statusPendaki = statusPendaki;
        }

        public String getIdentitasPendaki() {
            return identitasPendaki;
        }

        public void setIdentitasPendaki(String identitasPendaki) {
            this.identitasPendaki = identitasPendaki;
        }

        public String getSuratKetKes() {
            return suratKetKes;
        }

        public void setSuratKetKes(String suratKetKes) {
            this.suratKetKes = suratKetKes;
        }
    }
}
