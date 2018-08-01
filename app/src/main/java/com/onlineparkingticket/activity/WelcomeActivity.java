package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.TextViewBlack;
import com.onlineparkingticket.commonTextView.TextViewBold;

@SuppressWarnings("All")
public class WelcomeActivity extends BaseActivity {
    public static WelcomeActivity activity;
    private TextViewBold txtAppname;
    private TextViewBlack txtCreateaccount;
    private TextViewBlack txt_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        activity = this;
        init(activity);
        findViews();
        setAction();
    }

    private void setAction() {
        txtCreateaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, MobileActivity.class));
            }
        });

        txt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(activity, LoginActivity.class));
            }
        });
    }

    private void findViews() {
        txtAppname = (TextViewBold) findViewById(R.id.txt_appname);
        txtCreateaccount = (TextViewBlack) findViewById(R.id.txt_createaccount);
        txt_login = (TextViewBlack) findViewById(R.id.txt_login);
    }
}
