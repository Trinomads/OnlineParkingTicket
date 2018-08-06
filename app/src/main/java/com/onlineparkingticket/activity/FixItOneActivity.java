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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_it_one);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.fixit), true, false);

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
                    startActivity(new Intent(activity, FixItPleaActivity.class));
                } else {
                    CommonUtils.commonToast(activity, getString(R.string.msg_plz_checked_agree));
                }
            }
        });
    }
}
