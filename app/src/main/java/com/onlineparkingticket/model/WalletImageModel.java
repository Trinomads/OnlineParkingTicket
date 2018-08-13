package com.onlineparkingticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WalletImageModel {
    @SerializedName("imagepath")
    @Expose
    private String imagepath;
    @SerializedName("success")
    @Expose
    private Boolean success;

    public String getImagepath() {
        return imagepath;
    }

    public void setImagepath(String imagepath) {
        this.imagepath = imagepath;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }
}
