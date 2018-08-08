package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.EditTextRegular;
import com.onlineparkingticket.commonTextView.TextViewBlack;
import com.onlineparkingticket.commonTextView.TextViewBold;
import com.onlineparkingticket.commonTextView.TextViewRegular;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandler;
import com.onlineparkingticket.model.LoginModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class LoginActivity extends BaseActivity {

    private ImageView imgLogo;
    private TextViewBold txtAppname;
    private EditTextRegular edtMobile;
    private EditTextRegular edtPassword;
    private CheckBox chkPassword;
    private TextViewBlack txtLogin;
    private TextViewRegular txtRegisternow;
    private TextViewRegular txtForgedtPassword;
    private CountryCodePicker ccpCountryCode;

    public static LoginActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        activity = this;
        init(activity);
        findViews();
        setAction();
    }

    private void setAction() {

        if (AppGlobal.getStringPreference(activity, WsConstant.SP_REMEMBER).equalsIgnoreCase("")) {
            AppGlobal.setStringPreference(activity, "0", WsConstant.SP_REMEMBER);
        }

        if (AppGlobal.getStringPreference(activity, WsConstant.SP_REMEMBER).equalsIgnoreCase("1")) {
            edtMobile.setText(CommonUtils.isTextAvailableWithReturnData(AppGlobal.getStringPreference(activity, WsConstant.SP_MOBILE), ""));
            chkPassword.setChecked(true);
        }

        chkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppGlobal.setStringPreference(activity, "1", WsConstant.SP_REMEMBER);
                } else {
                    AppGlobal.setStringPreference(activity, "0", WsConstant.SP_REMEMBER);
                }
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidField()) {
                    loginUser();
                }
            }
        });

        txtRegisternow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MobileActivity.class);
                i.putExtra("activty", getString(R.string.signup));
                i.putExtra("redirect", "0");
                startActivity(i);
                finish();
            }
        });

        txtForgedtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, MobileActivity.class);
                i.putExtra("activty", getString(R.string.title_forgetpassword));
                i.putExtra("redirect", "1");
                startActivity(i);
                finish();
            }
        });
    }

    private boolean isValidField() {

        if (!CommonUtils.isTextAvailable(edtMobile.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_mobile));
            return false;
        }/* else if (!CommonUtils.isEmailValid(edtMobile.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_valid_email));
            return false;
        } */else if (!CommonUtils.isTextAvailable(edtPassword.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_password));
            return false;
        } else
            return true;
    }


    private void findViews() {
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        txtAppname = (TextViewBold) findViewById(R.id.txt_appname);
        edtMobile = (EditTextRegular) findViewById(R.id.edt_email);
        edtPassword = (EditTextRegular) findViewById(R.id.edt_password);
        chkPassword = (CheckBox) findViewById(R.id.chk_password);
        txtLogin = (TextViewBlack) findViewById(R.id.txt_login);
        txtRegisternow = (TextViewRegular) findViewById(R.id.txt_registernow);
        txtForgedtPassword = (TextViewRegular) findViewById(R.id.txt_forgetpassword);
        ccpCountryCode = (CountryCodePicker) findViewById(R.id.cpp_Login_CountryCode);
    }

    public void loginUser() {
        if (CommonUtils.isConnectingToInternet(activity)) {
            AppGlobal.showProgressDialog(activity);

            Map<String, String> params = new HashMap<String, String>();
            params.put("mobileno", "+" + ccpCountryCode.getSelectedCountryCode() + "" +edtMobile.getText().toString().trim());
            params.put("password", edtPassword.getText().toString().trim());

            ApiHandler.getApiService().loginUser(params).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(activity, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                CommonUtils.commonToast(activity, response.body().getMessage());

                                AppGlobal.setStringPreference(activity, response.body().getToken(), WsConstant.SP_TOKEN);
                                AppGlobal.setStringPreference(activity, response.body().getUser().getId(), WsConstant.SP_ID);
                                AppGlobal.setStringPreference(activity, response.body().getUser().getName(), WsConstant.SP_NAME);
                                AppGlobal.setStringPreference(activity, response.body().getUser().getEmail(), WsConstant.SP_EMAIL);
                                AppGlobal.setStringPreference(activity, response.body().getUser().getMobileno(), WsConstant.SP_MOBILE);
                                AppGlobal.setStringPreference(activity, response.body().getUser().getAddress(), WsConstant.SP_ADDRESS);
                                AppGlobal.setStringPreference(activity, response.body().getUser().getPlatno(), WsConstant.SP_LICENCE_PLAT);
                                AppGlobal.setStringPreference(activity, response.body().getUser().getCountrycode(), WsConstant.SP_COUNTRY_CODE);
                                AppGlobal.setStringPreference(activity, response.body().getUser().getPhoneno(), WsConstant.SP_PHONE);
                                AppGlobal.setArrayListPreference(activity, response.body().getUser().getImages(), WsConstant.SP_IMAGES);

                                Intent intent = new Intent(activity, HomeNavigationDrawer.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
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
                public void onFailure(Call<LoginModel> call, Throwable t) {
                    AppGlobal.showLog(activity, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(activity, getResources().getString(R.string.no_internet_exist));
        }
    }

}
