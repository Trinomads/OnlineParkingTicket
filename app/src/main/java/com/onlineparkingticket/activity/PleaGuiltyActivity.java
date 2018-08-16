package com.onlineparkingticket.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.TicketListingModel;
import com.williamww.silkysignature.views.SignaturePad;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class PleaGuiltyActivity extends BaseActivity {

    public static Context mContext;
    private SignaturePad mSignaturePad;
    private TextView tvClear;
    private LinearLayout lvNext;
    String date ="",name ="",email ="",phoneno ="",violationno ="";
    String stItemId ="",address="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plea_guilty);
        mContext = PleaGuiltyActivity.this;
        init(PleaGuiltyActivity.this);
        setHeaderWithBack(getString(R.string.plea_guilty), true, false);
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
        setClickEvent();
    }

    private void init() {
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {

            }

            @Override
            public void onSigned() {

            }

            @Override
            public void onClear() {

            }
        });

        tvClear = (TextView) findViewById(R.id.tv_PleaGuilty_Clear);
        lvNext = (LinearLayout) findViewById(R.id.linear_PleaGuilty_Next);
    }

    public void setClickEvent() {
        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSignaturePad.clear();
            }
        });

        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bitmap = mSignaturePad.getSignatureBitmap();

                uploadUserProfile(bitmap);

            }
        });



    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    public void uploadUserProfile(Bitmap bitmap) {
        if (CommonUtils.isConnectingToInternet(mContext)) {

            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
            Uri tempUri = getImageUri(getApplicationContext(), bitmap);

            // CALL THIS METHOD TO GET THE ACTUAL PATH
           String image =getRealPathFromURI(tempUri);

            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));
            params.put("_id", stItemId);
            params.put("type", "guilty");
            params.put("signature", image);
            System.out.println("Map is " + params);
            new ApiHandlerToken(PleaGuiltyActivity.this).getApiService().fixit(params).enqueue(new Callback<TicketListingModel>() {
                @Override
                public void onResponse(Call<TicketListingModel> call, Response<TicketListingModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                Toast.makeText(PleaGuiltyActivity.this, getString(R.string.payment_screen), Toast.LENGTH_SHORT).show();
                                finish();
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
            Log.e("this", "error" + "no internet");
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }

    }

}
