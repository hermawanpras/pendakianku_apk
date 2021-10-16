package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class KuotaResponse extends BaseResponse {
    @SerializedName("data")
    public List<InfoJalurModel> data;

    @SerializedName("date")
    public String date;

    public static class InfoJalurModel {
        @SerializedName("kuota")
        public String kuota;
    }
}
