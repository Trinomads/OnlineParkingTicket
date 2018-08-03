package com.onlineparkingticket.httpmanager;


import com.onlineparkingticket.model.EditUserDetailsModel;
import com.onlineparkingticket.model.ForgotPasswordModel;
import com.onlineparkingticket.model.LoginModel;
import com.onlineparkingticket.model.MobileVerifyModel;
import com.onlineparkingticket.model.NotificationModel;
import com.onlineparkingticket.model.OTPModel;
import com.onlineparkingticket.model.SaveImageModel;
import com.onlineparkingticket.model.SignupModel;
import com.onlineparkingticket.model.TicketDetailsModel;
import com.onlineparkingticket.model.TicketListingModel;
import com.onlineparkingticket.model.UserDetailsModel;
import com.onlineparkingticket.model.VerifyForgotPasswordModel;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Webservices {

    @FormUrlEncoded
    @POST("auth/request-phone-verfication")
    Call<OTPModel> getOTP(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("auth/create")
    Call<MobileVerifyModel> verifyOTP(@FieldMap Map<String, String> params);

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

    @FormUrlEncoded
    @POST("auth/forgot-password")
    Call<ForgotPasswordModel> getOTPForgotPassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("auth/recover-password-verify-mobileno")
    Call<VerifyForgotPasswordModel> verifyOTPForgotPassword(@FieldMap Map<String, String> params);

    @FormUrlEncoded
    @POST("auth/recover-password")
    Call<VerifyForgotPasswordModel> changePasswordForgotPassword(@FieldMap Map<String, String> params);

    @Multipart
    @POST("user/save-images-single")
    Call<SaveImageModel> editUserProfile(@Part MultipartBody.Part image);
}

