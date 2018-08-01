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

import com.onlineparkingticket.R;
import com.onlineparkingticket.adapter.ResolvedTicketAdapter;

import java.util.ArrayList;

@SuppressWarnings("All")
public class FragmentResolvedTicket extends Fragment {
    public static Context mContext;

    RecyclerView rvList;


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
        rvList = (RecyclerView) view.findViewById(R.id.rv_Common);
        rvList.setLayoutManager(new LinearLayoutManager(mContext));

        ArrayList<String> listTicket = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            listTicket.add("");
        }

        ResolvedTicketAdapter adapter = new ResolvedTicketAdapter(getActivity(), listTicket, rvList, true);
        rvList.setAdapter(adapter);
    }

}