package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse extends BaseResponse {
    @SerializedName("data")
    public List<UserModel> data;

    public static class UserModel {
        @SerializedName("id_user")
        public String idUser;

        @SerializedName("username")
        public String username;

        @SerializedName("password")
        public String password;

        @SerializedName("email")
        public String email;

        @SerializedName("no_telp")
        public String noTelp;

        @SerializedName("alamat_user")
        public String alamat;

        @SerializedName("role_user")
        public String roleUser;
    }
}
