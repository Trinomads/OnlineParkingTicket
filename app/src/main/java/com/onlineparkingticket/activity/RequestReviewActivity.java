package com.onlineparkingticket.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onlineparkingticket.R;
import com.onlineparkingticket.adapter.ImageListAdapter;
import com.onlineparkingticket.allInterface.OnItemClick;

import java.util.ArrayList;

@SuppressWarnings("All")
public class RequestReviewActivity extends BaseActivity implements OnItemClick {

    public static RequestReviewActivity mContext;
    private LinearLayout lvSubmit;
    private ArrayList<String> listImages = new ArrayList<>();
    private String stMessage = "";
    private TextView tvEvidence, tvName, tvDate, tvEmail, tvMobile, tvAddress, tvViolationNo;
    private EditText edMsg;
    private RecyclerView rvImages;
    private ImageListAdapter adapterImages;
    private ImageView imEditMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_review);
        mContext = this;
        init(mContext);
        setHeaderWithBack(getResources().getString(R.string.reviewrequest), true, false);

        Intent intent = getIntent();
        if (intent != null) {
            stMessage = intent.getStringExtra("msg");

            listImages = new ArrayList<>();
            listImages = intent.getStringArrayListExtra("listImages");
        }

        init();
        setClickEvent();
    }

    private void init() {
        lvSubmit = (LinearLayout) findViewById(R.id.linear_RequestReview_Next);

        tvEvidence = (TextView) findViewById(R.id.tv_RequestReview_Evidence);

        tvName = (TextView) findViewById(R.id.tv_RequestReview_Name);
        tvDate = (TextView) findViewById(R.id.tv_RequestReview_Date);
        tvEmail = (TextView) findViewById(R.id.tv_RequestReview_Email);
        tvMobile = (TextView) findViewById(R.id.tv_RequestReview_Mobile);
        tvAddress = (TextView) findViewById(R.id.tv_RequestReview_Address);
        tvViolationNo = (TextView) findViewById(R.id.tv_RequestReview_ViolationNo);

        edMsg = (EditText) findViewById(R.id.tv_RequestReview_Message);
        edMsg.setText(stMessage);

        imEditMessage = (ImageView) findViewById(R.id.image_RequestReview_Msg);

        rvImages = (RecyclerView) findViewById(R.id.recyclerView_RequestReview);
        rvImages.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));

        setAdapterImages();
    }

    private void setClickEvent() {
        imEditMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edMsg.setEnabled(true);
                if (!edMsg.getText().toString().equalsIgnoreCase("")) {
                    edMsg.setSelection(edMsg.getText().toString().length());
                }
                edMsg.requestFocus();
                ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(edMsg, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        lvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRequestDate();
            }
        });
    }

    private void setAdapterImages() {
        if (listImages.size() > 0) {
            if (adapterImages == null) {
                adapterImages = new ImageListAdapter(mContext, listImages, rvImages, this);
                rvImages.setAdapter(adapterImages);
            } else {
                adapterImages.notifyDataSetChanged();
            }

            rvImages.setVisibility(View.VISIBLE);
            tvEvidence.setVisibility(View.GONE);
        } else {
            tvEvidence.setVisibility(View.VISIBLE);
            rvImages.setVisibility(View.GONE);
        }
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
                RequestReviewActivity.this.finish();
            }
        });

        dialog.show();
    }

    @Override
    public void onItemClickPosition(int position) {
        listImages.remove(position);
        setAdapterImages();
    }
}
