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
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CompressImageUtil;

import java.io.ByteArrayOutputStream;

@SuppressWarnings("All")
public class DigitalWalletDocumentsActivity extends BaseActivity {

    public static Context mContext;
    private LinearLayout lvSubmit;
    private RelativeLayout rvDL, rvVP, rvRV, rvIP;
    private ImageView imDL, imVP, imRV, imIP;
    private TextView tvDL, tvVP, tvRV, tvIP;
    private String imagePathDL = "", imagePathVP = "", imagePathRV = "", imagePathIP = "";
    private String stFromImage = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital_wallet_documents);
        mContext = DigitalWalletDocumentsActivity.this;
        init(DigitalWalletDocumentsActivity.this);
        setHeaderWithBack(getString(R.string.digital_wallet), true, false);

        init();
        setClickEvent();
    }

    private void init() {
        lvSubmit = (LinearLayout) findViewById(R.id.linear_DigitalWalletDocuments_Submit);

        rvDL = (RelativeLayout) findViewById(R.id.relative_DigitalWalletDetails_DL);
        rvVP = (RelativeLayout) findViewById(R.id.relative_DigitalWalletDetails_VP);
        rvRV = (RelativeLayout) findViewById(R.id.relative_DigitalWalletDetails_RV);
        rvIP = (RelativeLayout) findViewById(R.id.relative_DigitalWalletDetails_IP);

        imDL = (ImageView) findViewById(R.id.image_DigitalWalletDetails_DL);
        imVP = (ImageView) findViewById(R.id.image_DigitalWalletDetails_VP);
        imRV = (ImageView) findViewById(R.id.image_DigitalWalletDetails_RV);
        imIP = (ImageView) findViewById(R.id.image_DigitalWalletDetails_IP);

        tvDL = (TextView) findViewById(R.id.tv_DigitalWalletDetails_DL);
        tvVP = (TextView) findViewById(R.id.tv_DigitalWalletDetails_VP);
        tvRV = (TextView) findViewById(R.id.tv_DigitalWalletDetails_RV);
        tvIP = (TextView) findViewById(R.id.tv_DigitalWalletDetails_IP);
    }

    private void setClickEvent() {
        lvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DigitalWalletDocumentsActivity.this.finish();
            }
        });

        rvDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stFromImage = "DL";
                showImageDialog();
            }
        });

        rvVP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stFromImage = "VP";
                showImageDialog();
            }
        });

        rvRV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stFromImage = "RV";
                showImageDialog();
            }
        });

        rvIP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stFromImage = "IP";
                showImageDialog();
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
                        } else if (stFromImage.equalsIgnoreCase("VP")) {
                            selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imVP);
                            imagePathVP = selectedImagePathFront;
                            tvVP.setVisibility(View.GONE);
                        } else if (stFromImage.equalsIgnoreCase("RV")) {
                            selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imRV);
                            imagePathRV = selectedImagePathFront;
                            tvRV.setVisibility(View.GONE);
                        } else if (stFromImage.equalsIgnoreCase("IP")) {
                            selectedImagePathFront = compressUtil.compressImage(selectedImagePathFront, imIP);
                            imagePathIP = selectedImagePathFront;
                            tvIP.setVisibility(View.GONE);
                        }

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

                if (stFromImage.equalsIgnoreCase("DL")) {
                    imDL.setImageBitmap(photo);
                    imagePathDL = selectedImagePathFront;
                    tvDL.setVisibility(View.GONE);
                } else if (stFromImage.equalsIgnoreCase("VP")) {
                    imVP.setImageBitmap(photo);
                    imagePathVP = selectedImagePathFront;
                    tvVP.setVisibility(View.GONE);
                } else if (stFromImage.equalsIgnoreCase("RV")) {
                    imRV.setImageBitmap(photo);
                    imagePathRV = selectedImagePathFront;
                    tvRV.setVisibility(View.GONE);
                } else if (stFromImage.equalsIgnoreCase("IP")) {
                    imIP.setImageBitmap(photo);
                    imagePathIP = selectedImagePathFront;
                    tvIP.setVisibility(View.GONE);
                }

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
