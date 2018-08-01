package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.EditTextBold;
import com.onlineparkingticket.commonTextView.TextViewRegular;
import com.onlineparkingticket.constant.CommonUtils;

@SuppressWarnings("All")
public class ConfirmPasswordActivity extends BaseActivity {
    private Toolbar toolbar;
    private EditTextBold edtPassword;
    private EditTextBold edtCpassword;
    private LinearLayout linearPleaGuiltyNext;
    public static ConfirmPasswordActivity activity;
    Bundle b;
    private String activty;
    private String redirect = "0";
    private TextViewRegular txt_password;
    public String stMobile = "", stName = "", stEmail = "", stLicense = "", stToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);
        activity = this;
        init(activity);


        b = new Bundle();
        b = getIntent().getExtras();
        if (b != null) {
            activty = b.getString("activty");
            redirect = b.getString("redirect");

            if (redirect.equalsIgnoreCase("0")) {
                stMobile = b.getString("mobileNo");
                stName = b.getString("name");
                stEmail = b.getString("email");
                stLicense = b.getString("drivingLicense");
                stToken = b.getString("userToken");
            }

            setHeaderWithBack(activty, true, false);
        } else {
            setHeaderWithBack(getResources().getString(R.string.signup), true, false);
        }

        findViews();
        setAction();
    }

    private void setAction() {
        linearPleaGuiltyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidField()) {
                    if (redirect.equalsIgnoreCase("1")) {
                        startActivity(new Intent(activity, LoginActivity.class));
                        finish();
                    } else {
                        Intent intent = new Intent(activity, FinalRegistrationActivity.class);
                        intent.putExtra("mobileNo", stMobile);
                        intent.putExtra("name", stName);
                        intent.putExtra("email", stEmail);
                        intent.putExtra("drivingLicense", stLicense);
                        intent.putExtra("password", edtPassword.getText().toString());
                        intent.putExtra("userToken", stToken);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private boolean isValidField() {

        if (!CommonUtils.isTextAvailable(edtPassword.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_password));
            return false;
        } else if (!CommonUtils.isTextAvailable(edtCpassword.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_confirm_password));
            return false;
        } else if (!edtPassword.getText().toString().equalsIgnoreCase(edtCpassword.getText().toString())) {
            CommonUtils.commonToast(this, getString(R.string.validate_password));
            return false;
        } else
            return true;


    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txt_password = (TextViewRegular) findViewById(R.id.txt_password);
        edtPassword = (EditTextBold) findViewById(R.id.edt_password);
        edtCpassword = (EditTextBold) findViewById(R.id.edt_cpassword);
        linearPleaGuiltyNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);

        if (redirect.equalsIgnoreCase("1")) {
            txt_password.setText(getResources().getString(R.string.newpassword));
        }
    }

}
