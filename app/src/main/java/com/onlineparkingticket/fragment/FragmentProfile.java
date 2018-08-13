package com.onlineparkingticket.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.HomeNavigationDrawer;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.GetDigitalWalletModel;
import com.onlineparkingticket.model.UserDetailsModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class FragmentProfile extends Fragment {
    public static Context mContext;

    private TextView tvName, tvMobile, tvEmail, tvPlate, tvLocation, tvPaid, tvUnPaid, tvTotal;
    private ImageView imUserImage;
    private ImageView imDL, imVP, imRV, imIP;

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
        HomeNavigationDrawer.mainTitle.setText(getString(R.string.Profile));
        getUserDetails();
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
        tvName = (TextView) view.findViewById(R.id.tv_UserProfile_Name);
        tvMobile = (TextView) view.findViewById(R.id.tv_UserProfile_Mobile);
        tvEmail = (TextView) view.findViewById(R.id.tv_UserProfile_Email);
        tvPlate = (TextView) view.findViewById(R.id.tv_UserProfile_PlateNo);
        tvLocation = (TextView) view.findViewById(R.id.tv_UserProfile_Location);

        tvPaid = (TextView) view.findViewById(R.id.tv_UserProfile_Ticket_Paid);
        tvUnPaid = (TextView) view.findViewById(R.id.tv_UserProfile_Ticket_UnPaid);
        tvTotal = (TextView) view.findViewById(R.id.tv_UserProfile_Ticket_Total);

        imUserImage = (ImageView) view.findViewById(R.id.image_Profile_UserImage);

        imDL = (ImageView) view.findViewById(R.id.image_Profile_DL);
        imVP = (ImageView) view.findViewById(R.id.image_Profile_VP);
        imRV = (ImageView) view.findViewById(R.id.image_Profile_RV);
        imIP = (ImageView) view.findViewById(R.id.image_Profile_IP);

        imDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listImages.size() > 0) {
                    AppGlobal.openUserBannerAndDP(getActivity(), listImages.get(0), false);
                }
            }
        });

        imIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listImages.size() > 0) {
                    AppGlobal.openUserBannerAndDP(getActivity(), listImages.get(1), false);
                }
            }
        });

        setImages();
        getDigitalWallet();
    }

    public void setImages() {
        if (AppGlobal.getArrayListPreference(mContext, WsConstant.SP_IMAGES) != null && AppGlobal.getArrayListPreference(mContext, WsConstant.SP_IMAGES).size() > 0) {
            AppGlobal.loadUserImage(mContext, AppGlobal.getArrayListPreference(mContext, WsConstant.SP_IMAGES).get(0), imUserImage);
        }

        String mobileNo = AppGlobal.getStringPreference(mContext, WsConstant.SP_COUNTRY_CODE) + AppGlobal.getStringPreference(mContext, WsConstant.SP_PHONE);

        tvName.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_NAME), ""));
        tvMobile.setText(mobileNo);
        tvEmail.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_EMAIL), ""));
        tvLocation.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_ADDRESS), ""));
        tvPlate.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_LICENCE_PLAT), ""));
    }

    private void setClickEvent() {
        HomeNavigationDrawer.imNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentEditProfile fragment = new FragmentEditProfile();
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("digitalwallet", listImages);
                fragment.setArguments(bundle);

                ((HomeNavigationDrawer) mContext).callFragment(fragment, getString(R.string.Profile));


            }
        });
    }

    public void getUserDetails() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            AppGlobal.showProgressDialog(mContext);

            Map<String, String> params = new HashMap<String, String>();
            params.put("_id", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));

            new ApiHandlerToken(mContext).getApiService().getUserDetails(params).enqueue(new Callback<UserDetailsModel>() {
                @Override
                public void onResponse(Call<UserDetailsModel> call, Response<UserDetailsModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                CommonUtils.commonToast(mContext, response.body().getMessage());

                                AppGlobal.setStringPreference(mContext, response.body().getData().getUser().getId(), WsConstant.SP_ID);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getUser().getName(), WsConstant.SP_NAME);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getUser().getEmail(), WsConstant.SP_EMAIL);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getUser().getMobileno(), WsConstant.SP_MOBILE);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getUser().getAddress(), WsConstant.SP_ADDRESS);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getUser().getPlatno(), WsConstant.SP_LICENCE_PLAT);
                                AppGlobal.setArrayListPreference(mContext, response.body().getData().getUser().getImages(), WsConstant.SP_IMAGES);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getUser().getCountrycode(), WsConstant.SP_COUNTRY_CODE);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getUser().getPhoneno(), WsConstant.SP_PHONE);

                                tvPaid.setText(AppGlobal.isTextAvailableWithData("" + response.body().getData().getPaidTickets(), ""));
                                tvUnPaid.setText(AppGlobal.isTextAvailableWithData("" + response.body().getData().getUnpaidTickets(), ""));

                                if (AppGlobal.isTextAvailable("" + response.body().getData().getPaidTickets()) && AppGlobal.isTextAvailable("" + response.body().getData().getUnpaidTickets())) {
                                    int total = response.body().getData().getPaidTickets() + response.body().getData().getUnpaidTickets();
                                    tvTotal.setText(String.valueOf(total));
                                } else {
                                    tvTotal.setText(String.valueOf(0));
                                }

                                setImages();

                                HomeNavigationDrawer.callProfileData();

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
                public void onFailure(Call<UserDetailsModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
    }

    ArrayList<String> listImages = new ArrayList<>();

    public void getDigitalWallet() {
        if (CommonUtils.isConnectingToInternet(mContext)) {

            Map<String, String> params = new HashMap<String, String>();
            params.put("user", AppGlobal.getStringPreference(getActivity(), WsConstant.SP_ID));

            new ApiHandlerToken(getActivity()).getApiService().getDigitalWallet(params).enqueue(new Callback<GetDigitalWalletModel>() {
                @Override
                public void onResponse(Call<GetDigitalWalletModel> call, Response<GetDigitalWalletModel> response) {
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                listImages = new ArrayList<>();
                                if (response.body().getData().getData().size() > 0) {
                                    listImages = response.body().getData().getData().get(0).getImages();

                                    AppGlobal.loadImage(getActivity(), listImages.get(0), imDL);
//                                AppGlobal.loadImage(getActivity(), listImages.get(1), imVP);
//                                AppGlobal.loadImage(getActivity(), listImages.get(2), imRV);
                                    AppGlobal.loadImage(getActivity(), listImages.get(1), imIP);
                                }

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
                public void onFailure(Call<GetDigitalWalletModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                }
            });

        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
    }
}