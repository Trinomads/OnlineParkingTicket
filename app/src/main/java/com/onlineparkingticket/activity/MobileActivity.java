package com.onlineparkingticket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.EditTextBold;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.httpmanager.ApiHandler;
import com.onlineparkingticket.model.ForgotPasswordModel;
import com.onlineparkingticket.model.OTPModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class MobileActivity extends BaseActivity {
    private Toolbar toolbar;
    private EditTextBold edtMobile;
    private LinearLayout linearPleaGuiltyNext;
    public static MobileActivity activity;
    Bundle b;
    private String activty = "";
    private String redirect = "0";
    private CountryCodePicker cppCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile);
        activity = this;
        init(activity);
        b = new Bundle();
        b = getIntent().getExtras();

        activty = getResources().getString(R.string.signup);

        if (b != null) {
            activty = b.getString("activty");
            redirect = b.getString("redirect");
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
        edtMobile.requestFocus();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edtMobile, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(edtMobile.getWindowToken(), 0);
    }

    private void setAction() {
        linearPleaGuiltyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CommonUtils.isTextAvailable(edtMobile.getText().toString())) {
                    Toast.makeText(activity, getString(R.string.msg_plz_enter_mobile), Toast.LENGTH_SHORT).show();
                } else {
                    if (redirect.equalsIgnoreCase("0")) {
                        //Signup
                        sendOTPRegister("+" + cppCode.getSelectedCountryCode());
                    } else {
                        //ForgotPassword
                        sendOTPForgotPassword("+" + cppCode.getSelectedCountryCode());
                    }
                }
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtMobile = (EditTextBold) findViewById(R.id.edt_mobile);
        linearPleaGuiltyNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);

        cppCode = (CountryCodePicker) findViewById(R.id.cpp_CountryCode);
    }

    public void sendOTPRegister(final String countryCode) {

        if (CommonUtils.isConnectingToInternet(activity)) {
            AppGlobal.showProgressDialog(activity);

            Map<String, String> params = new HashMap<String, String>();

            params.put("phone_number", countryCode + "" + edtMobile.getText().toString());
            params.put("country_code", countryCode);
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
                                Intent i = new Intent(activity, OTPActivity.class);
                                i.putExtra("activty", activty);
                                i.putExtra("redirect", redirect);
                                i.putExtra("mobileNo", edtMobile.getText().toString());
                                i.putExtra("countryCode", countryCode);
//                                i.putExtra("OTP", response.body().getOTPcode());
//                                i.putExtra("userToken", response.body().getData().getResetpasswordtoken());
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
                public void onFailure(Call<OTPModel> call, Throwable t) {
                    AppGlobal.showLog(activity, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(activity, getResources().getString(R.string.no_internet_exist));
        }
    }

    public void sendOTPForgotPassword(final String countryCode) {

        if (CommonUtils.isConnectingToInternet(activity)) {
            AppGlobal.showProgressDialog(activity);

            Map<String, String> params = new HashMap<String, String>();

            params.put("phone_number", countryCode + "" + edtMobile.getText().toString());
            params.put("country_code", countryCode);
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
                                CommonUtils.commonToast(activity, response.body().getMessage());

                                Intent i = new Intent(activity, OTPActivity.class);
                                i.putExtra("activty", activty);
                                i.putExtra("redirect", redirect);
                                i.putExtra("mobileNo", edtMobile.getText().toString());
                                i.putExtra("countryCode", countryCode);
                                i.putExtra("resetToken", response.body().getResetToken());
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
                public void onFailure(Call<ForgotPasswordModel> call, Throwable t) {
                    AppGlobal.showLog(activity, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(activity, getResources().getString(R.string.no_internet_exist));
        }
    }
}