package com.onlineparkingticket.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.onlineparkingticket.R;
import com.onlineparkingticket.adapter.NotificationAdapter;

import java.util.ArrayList;

@SuppressWarnings("All")
public class NotificationActivity extends BaseActivity {

    public static Context mContext;
    private RecyclerView rvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mContext = NotificationActivity.this;
        init(NotificationActivity.this);
        setHeaderWithBack(getString(R.string.notification), true, false);

        init();
    }

    private void init() {
        rvList = (RecyclerView) findViewById(R.id.rv_Notification);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        ArrayList<String> listTicket = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            listTicket.add("");
        }

        NotificationAdapter adapter = new NotificationAdapter(mContext, listTicket, rvList);
        rvList.setAdapter(adapter);
    }
}
