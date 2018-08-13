package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.onlineparkingticket.R;
import com.onlineparkingticket.constant.AppGlobal;


@SuppressWarnings("All")
public class PictureActivity extends BaseActivity {

    ImageView imMainImage;
    ImageView imClose;
    String mediaURL;
    boolean isFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_picture);

        Intent intent = getIntent();
        if (intent != null) {
            mediaURL = intent.getStringExtra("mediaURL");
            isFile = intent.getBooleanExtra("isFile", false);

            initMy();
        }
    }

    private void initMy() {
        imMainImage = (ImageView) findViewById(R.id.image_ItemViewMedia_ivTrendingPic);
        imClose = (ImageView) findViewById(R.id.image_ViewMedia_Close);

        if (isFile) {
            AppGlobal.loadImageFile(PictureActivity.this, mediaURL, imMainImage);
        } else {
            AppGlobal.loadImage(PictureActivity.this, mediaURL, imMainImage);
        }

        imClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}