package com.onlineparkingticket.constant;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("All")
public class CommonUtils {

    public static ProgressDialog progressDialog;

    public static void hideSoftKeyboard(AppCompatActivity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void forceHideKeyboard(AppCompatActivity activity, EditText editText) {
        try {
            if (activity.getCurrentFocus() == null || !(activity.getCurrentFocus() instanceof EditText)) {
                editText.requestFocus();
            }
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setSingleImageUser(Context mContext, String path, ImageView imImage) {
        /*Picasso.with(mContext)
                .load(path)
                .placeholder(R.drawable.image_user)
                .error(R.drawable.image_user)
                .into(imImage);*/
    }

    public static void OpenHideKeyboard(AppCompatActivity activity, EditText editText) {
        try {
            if (activity.getCurrentFocus() == null || !(activity.getCurrentFocus() instanceof EditText)) {
                editText.requestFocus();
            }

            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
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

 /*   public static long getTimeToMilisecond(String stDate) {
        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = format.parse(stDate);

            Date dateTime = AppGlobal.getDate(date);
            return dateTime.getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }*/

    public static String getServerTimeFormatDate(String localDate) {

        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = inputFormat.parse(localDate);

            SimpleDateFormat outPutTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            outPutTime.setTimeZone(TimeZone.getTimeZone("UTC"));

            return outPutTime.format(date);
        } catch (Exception e) {

        }
        return "";
    }

    public static String convertLocalTimeToUTC(String p_localDateTime) throws Exception {

        String lv_dateFormateInUTC = "";//Will hold the final converted date
        Date lv_localDate = null;
        String lv_localTimeZone = "";
        SimpleDateFormat lv_formatter;
        SimpleDateFormat lv_parser;
        lv_parser = new SimpleDateFormat("HH:mm");
        lv_parser.setTimeZone(TimeZone.getTimeZone(lv_localTimeZone));
        lv_localDate = lv_parser.parse(p_localDateTime);

//Set output format prints "2007/10/25  18:35:07 EDT(-0400)"
        lv_formatter = new SimpleDateFormat("'T'HH:mm:ss.SSS'Z'");
        lv_formatter.setTimeZone(TimeZone.getTimeZone(lv_localTimeZone));

        System.out.println("convertLocalTimeToUTC: " + ": " + " The Date in the local time zone " + lv_formatter.format(lv_localDate));

//Convert the date from the local timezone to UTC timezone
        lv_formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        lv_dateFormateInUTC = lv_formatter.format(lv_localDate);
        System.out.println("convertLocalTimeToUTC: " + ": " + " The Date in the UTC time zone " + lv_dateFormateInUTC);


        return lv_dateFormateInUTC;
    }

    public static void showNoInternetSnackBar(View layout, View.OnClickListener clickListener) {
        Snackbar snackbar = Snackbar
                .make(layout, "No internet connection!", Snackbar.LENGTH_SHORT)
                .setAction("RETRY", clickListener);
        snackbar.setActionTextColor(Color.RED);
        snackbar.show();
    }

    public static boolean isEmailValid(String email) {
        Matcher matcher = null;
        try {
            Pattern pattern;
            final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matcher.matches();
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }

    public static boolean isPasswordValid(String psw) {
        Matcher matcher = null;
        try {
            Pattern pattern;
            final String PSW_PATTERN = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}$";
            pattern = Pattern.compile(PSW_PATTERN);
            matcher = pattern.matcher(psw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return matcher.matches();
    }


    public static boolean isTextAvailable(String text) {
        return !(text == null || text.equals("") || text.equals("null"));
    }

    public static String isTextAvailableWithReturnData(String text, String returnData) {
        if (text == null || text.equals("") || text.equals("null")) {
            return returnData;
        } else {
            return text;
        }
    }

    public static void commonToast(Context activity, String text) {
        if (CommonUtils.isTextAvailable(text)) {
            Toast.makeText(activity, text, Toast.LENGTH_SHORT).show();
        }
    }

    public static String gettime(String date) {

        SimpleDateFormat spf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date newDate = null;
        try {
            newDate = spf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat spf_date = new SimpleDateFormat("dd MMM");
        SimpleDateFormat spf_time = new SimpleDateFormat("hh:mm a");
        String newDateString = spf_date.format(newDate);
        String newTimeString = spf_time.format(newDate);

        return newTimeString;
    }

    public static void SetText(Activity activity, String text, TextView textView) {
        textView.setText(text);
    }

    public static void showDialog(Context context, String msg) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(msg);
        progressDialog.setCanceledOnTouchOutside(false);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    // Dismiss Progrss Dialog
    public static void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }


    public static String convertDateTime(String fromFormat, String toFormat, String dateOriginalGot) {

        try {
            //SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            //// Getting Source format here
            SimpleDateFormat fmt = new SimpleDateFormat(fromFormat);

            fmt.setTimeZone(TimeZone.getDefault());

            Date date = fmt.parse(dateOriginalGot);

            //SimpleDateFormat fmtOut = new SimpleDateFormat("EEE, MMM d, ''yyyy");

            //// Setting Destination format here
            SimpleDateFormat fmtOut = new SimpleDateFormat(toFormat);

            return fmtOut.format(date);

        } catch (Exception e) {

            e.printStackTrace();

            e.getMessage();

        }

        return "";

    }

    // for  Url is valid or not
    public static boolean isValidUrl(String string) {

        boolean b = Patterns.WEB_URL.matcher(string).matches();
        return b;
    }

    public static Date convertStringToDate(String date) {
        SimpleDateFormat f = new SimpleDateFormat("dd MMM yyyy hh:mm a");
        Date d = null;
        try {
            d = f.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }

    //
    public static void finishFragmentOrActivity(View v, Activity activity, FragmentManager fragmentManager) {

        if (fragmentManager.getBackStackEntryCount() > 0) {
            fragmentManager.popBackStack();
        } else {
            activity.finish();
        }
    }

    public static String roundToOneDigit(float paramFloat) {
        return String.format("%.2f%n", paramFloat);
    }

    public static String roundToTwoDigit(Double paramDouble) {
        DecimalFormat df = new DecimalFormat("#.00");

        String strValue = df.format(paramDouble);

        // String doubleValue= String.valueOf(Double.valueOf(strValue));

        Log.e("ad", "roundToTwoDigit =" + strValue);

        return strValue;
    }

    public static String encodeFileBinary(String uploadFilePath) {
        String encodedString = "0";
        if (uploadFilePath.length() < 2)
            return encodedString;
        try {
            InputStream inputStream = new FileInputStream(uploadFilePath);//You can get an inputStream using any IO API
            byte[] bytes;
            byte[] buffer = new byte[8192];
            int bytesRead;
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            try {
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            bytes = output.toByteArray();
            encodedString = Base64.encodeToString(bytes, Base64.DEFAULT);

        } catch (IOException es) {

        }
        return encodedString;
    }

    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap pathTOBitmap(String path) {
        File imgFile = new File(path);

        if (imgFile.exists()) {
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            return myBitmap;
        }
        return null;
    }

    public static String convertDateFormat(String time) {
        //2017-09-22
        String mTime = "";

        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            final Date dateObj = sdf.parse(time);
            System.out.println(dateObj);
            mTime = new SimpleDateFormat("dd MMM").format(dateObj);


        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return mTime;
    }

    public static String convertTimeFormat(String time) {
        //2017-09-22 10:50:59
        String mTime = "";
        String mTimeTwo = "";
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            final Date dateObj = sdf.parse(time);
            System.out.println(dateObj);
            mTime = new SimpleDateFormat("hh:mm a").format(dateObj);

            // mTimeTwo=mTime.replace("AM", "am").replace("PM","pm");
            //oldstr.replace("AM", "am").replace("PM","pm");

        } catch (final ParseException e) {
            e.printStackTrace();
        }
        return mTimeTwo;
    }

    public static String utcToLocalTime(String server_date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

        SimpleDateFormat formatted_df = new SimpleDateFormat("HH:mm");

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        String formattedDate = "";
        try {
            date = df.parse(server_date);
            df.setTimeZone(TimeZone.getDefault());
            formattedDate = formatted_df.format(date);
            Log.e("Local Time : ", formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String utcToLocalDate(String server_date) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        SimpleDateFormat formatted_df = new SimpleDateFormat("MMM.dd");

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        String formattedDate = "";
        try {
            date = df.parse(server_date);


            df.setTimeZone(TimeZone.getDefault());
            formattedDate = formatted_df.format(date);


            Log.e("Local Time : ", formattedDate);


            //formattedDate.replace("am", "AM");
            // formattedDate.replace("pm", "PM");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    public static String utcToLocalDateTwo(String server_date) {
        // SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); //2017-11-07 10:11:08 utcCreatedDateTime

        // SimpleDateFormat formatted_df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        SimpleDateFormat formatted_df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");

        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        String formattedDate = "";
        try {
            date = df.parse(server_date);


            df.setTimeZone(TimeZone.getDefault());
            formattedDate = formatted_df.format(date);


            Log.e("Local Time : ", formattedDate);


            //formattedDate.replace("am", "AM");
            // formattedDate.replace("pm", "PM");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return formattedDate;
    }

    //02-10-2017 04:10:36 AM   04-10-2017 07:10:43 AM
//03-10-2017 07:10:43 AM

    public static String parseDate(String givenDateString) {
        if (givenDateString.equalsIgnoreCase("")) {
            return "";
        }

        long timeInMilliseconds = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
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
                        return String.valueOf(elapsedMinutes) + "mins ago";
                    }
                } else {
                    return String.valueOf(elapsedHours) + "hr ago";
                }

            } else {
                if (elapsedDays <= 29) {
                    //return String.valueOf(elapsedDays) + "d ago";
                    return CommonUtils.convertDateFormat(givenDateString);
                } else {
                    SimpleDateFormat formatterYear = new SimpleDateFormat("MM/dd/yyyy");
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
}
