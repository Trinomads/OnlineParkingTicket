package com.onlineparkingticket.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.onlineparkingticket.R;
import com.onlineparkingticket.adapter.ImageListAdapter;
import com.onlineparkingticket.allInterface.OnItemClick;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.CompressImageUtil;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.DigitalWalletModel;
import com.onlineparkingticket.model.SaveImageModel;
import com.onlineparkingticket.model.TicketListingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class RequestReviewActivity extends BaseActivity implements OnItemClick {

    public static RequestReviewActivity mContext;
    private LinearLayout lvSubmit,ll_editimage;
    private ArrayList<String> listImages = new ArrayList<>();
    private String stMessage = "";
    private TextView tvEvidence, tvName, tvDate, tvEmail, tvMobile, tvAddress, tvViolationNo;
    private EditText edMsg;
    private RecyclerView rvImages;
    private ImageListAdapter adapterImages;
    private ImageView imEditMessage;
    String date ="",name ="",email ="",phoneno ="",violationno ="",address="";
    String stItemId ="";
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
            stItemId = intent.getStringExtra("itemId");
            date = intent.getStringExtra("date");
            name = intent.getStringExtra("name");
            email = intent.getStringExtra("email");
            phoneno = intent.getStringExtra("phoneno");
            violationno = intent.getStringExtra("violationno");
            address = intent.getStringExtra("address");
        }

        init();
        setData();
        setClickEvent();
    }

    private void setData() {

        tvName.setText(AppGlobal.isTextAvailableWithData(name,""));
        tvDate.setText(AppGlobal.isTextAvailableWithData(date,""));
        tvEmail.setText(AppGlobal.isTextAvailableWithData(email,""));
        tvMobile.setText(AppGlobal.isTextAvailableWithData(phoneno,""));
        tvViolationNo.setText(AppGlobal.isTextAvailableWithData(violationno,""));
        tvAddress.setText(AppGlobal.isTextAvailableWithData(address,""));


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
        ll_editimage = (LinearLayout) findViewById(R.id.ll_editimage);

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

                uploadUserProfile();

            }
        });
        ll_editimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                showImageDialog();

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
                finish();
            }
        });

        dialog.show();
    }

    @Override
    public void onItemClickPosition(int position) {
        listImages.remove(position);
        setAdapterImages();
    }


    public void uploadUserProfile() {
        if (CommonUtils.isConnectingToInternet(mContext)) {


            Map<String, Object> params = new HashMap<String, Object>();
            params.put("user", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));
            params.put("_id", stItemId);
            params.put("type", "fixit");

            JsonObject gsonObject = new JsonObject();
            JSONObject jsonObject = new JSONObject();
            JSONArray array = new JSONArray();
            for (int i = 0; i < listImages.size(); i++) {
                array.put(listImages.get(i));
            }
            try {
                jsonObject.put("message",stMessage);
                jsonObject.put("images",array);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            params.put("fixit", String.valueOf(jsonObject));
            System.out.println("Map is " + params);
            new ApiHandlerToken(RequestReviewActivity.this).getApiService().fixit(params).enqueue(new Callback<TicketListingModel>() {
                @Override
                public void onResponse(Call<TicketListingModel> call, Response<TicketListingModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                          dialogRequestDate();
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

    Dialog popupDialog;
    private int PICK_FROM_CAMERA = 123;
    private int PICK_FROM_GALLARY = 200;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String selectedImagePathFront = "";

    public void showImageDialog() {
        popupDialog = new Dialog(mContext);
        popupDialog.setContentView(R.layout.dialog_image_picker);
        popupDialog.setCancelable(true);

        TextView txtDialogReport = (TextView) popupDialog.findViewById(R.id.txt_dialog_report);
        TextView txtDialogSetting = (TextView) popupDialog.findViewById(R.id.txt_dialog_setting);
        TextView txtDialogCancel = (TextView) popupDialog.findViewById(R.id.txt_dialog_cancel);

        txtDialogReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (mContext.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    } else {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
                    }
                }
                popupDialog.dismiss();
            }
        });

        txtDialogSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent pictureActionIntent = null;
                    pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pictureActionIntent, PICK_FROM_GALLARY);
                    popupDialog.dismiss();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        txtDialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });
        popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));

        popupDialog.show();
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(mContext, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new
                        Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, PICK_FROM_CAMERA);
            } else {
                Toast.makeText(mContext, "camera permission denied", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (resultCode == RESULT_OK && requestCode == PICK_FROM_GALLARY) {
                try {
                    if (data != null) {

                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = mContext.getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        selectedImagePathFront = c.getString(columnIndex);
                        c.close();

                        CompressImageUtil compressUtil = new CompressImageUtil(mContext);

                        listImages.add(selectedImagePathFront);
                        setAdapterImages();

                        AppGlobal.showLog(mContext, "FilePath : " + selectedImagePathFront);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == PICK_FROM_CAMERA) {
                selectedImagePathFront = null;
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri imageUri = getImageUri(mContext, photo);
                selectedImagePathFront = getRealPathFromURI(imageUri);

                listImages.add(selectedImagePathFront);
                setAdapterImages();

                AppGlobal.showLog(mContext, "FilePath : " + selectedImagePathFront);
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

}
