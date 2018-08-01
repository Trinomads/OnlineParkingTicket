package com.onlineparkingticket.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.DigitalWalletActivity;
import com.onlineparkingticket.activity.HomeNavigationDrawer;
import com.onlineparkingticket.adapter.HomeSearchAdapter;
import com.onlineparkingticket.commonTextView.EditTextRegular;

import java.util.ArrayList;

@SuppressWarnings("All")
public class HomeFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LinearLayout mainserach;
    private EditTextRegular edtSearch;
    private LinearLayout txtSearch;
    private LinearLayout llSearch;
    private EditTextRegular edtsrch;
    private RecyclerView recycleHome;

    private String mParam1;
    private String mParam2;
    public static Context mContext;
    private ImageView floating;

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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HomeNavigationDrawer.imNotification.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setClickEvent();
    }

    private void init(View view) {

        mainserach = (LinearLayout) view.findViewById(R.id.mainserach);
        edtSearch = (EditTextRegular) view.findViewById(R.id.edt_search);
        txtSearch = (LinearLayout) view.findViewById(R.id.txt_search);
        llSearch = (LinearLayout) view.findViewById(R.id.ll_search);
        edtsrch = (EditTextRegular) view.findViewById(R.id.edtsrch);
        recycleHome = (RecyclerView) view.findViewById(R.id.recycle_home);
        floating = (ImageView) view.findViewById(R.id.floating);
    }


    private void setClickEvent() {
        txtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mainserach.setVisibility(View.GONE);
                llSearch.setVisibility(View.VISIBLE);

                recycleHome.setLayoutManager(new LinearLayoutManager(mContext));

                ArrayList<String> listTicket = new ArrayList<>();
                for (int i = 0; i < 50; i++) {
                    listTicket.add("");
                }

                HomeSearchAdapter adapter = new HomeSearchAdapter(getActivity(), listTicket, recycleHome, true);
                recycleHome.setAdapter(adapter);
            }
        });

        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, DigitalWalletActivity.class));
            }
        });
    }
}
