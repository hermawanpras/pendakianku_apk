package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SOSResponse extends BaseResponse{
    @SerializedName("data")
    public List<SOSModel> data;

    public class SOSModel{
        @SerializedName("id_sos")
        public String idSos;
        @SerializedName("nama")
        public String nama;
        @SerializedName("latitude")
        public double latitude;
        @SerializedName("longitude")
        public double longitude;
        @SerializedName("is_seen")
        public int isSeen;
    }
}
