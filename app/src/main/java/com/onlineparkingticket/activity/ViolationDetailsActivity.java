package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.TicketDetailsModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class ViolationDetailsActivity extends BaseActivity {

    public static ViolationDetailsActivity mContext;
    private LinearLayout lvNext, lvFixIt;
    private String stItemId = "";
    private TextView tvDate, tvPlateNo, tvViolationNo, tvUserName, tvViolationDesc, tvSpeed, tvPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_violation_details);
        mContext = this;
        init(mContext);
        setHeaderWithBack(getResources().getString(R.string.violation), true, false);

        Intent intent = getIntent();
        if (intent != null) {
            stItemId = intent.getStringExtra("itemId");
        }

        init();
        setClickEvent();

        if (!stItemId.equalsIgnoreCase("")) {
            getTicketDetails();
        }
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);
        lvFixIt = (LinearLayout) findViewById(R.id.linear_fixit);

        tvDate = (TextView) findViewById(R.id.tv_ViolationDetails_Date);
        tvPlateNo = (TextView) findViewById(R.id.tv_ViolationDetails_Plate);
        tvViolationNo = (TextView) findViewById(R.id.tv_ViolationDetails_ViolationNo);
        tvUserName = (TextView) findViewById(R.id.tv_violation_Name);
        tvViolationDesc = (TextView) findViewById(R.id.tv_violation_Description);
        tvSpeed = (TextView) findViewById(R.id.tv_ViolationDetails_Speed);
        tvPrice = (TextView) findViewById(R.id.tv_ViolationDetails_Price);
    }

    private void setClickEvent() {
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, PleaActivity.class));
            }
        });

        lvFixIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, FixItOneActivity.class));
            }
        });
    }

    public void getTicketDetails() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            AppGlobal.showProgressDialog(mContext);

            Map<String, String> params = new HashMap<String, String>();
            params.put("user", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));
            params.put("_id", stItemId);

            new ApiHandlerToken(mContext).getApiService().getTicketDetails(params).enqueue(new Callback<TicketDetailsModel>() {
                @Override
                public void onResponse(Call<TicketDetailsModel> call, Response<TicketDetailsModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                tvDate.setText(AppGlobal.getDateFromServer(response.body().getData().getDate()));
                                tvPlateNo.setText("Plate No : " + AppGlobal.isTextAvailableWithData(response.body().getData().getUser().getPlatno(), ""));
                                tvViolationNo.setText("Violation No : " + AppGlobal.isTextAvailableWithData(response.body().getData().getViolationno(), ""));
                                tvViolationDesc.setText("Description");
                                tvPrice.setText("$ " + AppGlobal.isTextAvailableWithData("" + response.body().getData().getPrice(), "0"));
                                tvUserName.setText(AppGlobal.isTextAvailableWithData(response.body().getData().getUser().getName(), ""));
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
                public void onFailure(Call<TicketDetailsModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
    }
}
