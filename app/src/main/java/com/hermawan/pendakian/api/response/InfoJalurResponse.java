package com.hermawan.pendakian.api.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InfoJalurResponse extends BaseResponse {
    @SerializedName("data")
    public List<InfoJalurResponse.InfoJalurModel> data;

    public static class InfoJalurModel {
        @SerializedName("id_info_jalur")
        public String idInfoGunung;

        @SerializedName("nama_gunung")
        public String namagunung;

        @SerializedName("tgl_mulai_jlr")
        public String tglMulaiJalur;

        @SerializedName("tgl_selesai_jlr")
        public String tglSelesaiJalur;

        @SerializedName("status_jlr")
        public String statusJalur;

        @SerializedName("ket_jlr")
        public String ketJalur;

        @NonNull
        @Override
        public String toString() {
            return namagunung;
        }
    }
}
