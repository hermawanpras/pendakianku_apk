package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KuotayJalurResponse extends BaseResponse {
    @SerializedName("data")
    public List<KuotaModel> data;

    public static class KuotaModel {
        @SerializedName("tgl_mulai_pendakian")
        public String tanggal;

        @SerializedName("jumlah_pendaftar")
        public String kuota;
    }
}
