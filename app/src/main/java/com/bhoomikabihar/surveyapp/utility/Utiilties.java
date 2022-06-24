package com.bhoomikabihar.surveyapp.utility;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.regex.Pattern;


public class Utiilties {




    public static void ShowMessage(Context context, String Title, String Message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(Title);
        alertDialog.setMessage(Message);
        alertDialog.show();
    }


    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() == true);
    }

    public static Bitmap GenerateThumbnail(Bitmap imageBitmap,
                                           int THUMBNAIL_HEIGHT, int THUMBNAIL_WIDTH) {

        Float width = new Float(imageBitmap.getWidth());
        Float height = new Float(imageBitmap.getHeight());
        Float ratio = width / height;
        Bitmap CompressedBitmap = Bitmap.createScaledBitmap(imageBitmap,
                (int) (THUMBNAIL_HEIGHT * ratio), THUMBNAIL_HEIGHT, false);

        return CompressedBitmap;
    }


    public static Bitmap DrawText(Activity activity, Bitmap mBitmap, String displaytext1,
                                  String displaytext2, String displaytext3, String displaytext4) {
        Bitmap bmOverlay = Bitmap.createBitmap(mBitmap.getWidth(),
                mBitmap.getHeight(), Bitmap.Config.ARGB_4444);
        // create a canvas on which to draw
        Canvas canvas = new Canvas(bmOverlay);

        Paint paint = new Paint();
        //	paint.setColor(activity.getResources().getColor(R.color.holo_red_dark));
        paint.setTextSize(40);
        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setFakeBoldText(false);
        paint.setShadowLayer(1, 0, 0, Color.BLACK);

        // if the background image is defined in main.xml, omit this line
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        canvas.drawText(displaytext1, 10, mBitmap.getHeight() - 100, paint);
        canvas.drawText(displaytext2, 10, mBitmap.getHeight() - 50, paint);

        canvas.drawText(displaytext3, 10, mBitmap.getHeight() - 150, paint);

        canvas.drawText(displaytext4, 10, mBitmap.getHeight() - 200, paint);
        // set the bitmap into the ImageView
        return bmOverlay;
    }

    public static Object deserialize(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        } catch (Exception ex) {
            return null;
        }
    }

    public static String getDateString() {
        SimpleDateFormat postFormater = new SimpleDateFormat(
                "dd MMMM, yyyy hh:mm:s a");

        String newDateStr = postFormater.format(Calendar.getInstance()
                .getTime());
        return newDateStr;


    }

    public static String getDateString(String Formats) {
        SimpleDateFormat postFormater = new SimpleDateFormat(Formats);

        String newDateStr = postFormater.format(Calendar.getInstance()
                .getTime());
        return newDateStr;
    }


    public static void setActionBarBackground(Activity activity) {
        ActionBar actionBar;
        actionBar = activity.getActionBar();
        Resources res = activity.getResources();
        //Drawable drawable = res.getDrawable(R.drawable.digitallogo2);
        //actionBar.setBackgroundDrawable(drawable);
    }


    public static void setStatusBarColor(Activity activity) {
        if (Build.VERSION.SDK_INT >= 21) {

            Window window = activity.getWindow();


            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            // finally change the color
            window.setStatusBarColor(Color.parseColor("#1565a9"));
        }
    }


    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        month = month + 1;

        int h = cal.get(Calendar.HOUR);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);

        if (month < 10) {
            String date = year + "/" + "0" + month + "/" + day;
            Log.e("getCurrentDate", date);
            return date;
        } else {
            String date = year + "/" + month + "/" + day;
            Log.e("getCurrentDate", date);
            return date;
        }


    }


    public static String getCurrentDateWithTime() throws ParseException {

        SimpleDateFormat f = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy");
        Date date = null;
        date = f.parse(getDateString());
        SimpleDateFormat formatter = new SimpleDateFormat("MMM d,yyyy HH:mm a");
        String dateString = formatter.format(date);
        return dateString;
    }

    public static String getDateTime() {

        String date = getDateString();
        String a = "";
        StringTokenizer st = new StringTokenizer(date, " ");
        while (st.hasMoreTokens()) {
            a = st.nextToken();
        }

        if (a.equals("a.m.")) {

            date = date.replace(a, "AM");
        }
        if (a.equals("p.m.")) {
            date = date.replace(a, "PM");


        }


        return date;
    }


    public static String getshowCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        month = month + 1;

        int h = cal.get(Calendar.HOUR);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);

        String date = day + "/" + month + "/" + year;
        return date;

    }

    public static String parseDate(String date) {
        StringTokenizer st = new StringTokenizer(date, "/");
        String month = "", day = "", year = "";
        try {
            month = st.nextToken();
            day = st.nextToken();
            year = st.nextToken();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return day + "/" + month + "/" + year;
    }

    public static void displayPromptForEnablingGPS(final Activity activity) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final String action = Settings.ACTION_LOCATION_SOURCE_SETTINGS;
        final String message = "Do you want open GPS setting?";

        builder.setMessage(message)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                activity.startActivity(new Intent(action));
                                d.dismiss();
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface d, int id) {
                                d.cancel();
                                activity.finish();
                            }
                        });
        builder.create().show();
    }

    public static boolean isGPSEnabled(Context mContext) {
        LocationManager locationManager = (LocationManager)
                mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    public static boolean isfrontCameraAvalable() {
        int numCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numCameras; i++) {
            Camera.CameraInfo info = new Camera.CameraInfo();
            Camera.getCameraInfo(i, info);
            if (Camera.CameraInfo.CAMERA_FACING_FRONT == info.facing) {
                return true;
            }
        }
        return false;
    }

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static boolean checkPermission(final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                    alertBuilder.setCancelable(true);
                    alertBuilder.setTitle("Permission necessary");
                    alertBuilder.setMessage("External storage permission is necessary");
                    alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                        }
                    });
                    AlertDialog alert = alertBuilder.create();
                    alert.show();
                } else {
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }
        } else {
            return true;
        }
    }

    //ye nicche ka charo method use hoga jaisa need ho

    //string to bitmap
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

    public static byte[] bitmaptoByte(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    //byte array to string
    public static String BitArrayToString(byte[] b1) {
        byte[] b = b1;
        String temp = Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

    //jada ye use hoga
    //bitmap to string
    public static String BitMapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] b = baos.toByteArray();
        String temp = Base64.encodeToString(b, Base64.NO_WRAP);
        return temp;
    }

    public static String getAge(int year, int month, int day) {
        Calendar dob = Calendar.getInstance();
        Calendar today = Calendar.getInstance();

        dob.set(year, month, day);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) {
            age--;
        }

        Integer ageInt = new Integer(age);
        String ageS = ageInt.toString();

        return ageS;
    }


    public static boolean isValidMail(String email) {

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();

    }

    public static boolean isNotNull(String txt) {
        return txt != null && txt.trim().length() > 0 ? true : false;
    }

    public static boolean validatePassword(String pass) {
        if (pass != null && pass.length() > 2) {
            return true;
        }
        return false;
    }

    public static boolean validateMaxMobile(String mobile) {
        if (mobile != null && mobile.length() > 10) {
            return true;
        }
        return false;
    }

    public static boolean validateMinMobile(String mobile) {
        if (mobile != null && mobile.length() < 10) {
            return true;
        }
        return false;
    }

    public static boolean UserHaveSpace(String user) {
        boolean checkSpace = false;
        int f = 0;
        for (int i = 0; i < user.length(); i++) {
            if (user.contains(" ")) {
                f = 1;
                checkSpace = true;
            }
        }
        if (f == 1) {
            return checkSpace;
        } else {
            return checkSpace;
        }
    }


}

