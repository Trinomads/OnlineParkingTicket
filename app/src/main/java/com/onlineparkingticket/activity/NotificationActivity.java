package com.onlineparkingticket.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.adapter.NotificationAdapter;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.NotificationModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class NotificationActivity extends BaseActivity {

    public static Context mContext;
    private RecyclerView rvList;
    private ArrayList<NotificationModel.Notification> listNotification = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;
    private TextView tvNoTicket;
    NotificationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        mContext = NotificationActivity.this;
        init(NotificationActivity.this);
        setHeaderWithBack(getString(R.string.notification), true, false);

        init();
        getNotifications(true);
    }

    private void init() {
        rvList = (RecyclerView) findViewById(R.id.rv_Notification);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh_Notification);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNotifications(false);
            }
        });

        tvNoTicket = (TextView) findViewById(R.id.tv_Notification_NoTickets);
    }

    public void getNotifications(boolean loader) {
        if (CommonUtils.isConnectingToInternet(mContext)) {

            if (loader) {
                AppGlobal.showProgressDialog(mContext);
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("user", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));

            new ApiHandlerToken(mContext).getApiService().getNotifications(params).enqueue(new Callback<NotificationModel>() {
                @Override
                public void onResponse(Call<NotificationModel> call, Response<NotificationModel> response) {
                    AppGlobal.hideProgressDialog();
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                if (response.body().getData().getNotifications().size() > 0) {

                                    listNotification = new ArrayList<>();
                                    listNotification.addAll(response.body().getData().getNotifications());

                                    if (adapter == null) {
                                        adapter = new NotificationAdapter(mContext, listNotification, rvList);
                                        rvList.setAdapter(adapter);
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }

                                    rvList.setVisibility(View.VISIBLE);
                                    tvNoTicket.setVisibility(View.GONE);
                                } else {
                                    rvList.setVisibility(View.GONE);
                                    tvNoTicket.setVisibility(View.VISIBLE);
                                }
                            } else {
                                rvList.setVisibility(View.GONE);
                                tvNoTicket.setVisibility(View.VISIBLE);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(mContext, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<NotificationModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                }
            });
        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
    }
}
