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
public class RegistrationActivity extends BaseActivity {

    private Toolbar toolbar;
    private TextViewRegular txtName;
    private EditTextBold edtName;
    private TextViewRegular txtEmail;
    private EditTextBold edtEmail;
    private TextViewRegular txtDriving;
    private EditTextBold edtDriving;
    private LinearLayout linearPleaGuiltyNext;
    public static RegistrationActivity activity;
    String stMobile = "", stToken = "", stCountryCode, countryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.signup), true, false);

        Intent intent = getIntent();
        if (intent != null) {
            stMobile = intent.getStringExtra("mobileNo");
            stToken = intent.getStringExtra("userToken");
            stCountryCode = intent.getStringExtra("countryCode");
            countryName = intent.getStringExtra("countryName");
        }


        findViews();
        setAction();
    }

    private void setAction() {
        linearPleaGuiltyNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidField()) {
                    Intent intent = new Intent(activity, ConfirmPasswordActivity.class);
                    intent.putExtra("mobileNo", stMobile);
                    intent.putExtra("redirect", "0");
                    intent.putExtra("activty", getString(R.string.signup));
                    intent.putExtra("name", edtName.getText().toString());
                    intent.putExtra("email", edtEmail.getText().toString());
                    intent.putExtra("drivingLicense", edtDriving.getText().toString());
                    intent.putExtra("userToken", stToken);
                    intent.putExtra("stCountryCode", stCountryCode);
                    intent.putExtra("countryName", countryName);
                    startActivity(intent);
                }

                /*startActivity(new Intent(activity, ConfirmPasswordActivity.class));
                finish();*/
            }
        });

    }


    private boolean isValidField() {

        if (!CommonUtils.isTextAvailable(edtName.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_name));
            return false;
        } else if (!CommonUtils.isTextAvailable(edtEmail.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_email));
            return false;
        } else if (!CommonUtils.isEmailValid(edtEmail.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_valid_email));
            return false;
        } else if (!CommonUtils.isTextAvailable(edtDriving.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_dl));
            return false;
        } else
            return true;


    }


    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        txtName = (TextViewRegular) findViewById(R.id.txt_name);
        edtName = (EditTextBold) findViewById(R.id.edt_name);
        txtEmail = (TextViewRegular) findViewById(R.id.txt_email);
        edtEmail = (EditTextBold) findViewById(R.id.edt_email);
        txtDriving = (TextViewRegular) findViewById(R.id.txt_driving);
        edtDriving = (EditTextBold) findViewById(R.id.edt_driving);
        linearPleaGuiltyNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);
    }

}
