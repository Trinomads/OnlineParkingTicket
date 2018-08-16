package com.onlineparkingticket.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.TicketListingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class DefferedDispositionActivity extends BaseActivity {

    public static Context mContext;
    private LinearLayout lvNext;
    private EditText edName, edEmail, edDrivingLicense, edVioNo, edVioDesc, edTicketCharge;
    private String stItemId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deffered_position);
        mContext = DefferedDispositionActivity.this;
        init(DefferedDispositionActivity.this);
        setHeaderWithBack(getString(R.string.deffered_disposition), true, false);
        Intent intent = getIntent();
        if (intent != null) {
            stItemId = intent.getStringExtra("itemId");

        }
        init();
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_DefferedDisposition_Next);

        edName = (EditText) findViewById(R.id.ed_DefferedPosition_Name);
        edEmail = (EditText) findViewById(R.id.ed_DefferedPosition_Email);
        edDrivingLicense = (EditText) findViewById(R.id.ed_DefferedPosition_DrivingLicense);
        edVioNo = (EditText) findViewById(R.id.ed_DefferedPosition_Vio_NO);
        edVioDesc = (EditText) findViewById(R.id.ed_DefferedPosition_VioDesc);
        edTicketCharge = (EditText) findViewById(R.id.ed_DefferedPosition_TicketCharge);

        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidField()) {

                    uploadUserProfile();

               }
            }
        });
    }

    private boolean isValidField() {

        if (!CommonUtils.isTextAvailable(edName.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_name));
            return false;
        } else if (!CommonUtils.isTextAvailable(edEmail.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_email));
            return false;
        } else if (!CommonUtils.isEmailValid(edEmail.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_valid_email));
            return false;
        } else if (!CommonUtils.isTextAvailable(edDrivingLicense.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_license));
            return false;
        } else if (!CommonUtils.isTextAvailable(edVioNo.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_vio_no));
            return false;
        } else if (!CommonUtils.isTextAvailable(edVioDesc.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_vio_desc));
            return false;
        } else if (!CommonUtils.isTextAvailable(edTicketCharge.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_ticket_charge));
            return false;
        } else
            return true;
    }

    public void dialogRequestDate() {
        final Dialog dialog = new Dialog(mContext);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        View vi = getLayoutInflater().inflate(R.layout.dialog_thank_you, null, false);
        dialog.setContentView(vi);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setAttributes(lp);

        TextView tvDone = (TextView) dialog.findViewById(R.id.tv_Dialog_ThankYou_Done);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                DefferedDispositionActivity.this.finish();
            }
        });

        dialog.show();
    }


    public void uploadUserProfile() {
        if (CommonUtils.isConnectingToInternet(mContext)) {


            AppGlobal.showProgressDialog(mContext);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));
            params.put("_id", stItemId);
            params.put("type", "nocontest");

            JsonObject gsonObject = new JsonObject();
            JSONObject jsonObject = new JSONObject();

            try {
                jsonObject.put("type","DEFFERED_DISPOSITION");
                jsonObject.put("name",edName.getText().toString().trim());
                jsonObject.put("email",edEmail.getText().toString().trim());
                jsonObject.put("drivinglicense",edDrivingLicense.getText().toString().trim());
                jsonObject.put("violationnumber",edVioNo.getText().toString().trim());
                jsonObject.put("violationdescription",edVioDesc.getText().toString().trim());
                jsonObject.put("price",edTicketCharge.getText().toString().trim());
                jsonObject.put("currency","$");

            } catch (JSONException e) {
                e.printStackTrace();
            }


            params.put("nocontest", String.valueOf(jsonObject));
            System.out.println("Map is " + params);
            new ApiHandlerToken(DefferedDispositionActivity.this).getApiService().fixit(params).enqueue(new Callback<TicketListingModel>() {
                @Override
                public void onResponse(Call<TicketListingModel> call, Response<TicketListingModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                dialogRequestDate();
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
                public void onFailure(Call<TicketListingModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            Log.e("this", "error" + "no internet");
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }

    }
}
