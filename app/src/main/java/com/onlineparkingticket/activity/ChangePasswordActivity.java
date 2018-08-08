package com.onlineparkingticket.activity;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.ChangePasswordModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class ChangePasswordActivity extends BaseActivity {

    public static ChangePasswordActivity mContext;
    private EditText edOld, edNew, edConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mContext = ChangePasswordActivity.this;
        init(ChangePasswordActivity.this);
        setHeaderWithBack(getString(R.string.change_password), true, true);

        txt_right_header.setImageResource(R.drawable.done);
        txt_right_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidField()) {
                    changePassword();
                }
            }
        });

        init();
    }

    private void init() {
        edOld = (EditText) findViewById(R.id.ed_ChangePassword_Old);
        edNew = (EditText) findViewById(R.id.ed_ChangePassword_New);
        edConfirm = (EditText) findViewById(R.id.ed_ChangePassword_Confirm);
    }

    private boolean isValidField() {
        if (!CommonUtils.isTextAvailable(edOld.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_password));
            return false;
        } else if (edOld.length() < 8) {
            CommonUtils.commonToast(this, getString(R.string.msg_validate_password));
            return false;
        } else if (!CommonUtils.isTextAvailable(edNew.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_new_password));
            return false;
        } else if (edNew.length() < 8) {
            CommonUtils.commonToast(this, getString(R.string.msg_validate_new_password));
            return false;
        } else if (!CommonUtils.isTextAvailable(edConfirm.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_confirm_password));
            return false;
        } else if (edConfirm.length() < 8) {
            CommonUtils.commonToast(this, getString(R.string.msg_validate_cnf_password));
            return false;
        } else if (!edNew.getText().toString().equalsIgnoreCase(edConfirm.getText().toString())) {
            CommonUtils.commonToast(this, getString(R.string.validate_password));
            return false;
        } else
            return true;
    }

    public void changePassword() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            AppGlobal.showProgressDialog(mContext);

            Map<String, String> params = new HashMap<String, String>();
            params.put("password", edOld.getText().toString().trim());
            params.put("new_password", edNew.getText().toString().trim());

            ApiHandlerToken.getApiService().changePassword(params).enqueue(new Callback<ChangePasswordModel>() {
                @Override
                public void onResponse(Call<ChangePasswordModel> call, Response<ChangePasswordModel> response) {
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
                public void onFailure(Call<ChangePasswordModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
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

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_DialogThankYou_Title);
        tvTitle.setText(getString(R.string.success));

        TextView tvMsg = (TextView) dialog.findViewById(R.id.tv_DialogThankYou_Msg);
        tvMsg.setText(getString(R.string.forgot_success));

        TextView tvDone = (TextView) dialog.findViewById(R.id.tv_Dialog_ThankYou_Done);
        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                AppGlobal.logoutApp(mContext);
            }
        });

        dialog.show();
    }
}
