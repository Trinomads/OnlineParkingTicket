package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.EditTextRegular;
import com.onlineparkingticket.commonTextView.TextViewBlack;
import com.onlineparkingticket.commonTextView.TextViewBold;
import com.onlineparkingticket.commonTextView.TextViewRegular;
import com.onlineparkingticket.constant.CommonUtils;

public class LoginActivity extends BaseActivity {

    private ImageView imgLogo;
    private TextViewBold txtAppname;
    private EditTextRegular edtEmail;
    private EditTextRegular edtPassword;
    private CheckBox chkPassword;
    private TextViewBlack txtLogin;
    private TextViewRegular txtRegisternow;
    private TextViewRegular txtForgedtPassword;

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

        chkPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isValidField()) {


                // getLogin();

            }
        }
        });

        txtRegisternow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity,MobileActivity.class);
                i.putExtra("activty","SIGN UP");
                i.putExtra("redirect","0");
                startActivity(i);
                finish();

            }
        });

        txtForgedtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(activity,MobileActivity.class);
                i.putExtra("activty","Forgot Password?");
                i.putExtra("redirect","1");
                startActivity(i);
                finish();
            }
        });
    }

    private boolean isValidField() {

        if (!CommonUtils.isTextAvailable(edtEmail.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_email));
            return false;
        } else if (!CommonUtils.isEmailValid(edtEmail.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_valid_email));
            return false;
        } else if (!CommonUtils.isTextAvailable(edtPassword.getText().toString().trim())) {
            CommonUtils.commonToast(this, getString(R.string.msg_plz_enter_password));
            return false;
        } else
            return true;


    }


    private void findViews() {
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        txtAppname = (TextViewBold) findViewById(R.id.txt_appname);
        edtEmail = (EditTextRegular) findViewById(R.id.edt_email);
        edtPassword = (EditTextRegular) findViewById(R.id.edt_password);
        chkPassword = (CheckBox) findViewById(R.id.chk_password);
        txtLogin = (TextViewBlack) findViewById(R.id.txt_login);
        txtRegisternow = (TextViewRegular) findViewById(R.id.txt_registernow);
        txtForgedtPassword = (TextViewRegular) findViewById(R.id.txt_forgetpassword);
    }

}
