package com.onlineparkingticket.fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.ChangePasswordActivity;
import com.onlineparkingticket.activity.StaticPageActivity;

@SuppressWarnings("All")
public class FragmentSettings extends Fragment {
    public static Context mContext;
    private ToggleButton tgNotification;
    private LinearLayout lvChangePassword, lvAboutUs, lvTerms, lvPrivacy, lvLogout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setClickEvent();
    }

    private void init(View view) {
        tgNotification = (ToggleButton) view.findViewById(R.id.toggle_PushNotification);

        lvChangePassword = (LinearLayout) view.findViewById(R.id.linear_Settings_ChangePassword);
        lvAboutUs = (LinearLayout) view.findViewById(R.id.linear_Settings_AboutUs);
        lvTerms = (LinearLayout) view.findViewById(R.id.linear_Settings_Terms);
        lvPrivacy = (LinearLayout) view.findViewById(R.id.linear_Settings_Privacy);
        lvLogout = (LinearLayout) view.findViewById(R.id.linear_Settings_Logout);
    }

    private void setClickEvent() {
        lvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ChangePasswordActivity.class));
            }
        });

        lvAboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StaticPageActivity.class);
                intent.putExtra("title", getString(R.string.about_us));
                startActivity(intent);
            }
        });

        lvTerms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StaticPageActivity.class);
                intent.putExtra("title", getString(R.string.terms_of_service));
                startActivity(intent);
            }
        });

        lvPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, StaticPageActivity.class);
                intent.putExtra("title", getString(R.string.privacy_policy));
                startActivity(intent);
            }
        });

        lvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Logout", Toast.LENGTH_SHORT).show();
            }
        });
    }
}