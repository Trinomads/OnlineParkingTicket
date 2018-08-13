package com.onlineparkingticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GetDigitalWalletModel {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private Data data;

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

    public class Data {

        @SerializedName("data")
        @Expose
        private ArrayList<Datum> data;
        @SerializedName("totalRecords")
        @Expose
        private Integer totalRecords;
        @SerializedName("pageNo")
        @Expose
        private Integer pageNo;
        @SerializedName("perPage")
        @Expose
        private Integer perPage;
        @SerializedName("totalPages")
        @Expose
        private Integer totalPages;

        public ArrayList<Datum> getData() {
            return data;
        }

        public void setData(ArrayList<Datum> data) {
            this.data = data;
        }

        public Integer getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(Integer totalRecords) {
            this.totalRecords = totalRecords;
        }

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }

        public Integer getPerPage() {
            return perPage;
        }

        public void setPerPage(Integer perPage) {
            this.perPage = perPage;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

    }

    public static class Datum {

        @SerializedName("images")
        @Expose
        private ArrayList<String> images;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("insurance")
        @Expose
        private String insurance;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("registrationvin")
        @Expose
        private String registrationvin;
        @SerializedName("licenceplate")
        @Expose
        private String licenceplate;
        @SerializedName("drivinglicience")
        @Expose
        private String drivinglicience;
        @SerializedName("user")
        @Expose
        private String user;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public ArrayList<String> getImages() {
            return images;
        }

        public void setImages(ArrayList<String> images) {
            this.images = images;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getInsurance() {
            return insurance;
        }

        public void setInsurance(String insurance) {
            this.insurance = insurance;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getRegistrationvin() {
            return registrationvin;
        }

        public void setRegistrationvin(String registrationvin) {
            this.registrationvin = registrationvin;
        }

        public String getLicenceplate() {
            return licenceplate;
        }

        public void setLicenceplate(String licenceplate) {
            this.licenceplate = licenceplate;
        }

        public String getDrivinglicience() {
            return drivinglicience;
        }

        public void setDrivinglicience(String drivinglicience) {
            this.drivinglicience = drivinglicience;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
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
