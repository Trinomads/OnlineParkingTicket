package com.onlineparkingticket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.EditTextBold;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.httpmanager.ApiHandler;
import com.onlineparkingticket.model.ForgotPasswordModel;
import com.onlineparkingticket.model.MobileVerifyModel;
import com.onlineparkingticket.model.OTPModel;
import com.onlineparkingticket.model.VerifyForgotPasswordModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class OTPActivity extends BaseActivity {
    private Toolbar toolbar;
    private EditTextBold edtOtp;
    private LinearLayout linearDidnotgetcode;
    private LinearLayout linearPleaGuiltyNext;
    public static OTPActivity activity;
    Bundle b;
    private String activty;
    private String redirect = "0";
    private String stMobile = "";
    private String stCountryCode = "";
    private String stResetToken = "";
    private TextView tvResendOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        activity = this;
        init(activity);

        b = new Bundle();
        b = getIntent().getExtras();

        if (b != null) {
            activty = b.getString("activty");
            redirect = b.getString("redirect");

            if (b.containsKey("mobileNo")) {
                stMobile = b.getString("mobileNo");
                stCountryCode = b.getString("countryCode");
            }

            if (redirect.equalsIgnoreCase("1")) {
                stResetToken = b.getString("resetToken");
                stMobile = b.getString("mobileNo");
                stCountryCode = b.getString("countryCode");
            }

            setHeaderWithBack(activty, true, false);
            findViews();
        } else {
            setHeaderWithBack(getResources().getString(R.string.signup), true, false);
        }

        findViews();
        setAction();
    }

    @Override
    protected void onResume() {
        super.onResume();
        edtOtp.requestFocus();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edtOtp, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edtOtp.getWindowToken(), 0);
    }

    private void setAction() {
        linearPleaGuiltyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isTextAvailable(edtOtp.getText().toString())) {
                    CommonUtils.commonToast(activity, getString(R.string.msg_plz_enter_opt));
                } else {
                    if (redirect.equalsIgnoreCase("0")) {
                        //Signup
                        verifyOTPSignup();
                    } else {
                        //ForgotPassword
                        verifyOTPForgotPassword();
                    }
                }
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtOtp = (EditTextBold) findViewById(R.id.edt_otp);
        linearDidnotgetcode = (LinearLayout) findViewById(R.id.linear_didnotgetcode);
        linearPleaGuiltyNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);

        tvResendOTP = (TextView) findViewById(R.id.tv_OTPScreen_Resend);
        tvResendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (redirect.equalsIgnoreCase("0")) {
                    //Signup
                    sendOTPRegister();
                } else {
                    //ForgotPassword
                    sendOTPForgotPassword();
                }
            }
        });
    }

    public void sendOTPRegister() {

        if (CommonUtils.isConnectingToInternet(activity)) {
            AppGlobal.showProgressDialog(activity);

            Map<String, String> params = new HashMap<String, String>();

            params.put("phone_number", stCountryCode + "" + stMobile);
            params.put("country_code", stCountryCode);
            params.put("via", "sms");

            ApiHandler.getApiService().getOTP(params).enqueue(new Callback<OTPModel>() {
                @Override
                public void onResponse(Call<OTPModel> call, Response<OTPModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(activity, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                               CommonUtils.commonToast(activity, getString(R.string.resen_otp_sent));
                            } else {
                                CommonUtils.commonToast(activity, response.body().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(activity, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<OTPModel> call, Throwable t) {
                    AppGlobal.showLog(activity, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(activity, getResources().getString(R.string.no_internet_exist));
        }
    }

    public void sendOTPForgotPassword() {

        if (CommonUtils.isConnectingToInternet(activity)) {
            AppGlobal.showProgressDialog(activity);

            Map<String, String> params = new HashMap<String, String>();

            params.put("phone_number", stMobile);
            params.put("country_code", stCountryCode);
//            params.put("via", "sms");

            ApiHandler.getApiService().getOTPForgotPassword(params).enqueue(new Callback<ForgotPasswordModel>() {
                @Override
                public void onResponse(Call<ForgotPasswordModel> call, Response<ForgotPasswordModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(activity, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                CommonUtils.commonToast(activity, getString(R.string.resen_otp_sent));
                            } else {
                                CommonUtils.commonToast(activity, response.body().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(activity, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                    AppGlobal.showLog(activity, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(activity, getResources().getString(R.string.no_internet_exist));
        }
    }

    public void verifyOTPSignup() {

        if (CommonUtils.isConnectingToInternet(activity)) {
            AppGlobal.showProgressDialog(activity);

            Map<String, String> params = new HashMap<String, String>();
            params.put("country_code", stCountryCode);
            params.put("phone_number", stCountryCode + "" + stMobile);
            params.put("token", edtOtp.getText().toString().trim());

            ApiHandler.getApiService().verifyOTP(params).enqueue(new Callback<MobileVerifyModel>() {
                @Override
                public void onResponse(Call<MobileVerifyModel> call, Response<MobileVerifyModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(activity, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {

                                if (redirect.equalsIgnoreCase("1")) {
                                    Intent i = new Intent(activity, ConfirmPasswordActivity.class);
                                    i.putExtra("activty", activty);
                                    i.putExtra("redirect", redirect);
                                    startActivity(i);

                                } else {
                                    Intent i = new Intent(activity, RegistrationActivity.class);
                                    i.putExtra("mobileNo", stMobile);
                                    i.putExtra("countryCode", stCountryCode);
                                    i.putExtra("userToken", response.body().getData().getResetpasswordtoken());
                                    startActivity(i);
                                }

                            } else {
                                CommonUtils.commonToast(activity, response.body().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(activity, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<MobileVerifyModel> call, Throwable t) {
                    AppGlobal.showLog(activity, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(activity, getResources().getString(R.string.no_internet_exist));
        }
    }

    public void verifyOTPForgotPassword() {

        if (CommonUtils.isConnectingToInternet(activity)) {
            AppGlobal.showProgressDialog(activity);

            Map<String, String> params = new HashMap<String, String>();
            params.put("country_code", stCountryCode);
            params.put("phone_number", stCountryCode + "" + stMobile);
            params.put("token", edtOtp.getText().toString().trim());
            params.put("reset_token", stResetToken);

            ApiHandler.getApiService().verifyOTPForgotPassword(params).enqueue(new Callback<VerifyForgotPasswordModel>() {
                @Override
                public void onResponse(Call<VerifyForgotPasswordModel> call, Response<VerifyForgotPasswordModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(activity, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {

                                Intent i = new Intent(activity, ConfirmPasswordActivity.class);
                                i.putExtra("activty", activty);
                                i.putExtra("redirect", redirect);
                                i.putExtra("resetToken", stResetToken);
                                i.putExtra("mobileNo", stMobile);
                                i.putExtra("otp", edtOtp.getText().toString().trim());
                                i.putExtra("countryCode", stCountryCode);
                                startActivity(i);

                            } else {
                                CommonUtils.commonToast(activity, response.body().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(activity, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<VerifyForgotPasswordModel> call, Throwable t) {
                    AppGlobal.showLog(activity, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(activity, getResources().getString(R.string.no_internet_exist));
        }
    }

}
