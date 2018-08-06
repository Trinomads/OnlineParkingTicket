package com.onlineparkingticket.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.WsConstant;

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
                startActivity(new Intent(DigitalWalletActivity.this, DigitalWalletDocumentsActivity.class));
            }
        });
    }
}
