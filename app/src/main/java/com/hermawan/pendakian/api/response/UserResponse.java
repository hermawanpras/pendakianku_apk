package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserResponse extends BaseResponse {
    @SerializedName("data")
    public List<UserModel> data;

    public static class UserModel {
        @SerializedName("id_user")
        public String idUser;

        @SerializedName("nama_user")
        public String username;

        @SerializedName("password")
        public String password;

        @SerializedName("email")
        public String email;

        @SerializedName("no_telp_user")
        public String noTelp;

        @SerializedName("alamat_user")
        public String alamat;

        @SerializedName("role_user")
        public String roleUser;

        @SerializedName("foto_profil")
        public String fotoProfil;

        public UserModel(String idUser, String username, String password, String email, String noTelp, String alamat, String roleUser, String fotoProfil) {
            this.idUser = idUser;
            this.username = username;
            this.password = password;
            this.email = email;
            this.noTelp = noTelp;
            this.alamat = alamat;
            this.roleUser = roleUser;
            this.fotoProfil = fotoProfil;
        }

        public String getIdUser() {
            return idUser;
        }

        public void setIdUser(String idUser) {
            this.idUser = idUser;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getNoTelp() {
            return noTelp;
        }

        public void setNoTelp(String noTelp) {
            this.noTelp = noTelp;
        }

        public String getAlamat() {
            return alamat;
        }

        public void setAlamat(String alamat) {
            this.alamat = alamat;
        }

        public String getRoleUser() {
            return roleUser;
        }

        public void setRoleUser(String roleUser) {
            this.roleUser = roleUser;
        }

        public String getFotoProfil() {
            return fotoProfil;
        }

        public void setFotoProfil(String fotoProfil) {
            this.fotoProfil = fotoProfil;
        }
    }
}
