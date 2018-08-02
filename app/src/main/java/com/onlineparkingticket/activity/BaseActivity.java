package com.onlineparkingticket.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onlineparkingticket.R;
import com.onlineparkingticket.commonTextView.TextViewBlack;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import okhttp3.OkHttpClient;


@SuppressWarnings("ALL")
public class BaseActivity extends AppCompatActivity {


    public ImageView image_back_header;
    public ImageView txt_right_header; //header with done edit
    public Typeface fontAwesomeFont;
    TextView tv;
    ProgressBar mProgress;
    int pStatus = 0;

    public TextView txt_nodata;
    public ProgressBar pgb_progress;

    public Dialog popupDialog;
    public ArrayList<String> filterAddressListDisallow;


    OkHttpClient client;


    public TextView txtDialogReport;
    public TextView txtDialogSetting;
    public TextView txtDialogCancel;


//    public int getStatusBarHeight() {
//        int result = 0;
//        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
//        if (resourceId > 0) {
//            result = getResources().getDimensionPixelSize(resourceId);
//        }
//        return result;
//    }

    public ArrayList<String> filterAddressList;


    public void init(Activity mActivity) {
        try {

            String _DEVICE = android.os.Build.DEVICE;
            String _MODEL = "";
            _MODEL = android.os.Build.MODEL;
            client = new OkHttpClient();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = this.getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int getNumCores() {
        //Private Class to display only CPU devices in the directory listing
        class CpuFilter implements FileFilter {
            @Override
            public boolean accept(File pathname) {
                //Check if filename is "cpu", followed by a single digit number
                if (Pattern.matches("cpu[0-9]+", pathname.getName())) {
                    return true;
                }
                return false;
            }
        }

        try {
            //Get directory containing CPU info
            File dir = new File("/sys/devices/system/cpu/");
            //Filter to only list the devices we care about
            File[] files = dir.listFiles(new CpuFilter());
            //Return the number of cores (virtual CPU devices)
            return files.length;
        } catch (Exception e) {
            //Default to return 1 core
            return 1;
        }
    }

    public void setHeaderWithBack(String title, boolean isBackButton, boolean isDoneButton) {
        try {

            Toolbar toolbar;
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setPadding(0, 0, 0, 0);

            //toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

            //for tab otherwise give space in tab
            toolbar.setContentInsetsAbsolute(0, 0);
            setSupportActionBar(toolbar);

            TextViewBlack txt_title_header;

            image_back_header = (ImageView) findViewById(R.id.image_back_header);
            txt_title_header = (TextViewBlack) findViewById(R.id.txt_title_header);
            txt_right_header = (ImageView) findViewById(R.id.txt_right_header);

            if (isBackButton) {
                image_back_header.setVisibility(View.VISIBLE);
            } else {
                image_back_header.setVisibility(View.GONE);
            }

            if (isDoneButton) {
                txt_right_header.setVisibility(View.VISIBLE);
            } else {
                txt_right_header.setVisibility(View.INVISIBLE);
            }

            txt_title_header.setText(title);

            image_back_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*

    public void setHeaderone(String title) {
        try {
            TextView txtTitle;
            Toolbar toolbar;

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setPadding(0, 0, 0, 0);
            img_setting_right = (ImageView) findViewById(R.id.image_logo);

            toolbar.setContentInsetsAbsolute(0, 0);
            setSupportActionBar(toolbar);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHeadertwo(String title, boolean isimage,boolean isBackButton, boolean isDoneButton) {
        try {

            Toolbar toolbar;



            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setPadding(0, 0, 0, 0);

            //toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

            //for tab otherwise give space in tab
            toolbar.setContentInsetsAbsolute(0, 0);
            setSupportActionBar(toolbar);






            txt_back_arrow = (ImageView) findViewById(R.id.image_back_header);
            txt_header = (MyTextView) findViewById(R.id.txt_title_header);
            img_setting_right = (ImageView) findViewById(R.id.img_right);
            image_comment = (ImageView) findViewById(R.id.image_comment);

            if (isBackButton) {
                txt_back_arrow.setVisibility(View.VISIBLE);
            } else {
                txt_back_arrow.setVisibility(View.GONE);
            }
            if (isDoneButton) {
                img_setting_right.setVisibility(View.VISIBLE);
            } else {
                img_setting_right.setVisibility(View.INVISIBLE);
            } if (isimage) {
                image_comment.setVisibility(View.VISIBLE);
            } else {
                image_comment.setVisibility(View.INVISIBLE);
            }


            txt_header.setText(title);
            txt_back_arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();


                }

            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setHeaderSetting(String title, boolean isBackButton, boolean isDoneButton) {
        try {

            Toolbar toolbar;



            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setPadding(0, 0, 0, 0);

            //toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

            //for tab otherwise give space in tab
            toolbar.setContentInsetsAbsolute(0, 0);
            setSupportActionBar(toolbar);






            txt_backs = (MyTextView) findViewById(R.id.txt_back_arrow);
            txtTitle_header = (MyTextView) findViewById(R.id.txtTitle_header);
            img_setting = (MyTextView) findViewById(R.id.img_setting);

            if (isBackButton) {
                txt_backs.setVisibility(View.VISIBLE);
            } else {
                txt_backs.setVisibility(View.GONE);
            }
            if (isDoneButton) {
                img_setting.setVisibility(View.VISIBLE);
            } else {
                img_setting.setVisibility(View.INVISIBLE);
            }



            txt_backs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }





   /*    public void showImageDialog(final Activity mActivity) {
        // custom dialog
        popupDialog = new Dialog(mActivity);
        popupDialog.setContentView(R.layout.custom_settingdialog);
        popupDialog.setCancelable(true);


           txtDialogReport = (MyTextView)popupDialog.findViewById( R.id.txt_dialog_report );
           txtDialogCreateMovment = (MyTextView)popupDialog.findViewById( R.id.txt_dialog_create_movment );
           txtDialogSetting = (MyTextView)popupDialog.findViewById( R.id.txt_dialog_setting );
           txtDialogHelp = (MyTextView)popupDialog.findViewById( R.id.txt_dialog_help );
           txtDialogCancel = (MyTextView)popupDialog.findViewById( R.id.txt_dialog_cancel );
        // set the custom dialog components - text, image and button

           txtDialogReport.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   popupDialog.dismiss();

                   startActivity(new Intent(mActivity, Reporta.class));
               }
           });
           txtDialogSetting.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   popupDialog.dismiss();

                   startActivity(new Intent(mActivity, SettingFirstActivity.class));
               }
           });
           txtDialogHelp.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   popupDialog.dismiss();

                   startActivity(new Intent(mActivity, HelpActivity.class));
               }
           });

           txtDialogCreateMovment.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   popupDialog.dismiss();

                   if (Post1.activity != null)
                   {
                       Post1.activity.finish();
                   }
                   startActivity(new Intent(mActivity, Post1.class));
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
    }*/
    /*public void setfooter(boolean ishome, final Activity activity , boolean isBackButton, boolean isDoneButton) {
        try {

            Toolbar toolbar;
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setPadding(0, 0, 0, 0);



            //toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

            //for tab otherwise give space in tab
            toolbar.setContentInsetsAbsolute(0, 0);
            setSupportActionBar(toolbar);

            TextView txt_title_header;



            home_footer = (LinearLayout) findViewById(R.id.linear_home_ll);
            plus_footer = (LinearLayout) findViewById(R.id.linear_plus);
            user_footer = (LinearLayout) findViewById(R.id.linear_user);
            image_home_fooer = (ImageView) findViewById(R.id.image_home_fooer);
            image_plus_footer = (ImageView) findViewById(R.id.image_plus_footer);
            image_user_footer = (ImageView) findViewById(R.id.image_user_footer);


            home_footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (HomeActivity.activity != null)
                    {
                        HomeActivity.activity.finish();
                    }
                    startActivity(new Intent(activity, HomeActivity.class));
                }
            });

            plus_footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (Post1.activity != null)
                    {
                        Post1.activity.finish();
                    }
                    startActivity(new Intent(activity, Post1.class));
                }
            });

            user_footer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (UserProfileActivity.activity != null)
                    {
                        UserProfileActivity.activity.finish();
                    }
                    startActivity(new Intent(activity, UserProfileActivity.class));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

 /*   public void setIconOfImageview(int icon)
    {
        image_back_header.setImageDrawable(ContextCompat.getDrawable(this, icon));

    }

    public void logoutDialog(final Activity mActivity) {
        try {
            android.support.v7.app.AlertDialog.Builder myAlertDialog = new android.support.v7.app.AlertDialog.Builder(
                    mActivity, R.style.MyAlertDialogStyle);
            myAlertDialog.setMessage(mActivity.getResources().getString(R.string.logout_message));

            myAlertDialog.setPositiveButton(mActivity.getResources().getString(R.string.logout_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            try {

                                userLogoutApi(mActivity);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

            myAlertDialog.setNegativeButton(mActivity.getResources().getString(R.string.logout_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {


                        }
                    });
            myAlertDialog.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private void userLogoutApi(final Activity mActivity) {

        try {
            if (CommonUtils.isConnectingToInternet(mActivity)) {

                final ProgressDialog dialog;
                dialog = new ProgressDialog(mActivity);
                dialog.setMessage("Loading...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();


                Call<LoginBase> call = ApiHandler.getApiService().logoutApi(logoutApiJsonMap());

                call.enqueue(new retrofit2.Callback<LoginBase>() {
                    @Override
                    public void onResponse(Call<LoginBase> registerCall, Response<LoginBase> response) {
                        Log.e(" Full json gson => ", "Hi i am here");
                        try {
                            Log.e(" Full json gson => ", new Gson().toJson(response));
                            JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
                            Log.e(" responce => ", jsonObj.getJSONObject("body").toString());

                            if (response.isSuccessful()) {
                                dialog.dismiss();

                                int success = response.body().getSuccess();
                                if (success == 1) {

                                    //chat
                                    SharedPrefsHelper.getInstance().removeQbUser();
                                    ChatHelper.getInstance().destroy();
                                    SubscribeService.unSubscribeFromPushes(BaseClass.this);
                                    QbDialogHolder.getInstance().clear();
                                    QBUsers.signOut();

                                    SharedPreferenceHelper.clearAllPrefs();


                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);

                                    if (UserProfileSettingMenuActivity.mActivity !=null)
                                    {
                                        UserProfileSettingMenuActivity.mActivity.finish();
                                    }
                                    if (UserNavigationDrawer.mActivity !=null)
                                    {
                                        UserNavigationDrawer.mActivity.finish();
                                    }


                                    String message = response.body().getMessage();
                                    if (CommonUtils.isTextAvailable(message)) {
                                        CommonUtils.commonToast(mActivity, message);
                                    }

                                    finish();


                                } else if (success == 0) {
                                    String message = response.body().getMessage();
                                    if (CommonUtils.isTextAvailable(message)) {
                                        CommonUtils.commonToast(mActivity, message);
                                    }
                                } else {
                                    CommonUtils.commonToast(mActivity, getResources().getString(R.string.after_some_time));
                                }
                            } else {
                                dialog.dismiss();
                                try {
                                    CommonUtils.commonToast(mActivity, getResources().getString(R.string.after_some_time));
                                } catch (Resources.NotFoundException e) {
                                    e.printStackTrace();
                                }

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            try {
                                Log.e("Tag", "error=" + e.toString());
                                CommonUtils.commonToast(mActivity, getResources().getString(R.string.after_some_time));
                                dialog.dismiss();
                            } catch (Resources.NotFoundException e1) {
                                e1.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<LoginBase> call, Throwable t) {
                        try {
                            Log.e("Tag", "error" + t.toString());
                            CommonUtils.commonToast(mActivity, getResources().getString(R.string.after_some_time));
                            dialog.dismiss();
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                    }


                });

            } else {
                CommonUtils.commonToast(mActivity, getResources().getString(R.string.no_internet_exist));

            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    private JsonObject logoutApiJsonMap() {

        JsonObject gsonObject = new JsonObject();
        try {

            JSONObject jsonObj_login = new JSONObject();
            jsonObj_login.put("senderId", SharedPreferenceHelper.get(Keys.SharedPreferenceKeys.SENDER_ID));
            //jsonObj_login.put("deviceToken", SharedPreferenceHelper.get(Keys.SharedPreferenceKeys.DEVICE_TOCKEN));
            String token=commonSession.getDeviceTocken();
            if (CommonUtils.isTextAvailable(token))
            {
                jsonObj_login.put("deviceToken", commonSession.getDeviceTocken());}
            else
            {
                jsonObj_login.put("deviceToken", "0");
            }

            JsonParser jsonParser = new JsonParser();
            gsonObject = (JsonObject) jsonParser.parse(jsonObj_login.toString());
            Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return gsonObject;
    }


    public void alertDialog(final Activity mActivity) {
        try {
            android.support.v7.app.AlertDialog.Builder myAlertDialog = new android.support.v7.app.AlertDialog.Builder(
                    mActivity, R.style.MyAlertDialogStyle);
            myAlertDialog.setMessage(mActivity.getResources().getString(R.string.discard_message));

            myAlertDialog.setPositiveButton(mActivity.getResources().getString(R.string.logout_ok),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {

                            try {
                                if (GetEstimateActivity.mActivity !=null)
                                {
                                    GetEstimateActivity.mActivity.finish();
                                }
                                if (SendSomethingTodayActivityOne.mActivity !=null)
                                {
                                    SendSomethingTodayActivityOne.mActivity.finish();
                                }
                                if (SendSomethingTodayActivityTwo.mActivity !=null)
                                {
                                    SendSomethingTodayActivityTwo.mActivity.finish();
                                }
                                if (SendSomethingTodayActivityThree.mActivity !=null)
                                {
                                    SendSomethingTodayActivityThree.mActivity.finish();
                                }
                                if (PendingJobsActivity.mActivity !=null)
                                {
                                    PendingJobsActivity.mActivity.finish();
                                }
                                if (UserNavigationDrawer.mActivity !=null)
                                {
                                    UserNavigationDrawer.mActivity.finish();
                                }
                                Intent intent=new Intent(mActivity,UserNavigationDrawer.class);
                                 startActivity(intent);
                                finish();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                    });

            myAlertDialog.setNegativeButton(mActivity.getResources().getString(R.string.logout_cancel),
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface arg0, int arg1) {


                        }
                    });
            myAlertDialog.show();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    //for activity
    public void setProgressbarNodata(boolean isProgressbar, boolean isNodata, String str) {
        try {
            RelativeLayout rl_progressbar_nodata = (RelativeLayout) findViewById(R.id.rl_progressbar_nodata);
            TextView txt_nodata = (TextView) findViewById(R.id.txt_nodata);
            ProgressBar pgb_progress = (ProgressBar) findViewById(R.id.pgb_progress);

            if (CommonUtils.isTextAvailable(str)) {
                txt_nodata.setText(str);
            } else {
                txt_nodata.setText("No data available.");
            }

            if (isProgressbar) {
                pgb_progress.setVisibility(View.VISIBLE);
            } else {
                pgb_progress.setVisibility(View.GONE);
            }

            if (isNodata) {
                txt_nodata.setVisibility(View.VISIBLE);
            } else {
                txt_nodata.setVisibility(View.GONE);
            }

            if (!isNodata && !isProgressbar) {
                rl_progressbar_nodata.setVisibility(View.GONE);
            } else {
                rl_progressbar_nodata.setVisibility(View.VISIBLE);
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    //for fragment
    public void setProgressbarNodata(View view,boolean isProgressbar, boolean isNodata, String str) {
        try {
            RelativeLayout rl_progressbar_nodata = (RelativeLayout) view.findViewById(R.id.rl_progressbar_nodata);
            TextView txt_nodata = (TextView) view.findViewById(R.id.txt_nodata);
            ProgressBar pgb_progress = (ProgressBar) view.findViewById(R.id.pgb_progress);

            if (CommonUtils.isTextAvailable(str)) {
                txt_nodata.setText(str);
            } else {
                txt_nodata.setText("No data available.");
            }

            if (isProgressbar) {
                pgb_progress.setVisibility(View.VISIBLE);
            } else {
                pgb_progress.setVisibility(View.GONE);
            }

            if (isNodata) {
                txt_nodata.setVisibility(View.VISIBLE);
            } else {
                txt_nodata.setVisibility(View.GONE);
            }

            if (!isNodata && !isProgressbar) {
                rl_progressbar_nodata.setVisibility(View.GONE);
            } else {
                rl_progressbar_nodata.setVisibility(View.VISIBLE);
            }

        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    public void intentToPlayStore(Activity mActivity) {
       */
/* String sharedLink;
        if (CommonUtils.isTextAvailable(commonSession.getSenderAndroidLink()))
        {
            sharedLink=commonSession.getSenderAndroidLink();
        }
        else
        {
            sharedLink= CommonKeyword.APP_STORE_LINK;
        }*//*


       // final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        //final String appPackageName = "com.boon4.driver"; // getPackageName() from Context or Activity object
        try {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + CommonKeyword.DRIVER_APP_PACKAGE_NAME)));
        } catch (android.content.ActivityNotFoundException anfe) {
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + CommonKeyword.DRIVER_APP_PACKAGE_NAME)));
        }
    }
    public void shareIntent(Activity mActivity,String shareBody, String subjectStr,boolean isPickUpCode) {
        try {
            String shareStr="";
            if (isPickUpCode)
            {
                shareStr=getResources().getString(R.string.share_title_one)+" "+shareBody+getResources().getString(R.string.share_title_two);
            }
            else
            {
                shareStr=getResources().getString(R.string.share_title_three)+" "+shareBody+getResources().getString(R.string.share_title_four);
            }
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subjectStr);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareStr);
            mActivity.startActivity(Intent.createChooser(sharingIntent, getResources().getString(R.string.share_using)));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }


    public boolean isValidAddressFilter(String str) {
        filterAddressList = new ArrayList<>();
        filterAddressListDisallow = new ArrayList<>();

        try {
            filterAddressListDisallow = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.filter_address_disallow)));
        } catch (Resources.NotFoundException e) {
            Log.e("Adress", "Error filterAddressListDisallow : " + e.getMessage());
            e.printStackTrace();
        }

        Log.e("dc", "Address " + str);
        filterAddressList.add("Federal Territory of Kuala Lumpur");
        filterAddressList.add("Kuala Lumpur");
         filterAddressList.add("Putrajaya");
        filterAddressList.add("Johor");
        filterAddressList.add("Kedah");
        filterAddressList.add("Kelantan");
        filterAddressList.add("Malacca");
        filterAddressList.add("Melaka");
        filterAddressList.add("Negeri");
        filterAddressList.add("Pahang");
        filterAddressList.add("Penang");
        filterAddressList.add("Pinang");
        filterAddressList.add("Perak");
        filterAddressList.add("Perlis");
        filterAddressList.add("Selangor");
        filterAddressList.add("Sembilan");
        filterAddressList.add("Klang Valley");
        filterAddressList.add("Terengganu");
        filterAddressList.add("Wilayah Persekutuan");
        filterAddressList.add("Negeri Sembilan");
        filterAddressList.add("Wilayah Persekutuan Kuala Lumpur");
        filterAddressList.add("Penang Island");


        */
/* ["Federal Territory of Kuala Lumpur","Putrajaya","Johor","Kedah","Kelantan", "Melaka", "Malacca","Negeri Sembilan",
        "Penang","Perlis","Selangor","Terengganu", "Wilayah Persekutuan Kuala Lumpur", "Pahang", "Perak", "Pulau Pinang", "Penang Island"]  *//*


        boolean isValidName = false;

        for (int i = 0; i < filterAddressList.size(); i++) {
            String item = filterAddressList.get(i).toString();
            isValidName = str.contains(item) ? true : false;

            if (isValidName) {
                Log.e("dc", "item======item=======>" + item);
                //return true;
                isValidName = true;
                break;

            }
        }

        for (int i = 0; i < filterAddressListDisallow.size(); i++) {
            String item = filterAddressListDisallow.get(i).toString().toLowerCase();
            if (isValidName && str.toLowerCase().contains(item)) {
                isValidName = false;
                break;
            }
        }

        return isValidName;


    }
*/

/*

    public void setHeaderWithBack(String title,String right, boolean isBackButton, boolean isDoneButton) {
        try {

            Toolbar toolbar;
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setPadding(0, 0, 0, 0);

            //toolbar.setPadding(0, getStatusBarHeight(), 0, 0);

            //for tab otherwise give space in tab
            toolbar.setContentInsetsAbsolute(0, 0);
            setSupportActionBar(toolbar);

            TextView txt_title_header;

            image_back_header = (ImageView) findViewById(R.id.image_back_header);
            txt_title_header = (TextView) findViewById(R.id.txt_title_header);
            txt_right_header = (TextView) findViewById(R.id.txt_right_header);

            if (isBackButton) {
                image_back_header.setVisibility(View.VISIBLE);
            } else {
                image_back_header.setVisibility(View.GONE);
            }

            if (isDoneButton) {
                txt_right_header.setVisibility(View.VISIBLE);
            } else {
                txt_right_header.setVisibility(View.INVISIBLE);
            }

            txt_title_header.setText(title);
            txt_right_header.setText(right);

            image_back_header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
*/


   /*
    public static void setSingleImageUser(Context mContext, String path, ImageView imImage) {


        Picasso.with(mContext)
                .load(path)
                .placeholder(R.drawable.noimage)
                .error(R.drawable.noimage)
                .fit()
                .centerCrop()
                .into(imImage);
    }
*/


    public Date getDateFormat(String fromDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd");
            return inputFormat.parse(fromDate);
        } catch (Exception e) {
            Log.e("DatingApp", "Error converting to date : " + e.toString());
        }
        return null;
    }

    public boolean getFormattedDate(Context context, long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return true;
        }
        return false;
    }
}