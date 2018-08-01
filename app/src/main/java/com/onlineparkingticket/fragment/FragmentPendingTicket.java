package com.onlineparkingticket.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.HomeNavigationDrawer;
import com.onlineparkingticket.adapter.PendingTicketAdapter;

import java.util.ArrayList;

@SuppressWarnings("All")
public class FragmentPendingTicket extends Fragment {
    public static Context mContext;

    RecyclerView rvList;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_common_ticket, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeNavigationDrawer.imNotification.setVisibility(View.GONE);
        HomeNavigationDrawer.lvPayNow.setVisibility(View.VISIBLE);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HomeNavigationDrawer.imNotification.setVisibility(View.INVISIBLE);
        HomeNavigationDrawer.lvPayNow.setVisibility(View.GONE);
        HomeNavigationDrawer.imNotification.setImageResource(R.drawable.notification);
        HomeNavigationDrawer.resetData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    ArrayList<String> listTicket;

    private void init(View view) {
        rvList = (RecyclerView) view.findViewById(R.id.rv_Common);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        listTicket = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            listTicket.add("");
        }

        PendingTicketAdapter adapter = new PendingTicketAdapter(getActivity(), listTicket, rvList, false, false);
        rvList.setAdapter(adapter);

        HomeNavigationDrawer.lvPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PendingTicketAdapter adapter = new PendingTicketAdapter(getActivity(), listTicket, rvList, false, true);
                rvList.setAdapter(adapter);

                HomeNavigationDrawer.lvPayNow.setVisibility(View.GONE);
                HomeNavigationDrawer.imNotification.setVisibility(View.VISIBLE);
                HomeNavigationDrawer.imNotification.setImageResource(R.drawable.pay);
            }
        });
        HomeNavigationDrawer.imNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Pay now ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}