package com.hermawan.pendakian.api.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportResponse extends BaseResponse {
    @SerializedName("data")
    public List<ReportModel> data;

    public static class ReportModel {
        @SerializedName("total_pendaki")
        public String totalPendaki;

        @SerializedName("total_pendapatan")
        public String totalPendapatan;

        @SerializedName("total_bl")
        public String totalBl;

        @SerializedName("total_p")
        public String totalP;

        @SerializedName("total_l")
        public String totalL;

        @SerializedName("total_panderman")
        public String totalPanderman;

        @SerializedName("total_buthak")
        public String totalButhak;

        public ReportModel(String totalPendaki, String totalPendapatan, String totalBl, String totalP, String totalL, String totalPanderman, String totalButhak) {
            this.totalPendaki = totalPendaki;
            this.totalPendapatan = totalPendapatan;
            this.totalBl = totalBl;
            this.totalP = totalP;
            this.totalL = totalL;
            this.totalPanderman = totalPanderman;
            this.totalButhak = totalButhak;
        }

        public String getTotalPendaki() {
            return totalPendaki;
        }

        public void setTotalPendaki(String totalPendaki) {
            this.totalPendaki = totalPendaki;
        }

        public String getTotalPendapatan() {
            return totalPendapatan;
        }

        public void setTotalPendapatan(String totalPendapatan) {
            this.totalPendapatan = totalPendapatan;
        }

        public String getTotalBl() {
            return totalBl;
        }

        public void setTotalBl(String totalBl) {
            this.totalBl = totalBl;
        }

        public String getTotalP() {
            return totalP;
        }

        public void setTotalP(String totalP) {
            this.totalP = totalP;
        }

        public String getTotalL() {
            return totalL;
        }

        public void setTotalL(String totalL) {
            this.totalL = totalL;
        }

        public String getTotalPanderman() {
            return totalPanderman;
        }

        public void setTotalPanderman(String totalPanderman) {
            this.totalPanderman = totalPanderman;
        }

        public String getTotalButhak() {
            return totalButhak;
        }

        public void setTotalButhak(String totalButhak) {
            this.totalButhak = totalButhak;
        }
    }
}
