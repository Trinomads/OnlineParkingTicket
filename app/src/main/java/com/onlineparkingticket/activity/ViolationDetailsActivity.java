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
import com.onlineparkingticket.model.TicketListingModel;

import org.json.JSONObject;

import java.util.ArrayList;
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
    private TextView tvDate, tvPlateNo, tvViolationNo, tvUserName, tvViolationDesc, tvSpeed, tvPrice, tvZone;
    private String cretedat = "";
    private ArrayList<TicketListingModel.Ticket> tickets;
    String date = "", name = "", email = "", phoneno = "", violationno = "", address = "";

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
            cretedat = intent.getStringExtra("cretedat");
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
        tvZone = (TextView) findViewById(R.id.tv_ViolationDetails_zone);
        tvPrice = (TextView) findViewById(R.id.tv_ViolationDetails_Price);
    }

    private void setClickEvent() {
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                for (int i = 0; i < tickets.size(); i++) {
                    if (CommonUtils.isTextAvailable(tickets.get(i).getDate()))
                    {
                        date = AppGlobal.getDateFromServer(tickets.get(i).getDate());
                    }


                    if (CommonUtils.isTextAvailable(tickets.get(i).getUser().getName()))
                    {
                        name = tickets.get(i).getUser().getName();

                    }
                    if (CommonUtils.isTextAvailable(tickets.get(i).getUser().getEmail()))
                    {
                        email = tickets.get(i).getUser().getEmail();

                    } if (CommonUtils.isTextAvailable(tickets.get(i).getUser().getPhoneno()))
                    {
                        phoneno = tickets.get(i).getUser().getPhoneno();

                    }
                    if (CommonUtils.isTextAvailable(tickets.get(i).getViolationno()))
                    {
                        violationno = tickets.get(i).getViolationno();

                    }

                    if (CommonUtils.isTextAvailable(tickets.get(i).getUser().getId()))
                    {
                        stItemId = tickets.get(i).getId();

                    }


                }
                Intent intent = new Intent(mContext, PleaActivity.class);
                intent.putExtra("itemId", stItemId);
                intent.putExtra("date", date);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phoneno", phoneno);
                intent.putExtra("violationno", violationno);
                intent.putExtra("address", address);
                mContext.startActivity(intent);
            }
        });

        lvFixIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for (int i = 0; i < tickets.size(); i++) {

                    date = AppGlobal.getDateFromServer(tickets.get(i).getDate());
                    name = tickets.get(i).getUser().getName();
                    email = tickets.get(i).getUser().getEmail();
                    phoneno = tickets.get(i).getUser().getPhoneno();
                    violationno = tickets.get(i).getViolationno();
                    address = tickets.get(i).getUser().getAddress();
                    stItemId = tickets.get(i).getId();
                }
                Intent intent = new Intent(mContext, FixItOneActivity.class);
                intent.putExtra("itemId", stItemId);
                intent.putExtra("date", date);
                intent.putExtra("name", name);
                intent.putExtra("email", email);
                intent.putExtra("phoneno", phoneno);
                intent.putExtra("violationno", violationno);
                intent.putExtra("address", address);
                mContext.startActivity(intent);
            }
        });
    }

    public void getTicketDetails() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            AppGlobal.showProgressDialog(mContext);

            Map<String, String> params = new HashMap<String, String>();
            params.put("user", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));
            params.put("_id", stItemId);

            new ApiHandlerToken(mContext).getApiService().getTicketDetails(params).enqueue(new Callback<TicketListingModel>() {
                @Override
                public void onResponse(Call<TicketListingModel> call, Response<TicketListingModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {

                                tickets = new ArrayList<>();
                                tickets = response.body().getData().getTickets();
                                for (int i = 0; i < tickets.size(); i++) {
                                    tvDate.setText(AppGlobal.getDateFromServer(tickets.get(i).getDate()));
                                    tvPlateNo.setText("Plate No : " + AppGlobal.isTextAvailableWithData(tickets.get(i).getPlateno(), ""));
                                    tvViolationNo.setText("Citation No : " + AppGlobal.isTextAvailableWithData(tickets.get(i).getViolationno(), ""));
                                    tvPrice.setText("$ " + AppGlobal.isTextAvailableWithData("" + tickets.get(i).getPrice(), "0"));
                                    tvUserName.setText(AppGlobal.isTextAvailableWithData(tickets.get(i).getUser().getName(), ""));
                                    tvViolationDesc.setText("Description");
                                    tvSpeed.setText("Speed : " + AppGlobal.isTextAvailableWithData(tickets.get(i).getSpeed(), ""));
                                    tvZone.setText("Zone : " + AppGlobal.isTextAvailableWithData(tickets.get(i).getZone(), ""));
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
                public void onFailure(Call<TicketListingModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
    }
}
