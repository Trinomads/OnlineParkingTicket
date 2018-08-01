package com.onlineparkingticket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.TextViewBlack;
import com.onlineparkingticket.commonTextView.TextViewBold;

public class FinalRegistrationActivity extends BaseActivity {
    private Toolbar toolbar;
    private LinearLayout linearDidnotgetcode;
    private TextViewBold txtTandc;
    private TextViewBold txtPrivacypolicy;
    private TextViewBlack txtGetstarted;


    public static FinalRegistrationActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_registration);
        activity =this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.signup),true,false);
        findViews();
        setAction();
    }

    private void setAction() {
        txtGetstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(activity, HomeNavigationDrawer.class));
                finish();

            }
        });
    }


    private void findViews() {
        toolbar = (Toolbar)findViewById( R.id.toolbar );
        linearDidnotgetcode = (LinearLayout)findViewById( R.id.linear_didnotgetcode );
        txtTandc = (TextViewBold)findViewById( R.id.txt_tandc );
        txtPrivacypolicy = (TextViewBold)findViewById( R.id.txt_privacypolicy );
        txtGetstarted = (TextViewBlack)findViewById( R.id.txt_getstarted );
    }

}
