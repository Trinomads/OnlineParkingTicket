package com.onlineparkingticket.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.adapter.ResolvedTicketAdapter;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.LoginModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class FragmentResolvedTicket extends Fragment {
    public static Context mContext;

    private RecyclerView rvList;
    private SwipeRefreshLayout refreshLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_ticket, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        getPaidListing(true);

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh_Common);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPaidListing(false);
            }
        });

        rvList = (RecyclerView) view.findViewById(R.id.rv_Common);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        ArrayList<String> listTicket = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            listTicket.add("");
        }

        ResolvedTicketAdapter adapter = new ResolvedTicketAdapter(getActivity(), listTicket, rvList, true);
        rvList.setAdapter(adapter);
    }

    public void getPaidListing(boolean loader) {
        if (CommonUtils.isConnectingToInternet(mContext)) {

            if (loader) {
                AppGlobal.showProgressDialog(mContext);
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("user", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));
            params.put("status", "PAID");

            new ApiHandlerToken(mContext).getApiService().resolvedList(params).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
                    AppGlobal.hideProgressDialog();
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                CommonUtils.commonToast(mContext, "True");
                            } else {
                                CommonUtils.commonToast(mContext, response.body().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(mContext, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<LoginModel> call, Throwable t) {
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