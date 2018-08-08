package com.onlineparkingticket.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TicketListingModel {
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
        private ArrayList<Ticket> tickets;
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

        public ArrayList<Ticket> getTickets() {
            return tickets;
        }

        public void setTickets(ArrayList<Ticket> tickets) {
            this.tickets = tickets;
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

    public class Ticket {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("price")
        @Expose
        private Integer price;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("violationno")
        @Expose
        private String violationno;
        @SerializedName("plateno")
        @Expose
        private String plateno;
        @SerializedName("speed")
        @Expose
        private String speed;
        @SerializedName("zone")
        @Expose
        private String zone;
        @SerializedName("user")
        @Expose
        private User user;
        @SerializedName("fixit")
        @Expose
        private Fixit fixit;
        @SerializedName("guilty")
        @Expose
        private Guilty guilty;
        @SerializedName("nocontest")
        @Expose
        private Nocontest nocontest;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;
        @SerializedName("__v")
        @Expose
        private Integer v;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getPrice() {
            return price;
        }

        public void setPrice(Integer price) {
            this.price = price;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getViolationno() {
            return violationno;
        }

        public void setViolationno(String violationno) {
            this.violationno = violationno;
        }

        public String getPlateno() {
            return plateno;
        }

        public void setPlateno(String plateno) {
            this.plateno = plateno;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getZone() {
            return zone;
        }

        public void setZone(String zone) {
            this.zone = zone;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public Fixit getFixit() {
            return fixit;
        }

        public void setFixit(Fixit fixit) {
            this.fixit = fixit;
        }

        public Guilty getGuilty() {
            return guilty;
        }

        public void setGuilty(Guilty guilty) {
            this.guilty = guilty;
        }

        public Nocontest getNocontest() {
            return nocontest;
        }

        public void setNocontest(Nocontest nocontest) {
            this.nocontest = nocontest;
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

    public class User {

        @SerializedName("images")
        @Expose
        private ArrayList<String> images;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("access")
        @Expose
        private String access;
        @SerializedName("platno")
        @Expose
        private String platno;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobileno")
        @Expose
        private String mobileno;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("password")
        @Expose
        private String password;
        @SerializedName("countrycode")
        @Expose
        private String countrycode;
        @SerializedName("phoneno")
        @Expose
        private String phoneno;
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

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getAccess() {
            return access;
        }

        public void setAccess(String access) {
            this.access = access;
        }

        public String getPlatno() {
            return platno;
        }

        public void setPlatno(String platno) {
            this.platno = platno;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobileno() {
            return mobileno;
        }

        public void setMobileno(String mobileno) {
            this.mobileno = mobileno;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public void setCountrycode(String countrycode) {
            this.countrycode = countrycode;
        }

        public String getPhoneno() {
            return phoneno;
        }

        public void setPhoneno(String phoneno) {
            this.phoneno = phoneno;
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

    public class Fixit {

        @SerializedName("images")
        @Expose
        private ArrayList<String> images;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

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

    }

    public class Guilty {

        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    }

    public class Nocontest {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("updatedAt")
        @Expose
        private String updatedAt;
        @SerializedName("createdAt")
        @Expose
        private String createdAt;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

    }
}
