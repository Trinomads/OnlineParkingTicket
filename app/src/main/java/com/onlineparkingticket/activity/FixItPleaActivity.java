package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class FixItPleaActivity extends BaseActivity {

    public static FixItPleaActivity activity;
    private LinearLayout lvNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_it_plea);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.fixit_plea), true, false);


        init();
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_FixItPLea_Next);
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, RequestReviewActivity.class));
            }
        });
    }
}
