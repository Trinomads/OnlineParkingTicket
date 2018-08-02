package com.onlineparkingticket.httpmanager;


import com.onlineparkingticket.model.EditUserDetailsModel;
import com.onlineparkingticket.model.LoginModel;
import com.onlineparkingticket.model.NotificationModel;
import com.onlineparkingticket.model.OTPModel;
import com.onlineparkingticket.model.SignupModel;
import com.onlineparkingticket.model.TicketDetailsModel;
import com.onlineparkingticket.model.TicketListingModel;
import com.onlineparkingticket.model.UserDetailsModel;

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
    Call<TicketListingModel> resolvedList(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("user/view")
    Call<UserDetailsModel> getUserDetails(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("user/update")
    Call<EditUserDetailsModel> editUserDetails(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("ticket/view")
    Call<TicketDetailsModel> getTicketDetails(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("notification/listing")
    Call<NotificationModel> getNotifications(@FieldMap Map<String, String> params);

    /*@Multipart
    @POST("editUserProfile")
    Call<LoginBase> editUserProfile(@Part MultipartBody.Part image,
                                    @Part("user_id") RequestBody user_id,
                                    @Part("user_name") RequestBody user_name,
                                    @Part("user_location") RequestBody user_location,
                                    @Part("first_key") RequestBody first_key,
                                    @Part("user_desc") RequestBody user_desc);*/
}

