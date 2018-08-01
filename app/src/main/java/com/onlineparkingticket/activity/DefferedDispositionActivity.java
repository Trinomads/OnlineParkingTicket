package com.onlineparkingticket.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlineparkingticket.R;

@SuppressWarnings("All")
public class DefferedDispositionActivity extends BaseActivity {

    public static Context mContext;
    private LinearLayout lvNext;
    private EditText edName, edEmail, edDrivingLicense, edVioNo, edVioDesc, edTicketCharge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deffered_position);
        mContext = DefferedDispositionActivity.this;
        init(DefferedDispositionActivity.this);
        setHeaderWithBack(getString(R.string.deffered_disposition), true, false);

        init();
    }

    private void init() {
        lvNext = (LinearLayout) findViewById(R.id.linear_DefferedDisposition_Next);

        edName = (EditText) findViewById(R.id.ed_DefferedPosition_Name);
        edEmail = (EditText) findViewById(R.id.ed_DefferedPosition_Email);
        edDrivingLicense = (EditText) findViewById(R.id.ed_DefferedPosition_DrivingLicense);
        edVioNo = (EditText) findViewById(R.id.ed_DefferedPosition_Vio_NO);
        edVioDesc = (EditText) findViewById(R.id.ed_DefferedPosition_VioDesc);
        edTicketCharge = (EditText) findViewById(R.id.ed_DefferedPosition_TicketCharge);

        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRequestDate();
            }
        });
    }

    public void dialogRequestDate() {
        final Dialog dialog = new Dialog(mContext);
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);

        View vi = getLayoutInflater().inflate(R.layout.dialog_thank_you, null, false);
        dialog.setContentView(vi);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        lp.copyFrom(window.getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.gravity = Gravity.CENTER;
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setAttributes(lp);

        TextView tvDone = (TextView) dialog.findViewById(R.id.tv_Dialog_ThankYou_Done);

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                DefferedDispositionActivity.this.finish();
            }
        });

        dialog.show();
    }
}
