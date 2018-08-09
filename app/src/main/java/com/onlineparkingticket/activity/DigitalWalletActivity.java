package com.onlineparkingticket.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.hbb20.CountryCodePicker;
import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandler;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.DigitalWalletModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class DigitalWalletActivity extends BaseActivity {

    public static Activity mContext;
    private LinearLayout lvNext;
    private EditText edDrivingLicense, edPlate, edVIN, edInsurance, edAddress, edState, edZip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_wallet);
        mContext = DigitalWalletActivity.this;
        init(DigitalWalletActivity.this);
        setHeaderWithBack(getString(R.string.digital_wallet), true, false);

        init();
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_DigitalWallet_Next);

        edDrivingLicense = (EditText) findViewById(R.id.ed_DigitalWallet_DrivingLicense);
        edPlate = (EditText) findViewById(R.id.ed_DigitalWallet_Plate);
        edVIN = (EditText) findViewById(R.id.ed_DigitalWallet_VIN);
        edInsurance = (EditText) findViewById(R.id.ed_DigitalWallet_Insurance);
        edAddress = (EditText) findViewById(R.id.ed_DigitalWallet_Address);
        edState = (EditText) findViewById(R.id.ed_DigitalWallet_State);
        edZip = (EditText) findViewById(R.id.ed_DigitalWallet_Zip);

        edDrivingLicense.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_LICENCE_PLAT), ""));

        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidField()) {
//                    startActivity(new Intent(DigitalWalletActivity.this, DigitalWalletDocumentsActivity.class));
                    createDigitalWallet();
                }
            }
        });
    }

    private boolean isValidField() {

        if (!CommonUtils.isTextAvailable(edDrivingLicense.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_license));
            return false;
        } else if (!CommonUtils.isTextAvailable(edPlate.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_plat));
            return false;
        } else if (!CommonUtils.isTextAvailable(edVIN.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_vin));
            return false;
        } else if (!CommonUtils.isTextAvailable(edInsurance.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_insurance));
            return false;
        } else if (!CommonUtils.isTextAvailable(edAddress.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_address));
            return false;
        } else if (!CommonUtils.isTextAvailable(edState.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_state));
            return false;
        } else if (!CommonUtils.isTextAvailable(edZip.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_zip));
            return false;
        } else
            return true;
    }

    public void createDigitalWallet() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            AppGlobal.showProgressDialog(mContext);

            Map<String, String> params = new HashMap<String, String>();
            params.put("drivinglicience", edDrivingLicense.getText().toString().trim());
            params.put("licenceplate", edPlate.getText().toString().trim());
            params.put("registrationvin", edVIN.getText().toString().trim());
            params.put("insurance", edInsurance.getText().toString().trim());
            params.put("address", edAddress.getText().toString().trim());
            params.put("state", edState.getText().toString().trim());
            params.put("city", edZip.getText().toString().trim());

            ApiHandlerToken.getApiService().createDigitalWallet(params).enqueue(new Callback<DigitalWalletModel>() {
                @Override
                public void onResponse(Call<DigitalWalletModel> call, Response<DigitalWalletModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                CommonUtils.commonToast(mContext, response.body().getMessage());
                                DigitalWalletActivity.this.finish();
                            } else {
                                CommonUtils.commonToast(mContext, response.body().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(mContext, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<DigitalWalletModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
    }
}
