package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class SearchDetailActivity extends BaseActivity {

    public static SearchDetailActivity activity;
    private LinearLayout lvNext, lvFixIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.violation), true, false);

        init();
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, PleaActivity.class));
            }
        });

        lvFixIt = (LinearLayout) findViewById(R.id.linear_fixit);
        lvFixIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, FixItOneActivity.class));
            }
        });
    }
}
