package com.hermawan.pendakian.api.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DetailJalurResponse extends BaseResponse {
    @SerializedName("data")
    public List<DetailJalurModel> data;

    public static class DetailJalurModel {
        @SerializedName("id_info_jalur")
        public String idInfoGunung;

        @SerializedName("nama_gunung")
        public String namagunung;

        @NonNull
        @Override
        public String toString() {
            return namagunung;
        }
    }
}
