package com.onlineparkingticket.fragment;

import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.DigitalWalletActivity;
import com.onlineparkingticket.activity.HomeNavigationDrawer;
import com.onlineparkingticket.activity.ViolationDetailsActivity;
import com.onlineparkingticket.adapter.PendingTicketAdapter;
import com.onlineparkingticket.allInterface.OnItemClick;
import com.onlineparkingticket.allInterface.OnLoadMoreListener;
import com.onlineparkingticket.commonTextView.EditTextRegular;
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
public class HomeFragment extends Fragment implements OnItemClick {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout mainserach;
    private EditTextRegular edtSearch;
    private LinearLayout txtSearch;
    private LinearLayout llSearch;
    private EditTextRegular edtsrch;
    private RecyclerView rvList;
    private TextView tvTotalTickets;
    private String mParam1;
    private String mParam2;
    public static Context mContext;
    private ImageView floating;

    private SwipeRefreshLayout refreshLayout;
    private ArrayList<TicketListingModel.Ticket> listTicket = new ArrayList<>();
    private PendingTicketAdapter adapter;
    int pageNo = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeNavigationDrawer.imNotification.setVisibility(View.VISIBLE);
        HomeNavigationDrawer.mainTitle.setText(getString(R.string.home));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HomeNavigationDrawer.imNotification.setVisibility(View.INVISIBLE);
        adapter = null;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setClickEvent();

        getPaidListing(true, pageNo);
    }

    private void init(View view) {

        mainserach = (LinearLayout) view.findViewById(R.id.mainserach);
        edtSearch = (EditTextRegular) view.findViewById(R.id.edt_search);
        txtSearch = (LinearLayout) view.findViewById(R.id.txt_search);
        llSearch = (LinearLayout) view.findViewById(R.id.ll_search);
        edtsrch = (EditTextRegular) view.findViewById(R.id.edtsrch);
        floating = (ImageView) view.findViewById(R.id.floating);

        tvTotalTickets = (TextView) view.findViewById(R.id.tv_Home_TotalTickets);

        rvList = (RecyclerView) view.findViewById(R.id.recycle_home);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeToRefresh_Home);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNo = 0;
                getPaidListing(false, pageNo);
            }
        });
    }


    private void setClickEvent() {
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*mainserach.setVisibility(View.GONE);
                llSearch.setVisibility(View.VISIBLE);*/
            }
        });

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, DigitalWalletActivity.class));
            }
        });
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
            params.put("status", "UNPAID");

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

                                tvTotalTickets.setText(String.valueOf(response.body().getData().getTotalRecords()));

                                if (response.body().getData().getTickets().size() > 0) {

                                    listTicket.addAll(response.body().getData().getTickets());

                                    if (adapter == null) {
                                        adapter = new PendingTicketAdapter(getActivity(), listTicket, rvList, HomeFragment.this);
                                        rvList.setAdapter(adapter);
                                    } else {
                                        adapter.notifyDataSetChanged();
                                    }

                                    setRefreshAdapter();

                                    mainserach.setVisibility(View.GONE);
                                    llSearch.setVisibility(View.VISIBLE);
                                } else {
                                    if (pageNo == 0) {
                                        mainserach.setVisibility(View.VISIBLE);
                                        llSearch.setVisibility(View.GONE);
                                    }
                                }

                                if (adapter != null) {
                                    adapter.setLoaded();
                                }
                            } else {
                                if (pageNo == 0) {
                                    mainserach.setVisibility(View.VISIBLE);
                                    llSearch.setVisibility(View.GONE);
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

    @Override
    public void onItemClickPosition(int position) {
        Intent intent = new Intent(mContext, ViolationDetailsActivity.class);
        intent.putExtra("itemId", listTicket.get(position).getId());
        mContext.startActivity(intent);
    }
}
