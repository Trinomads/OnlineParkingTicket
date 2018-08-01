package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class FixItOneActivity extends BaseActivity {

    public static FixItOneActivity activity;
    private LinearLayout lvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_it_one);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.fixit), true, false);

        init();
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_FixIt_Next);
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, FixItPleaActivity.class));
            }
        });
    }
}
