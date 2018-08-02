package com.onlineparkingticket.fragment;


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
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.HomeNavigationDrawer;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.CompressImageUtil;
import com.onlineparkingticket.constant.WsConstant;
import com.onlineparkingticket.httpmanager.ApiHandlerToken;
import com.onlineparkingticket.model.EditUserDetailsModel;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("All")
public class FragmentEditProfile extends Fragment {
    public static Context mContext;
    private EditText edName, edMobile, edEmail, edPlate, edAddress;
    private LinearLayout lvEditDP;
    private ImageView imDP;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        mContext = getActivity();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeNavigationDrawer.imNotification.setVisibility(View.VISIBLE);
        HomeNavigationDrawer.imNotification.setImageResource(R.drawable.done);
        HomeNavigationDrawer.drawerImg.setImageResource(R.drawable.back_arro);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        HomeNavigationDrawer.imNotification.setVisibility(View.INVISIBLE);
        HomeNavigationDrawer.imNotification.setImageResource(R.drawable.notification);
        HomeNavigationDrawer.drawerImg.setImageResource(R.drawable.hamburger);
        HomeNavigationDrawer.resetData();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        setClickEvent();
    }

    private void init(View view) {
        edName = (EditText) view.findViewById(R.id.ed_EditProfile_Name);
        edMobile = (EditText) view.findViewById(R.id.ed_EditProfile_Mobile);
        edEmail = (EditText) view.findViewById(R.id.ed_EditProfile_Email);
        edPlate = (EditText) view.findViewById(R.id.ed_EditProfile_PlateNo);
        edAddress = (EditText) view.findViewById(R.id.ed_EditProfile_Address);

        imDP = (ImageView) view.findViewById(R.id.image_EditProfile_DP);

        edName.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_NAME), ""));
        edMobile.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_MOBILE), ""));
        edEmail.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_EMAIL), ""));
        edAddress.setText(AppGlobal.isTextAvailableWithData(AppGlobal.getStringPreference(mContext, WsConstant.SP_ADDRESS), ""));

        lvEditDP = (LinearLayout) view.findViewById(R.id.linear_EditProfile_DP);
    }

    private void setClickEvent() {
        HomeNavigationDrawer.imNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isValidField()) {
                    editUserDetails();
                }
            }
        });

        HomeNavigationDrawer.drawerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        lvEditDP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog1(mContext);
            }
        });
    }

    private boolean isValidField() {

        if (!CommonUtils.isTextAvailable(edName.getText().toString().trim())) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_name));
            return false;
        } else if (!CommonUtils.isTextAvailable(edMobile.getText().toString().trim())) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_mobile));
            return false;
        } else if (!CommonUtils.isTextAvailable(edEmail.getText().toString().trim())) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_email));
            return false;
        } else if (!CommonUtils.isEmailValid(edEmail.getText().toString().trim())) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_valid_email));
            return false;
        } else if (!CommonUtils.isTextAvailable(edAddress.getText().toString().trim())) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_address));
            return false;
        } else if (!CommonUtils.isTextAvailable(edPlate.getText().toString().trim())) {
            CommonUtils.commonToast(mContext, getString(R.string.msg_plz_enter_dl));
            return false;
        } else
            return true;
    }

    public void editUserDetails() {
        if (CommonUtils.isConnectingToInternet(mContext)) {
            AppGlobal.showProgressDialog(mContext);

            Map<String, String> params = new HashMap<String, String>();
            params.put("_id", AppGlobal.getStringPreference(mContext, WsConstant.SP_ID));
            params.put("name", edName.getText().toString());
            params.put("mobileno", edMobile.getText().toString());
            params.put("email", edEmail.getText().toString());
            params.put("plate", edPlate.getText().toString());
            params.put("address", edAddress.getText().toString());

            new ApiHandlerToken(mContext).getApiService().editUserDetails(params).enqueue(new Callback<EditUserDetailsModel>() {
                @Override
                public void onResponse(Call<EditUserDetailsModel> call, Response<EditUserDetailsModel> response) {
                    AppGlobal.hideProgressDialog();
                    try {
                        JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                        AppGlobal.showLog(mContext, "Response : " + jsonObj.getJSONObject("body").toString());

                        if (response.isSuccessful()) {
                            if (response.body().getSuccess()) {
                                CommonUtils.commonToast(mContext, response.body().getMessage());

                                AppGlobal.setStringPreference(mContext, response.body().getData().getId(), WsConstant.SP_ID);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getName(), WsConstant.SP_NAME);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getEmail(), WsConstant.SP_EMAIL);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getMobileno(), WsConstant.SP_MOBILE);
                                AppGlobal.setStringPreference(mContext, response.body().getData().getAddress(), WsConstant.SP_ADDRESS);

                                getActivity().onBackPressed();

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
                public void onFailure(Call<EditUserDetailsModel> call, Throwable t) {
                    AppGlobal.showLog(mContext, "Error : " + t.toString());
                    AppGlobal.hideProgressDialog();
                }
            });

        } else {
            CommonUtils.commonToast(mContext, getResources().getString(R.string.no_internet_exist));
        }
    }

    Dialog popupDialog;
    private int PICK_FROM_CAMERA = 123;
    private int PICK_FROM_GALLARY = 200;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    String selectedImagePathFront = "";
    Bitmap bitmapImagePathFront = null;

    public void showImageDialog1(final Context mActivity) {
        // custom dialog

        popupDialog = new Dialog(mActivity);
        popupDialog.setContentView(R.layout.dialog_image_picker);
        popupDialog.setCancelable(true);

        TextView txtDialogReport = (TextView) popupDialog.findViewById(R.id.txt_dialog_report);
        TextView txtDialogSetting = (TextView) popupDialog.findViewById(R.id.txt_dialog_setting);
        TextView txtDialogCancel = (TextView) popupDialog.findViewById(R.id.txt_dialog_cancel);
        // set the custom dialog components - text, image and button

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

                // startActivity(new Intent(mActivity, SettingFirstActivity.class));
            }
        });

        // Close Button
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
        if (resultCode == getActivity().RESULT_OK) {

            if (resultCode == getActivity().RESULT_OK && requestCode == PICK_FROM_GALLARY) {

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
                        selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imDP);
                        bitmapImagePathFront = compressUtil.getBitmap();

                        AppGlobal.showLog(mContext, "FilePath : " + selectedImagePathFront);
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                } finally {

                }
            } else if (requestCode == PICK_FROM_CAMERA) {
                bitmapImagePathFront = null;
                selectedImagePathFront = null;
                Bitmap photo = (Bitmap) data.getExtras().get("data");

                imDP.setImageBitmap(photo);

                Uri imageUri = getImageUri(mContext, photo);

                selectedImagePathFront = getRealPathFromURI(imageUri);

                AppGlobal.showLog(mContext, "FilePath : " + selectedImagePathFront);
                if (!selectedImagePathFront.equalsIgnoreCase("")) {
                    //editProfileImageeditProfileImage();
                }
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