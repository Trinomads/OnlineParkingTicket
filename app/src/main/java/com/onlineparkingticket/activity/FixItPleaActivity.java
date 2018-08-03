package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class FixItPleaActivity extends BaseActivity {

    public static FixItPleaActivity activity;
    private TextView tvAddImages;
    private LinearLayout lvNext;
    private RecyclerView rvListImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_it_plea);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.fixit_plea), true, false);

        init();
        setClickEvent();
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_FixItPLea_Next);

        tvAddImages = (TextView) findViewById(R.id.tv_FixItPlea_AddImages);

        rvListImages = (RecyclerView) findViewById(R.id.recyclerView_FitItPlea);
        rvListImages.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setClickEvent() {
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity, RequestReviewActivity.class));
            }
        });

        tvAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
