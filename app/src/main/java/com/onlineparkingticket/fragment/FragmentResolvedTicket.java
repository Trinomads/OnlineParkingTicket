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
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.HomeNavigationDrawer;
import com.onlineparkingticket.adapter.ResolvedTicketAdapter;
import com.onlineparkingticket.allInterface.OnLoadMoreListener;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.TicketListingModel;

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
    private TextView tvNoTicket;
    private RecyclerView rvList;
    private SwipeRefreshLayout refreshLayout;
    private ArrayList<TicketListingModel.Ticket> listTicket = new ArrayList<>();
    private ResolvedTicketAdapter adapter;
    int pageNo = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_ticket, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeNavigationDrawer.mainTitle.setText(getString(R.string.Resolvedtickets));
        init(view);
        getPaidListing(true, pageNo);
    }

    private void init(View view) {
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh_Common);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 0;
                getPaidListing(false, pageNo);
            }
        });

        tvNoTicket = (TextView) view.findViewById(R.id.tv_CommonList_NoTickets);

        rvList = (RecyclerView) view.findViewById(R.id.rv_Common);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));
    }

    public void setRefreshAdapter() {
        if (adapter != null) {
            adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
                @Override
                public void onLoadMore() {
                    pageNo = pageNo + 1;
                    getPaidListing(false, pageNo);
                }
            });
        }
    }

    public void getPaidListing(boolean loader, final int pageNo) {
        if (CommonUtils.isConnectingToInternet(mContext)) {

            if (loader) {
                AppGlobal.showProgressDialog(mContext);
            }

            Map<String, String> params = new HashMap<String, String>();
            params.put("user", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));
            params.put("status", "PAID");

            new ApiHandlerToken(mContext).getApiService().resolvedList(params).enqueue(new Callback<TicketListingModel>() {
                @Override
                public void onResponse(Call<TicketListingModel> call, Response<TicketListingModel> response) {
                    AppGlobal.hideProgressDialog();
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {

                                if (pageNo == 0) {
                                    listTicket = new ArrayList<>();
                                    adapter = null;
                                }

                                if (response.body().getData().getTickets().size() > 0) {

                                    listTicket.addAll(response.body().getData().getTickets());

                                    if (adapter == null) {
                                        adapter = new ResolvedTicketAdapter(getActivity(), listTicket, rvList);
                                        rvList.setAdapter(adapter);
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }

                                    setRefreshAdapter();

                                    rvList.setVisibility(View.VISIBLE);
                                    tvNoTicket.setVisibility(View.GONE);
                                } else {
                                    if (pageNo == 0) {
                                        rvList.setVisibility(View.GONE);
                                        tvNoTicket.setVisibility(View.VISIBLE);
                                    }
                                }
                                if (adapter != null) {
                                    adapter.setLoaded();
                                }
                            } else {
                                if (pageNo == 0) {
                                    rvList.setVisibility(View.GONE);
                                    tvNoTicket.setVisibility(View.VISIBLE);
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(mContext, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<TicketListingModel> call, Throwable t) {
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