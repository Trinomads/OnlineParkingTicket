package com.onlineparkingticket.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.onlineparkingticket.R;

public class SearchDetailActivity extends BaseActivity {

    public static SearchDetailActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.violation),true,false);
    }
}
