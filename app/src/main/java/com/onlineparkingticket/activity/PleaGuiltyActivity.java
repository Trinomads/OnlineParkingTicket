package com.onlineparkingticket.activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.TextViewRegular;
import com.williamww.silkysignature.views.SignaturePad;

@SuppressWarnings("All")
public class PleaGuiltyActivity extends BaseActivity {

    public static Context mContext;
    private SignaturePad mSignaturePad;
    private TextView tvClear;
    private LinearLayout lvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plea_guilty);
        mContext = PleaGuiltyActivity.this;
        init(PleaGuiltyActivity.this);
        setHeaderWithBack(getString(R.string.plea_guilty), true, false);

        init();
        setClickEvent();
    }

    private void init() {
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {

            }

            @Override
            public void onClear() {

            }
        });

        tvClear = (TextView) findViewById(R.id.tv_PleaGuilty_Clear);
        lvNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);
    }
    
    public void setClickEvent(){
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });
        
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PleaGuiltyActivity.this, getString(R.string.payment_screen), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
