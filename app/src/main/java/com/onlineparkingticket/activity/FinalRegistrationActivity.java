package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.TextViewBlack;
import com.onlineparkingticket.commonTextView.TextViewBold;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.httpmanager.ApiHandler;
import com.onlineparkingticket.model.SignupModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class FinalRegistrationActivity extends BaseActivity {
    private Toolbar toolbar;
    private LinearLayout linearDidnotgetcode;
    private TextViewBold txtTandc;
    private TextViewBold txtPrivacypolicy;
    private TextViewBlack txtGetstarted;

    public static FinalRegistrationActivity activity;
    public String stMobile = "", stName = "", stEmail = "", stLicense = "", stPassword = "", stToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_registration);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.signup), true, false);

        Intent intent = getIntent();
        if (intent != null) {
            stMobile = intent.getStringExtra("mobileNo");
            stName = intent.getStringExtra("name");
            stEmail = intent.getStringExtra("email");
            stLicense = intent.getStringExtra("drivingLicense");
            stPassword = intent.getStringExtra("password");
            stToken = intent.getStringExtra("userToken");
        }

        findViews();
        setAction();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        linearDidnotgetcode = (LinearLayout) findViewById(R.id.linear_didnotgetcode);
        txtTandc = (TextViewBold) findViewById(R.id.txt_tandc);
        txtPrivacypolicy = (TextViewBold) findViewById(R.id.txt_privacypolicy);
        txtGetstarted = (TextViewBlack) findViewById(R.id.txt_getstarted);
    }

    private void setAction() {
        txtGetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
                /*Intent intent = new Intent(activity, HomeNavigationDrawer.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();*/
            }
        });
    }

    public void signUpUser() {

        if (CommonUtils.isConnectingToInternet(activity)) {
            AppGlobal.showProgressDialog(activity);

            Map<String, String> params = new HashMap<String, String>();
            params.put("name", stName);
            params.put("email", stEmail);
            params.put("platno", stLicense);
            params.put("mobileno", stMobile);
            params.put("password", stPassword);
            params.put("address", stPassword);
            params.put("token", stToken);

            ApiHandler.getApiService().SignUp(params).enqueue(new Callback<SignupModel>() {
                @Override
                public void onResponse(Call<SignupModel> call, Response<SignupModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(activity, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                CommonUtils.commonToast(activity, response.body().getMessage());

                                Intent intent = new Intent(activity, LoginActivity.class);
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
                public void onFailure(Call<SignupModel> call, Throwable t) {
                    AppGlobal.showLog(activity, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(activity, getResources().getString(R.string.no_internet_exist));
        }
    }
}
