package com.onlineparkingticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MobileVerifyModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;
    @SerializedName("OTPcode")
    @Expose
    private String oTPcode;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getOTPcode() {
        return oTPcode;
    }

    public void setOTPcode(String oTPcode) {
        this.oTPcode = oTPcode;
    }

    public class Data {

        @SerializedName("status")
        @Expose
        private Integer status;
        @SerializedName("hasregistered")
        @Expose
        private Boolean hasregistered;
        @SerializedName("resetpasswordtoken")
        @Expose
        private String resetpasswordtoken;/*
        @SerializedName("resetpasswordexpires")
        @Expose
        private Integer resetpasswordexpires;*/
        @SerializedName("frgtpasswordtoken")
        @Expose
        private String frgtpasswordtoken;
        @SerializedName("frgtpasswordexpires")
        @Expose
        private Integer frgtpasswordexpires;
        @SerializedName("refreshtoken")
        @Expose
        private String refreshtoken;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("mobileno")
        @Expose
        private String mobileno;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }

        public Boolean getHasregistered() {
            return hasregistered;
        }

        public void setHasregistered(Boolean hasregistered) {
            this.hasregistered = hasregistered;
        }

        public String getResetpasswordtoken() {
            return resetpasswordtoken;
        }

        public void setResetpasswordtoken(String resetpasswordtoken) {
            this.resetpasswordtoken = resetpasswordtoken;
        }

       /* public Integer getResetpasswordexpires() {
            return resetpasswordexpires;
        }

        public void setResetpasswordexpires(Integer resetpasswordexpires) {
            this.resetpasswordexpires = resetpasswordexpires;
        }*/

        public String getFrgtpasswordtoken() {
            return frgtpasswordtoken;
        }

        public void setFrgtpasswordtoken(String frgtpasswordtoken) {
            this.frgtpasswordtoken = frgtpasswordtoken;
        }

        public Integer getFrgtpasswordexpires() {
            return frgtpasswordexpires;
        }

        public void setFrgtpasswordexpires(Integer frgtpasswordexpires) {
            this.frgtpasswordexpires = frgtpasswordexpires;
        }

        public String getRefreshtoken() {
            return refreshtoken;
        }

        public void setRefreshtoken(String refreshtoken) {
            this.refreshtoken = refreshtoken;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }
}
