package com.onlineparkingticket.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlineparkingticket.fragment.HomeFragment;
import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.TextViewBlack;

import static com.onlineparkingticket.constant.AppGlobal.toast;


public class HomeNavigationDrawer extends BaseActivity {
    public static ImageView drawerImg, userImage;
    public static TextViewBlack drawerName, drawerEmail, mainTitle;
    public static HomeNavigationDrawer mainActivity;
    public static ProgressBar pDialog;

    long lastBackPressTime = 0;
    String mainActivityComeFrom = "";
    private LinearLayout linearResolvedtickets;
    private LinearLayout linearPendingtickets;
    private LinearLayout linearSetting;
    private LinearLayout linearProfile;
    private LinearLayout linearLogout;
    private DrawerLayout drawerLayout;
    private ImageView imEditProfile;
    private Toolbar toolbar;

    LinearLayout lvHome;

/*
    public static void setUserData() {
        drawerName.setText("" + AppGlobal.getStringPreference(mainActivity, WsConstant.SP_LOGIN_NAME));
        drawerEmail.setText("" + AppGlobal.getStringPreference(mainActivity, WsConstant.SP_LOGIN_EMAIL));

        if (!AppGlobal.getStringPreference(mainActivity, WsConstant.SP_LOGIN_PROFILE_PIC).equalsIgnoreCase("")) {
            Glide.with(mainActivity)
                    .load(AppGlobal.getStringPreference(mainActivity, WsConstant.SP_LOGIN_PROFILE_PIC))
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            pDialog.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            pDialog.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .into(userImage);
        } else {
            pDialog.setVisibility(View.GONE);
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity = HomeNavigationDrawer.this;

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            mainActivityComeFrom = bundle.getString("forVerifyAccount");
        }

        init();
        setClickEvent();
        callFragment(new HomeFragment(), getString(R.string.home));
    }

    private void init() {

        mainTitle = (TextViewBlack) findViewById(R.id.titleActionbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        pDialog = (ProgressBar) findViewById(R.id.progressBar_Main);

        // toolbar.setNavigationIcon(R.drawable.dr_icon);
        drawerName = (TextViewBlack) findViewById(R.id.activity_main_drawer_name);

        drawerImg = (ImageView) findViewById(R.id.drawerImg);
        userImage = (ImageView) findViewById(R.id.user_image);

        lvHome = (LinearLayout) findViewById(R.id.linear_Home_Home);
        linearResolvedtickets = (LinearLayout)findViewById( R.id.linear_resolvedtickets );
        linearPendingtickets = (LinearLayout)findViewById( R.id.linear_pendingtickets );
        linearSetting = (LinearLayout)findViewById( R.id.linear_setting );
        linearProfile = (LinearLayout)findViewById( R.id.linear_profile );
        linearLogout = (LinearLayout)findViewById( R.id.linear_logout );
        setClickEvent();
    }

    private void setClickEvent() {



        lvHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callFragment(new HomeFragment(), getString(R.string.home));
            }
        });



        drawerImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
    }

    public void callFragment(Fragment fragment, String title) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relative_container, fragment)
                .addToBackStack(null)
                .commit();

        if (drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawers();

        mainTitle.setText(title);
    }

    public void callFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.relative_container, fragment)
                .addToBackStack(null)
                .commit();

        if (drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawers();
    }

    public boolean getFragmentIntanceForClick() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.relative_container);
        if (fragment instanceof HomeFragment) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Fragment frag = getSupportFragmentManager().findFragmentById(R.id.relative_container);

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
        } else if (frag instanceof HomeFragment) {
//            openDialogForCloseApp();
            if (this.lastBackPressTime < System.currentTimeMillis() - 3000) {
                Snackbar snackbar = Snackbar.make(drawerLayout, "Double Tap to Exit ..!!", Snackbar.LENGTH_SHORT);
                View view = snackbar.getView();
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                snackbar.setActionTextColor(getResources().getColor(R.color.white));
                TextView txt = (TextView) view.findViewById(R.id.snackbar_text);
                txt.setTextColor(getResources().getColor(R.color.white));
                snackbar.show();
                this.lastBackPressTime = System.currentTimeMillis();

            } else {
                if (toast != null) {
                    toast.cancel();
                }

                HomeNavigationDrawer.this.finish();
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.relative_container);
        super.onActivityResult(requestCode, resultCode, data);
    }

}