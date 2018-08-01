package com.onlineparkingticket.httpmanager;


import com.onlineparkingticket.model.LoginModel;
import com.onlineparkingticket.model.OTPModel;
import com.onlineparkingticket.model.SignupModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Webservices {

    @FormUrlEncoded
    @POST("auth/create")
    Call<OTPModel> getOTP(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("user/create")
    Call<SignupModel> SignUp(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("auth/login")
    Call<LoginModel> loginUser(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("ticket/listing")
    Call<LoginModel> resolvedList(@FieldMap Map<String, String> params);

    /*@Multipart
    @POST("editUserProfile")
    Call<LoginBase> editUserProfile(@Part MultipartBody.Part image,
                                    @Part("user_id") RequestBody user_id,
                                    @Part("user_name") RequestBody user_name,
                                    @Part("user_location") RequestBody user_location,
                                    @Part("first_key") RequestBody first_key,
                                    @Part("user_desc") RequestBody user_desc);


    @POST("listLocation")
    Call<LocationBase> listLocation(@Body JsonObject jsonBody);

    @POST("listCategory")
    Call<CategoryBase> listCategory(@Body JsonObject jsonBody);

    @POST("listListing")
    Call<CategorydetailBase> listListing(@Body JsonObject jsonBody);


    @POST("listingDetails")
    Call<ListdetailBase> listingDetails(@Body JsonObject jsonBody);


    @POST("userProfile")
    Call<LoginBase> userProfile(@Body JsonObject jsonBody);

    @POST("bookmark")
    Call<LoginBase> bookmark(@Body JsonObject jsonBody);

    @POST("pushnotification_flag")
    Call<LoginBase> pushnotification_flag(@Body JsonObject jsonBody);


    @POST("staticPage")
    Call<StaticPageBase> staticPage(@Body JsonObject jsonBody);

    @POST("contactUs")
    Call<LoginBase> contactUs(@Body JsonObject jsonBody);*/
}

