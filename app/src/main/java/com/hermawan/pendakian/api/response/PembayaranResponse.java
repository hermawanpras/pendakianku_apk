package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PembayaranResponse extends BaseResponse {
    @SerializedName("data")
    public List<PembayaranModel> data;

    public static class PembayaranModel {
        @SerializedName("nama_user")
        public String namaUser;

        @SerializedName("foto_profil")
        public String fotoProfil;

        @SerializedName("id_pembayaran")
        public String idPembayaran;

        @SerializedName("id_daftar")
        public String idDaftar;

        @SerializedName("bukti")
        public String bukti;

        @SerializedName("tanggal_bayar")
        public String tanggalBayar;

        @SerializedName("nominal")
        public String nominal;

        @SerializedName("nama_rekening")
        public String namaRekening;

        @SerializedName("bank_pengirim")
        public String bankPengirim;

        @SerializedName("status")
        public String status;

        @SerializedName("id_user")
        public String idUser;

        public PembayaranModel(String namaUser, String fotoProfil, String idPembayaran, String idDaftar, String bukti, String tanggalBayar, String nominal, String namaRekening, String bankPengirim, String status, String idUser) {
            this.namaUser = namaUser;
            this.fotoProfil = fotoProfil;
            this.idPembayaran = idPembayaran;
            this.idDaftar = idDaftar;
            this.bukti = bukti;
            this.tanggalBayar = tanggalBayar;
            this.nominal = nominal;
            this.namaRekening = namaRekening;
            this.bankPengirim = bankPengirim;
            this.status = status;
            this.idUser = idUser;
        }

        public String getNamaUser() {
            return namaUser;
        }

        public void setNamaUser(String namaUser) {
            this.namaUser = namaUser;
        }

        public String getFotoProfil() {
            return fotoProfil;
        }

        public void setFotoProfil(String fotoProfil) {
            this.fotoProfil = fotoProfil;
        }

        public String getIdPembayaran() {
            return idPembayaran;
        }

        public void setIdPembayaran(String idPembayaran) {
            this.idPembayaran = idPembayaran;
        }

        public String getIdDaftar() {
            return idDaftar;
        }

        public void setIdDaftar(String idDaftar) {
            this.idDaftar = idDaftar;
        }

        public String getBukti() {
            return bukti;
        }

        public void setBukti(String bukti) {
            this.bukti = bukti;
        }

        public String getTanggalBayar() {
            return tanggalBayar;
        }

        public void setTanggalBayar(String tanggalBayar) {
            this.tanggalBayar = tanggalBayar;
        }

        public String getNominal() {
            return nominal;
        }

        public void setNominal(String nominal) {
            this.nominal = nominal;
        }

        public String getNamaRekening() {
            return namaRekening;
        }

        public void setNamaRekening(String namaRekening) {
            this.namaRekening = namaRekening;
        }

        public String getBankPengirim() {
            return bankPengirim;
        }

        public void setBankPengirim(String bankPengirim) {
            this.bankPengirim = bankPengirim;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }
    }
}
