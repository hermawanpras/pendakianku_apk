package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TitikJalurResponse extends BaseResponse {
    @SerializedName("data")
    public List<TitikJalurModel> data;

    public static class TitikJalurModel {
        @SerializedName("latitude")
        public String latitude;

        @SerializedName("longitude")
        public String longitude;
    }
}
