package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportUmurResponse extends BaseResponse {
    @SerializedName("data")
    public List<ReportUmurModel> data;

    public static class ReportUmurModel {
        @SerializedName("age")
        public String age;

        @SerializedName("total")
        public String total;
    }
}
