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
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.onlineparkingticket.R;
import com.onlineparkingticket.adapter.ImageListAdapter;
import com.onlineparkingticket.allInterface.OnItemClick;
import com.onlineparkingticket.constant.AppGlobal;
import com.onlineparkingticket.constant.CommonUtils;
import com.onlineparkingticket.constant.CompressImageUtil;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

@SuppressWarnings("All")
public class FixItPleaActivity extends BaseActivity implements OnItemClick{

    public static FixItPleaActivity mContext;
    private TextView tvAddImages;
    private EditText edExplanation;
    private LinearLayout lvNext;
    private RecyclerView rvListImages;
    private CheckBox chkAgree;
    private ArrayList<String> listImages = new ArrayList<>();
    String date ="",name ="",email ="",phoneno ="",violationno ="",address="";
    String stItemId ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_it_plea);
        mContext = this;
        init(mContext);
        setHeaderWithBack(getResources().getString(R.string.fixit_plea), true, false);
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
        lvNext = (LinearLayout) findViewById(R.id.linear_FixItPLea_Next);

        chkAgree = (CheckBox) findViewById(R.id.checkbox_FixItPlea_Select);

        edExplanation = (EditText) findViewById(R.id.ed_FixItPlea_Explanation);
        tvAddImages = (TextView) findViewById(R.id.tv_FixItPlea_AddImages);

        rvListImages = (RecyclerView) findViewById(R.id.recyclerView_FitItPlea);
        rvListImages.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
    }

    ImageListAdapter adapterImages;
    public void setAdapter() {
        if (listImages.size() > 0) {
            if (adapterImages == null) {
                adapterImages = new ImageListAdapter(mContext, listImages, rvListImages, this);
                rvListImages.setAdapter(adapterImages);
            } else {
                adapterImages.notifyDataSetChanged();
            }
        }
    }

    private void setClickEvent() {
        lvNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chkAgree.isChecked()) {
                    Intent intent = new Intent(mContext, RequestReviewActivity.class);
                    intent.putExtra("msg", edExplanation.getText().toString().trim());
                    intent.putStringArrayListExtra("listImages", listImages);
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
                    CommonUtils.commonToast(mContext, getString(R.string.msg_plz_checked_agree));
                }
            }
        });

        tvAddImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
            }
        });
    }

    @Override
    public void onItemClickPosition(int position) {
        listImages.remove(position);
        adapterImages.notifyDataSetChanged();
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
                        setAdapter();

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
                setAdapter();

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
