package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.CommonUtils;

@SuppressWarnings("All")
public class FixItOneActivity extends BaseActivity {

    public static FixItOneActivity activity;
    private LinearLayout lvNext;
    private TextView tvDesc;
    private CheckBox chkAgree;

    String date ="",name ="",email ="",phoneno ="",violationno ="";
    String stItemId ="",address="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_it_one);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.fixit), true, false);
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
        tvDesc = (TextView) findViewById(R.id.tv_FixItOne_Desc);

        chkAgree = (CheckBox) findViewById(R.id.checkbox_FixItOne_Select);

        lvNext = (LinearLayout) findViewById(R.id.linear_FixIt_Next);
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkAgree.isChecked()) {
                    Intent intent = new Intent(activity, FixItPleaActivity.class);
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
                    CommonUtils.commonToast(activity, getString(R.string.msg_plz_checked_agree));
                }
            }
        });
    }
}
