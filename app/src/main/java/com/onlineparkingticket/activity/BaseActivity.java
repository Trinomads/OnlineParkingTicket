package com.onlineparkingticket.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


@SuppressWarnings("ALL")
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    /*    if (android.os.Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.black));
            window.setStatusBarColor(getResources().getColor(R.color.black));
        }*/

        getSupportActionBar().hide();

//        overridePendingTransitionEnter();
    }

    /*protected void overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
    }


    protected void overridePendingTransitionExit() {
        overridePendingTransition(R.anim.trans_right_in, R.anim.trans_left_out);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransitionExit();
    }*/

}
