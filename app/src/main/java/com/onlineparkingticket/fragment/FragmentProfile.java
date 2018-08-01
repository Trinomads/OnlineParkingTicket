package com.onlineparkingticket.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.HomeNavigationDrawer;

@SuppressWarnings("All")
public class FragmentProfile extends Fragment {
    public static Context mContext;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeNavigationDrawer.imNotification.setVisibility(View.VISIBLE);
        HomeNavigationDrawer.imNotification.setImageResource(R.drawable.edit_white);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HomeNavigationDrawer.imNotification.setVisibility(View.INVISIBLE);
        HomeNavigationDrawer.imNotification.setImageResource(R.drawable.notification);
        HomeNavigationDrawer.resetData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setClickEvent();
    }

    private void init(View view) {

    }

    private void setClickEvent() {
        HomeNavigationDrawer.imNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((HomeNavigationDrawer) mContext).callFragment(new FragmentEditProfile(), getString(R.string.Profile));
            }
        });
    }
}