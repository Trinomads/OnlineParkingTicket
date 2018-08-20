package com.onlineparkingticket.constant;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ViewStub;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.onlineparkingticket.BuildConfig;
import com.onlineparkingticket.R;
import com.onlineparkingticket.activity.PictureActivity;
import com.onlineparkingticket.activity.WelcomeActivity;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;
import java.util.regex.Pattern;

@SuppressWarnings("ALL")
@SuppressLint("InflateParams")
public class AppGlobal {

    public static final String PREFS_NAME = "kasi_lifestyle";
    // Email Pattern
    public final static Pattern EMAIL_ADDRESS_PATTERN = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" + "\\@"
            + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" + "(" + "\\." + "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" + ")+");

    public final static Pattern PASSWORD_NUMBER_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{6,}$");

    // PhoneNumber Pattern
    public final static Pattern PHONE_NUMBER_PATTERN = Pattern.compile("^[7-9][0-9]{9}$");
    static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";
    public static ProgressDialog progressDialog;
    public static String provider = null;
    public static ViewStub stubLoader;
    public static Animation alert_show, alert_hide;
    public static boolean isAlert = false;
    public static Toast toast = null;

    /**
     * Email validation
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        return EMAIL_ADDRESS_PATTERN.matcher(email).matches();
    }

    public static boolean checkPhoneNumber(String phone) {
        return PHONE_NUMBER_PATTERN.matcher(phone).matches();
    }

    public static boolean checkPassword(String password) {
        return PASSWORD_NUMBER_PATTERN.matcher(password).matches();
    }

    public static void showAlertDialog(Context context, String msg, String title) {
        final AlertDialog localAlertDialog = new AlertDialog.Builder(context).create();
        localAlertDialog.setTitle(title);
        localAlertDialog.setMessage(msg);

        localAlertDialog.setButton(-1, "Tamam", new DialogInterface.OnClickListener() {
            @SuppressLint({"NewApi"})
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int) {

                localAlertDialog.dismiss();

            }
        });

        localAlertDialog.show();
    }
    /**
     * show application Loader
     *
     * @param mContext
     */
   /* public static void showAlert(final Context mContext, String msg, int status) {

        if (mContext != null) {
            alert_show = AnimationUtils.loadAnimation(mContext,
                    R.anim.alert_show);

            alert_hide = AnimationUtils.loadAnimation(mContext,
                    R.anim.alert_hide);

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(R.layout.layout_alert, null);

            LinearLayout layoutMain = (LinearLayout) v.findViewById(R.id.layoutAlertMain);
            final LinearLayout layoutAlert = (LinearLayout) v.findViewById(R.id.layoutAlert);

            TextView txtAlertTitle = (TextView) v.findViewById(R.id.txtAlertTitle);
            TextView txtAlertDesp = (TextView) v.findViewById(R.id.txtAlertDesp);
            View viewBottom = (View) v.findViewById(R.id.bottomStrip);
            txtAlertDesp.setText(msg);

            switch (status) {
                case 0:
                    txtAlertTitle.setText("Alert :|");
                    txtAlertTitle.setTextColor(mContext.getResources().getColor(R.color.orange));
                    viewBottom.setBackgroundColor(mContext.getResources().getColor(R.color.orange));
                    break;
                case 1:
                    txtAlertTitle.setText("Success :)");
                    txtAlertTitle.setTextColor(mContext.getResources().getColor(R.color.green));
                    viewBottom.setBackgroundColor(mContext.getResources().getColor(R.color.green));
                    break;
                case 2:
                    txtAlertTitle.setText("Sorry :(");
                    txtAlertTitle.setTextColor(mContext.getResources().getColor(R.color.red));
                    viewBottom.setBackgroundColor(mContext.getResources().getColor(R.color.red));
                    break;
            }

            layoutAlert.startAnimation(alert_show);
            layoutMain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    hideAlert(mContext);

                }
            });

            ViewGroup parent = (ViewGroup) ((Activity) mContext).getWindow()
                    .getDecorView();

            parent.addView(v);

            isAlert = true;
        }
    }
*/
   /* public static void hideAlert(Context mContext) {
        try {

            if (isAlert) {

                isAlert = false;
                ViewGroup parent = (ViewGroup) ((Activity) mContext).getWindow()
                        .getDecorView();

                final View v = parent.findViewById(R.id.layoutAlertMain);
                if (v != null) {
                    final LinearLayout layoutAlert = (LinearLayout) v.findViewById(R.id.layoutAlert);
                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            if (v.getParent() != null)
                                ((ViewGroup) v.getParent()).removeView(v);
                        }
                    }, 700);
                    layoutAlert.startAnimation(alert_hide);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    /**
     * Display Toast
     *
     * @param context
     * @param message
     * @param status  0 - Alert
     *                1 - success
     *                2 - error
     */
    public static void showToast(Activity context, String message, int status) {
        // TODO Auto-generated method stub
        if (context != null) {

           /* LayoutInflater inflater = context.getLayoutInflater();
            View toastRoot = inflater.inflate(R.layout.layout_custom_toast, (ViewGroup) context.findViewById(R.id.custom_toast));
            TextView txt = (TextView) toastRoot.findViewById(R.id.custom_toast_message);
            txt.setText(message);

            switch (status) {
                case 0:
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        txt.setBackground(context.getResources().getDrawable(R.drawable.toast_alert));
                    } else {
                        txt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.toast_alert));
                    }

                    break;
                case 1:
                    txt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.toast_success));
                    break;
                case 2:
                    txt.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.toast_error));
                    break;
            }*/
           /* toast = new Toast(context);
            //toast.setView(toastRoot);
            toast.setGravity(Gravity.TOP, 0, 0);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.show();*/
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();


        }
    }

    /**
     * Show Progress Dialog
     *
     * @param context
     * @param msg
     */
    public static void showProgressDialog(Context context) {
        try {
            if (context != null) {
                if (progressDialog != null && progressDialog.isShowing()) {
                    progressDialog.dismiss();
                    progressDialog = null;
                }
                progressDialog = new ProgressDialog(context);
                progressDialog.setMessage("Loading...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void hideProgressDialog() {
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void displayAlertDilog(Context mContext, String msg) {

        new AlertDialog.Builder(mContext).setTitle("Pleez").setMessage(msg)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                }).show();
    }

    /**
     * check Network Connection
     *
     * @param context
     * @return
     */
    public static boolean isNetwork(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }

    /**
     * Store String to Preference
     *
     * @param c
     * @param value
     * @param key
     * @return
     */


    static public boolean setArrayListPreference(Context c, ArrayList<String> value, String key) {
        Gson gson = new Gson();
        String json = gson.toJson(value);

        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, json);
        return editor.commit();
    }

    static public ArrayList<String> getArrayListPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String value = settings.getString(key, "");

        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        return gson.fromJson(value, type);
    }

    static public boolean setStringPreference(Context c, String value, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        return editor.commit();
    }

    /**
     * get String from Preference
     *
     * @param c
     * @param key
     * @return
     */
    static public String getStringPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String value = settings.getString(key, "");
        return value;
    }

    /**
     * Store integer to Preference.
     *
     * @param c
     * @param value
     * @param key
     * @return
     */
    static public boolean setIntegerPreference(Context c, int value, String key) {

        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key, value);
        return editor.commit();

    }

    /**
     * get Integer from Preference
     *
     * @param c
     * @param key
     * @return
     */
    static public int getIntegerPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        int value = settings.getInt(key, -1);
        return value;
    }

    /**
     * Store double to Preference.
     *
     * @param c
     * @param value
     * @param key
     * @return
     */
    static public boolean setFloatPreference(Context c, float value, String key) {

        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putFloat(key, value);
        return editor.commit();

    }

    /**
     * get Integer from Preference
     *
     * @param c
     * @param key
     * @return
     */
    static public float getFloatPreference(Context c, String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        float value = settings.getFloat(key, -1);
        return value;
    }

    /**
     * Store boolean to Preference
     *
     * @param c
     * @param value
     * @param key
     * @return
     */
    static public boolean setBooleanPreference(Context c, Boolean value,
                                               String key) {
        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean from Preference
     *
     * @param c
     * @param key
     * @return
     */
    static public Boolean getBooleanPreference(Context c, String key) {

        SharedPreferences settings = c.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        Boolean value = settings.getBoolean(key, false);
        return value;
    }

    /**
     * get the height of the device
     *
     * @param displayMetrics - see {@link DisplayMetrics}
     * @return height of the device
     */
    public static int getDeviceHeight(Context mContex) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContex).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.heightPixels;
    }

    /**
     * get the width of the device
     *
     * @param displayMetrics - see {@link DisplayMetrics}
     * @return width of the device
     */
    public static int getDeviceWidth(Context mContex) {

        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) mContex).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;

    }

    /**
     * Hide Keyboard
     *
     * @param mContext
     */
    public static void hideKeyboard(Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(((Activity) mContext).getWindow().getCurrentFocus().getWindowToken(), 0);
    }

    /**
     * Convert dp to PIx
     *
     * @param res
     * @param dp
     * @return
     */
    public static int dpToPx(Resources res, int dp) {

        // final float scale = res.getDisplayMetrics().density;
        // int pixels = (int) (dp * scale + 0.5f);

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                res.getDisplayMetrics());

    }

    /*
     * generate Unique number
     */
    public static String generateUniqueNumber() {

        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
                .toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }

        String output = sb.toString();
        return output;

    }

    public static String GetUTCdatetimeAsString() {

        SimpleDateFormat sdf = new SimpleDateFormat(DATEFORMAT,
                Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.format(new Date());
    }

    /**
     * Checks if the application is being sent in the background (i.e behind
     * another application's Activity).
     *
     * @param context the context
     * @return <code>true</code> if another application will be above this one.
     */
    @SuppressWarnings("deprecation")
    public static boolean isApplicationSentToBackground(final Context context) {

        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

    // Convert String to date
    public static Date convertStringToDate(String strdate) {

        SimpleDateFormat sourceFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date parsedDate = null;
        try {
            parsedDate = sourceFormat.parse(strdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        TimeZone tz = TimeZone.getDefault();

        SimpleDateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

        destFormat.setTimeZone(tz);

        String destDate = destFormat.format(parsedDate);

        Date date = null;
        try {
            date = destFormat.parse(destDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;

    }

    public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getNextDayDateTime(String dateTime) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateTime.split("-")[0]));
        calendar.set(Calendar.MONTH, Integer.parseInt(dateTime.split("-")[1]) - 1);
        calendar.set(Calendar.YEAR, Integer.parseInt(dateTime.split("-")[2]));

        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }

    public static String getDateTimeInFormat() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getNextDayDateTimeInFormat() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date date = calendar.getTime();
        return dateFormat.format(date);
    }

    public static void copyText(Context mContext, String text) {
        ClipboardManager cm = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        cm.setText(text);
    }

    /*public static void setTypeFaceBold(Context context, TextView view) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Montserrat-Bold.otf");
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.sp_14));
        view.setTypeface(tf);

    }

    public static void setTypeFaceNormal(Context context, TextView view) {
        Typeface tf = Typeface.createFromAsset(context.getAssets(), "Montserrat-Medium.otf");
        view.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.sp_14));
        view.setTypeface(tf);

    }*/

    public static String pasteText(Context mContext) {
        ClipboardManager myClipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData abc = myClipboard.getPrimaryClip();
        ClipData.Item item = abc.getItemAt(0);
        return item.getText().toString();
    }

    public static String getAddressFromLocation(Context mContext, double LATITUDE, double LONGITUDE) {

        String strAdd = "";

        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);

            if (addresses != null) {

                Address returnedAddress = addresses.get(0);

                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {

                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(",");
                }

                strAdd = strReturnedAddress.toString();

                Log.w("My Current loction address", "" + strReturnedAddress.toString());
            } else {
                Log.w("My Current loction address", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("My Current loction address", "Canont get Address!");
        }
        return strAdd;
    }

    public static void setTypeFaceSFUIBold(Context mContext, TextView tv) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-Display-Bold.otf");
        tv.setTypeface(tf);
    }

    public static void setTypeFaceSFUIMedium(Context mContext, TextView tv) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-Display-Medium.otf");
        tv.setTypeface(tf);
    }

    public static void setTypeFaceSFUIRegular(Context mContext, TextView tv) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-Display-Regular.otf");
        tv.setTypeface(tf);
    }

    public static void setTypeFaceSFUISemiBold(Context mContext, TextView tv) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/SF-UI-Display-Semibold.otf");
        tv.setTypeface(tf);
    }


    public static void setTypeFaceHelveticaBold(Context mContext, TextView tv) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/helvetica-neue-bold.ttf");
        tv.setTypeface(tf);
    }

    public static void setTypeFaceHelveticaMedium(Context mContext, TextView tv) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeue-Medium.ttf");
        tv.setTypeface(tf);
    }

    public static void setTypeFaceHelveticaRegular(Context mContext, TextView tv) {
        Typeface tf = Typeface.createFromAsset(mContext.getAssets(), "fonts/HelveticaNeue.ttf");
        tv.setTypeface(tf);
    }

    public static String formatToYesterdayOrToday(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar today = Calendar.getInstance();

        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);

        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);

        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();

        DateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
        timeFormatter.setTimeZone(tz);

        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
        dateFormat.setTimeZone(tz);

        if (calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR)) {
            return "Today$" + timeFormatter.format(date);
        } else if (calendar.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)) {
            return "Yesterday$" + timeFormatter.format(date);
        } else if (calendar.get(Calendar.YEAR) == tomorrow.get(Calendar.YEAR) && calendar.get(Calendar.DAY_OF_YEAR) == tomorrow.get(Calendar.DAY_OF_YEAR)) {
            return "Tomorrow$" + timeFormatter.format(date);
        } else {
            return dateFormat.format(date) + "$" + timeFormatter.format(date);
        }
    }

    public static Date getDate(Date date) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        DateFormat timeFormatter = new SimpleDateFormat("HH:mm:ss");
        timeFormatter.setTimeZone(TimeZone.getDefault());
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setTimeZone(TimeZone.getDefault());

        String stDate = dateFormat.format(date) + " " + timeFormatter.format(date);
        DateFormat dateFormatFinal = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        Date newDate = dateFormatFinal.parse(stDate);

        return newDate;
    }

    public static boolean getDateForCheck(String sDate, int minutes) throws ParseException {
        boolean status = false;
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = format.parse(sDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.MINUTE, -minutes);

            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();

            DateFormat timeFormatter = new SimpleDateFormat("hh:mm a");
            timeFormatter.setTimeZone(tz);
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
            dateFormat.setTimeZone(tz);

            String stDate = dateFormat.format(calendar.getTime()) + " " + timeFormatter.format(calendar.getTime());
            DateFormat dateFormatFinal = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
            Date newDate = dateFormatFinal.parse(stDate);

            if (new Date().before(newDate)) {
                //Current date is small then specify date
//                Log.e("Pleez", "Answer of date : true");
                status = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            status = false;
        }

        return status;
    }

    public static long getTimeToMilisecond(String stDate) {
        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = format.parse(stDate);

            Date dateTime = AppGlobal.getDate(date);
            return dateTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static Date getItemDate(String stDate) {
        Date dateTime = null;
        try {
            Calendar cal = Calendar.getInstance();
            TimeZone tz = cal.getTimeZone();

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
//            format.setTimeZone(tz);
            Date date = format.parse(stDate);

            dateTime = AppGlobal.getDate(date);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static int getAlarmId() {
        Random rnd = new Random();
        int n = 100000 + rnd.nextInt(900000);
        return n;
    }


    public static boolean isTextAvailable(String text) {
        return !(text == null || text.equals("") || text.equals("null"));
    }

    public static String isTextAvailableWithData(String text, String returnBlankTag) {
        if (text == null || text.equals("") || text.equals("null")) {
            return returnBlankTag;
        } else {
            return text;
        }
    }

    public static void logoutApp(Activity mainActivity) {

        if (AppGlobal.getStringPreference(mainActivity, WsConstant.SP_REMEMBER).equalsIgnoreCase("0")) {
            AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_PHONE);
        }

        ArrayList<String> listArray = new ArrayList<>();
        AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_TOKEN);
        AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_ID);
        AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_NAME);
        AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_EMAIL);
        AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_ADDRESS);
        AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_LICENCE_PLAT);
        AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_MOBILE);
        AppGlobal.setStringPreference(mainActivity, "", WsConstant.SP_COUNTRY_CODE);
        AppGlobal.setArrayListPreference(mainActivity, listArray, WsConstant.SP_IMAGES);

        Intent intent = new Intent(mainActivity, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mainActivity.startActivity(intent);
        mainActivity.finish();
    }

    public static void showLog(Context mContext, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(mContext.getResources().getString(R.string.app_name), mContext.getClass().getSimpleName() + " :: " + msg);
        }
    }

    public static String getDateFromServer(String localDate) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date date = inputFormat.parse(localDate);
            SimpleDateFormat outPutTime = new SimpleDateFormat("MMM dd, yyyy");
            return outPutTime.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getTimeAgoFromDate(String givenDateString) {
        if (givenDateString.equalsIgnoreCase("")) {
            return "";
        }

        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {

            Date mDate = sdf.parse(givenDateString);
            timeInMilliseconds = mDate.getTime();
            System.out.println("Date in milli :: " + timeInMilliseconds);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //API.log("Day Ago "+dayago);
        String result = "now";
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        // SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String todayDate = formatter.format(new Date());
        Calendar calendar = Calendar.getInstance();

        long dayagolong = timeInMilliseconds;
        calendar.setTimeInMillis(dayagolong);
        String agoformater = formatter.format(calendar.getTime());

        Date CurrentDate = null;
        Date CreateDate = null;

        try {
            CurrentDate = formatter.parse(todayDate);
            CreateDate = formatter.parse(agoformater);

            long different = Math.abs(CurrentDate.getTime() - CreateDate.getTime());

            long secondsInMilli = 1000;
            long minutesInMilli = secondsInMilli * 60;
            long hoursInMilli = minutesInMilli * 60;
            long daysInMilli = hoursInMilli * 24;

            long elapsedDays = different / daysInMilli;
            different = different % daysInMilli;

            long elapsedHours = different / hoursInMilli;
            different = different % hoursInMilli;

            long elapsedMinutes = different / minutesInMilli;
            different = different % minutesInMilli;

            long elapsedSeconds = different / secondsInMilli;

            different = different % secondsInMilli;
            if (elapsedDays == 0) {
                if (elapsedHours == 0) {
                    if (elapsedMinutes == 0) {
                        if (elapsedSeconds < 0) {
                            return "0" + " s";
                        } else {
                            if (elapsedDays > 0 && elapsedSeconds < 59) {
                                return "now";
                            }
                        }
                    } else {
                        return String.valueOf(elapsedMinutes) + "mins ago.";
                    }
                } else {
                    return String.valueOf(elapsedHours) + "hr ago.";
                }

            } else {
                if (elapsedDays <= 29) {
                    return String.valueOf(elapsedDays) + "d ago.";
//                    return CommonUtils.convertDateFormat(givenDateString);
                } else {
                    SimpleDateFormat formatterYear = new SimpleDateFormat("MMM yy");
                    Calendar calendarYear = Calendar.getInstance();
                    calendarYear.setTimeInMillis(dayagolong);
                    return formatterYear.format(calendarYear.getTime()) + "";
                }
               /* if (elapsedDays > 29 && elapsedDays <= 58) {
                    return "1Mth ago";
                }
                if (elapsedDays > 58 && elapsedDays <= 87) {
                    return "2Mth ago";
                }
                if (elapsedDays > 87 && elapsedDays <= 116) {
                    return "3Mth ago";
                }
                if (elapsedDays > 116 && elapsedDays <= 145) {
                    return "4Mth ago";
                }
                if (elapsedDays > 145 && elapsedDays <= 174) {
                    return "5Mth ago";
                }
                if (elapsedDays > 174 && elapsedDays <= 203) {
                    return "6Mth ago";
                }
                if (elapsedDays > 203 && elapsedDays <= 232) {
                    return "7Mth ago";
                }
                if (elapsedDays > 232 && elapsedDays <= 261) {
                    return "8Mth ago";
                }
                if (elapsedDays > 261 && elapsedDays <= 290) {
                    return "9Mth ago";
                }
                if (elapsedDays > 290 && elapsedDays <= 319) {
                    return "10Mth ago";
                }
                if (elapsedDays > 319 && elapsedDays <= 348) {
                    return "11Mth ago";
                }
                if (elapsedDays > 348 && elapsedDays <= 360) {
                    return "12Mth ago";
                }

                if (elapsedDays > 360 && elapsedDays <= 720) {
                    return "1 year ago";
                }*/


            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void loadUserImage(Context mContext, String path, ImageView imImage) {
        Picasso.with(mContext)
                .load(path)
                .placeholder(R.drawable.icon_user_placeholder)
                .error(R.drawable.icon_user_placeholder)
                .skipMemoryCache()
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .into(imImage);
    }

    public static void loadImage(Context mContext, String path, ImageView imImage) {
        Picasso.with(mContext)
                .load(path)
                .into(imImage);
    }

    public static void loadImageFile(Context mContext, String path, ImageView imImage) {
        File file = new File(path);
        Picasso.with(mContext)
                .load(file)
                .into(imImage);
    }



    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static String getRealPathFromURI(Context mContext, Uri uri) {
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public static void openUserBannerAndDP(Context mContext, String url, boolean isFile) {
        Intent intent = new Intent(mContext, PictureActivity.class);
        intent.putExtra("mediaURL", url);
        intent.putExtra("isFile", isFile);
        mContext.startActivity(intent);
    }
}