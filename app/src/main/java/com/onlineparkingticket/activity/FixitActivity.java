package com.onlineparkingticket.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onlineparkingticket.R;

public class FixitActivity extends BaseActivity {

    public static FixitActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixit);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.reviewrequest ),true,false);
    }
}
