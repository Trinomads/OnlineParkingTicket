package com.onlineparkingticket.activity;

import android.app.Dialog;
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
public class RequestReviewActivity extends BaseActivity {

    public static RequestReviewActivity activity;
    private LinearLayout lvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_review);
        activity = this;
        init(activity);
        setHeaderWithBack(getResources().getString(R.string.reviewrequest), true, false);

        init();
    }

    private void init() {
        lvSubmit = (LinearLayout) findViewById(R.id.linear_RequestReview_Next);
        lvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRequestDate();
            }
        });
    }

    public void dialogRequestDate() {
        final Dialog dialog = new Dialog(activity);
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
                RequestReviewActivity.this.finish();
            }
        });

        dialog.show();
    }
}
