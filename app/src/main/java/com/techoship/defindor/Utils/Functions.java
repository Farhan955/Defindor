package com.techoship.defindor.Utils;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION.SDK_INT;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.format.DateFormat;
import android.text.style.BackgroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.TextAppearanceSpan;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.DrawableRes;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.techoship.defindor.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Functions {

    /*private static FirebaseAuth mAuth;
    private static FirebaseUser user;
    private static DatabaseReference myRef;
    private static String currentUserId;*/

    public static Dialog loadingDialog;
    public static Dialog progressDialog;

    public static int currentApiVersion;

    public static void hideKeyboard(Context context) {
        Activity activity = (Activity) context;
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();

        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void setStatusBarTransparent(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            View decorView = window.getDecorView();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            } else {
                decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            }
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }


    public static void requestLocationTurnOn(Context context) {
        //check if gps is enabled or not and then request user to enable it
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);

        SettingsClient settingsClient = LocationServices.getSettingsClient(context);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnFailureListener((Activity) context, e -> {
            if (e instanceof ResolvableApiException) {
                ResolvableApiException resolvable = (ResolvableApiException) e;
                try {
                    resolvable.startResolutionForResult((Activity) context, 51);
                } catch (IntentSender.SendIntentException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void fullScreenWithNav(Context context) {
        Activity activity = (Activity) context;
        Window window = activity.getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
    }

    public static void changeNavigationBarColor(Context context, int color) {
        Activity activity = (Activity) context;
        activity.getWindow().setNavigationBarColor(activity.getResources().getColor(color));
    }

    public static boolean isNetworkConnectionAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnected();
        if (isConnected) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isWifiConnected(Context context) {
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (mWifi.isConnected()) {
            return true;
        }
        return false;
    }

    public static void showSnackBar(Context context, String message) {
        Activity activity = (Activity) context;
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .setAction("Ok", view -> {
                }).setActionTextColor(context.getResources().getColor(android.R.color.holo_red_light)).show();
    }



/*
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }*/

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void hideKeyboard(Context context, View view) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void hideSystemUI(Context context) {
        Activity activity = (Activity) context;
        activity.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        if (SDK_INT >= Build.VERSION_CODES.P) {
            activity.getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
    }

    public static String getFormattedDate(long smsTimeInMilis) {
        Calendar smsTime = Calendar.getInstance();
        smsTime.setTimeInMillis(smsTimeInMilis);

        Calendar now = Calendar.getInstance();

        final String timeFormatString = "h:mm aa";
        final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
        final long HOURS = 60 * 60 * 60;
        if (now.get(Calendar.DATE) == smsTime.get(Calendar.DATE)) {
            return "Today " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.DATE) - smsTime.get(Calendar.DATE) == 1) {
            return "Yesterday " + DateFormat.format(timeFormatString, smsTime);
        } else if (now.get(Calendar.YEAR) == smsTime.get(Calendar.YEAR)) {
            return DateFormat.format(dateTimeFormatString, smsTime).toString();
        } else {
            return DateFormat.format("MMMM dd yyyy, h:mm aa", smsTime).toString();
        }
    }

    public static String changeDateFormat(String date) {

        SimpleDateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);
        originalFormat.setTimeZone(TimeZone.getDefault());

        java.text.DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date1 != null;
        return targetFormat.format(date1);
    }

    public static String millisToTime(int millis) {
        // New date object from millis
        Date date = new Date(millis);
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        String time = formatter.format(date);
        return time;
    }

    public static String getDurationBreakdown(int millis) {
        if (millis < 0) {
            throw new IllegalArgumentException("Duration must be greater than zero!");
        }

        long days = TimeUnit.MILLISECONDS.toDays(millis);
        millis -= TimeUnit.DAYS.toMillis(days);
        long hours = TimeUnit.MILLISECONDS.toHours(millis);
        millis -= TimeUnit.HOURS.toMillis(hours);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        millis -= TimeUnit.MINUTES.toMillis(minutes);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);

        StringBuilder sb = new StringBuilder(64);
        sb.append(days);
        sb.append(" Days ");
        sb.append(hours);
        sb.append(" Hours ");
        sb.append(minutes);
        sb.append(" Minutes ");
        sb.append(seconds);
        sb.append(" Seconds");

        return (sb.toString());
    }

    public static String getCurrentDateTime() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }


    public static String getCurrentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);
        df.setTimeZone(TimeZone.getDefault());
        return df.format(c);
    }


    public static Date getCurrentDateTimeX() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(c);

        Date d = null;
        try {
            d = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return d;
    }

    public static Date getYesterdayDateTimeX() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, (cal.get(Calendar.DAY_OF_MONTH) - 1));

        Date c = cal.getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(c);

        Date d = null;
        try {
            d = df.parse(formattedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return d;
    }


    public static String getCurentDate() {
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        df.setTimeZone(TimeZone.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

/*
    public Long dateToMillis(String date) {
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
        Date d1 = null;
        try {
            d1 = myFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long l = d1.getTime();
        return 0l;
    }
*/

    public static String getTimeAgo(String time) {
        String enddate = getCurrentDateTime();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
        Date startDate = null;   // initialize start date
        try {
            startDate = myFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null; // initialize  end date
        try {
            endDate = myFormat.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endDate.getTime() - startDate.getTime();

        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);


        if (days > 365) {
            return ChangeDateFormate2(time);
        }

        if (days > 1) {
            return ChangeDateFormate(time);
        }
        if (days == 1) {
            return "1 day ago";
        }

        if (days == 0) {
            if (hours > 1) {
                return hours + " hours ago";
            }
            if (hours == 1) {
                return "1 hour ago";
            }

            if (min > 1) {
                return min + " minutes ago";
            }
            if (min == 1) {
                return "1 minute ago";
            }
        }

        return "now";

    }


    public static String getTimeAgoX(Date time) {
        String enddate = getCurrentDateTime();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
        Date startDate = null;   // initialize start date
        startDate = time;

        Date endDate = null; // initialize  end date
        try {
            endDate = myFormat.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endDate.getTime() - startDate.getTime();

        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);

        if (days > 365) {
            return ChangeDateFormate2X(time);
        }

        if (days > 1) {
            return ChangeDateFormateX(time);
        }
        if (days == 1) {
            return "1 day ago";
        }

        if (days == 0) {
            if (hours > 1) {
                return hours + " hours ago";
            }
            if (hours == 1) {
                return "1 hour ago";
            }

            if (min > 1) {
                return min + " minutes ago";
            }
            if (min == 1) {
                return "1 minute ago";
            }
        }

        return "now";
    }

    public static String ChangeDateFormate(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("MMM dd");
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String ChangeDateFormateX(Date date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("MMM dd");
        Date date1 = null;
        date1 = date;

        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String ChangeDateFormate2(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String ChangeDateFormate2X(Date date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("MMM dd, yyyy");
        Date date1 = null;
        date1 = date;

        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String ChangeDateFormate3(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("dd MMM , yyyy");
        targetFormat.setTimeZone(TimeZone.getDefault());
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }


    public static String changeDateFormate4(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("E, d MMM h:mm a");
        targetFormat.setTimeZone(TimeZone.getDefault());
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String changeDateFormate5(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd-m-yyyy", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("E, d MMM");
        targetFormat.setTimeZone(TimeZone.getDefault());
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }

    public static String changeDateFormate6(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("H : mm", Locale.ENGLISH);
        java.text.DateFormat targetFormat = new SimpleDateFormat("h:mm a");
        targetFormat.setTimeZone(TimeZone.getDefault());
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = targetFormat.format(date1);
        return formattedDate;
    }


    public static String latestMsgTimeDiff(String time) {
        long tim = Long.valueOf(time);
        long curentTime = System.currentTimeMillis();
        long diffrence = curentTime - tim;
        long seconds = diffrence / 1000;
        // seconds = seconds % 60;
        return String.valueOf(seconds);
    }

    public static void setLocale(Activity activity, String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config, resources.getDisplayMetrics());
    }


    public static void loadingDialog(Context context, Boolean show) {


        if (show) {
            loadingDialog = new Dialog(context);
            loadingDialog.setContentView(R.layout.loading_dialog);
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadingDialog.setCancelable(false);
            loadingDialog.show();
        } else {

            if (loadingDialog != null)
                loadingDialog.dismiss();
        }
    }

    public static void progressDialog(Context context, Boolean show) {


        if (show) {
            loadingDialog = new Dialog(context);
            loadingDialog.setContentView(R.layout.progress_dialog);
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadingDialog.setCancelable(false);
            loadingDialog.show();
        } else {

            if (loadingDialog != null)
                loadingDialog.dismiss();
        }
    }

    public static void setProgress(String progress) {

        if (loadingDialog != null) {
            TextView tv = loadingDialog.findViewById(R.id.tv_progress);
            tv.setText(progress);
        }
    }

    /*public static void uploadToken(Context context, String token) {
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            currentUserId = user.getUid();

            // upload fcm token to signup node
            myRef = FirebaseDatabase.getInstance().getReference().child(KEY_REF_SIGNUP);
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("fcmToken", token);
            myRef.child(currentUserId).updateChildren(hashMap);

            // also add fcm token to profile node
            myRef = FirebaseDatabase.getInstance().getReference().child(KEY_REF_PROFILE);
            HashMap<String, Object> hashMap1 = new HashMap<>();
            hashMap1.put("fcmToken", token);
            myRef.child(currentUserId).updateChildren(hashMap1);

            SharedPref sharedPref = new SharedPref(context);
            sharedPref.save(KEY_FCM_TOKEN, token);
        }
    }

    public static String formatNumber(Context context, String number, String countryCode) {
        PhoneNumberUtil util = PhoneNumberUtil.createInstance(context);
        Phonenumber.PhoneNumber phoneNumber;
        String phone = number;

        try {
            phoneNumber = util.parse(number, countryCode);
            phone = util.format(phoneNumber, PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL);
        } catch (NumberParseException e) {
            e.printStackTrace();
        }

        //remove empty spaces and dashes and ()
        if (phone != null) phone = phone
                .replace(" ", "")
                .replace("-", "")
                .replace("\\(", "")
                .replace("\\)", "");

        return phone;
    }*/


    public static long dateToMillis(String date) throws ParseException {


        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);

        formatter.setLenient(false);

        Date date1 = formatter.parse(date);
        return date1.getTime();
    }



    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }


    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static String getRandomNumberString() {
        // It will generate 6 digit random Number.
        // from 0 to 999999
        Random rnd = new Random();
        int number = rnd.nextInt(9999);
        String obj = String.format("%04d", number);

        if (obj.length() == 1) {
            obj = obj + "632";
        } else if (obj.length() == 2) {
            obj = obj + "42";
        } else if (obj.length() == 3) {
            obj = obj + "9";
        }
        return obj;
    }

    public static String TimeManger(String time) {
        String enddate = getCurentDate();
        SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
        Date startDate = null;   // initialize start date
        try {
            startDate = myFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = null; // initialize  end date
        try {
            endDate = myFormat.parse(enddate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = endDate.getTime() - startDate.getTime();

        int days = (int) (difference / (1000 * 60 * 60 * 24));
        int hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
        int min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
        hours = (hours < 0 ? -hours : hours);
        if (min < 1) {
            return "now";
        }
        if (hours < 1) {
            return min + " minuts ago";
        }
        if (days < 1) {
            return hours + " hours ago";
        }
        if (days < 365) {
            return ChangeDateFormate(time);
        }
        return ChangeDateFormate2(time);

    }


    public static boolean isValidPhone(CharSequence target) {
        if (target.length() < 11)
            return false;
        else return true;
    }

/*
    public static void MakeMsgRead(String hiz_Key, Context context, String msg_key) {
        SharedPref sharedPref = new SharedPref(context);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(hiz_Key).child("chat").child(sharedPref.get(Constants.UID)).child(msg_key).child("read").setValue("1");
    }
*/


    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public static String meterToFeet(double meters) {

//        String ft=+"";
        return String.format("%.0f", meters * 3.28084);
    }

    public static int feetToMeter(double feet) {

        double d = feet / 3.28084;

        return (int) d;
    }

    public static boolean checkPermissions(Context context) {
        if (SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                return true;
            }
        } else {
            return true;
        }
        return false;
    }


    public static void requestPermissions(Context context, int requesrCode) {
        Activity activity = (Activity) context;
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                },
                requesrCode);
    }


//    ----------------------Kashif Shb -------------------------------------------------------


    public static void hideStatusAndNavigationBar(Activity activity) {

        if (SDK_INT >= Build.VERSION_CODES.P) {
            activity.getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            currentApiVersion = SDK_INT;
            final int flags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

            if (currentApiVersion >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
                final View decorView = activity.getWindow().getDecorView();
                decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
                    @Override
                    public void onSystemUiVisibilityChange(int visibility) {
                        if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                            decorView.setSystemUiVisibility(flags);
                        }
                    }
                });
            }
        }
    }


    public static void transparentStatusAndNavigation(Activity activity) {
        //make full transparent statusBar
        if (SDK_INT >= 19 && SDK_INT < 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, true, activity);
        }
        if (SDK_INT >= 19) {
            activity.getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

            );
        }
        if (SDK_INT >= 21) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, false, activity);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
    }

    public static void setWindowFlag(final int bits, boolean on, Activity activity) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    public static boolean isUpcoming(String d1) throws ParseException {


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);
        sdf.setTimeZone(TimeZone.getDefault());

        Date bookingDate = sdf.parse(d1);


        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        Date currentDate = calendar.getTime();


        if (bookingDate.after(currentDate)) {
            return true;
        }
        return false;



     /*   SimpleDateFormat formatter = new SimpleDateFormat("E d MMM");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date currentDate = new Date(System.currentTimeMillis());
        String d2 = formatter.format(currentDate);


        //Fri 17 Sep
        Date startDate = (Date) formatter.parse(d1);

        Date today=formatter.parse(d2);

        if (startDate.before(today)) {
            return true;
        }

        return true;*/

    }




    public static String getTime(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") java.text.DateFormat targetFormat = new SimpleDateFormat("hh:mm a");
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date1 != null;
        return targetFormat.format(date1);
    }


    public static boolean checkPermission(Context context) {
        if (SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        } else {
            int result = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE);
            int result1 = ContextCompat.checkSelfPermission(context, WRITE_EXTERNAL_STORAGE);
            return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static void openFile(Context context, File aFile) throws IOException {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        try {

            Intent myIntent = new Intent(Intent.ACTION_VIEW);
            File file = new File(aFile.getAbsolutePath());
            String extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(file).toString());
            String mimetype = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
            myIntent.setDataAndType(Uri.fromFile(file), mimetype);
            context.startActivity(myIntent);

        } catch (Exception e) {
            String data = e.getMessage();
            Toast.makeText(context, "No app found to open file: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    public static void openPDF(Context context, String url) {
        Intent intent = new Intent();
        intent.setDataAndType(Uri.parse(url), "application/pdf");
        context.startActivity(intent);
    }

    public static String getMimeType(Context context, Uri uri) {
        String extension;

        if (uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            extension = mime.getExtensionFromMimeType(context.getContentResolver().getType(uri));
        } else {
            extension = MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(new File(uri.getPath())).toString());
        }

        return extension;
    }



    public static void setDialogGravity(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.y = 190;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
    }


    public static void pickImage(Context context) {
        ImagePicker.Companion.with((Activity) context).crop().compress(1024).maxResultSize(1080, 1080).start();
    }

    public static void openCamera(Context context) {
        ImagePicker.Companion.with((Activity) context).crop().compress(1024).maxResultSize(1080, 1080).cameraOnly().start();
    }




    public static String getFileNameFromURL(String url) {
        if (url == null) {
            return "";
        }
        try {
            URL resource = new URL(url);
            String host = resource.getHost();
            if (host.length() > 0 && url.endsWith(host)) {
                // handle ...example.com
                return "";
            }
        } catch (MalformedURLException e) {
            return "";
        }

        int startIndex = url.lastIndexOf('/') + 1;
        int length = url.length();

        int lastQMPos = url.lastIndexOf('?');
        if (lastQMPos == -1) {
            lastQMPos = length;
        }

        int lastHashPos = url.lastIndexOf('#');
        if (lastHashPos == -1) {
            lastHashPos = length;
        }

        int endIndex = Math.min(lastQMPos, lastHashPos);
        return url.substring(startIndex, endIndex);
    }


    public static String getStringSizeLengthFile(long size) {
        DecimalFormat df = new DecimalFormat("0.00");

        float sizeKb = 1024.0f;
        float sizeMb = sizeKb * sizeKb;
        float sizeGb = sizeMb * sizeKb;
        float sizeTerra = sizeGb * sizeKb;


        if (size < sizeMb)
            return df.format(size / sizeKb) + " Kb";
        else if (size < sizeGb)
            return df.format(size / sizeMb) + " Mb";
        else if (size < sizeTerra)
            return df.format(size / sizeGb) + " Gb";

        return "";
    }

    public static long checkTimeDifference(String time1, String time2, String format1) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat(format1);

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = format.parse(time1);
            d2 = format.parse(time2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        assert d2 != null;
        assert d1 != null;

        long diff = d2.getTime() - d1.getTime();
        long diffSeconds = diff / 1000;
        long diffMinutes = diff / (60 * 1000);
        long diffHours = diff / (60 * 60 * 1000);

        return TimeUnit.MILLISECONDS.toSeconds(diff);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public static long getFileSize(Context context, Uri fileUri) {
        @SuppressLint("Recycle") Cursor returnCursor = context.getContentResolver().
                query(fileUri, null, null, null, null);
        int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();

        return returnCursor.getLong(sizeIndex);
    }

    public static long getFileSize2(Uri fileUri) {
        File f = new File(fileUri.getPath());
        return f.length();
    }

    public static long getDateDiff(String newDate) {
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);

        Date c = Calendar.getInstance().getTime();
        String currentDate = format.format(c);

        try {
            return TimeUnit.DAYS.convert(Objects.requireNonNull(format.parse(newDate)).getTime() - Objects.requireNonNull(format.parse(currentDate)).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static long getDateDiff2(Date newDate) {
//        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.US);

        Date c = Calendar.getInstance().getTime();
        String currentDate = format.format(c);

        try {
            return TimeUnit.DAYS.convert(Objects.requireNonNull(newDate).getTime() - Objects.requireNonNull(format.parse(currentDate)).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
/*
    public static void chooseCurrentLocation(Context context) {
        Intent i = new Intent(context, MapsActivity.class);
        ((Activity) context).startActivityForResult(i, Const.OPEN_MAP_ACTIVITY);
    }*/



    public static String getFilePath(Context context, Uri uri) {
        final String[] projection = {
                MediaStore.MediaColumns.DISPLAY_NAME
        };

        try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null,
                null)) {
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                return cursor.getString(index);
            }
        }
        return null;
    }

    public static void setSearchTextHighlight2(TextView textView, String fullText, String searchText) {
        if (null != searchText && !searchText.isEmpty()) {

            SpannableStringBuilder wordSpan = new SpannableStringBuilder(fullText);
            Pattern p = Pattern.compile(searchText, Pattern.CASE_INSENSITIVE);
            Matcher m = p.matcher(fullText);
            while (m.find()) {

                int wordStart = m.start();
                int wordEnd = m.end();

                // Now highlight based on the word boundaries
                ColorStateList redColor = new ColorStateList(new int[][]{new int[]{}}, new int[]{0xFF000000});
                TextAppearanceSpan highlightSpan = new TextAppearanceSpan(null, Typeface.BOLD, -1, redColor, null);

                wordSpan.setSpan(highlightSpan, wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordSpan.setSpan(new BackgroundColorSpan(0x2584DB), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                wordSpan.setSpan(new RelativeSizeSpan(1.0f), wordStart, wordEnd, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            textView.setText(wordSpan, TextView.BufferType.SPANNABLE);

        } else {
            textView.setText(fullText);
        }
    }

    public static String getDateOnly() {
        return new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault()).format(new Date());
    }


    public static String getDayName(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") java.text.DateFormat targetFormat = new SimpleDateFormat("E");
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date1 != null;
        return targetFormat.format(date1);
    }

    public static String getDate(String date) {
        java.text.DateFormat originalFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z", Locale.ENGLISH);
        @SuppressLint("SimpleDateFormat") java.text.DateFormat targetFormat = new SimpleDateFormat("dd MMM, yyyy ");
        Date date1 = null;
        try {
            date1 = originalFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        assert date1 != null;
        return targetFormat.format(date1);
    }




    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }
}
