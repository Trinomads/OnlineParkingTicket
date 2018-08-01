package com.onlineparkingticket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.EditTextBold;
import com.onlineparkingticket.constant.CommonUtils;

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
    private String stOTP = "", stToken = "";

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
                stOTP = b.getString("OTP");
                stToken = b.getString("userToken");
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
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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
                } else if (!edtOtp.getText().toString().equalsIgnoreCase(stOTP)) {
                    CommonUtils.commonToast(activity, getString(R.string.msg_plz_enter_valid_opt));
                } else {
                    if (redirect.equalsIgnoreCase("1")) {
                        Intent i = new Intent(activity, ConfirmPasswordActivity.class);
                        i.putExtra("activty", activty);
                        i.putExtra("redirect", redirect);
                        startActivity(i);

                    } else {
                        Intent i = new Intent(activity, RegistrationActivity.class);
                        i.putExtra("mobileNo", stMobile);
                        i.putExtra("userToken", stToken);
                        startActivity(i);
                    }
                }
            }
        });
    }

    /**
     * Find the Views in the layout<br />
     * <br />
     * Auto-created on 2018-07-31 15:05:20 by Android Layout Finder
     * (http://www.buzzingandroid.com/tools/android-layout-finder)
     */
    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        edtOtp = (EditTextBold) findViewById(R.id.edt_otp);
        linearDidnotgetcode = (LinearLayout) findViewById(R.id.linear_didnotgetcode);
        linearPleaGuiltyNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);
    }

}
