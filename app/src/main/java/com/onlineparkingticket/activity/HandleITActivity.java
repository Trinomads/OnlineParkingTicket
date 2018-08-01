package com.onlineparkingticket.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class HandleITActivity extends BaseActivity {

    public static Context mContext;

    private TextView tvDispositoin, tvDrivingCourse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle_it);
        mContext = HandleITActivity.this;
        init(HandleITActivity.this);
        setHeaderWithBack(getString(R.string.handle_it), true, false);

        init();
    }

    private void init() {
        tvDispositoin = (TextView) findViewById(R.id.tv_HandleIt_DefferedDisposition);
        tvDrivingCourse = (TextView) findViewById(R.id.tv_HandleIt_DrivingCourse);

        tvDispositoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HandleITActivity.this, DefferedDispositionActivity.class));
            }
        });

        tvDrivingCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HandleITActivity.this, getString(R.string.redirect_driving_page), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
