package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class JumlahPendakiResponse extends BaseResponse {
    @SerializedName("data")
    public List<JumlahPendakiModel> data;

    public static class JumlahPendakiModel {
        @SerializedName("jumlah_pendaki")
        public String jumlahPendaki;

        public JumlahPendakiModel(String jumlahPendaki) {
            this.jumlahPendaki = jumlahPendaki;
        }

        public String getJumlahPendaki() {
            return jumlahPendaki;
        }

        public void setJumlahPendaki(String jumlahPendaki) {
            this.jumlahPendaki = jumlahPendaki;
        }
    }
}

