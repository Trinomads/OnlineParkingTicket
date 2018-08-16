package com.onlineparkingticket.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class PleaActivity extends BaseActivity {

    public static Context mContext;

    private TextView tvGuilty, tvNoContest, tvRequestDate;
    String date ="",name ="",email ="",phoneno ="",violationno ="";
    String stItemId ="",address="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plea);
        mContext = PleaActivity.this;
        init(PleaActivity.this);
        setHeaderWithBack(getString(R.string.plea), true, false);
        Intent intent = getIntent();
        if (intent != null) {
            stItemId = intent.getStringExtra("itemId");
            date = intent.getStringExtra("date");
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            phoneno = intent.getStringExtra("phoneno");
            violationno = intent.getStringExtra("violationno");
            address = intent.getStringExtra("address");

        }
        init();
    }

    private void init() {
        tvGuilty = (TextView) findViewById(R.id.tv_Plea_Guilty);
        tvNoContest = (TextView) findViewById(R.id.tv_Plea_NoContest);
        tvRequestDate = (TextView) findViewById(R.id.tv_Plea_RequestCourtDate);

        tvGuilty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogGuilty("guilty");
            }
        });

        tvNoContest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogGuilty("noContest");
            }
        });

        tvRequestDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRequestDate();
            }
        });
    }

    public void dialogGuilty(final String from) {
        final Dialog dialog = new Dialog(mContext);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        View vi = getLayoutInflater().inflate(R.layout.dialog_plea_guilty, null, false);
        dialog.setContentView(vi);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setAttributes(lp);

        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_Dialog_PleaGuilty_Title);
        TextView tvConfirmation = (TextView) dialog.findViewById(R.id.tv_Dialog_PleaGuilty_Confirmation);

        TextView tvYes = (TextView) dialog.findViewById(R.id.tv_Dialog_PleaGuilty_Yes);
        TextView tvNo = (TextView) dialog.findViewById(R.id.tv_Dialog_PleaGuilty_No);


        if (from.equalsIgnoreCase("guilty")) {
            tvTitle.setText(getString(R.string.plea_guilty));
            tvConfirmation.setText(getString(R.string.confirm_plea_guilty));
        } else {
            tvTitle.setText(getString(R.string.no_contest));
            tvConfirmation.setText(getString(R.string.confirm_no_contest));
        }

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (from.equalsIgnoreCase("guilty")) {
                    Intent intent = new Intent(mContext, PleaGuiltyActivity.class);
                    intent.putExtra("itemId",stItemId);
                    intent.putExtra("date",date);
                    intent.putExtra("name",name);
                    intent.putExtra("email",email);
                    intent.putExtra("phoneno",phoneno);
                    intent.putExtra("violationno",violationno);
                    intent.putExtra("address",address);
                    startActivity(intent);
                    finish();
                } else {

                    Intent intent = new Intent(mContext, HandleITActivity.class);
                    intent.putExtra("itemId",stItemId);
                    startActivity(intent);
                    finish();
                }
                dialog.dismiss();
            }
        });

        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void dialogRequestDate() {
        final Dialog dialog = new Dialog(mContext);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        View vi = getLayoutInflater().inflate(R.layout.dialog_request_court_date, null, false);
        dialog.setContentView(vi);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setAttributes(lp);

        TextView tvDone = (TextView) dialog.findViewById(R.id.tv_Dialog_RequestDate_Done);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                PleaActivity.this.finish();
            }
        });

        dialog.show();
    }
}
