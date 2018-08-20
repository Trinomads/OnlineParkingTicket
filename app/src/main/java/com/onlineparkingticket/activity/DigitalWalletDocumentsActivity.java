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
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.CompressImageUtil;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.DigitalWalletModel;
import com.onlineparkingticket.model.WalletImageModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
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
public class DigitalWalletDocumentsActivity extends BaseActivity {

    public static Context mContext;
    private LinearLayout lvSubmit, lvTitles;
    private RelativeLayout rvDL, rvVP, rvRV, rvIP;
    private ImageView imDL, imVP, imRV, imIP;
    private ImageView imCancelDL, imCancelVP, imCancelRV, imCancelIP;
    private TextView tvDL, tvVP, tvRV, tvIP;
    private String imagePathDL = "", imagePathVP = "", imagePathRV = "", imagePathIP = "";
    private String stFromImage = "", stUpdate = "";
    private String stDrivingLicense, stPlate, stVIN, stInsurance, stAddress, stState, stZip;
    private ArrayList<String> listImages = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_wallet_documents);
        mContext = DigitalWalletDocumentsActivity.this;
        init(DigitalWalletDocumentsActivity.this);
        setHeaderWithBack(getString(R.string.digital_wallet), true, false);

        Intent intent = getIntent();
        if (intent != null) {
            stDrivingLicense = intent.getStringExtra("drivingLicence");
            stPlate = intent.getStringExtra("plate");
            stVIN = intent.getStringExtra("vin");
            stInsurance = intent.getStringExtra("insaurance");
            stAddress = intent.getStringExtra("address");
            stState = intent.getStringExtra("state");
            stZip = intent.getStringExtra("zipCode");
            stUpdate = intent.getStringExtra("stUpdate");

            if (stUpdate.equalsIgnoreCase("yes")) {
                listImages = new ArrayList<>();
                listImages = intent.getStringArrayListExtra("images");
            }
        }

        init();

        if (stUpdate.equalsIgnoreCase("no")) {
            setClickEvent();
        }
    }

    private void init() {
        lvSubmit = (LinearLayout) findViewById(R.id.linear_DigitalWalletDocuments_Submit);
        lvTitles = (LinearLayout) findViewById(R.id.linear_DigitalWalletDocuments_Title);

        rvDL = (RelativeLayout) findViewById(R.id.relative_DigitalWalletDetails_DL);
        rvVP = (RelativeLayout) findViewById(R.id.relative_DigitalWalletDetails_VP);
        rvRV = (RelativeLayout) findViewById(R.id.relative_DigitalWalletDetails_RV);
        rvIP = (RelativeLayout) findViewById(R.id.relative_DigitalWalletDetails_IP);

        imDL = (ImageView) findViewById(R.id.image_DigitalWalletDetails_DL);
        imVP = (ImageView) findViewById(R.id.image_DigitalWalletDetails_VP);
        imRV = (ImageView) findViewById(R.id.image_DigitalWalletDetails_RV);
        imIP = (ImageView) findViewById(R.id.image_DigitalWalletDetails_IP);

        imCancelDL = (ImageView) findViewById(R.id.image_DigitalWalletDetails_Cancel_DL);
        imCancelVP = (ImageView) findViewById(R.id.image_DigitalWalletDetails_Cancel_VP);
        imCancelRV = (ImageView) findViewById(R.id.image_DigitalWalletDetails_Cancel_RV);
        imCancelIP = (ImageView) findViewById(R.id.image_DigitalWalletDetails_Cancel_IP);

        tvDL = (TextView) findViewById(R.id.tv_DigitalWalletDetails_DL);
        tvVP = (TextView) findViewById(R.id.tv_DigitalWalletDetails_VP);
        tvRV = (TextView) findViewById(R.id.tv_DigitalWalletDetails_RV);
        tvIP = (TextView) findViewById(R.id.tv_DigitalWalletDetails_IP);

        if (stUpdate.equalsIgnoreCase("yes")) {
            AppGlobal.loadImage(this, listImages.get(0), imDL);
//            AppGlobal.loadImage(this, listImages.get(1), imVP);
//            AppGlobal.loadImage(this, listImages.get(2), imRV);
            AppGlobal.loadImage(this, listImages.get(1), imIP);

            tvDL.setVisibility(View.GONE);
//            tvVP.setVisibility(View.GONE);
//            tvRV.setVisibility(View.GONE);
            tvIP.setVisibility(View.GONE);

            lvTitles.setVisibility(View.VISIBLE);
        } else {
            lvTitles.setVisibility(View.GONE);
        }
    }

    public void checkSubmit() {
        if (!imagePathDL.equalsIgnoreCase("") && !imagePathIP.equalsIgnoreCase("")) {
            lvSubmit.setVisibility(View.VISIBLE);
        } else {
            lvSubmit.setVisibility(View.GONE);
        }
    }

    private void setClickEvent() {
        lvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!CommonUtils.isTextAvailable(imagePathDL)) {
                    CommonUtils.commonToast(DigitalWalletDocumentsActivity.this, getString(R.string.img_license));
                }/* else if (!CommonUtils.isTextAvailable(imagePathVP)) {
                    CommonUtils.commonToast(DigitalWalletDocumentsActivity.this, getString(R.string.img_plate));
                } else if (!CommonUtils.isTextAvailable(imagePathRV)) {
                    CommonUtils.commonToast(DigitalWalletDocumentsActivity.this, getString(R.string.img_vin));
                }*/ else if (!CommonUtils.isTextAvailable(imagePathIP)) {
                    CommonUtils.commonToast(DigitalWalletDocumentsActivity.this, getString(R.string.img_insurance));
                } else {
                    uploadUserProfile(imagePathDL);
                }
            }
        });

        rvDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagePathDL.equalsIgnoreCase("")) {
                    stFromImage = "DL";
                    showImageDialog();
                }
            }
        });

        rvVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagePathVP.equalsIgnoreCase("")) {
                    stFromImage = "VP";
                    showImageDialog();
                }
            }
        });

        rvRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagePathRV.equalsIgnoreCase("")) {
                    stFromImage = "RV";
                    showImageDialog();
                }
            }
        });

        rvIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imagePathIP.equalsIgnoreCase("")) {
                    stFromImage = "IP";
                    showImageDialog();
                }
            }
        });

        imCancelDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePathDL = "";
                tvDL.setVisibility(View.VISIBLE);
                imCancelDL.setVisibility(View.GONE);
                imDL.setImageDrawable(null);

                checkSubmit();
            }
        });

        imCancelVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePathVP = "";
                tvVP.setVisibility(View.VISIBLE);
                imCancelVP.setVisibility(View.GONE);
                imVP.setImageDrawable(null);
            }
        });

        imCancelRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePathRV = "";
                tvRV.setVisibility(View.VISIBLE);
                imCancelRV.setVisibility(View.GONE);
                imRV.setImageDrawable(null);
            }
        });

        imCancelIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imagePathIP = "";
                tvIP.setVisibility(View.VISIBLE);
                imCancelIP.setVisibility(View.GONE);
                imIP.setImageDrawable(null);

                checkSubmit();
            }
        });
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

                        if (stFromImage.equalsIgnoreCase("DL")) {
                            selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imDL);
                            imagePathDL = selectedImagePathFront;
                            tvDL.setVisibility(View.GONE);
                            imCancelDL.setVisibility(View.VISIBLE);
                        } else if (stFromImage.equalsIgnoreCase("VP")) {
                            selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imVP);
                            imagePathVP = selectedImagePathFront;
                            tvVP.setVisibility(View.GONE);
                            imCancelVP.setVisibility(View.VISIBLE);
                        } else if (stFromImage.equalsIgnoreCase("RV")) {
                            selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imRV);
                            imagePathRV = selectedImagePathFront;
                            tvRV.setVisibility(View.GONE);
                            imCancelRV.setVisibility(View.VISIBLE);
                        } else if (stFromImage.equalsIgnoreCase("IP")) {
                            selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imIP);
                            imagePathIP = selectedImagePathFront;
                            tvIP.setVisibility(View.GONE);
                            imCancelIP.setVisibility(View.VISIBLE);
                        }

                        checkSubmit();
                        AppGlobal.showLog(mContext, "FilePath : " + selectedImagePathFront);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (requestCode == PICK_FROM_CAMERA) {
                selectedImagePathFront = null;
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                Uri imageUri = AppGlobal.getImageUri(mContext, photo);
                selectedImagePathFront = AppGlobal.getRealPathFromURI(mContext, imageUri);

                if (stFromImage.equalsIgnoreCase("DL")) {
                    imDL.setImageBitmap(photo);
                    imagePathDL = selectedImagePathFront;
                    tvDL.setVisibility(View.GONE);
                    imCancelDL.setVisibility(View.VISIBLE);
                } else if (stFromImage.equalsIgnoreCase("VP")) {
                    imVP.setImageBitmap(photo);
                    imagePathVP = selectedImagePathFront;
                    tvVP.setVisibility(View.GONE);
                    imCancelVP.setVisibility(View.VISIBLE);
                } else if (stFromImage.equalsIgnoreCase("RV")) {
                    imRV.setImageBitmap(photo);
                    imagePathRV = selectedImagePathFront;
                    tvRV.setVisibility(View.GONE);
                    imCancelRV.setVisibility(View.VISIBLE);
                } else if (stFromImage.equalsIgnoreCase("IP")) {
                    imIP.setImageBitmap(photo);
                    imagePathIP = selectedImagePathFront;
                    tvIP.setVisibility(View.GONE);
                    imCancelIP.setVisibility(View.VISIBLE);
                }

                checkSubmit();
                AppGlobal.showLog(mContext, "FilePath : " + selectedImagePathFront);
            }
        }
    }

    String[] imagesLink = new String[2];
    MultipartBody.Part body;
    int imageUploadCount = 0;

    public void uploadUserProfile(String path) {

        if (CommonUtils.isConnectingToInternet(mContext)) {
            AppGlobal.showProgressDialog(mContext);

            File file = new File(path);
            MultipartBody.Part body = null;

            if (!file.exists()) {
                Toast.makeText(mContext, getString(R.string.msg_plz_select_file), Toast.LENGTH_SHORT).show();
                return;
            }

            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

            new ApiHandlerToken(mContext).getApiService().uploadWalletImages(body).enqueue(new Callback<WalletImageModel>() {
                @Override
                public void onResponse(Call<WalletImageModel> call, Response<WalletImageModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {

                                imagesLink[imageUploadCount] = response.body().getImagepath();

                                imageUploadCount = imageUploadCount + 1;

                                if (imageUploadCount < 2) {
                                    /*if (imageUploadCount == 1) {
                                        uploadUserProfile(imagePathVP);
                                    } else if (imageUploadCount == 2) {
                                        uploadUserProfile(imagePathRV);
                                    } else */if (imageUploadCount == 1) {
                                        uploadUserProfile(imagePathIP);
                                    }
                                } else {
                                    createDigitalWallet();
                                }

//                                editUserDetails(response.body().getData().getNewimgpath());
                            } else {
//                                CommonUtils.commonToast(mContext, response.body().getMessage());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        AppGlobal.showLog(mContext, "Error : " + e.toString());
                    }
                }

                @Override
                public void onFailure(Call<WalletImageModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            Log.e("this", "error" + "no internet");
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }

    }

    public void createDigitalWallet() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            AppGlobal.showProgressDialog(mContext);

            Map<String, String> params = new HashMap<String, String>();
            params.put("drivinglicience", stDrivingLicense);
            params.put("licenceplate", stPlate);
            params.put("registrationvin", stVIN);
            params.put("insurance", stInsurance);
            params.put("address", stAddress);
            params.put("state", stState);
            params.put("city", stZip);

            JSONArray json = new JSONArray(Arrays.asList(imagesLink));
            params.put("images", json.toString());

            AppGlobal.showLog(this, "resposen : " + params);

            ApiHandlerToken.getApiService().createDigitalWallet(params).enqueue(new Callback<DigitalWalletModel>() {
                @Override
                public void onResponse(Call<DigitalWalletModel> call, Response<DigitalWalletModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                CommonUtils.commonToast(mContext, response.body().getMessage());
                                if (DigitalWalletActivity.mContext != null) {
                                    DigitalWalletActivity.mContext.finish();
                                }
                                DigitalWalletDocumentsActivity.this.finish();
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
                public void onFailure(Call<DigitalWalletModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
    }
}
