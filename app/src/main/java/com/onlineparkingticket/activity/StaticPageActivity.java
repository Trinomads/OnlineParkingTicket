package com.onlineparkingticket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class StaticPageActivity extends BaseActivity {

    public static Context mContext;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_static_page);
        mContext = StaticPageActivity.this;
        init(StaticPageActivity.this);

        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
        }

        setHeaderWithBack(title, true, false);

        init();
    }

    private void init() {

    }
}
