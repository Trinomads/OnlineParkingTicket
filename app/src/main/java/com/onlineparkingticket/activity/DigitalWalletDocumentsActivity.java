package com.onlineparkingticket.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class DigitalWalletDocumentsActivity extends BaseActivity {

    public static Context mContext;
    private LinearLayout lvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_wallet_documents);
        mContext = DigitalWalletDocumentsActivity.this;
        init(DigitalWalletDocumentsActivity.this);
        setHeaderWithBack(getString(R.string.digital_wallet), true, false);

        init();
    }

    private void init() {
        lvSubmit = (LinearLayout) findViewById(R.id.linear_DigitalWalletDocuments_Submit);
        lvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DigitalWalletDocumentsActivity.this.finish();
            }
        });
    }
}
