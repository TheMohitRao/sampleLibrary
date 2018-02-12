package com.mobileoid2.samplelibrary;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.view.Surface;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressLint("SimpleDateFormat")
public enum AppUtils {
    instance;




    public String stringValidator(String value) {
        if (value == null) value = "";
        return value;
    }

    public Integer integerValidator(Integer value) {
        if (value == null) value = 0;
        return value;
    }


    public void hideKeyboard(Activity context) {
        // Check if no view has focus:
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    // shows a Toast message.
    public  void showToast(String message) {

        if (AppConstants.showToasts) {
            Toast.makeText(AppLevelConstraints.getAppContext(),
                    message, Toast.LENGTH_SHORT).show();
        }
    }


    public boolean isLandscape(Context context) {
        final int rotation = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getOrientation();
        switch (rotation) {
            case Surface.ROTATION_0:
                return false;
            case Surface.ROTATION_90:
                return true;
            case Surface.ROTATION_180:
                return false;
            default:
                return true;
        }
    }


	/*
       DATE AND TIME FORMAT FOR SimpleDateFormat()
	   "yyyy.MM.dd G 'at' HH:mm:ss z" ---- 2001.07.04 AD at 12:08:56 PDT
	   "hh 'o''clock' a, zzzz" ----------- 12 o'clock PM, Pacific Daylight Time
	   "EEE, d MMM yyyy HH:mm:ss Z" ------ Wed, 4 Jul 2001 12:08:56 -0700
	   "yyyy-MM-dd'T'HH:mm:ss.SSSZ" ------ 2001-07-04T12:08:56.235-0700
	   "yyMMddHHmmssZ" ------------------- 010704120856-0700
	   "K:mm a, z" ----------------------- 0:08 PM, PDT
	   "h:mm a" -------------------------- 12:08 PM
	   "EEE, MMM d, ''yy" ---------------- Wed, Jul 4, '01
	 */


    public final String parseDate(long dd) {
        String result = "";
        try {
            DateFormat newDateFormat = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a", Locale.US);
            result = newDateFormat.format(dd);
        } catch (Exception e) {

        }
        return result;
    }

    public final String parseDateForNews(long dd) {
        String result = "";
        try {
            DateFormat newDateFormat = new SimpleDateFormat("h:mm a, d MMM yyyy", Locale.US);
            result = newDateFormat.format(dd);
        } catch (Exception e) {

        }
        return result;
    }

    // used in weather API
    public String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mmm dd, yyyy",
                Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    // TO CHECK INTERNET CONNECTION
    public  boolean checkInternetConnection(Context mContext) {

        if(true)
        return true;
        ConnectivityManager cm = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
            // //Log.v("TEST", "Internet Connection Not Present");

            return false;
        }

    }


    public  String getSyncDateTime(Date fetchDate) {
        try {

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            //Date date = df.parse(fetchDate);
            return df.format(fetchDate);
        } catch (Exception ex) {
            return null;
        }
    }


    public String getExceptionString(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }

    public Date getGetCurrentDate() {
        return new Date();
    }

    public Bitmap getCircularBitmap(Bitmap bitmap) {
        Bitmap output;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            output = Bitmap.createBitmap(bitmap.getHeight(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        } else {
            output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getWidth(), Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        float r = 0;

        if (bitmap.getWidth() > bitmap.getHeight()) {
            r = bitmap.getHeight() / 2;
        } else {
            r = bitmap.getWidth() / 2;
        }

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.WHITE);
        canvas.drawCircle(r, r, r, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);


        //  if (output.getWidth() > 500)
        //    output = Bitmap.createScaledBitmap(output, output.getWidth() / 3, output.getWidth() / 3, false);


        if (output.getWidth() > AppSharedPref.instance.getScreenWidth() * 2)
            output = Bitmap.createScaledBitmap(output, output.getWidth() / 3, output.getWidth() / 3, false);
        else
            output = Bitmap.createScaledBitmap(output, output.getWidth() / 2, output.getWidth() / 2, false);
        return output;
    }

}
