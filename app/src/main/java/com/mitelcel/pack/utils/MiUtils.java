package com.mitelcel.pack.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.DialogActivity;
import com.mitelcel.pack.ui.LoginOrRegister;
//import com.tatssense.core.Buckstracks;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class MiUtils {

    public static void startSkillActivity(Context context, Class<?> cls){
        Intent i = new Intent(context, cls);
        context.startActivity(i);
    }

    public static void startSkillActivityClearStack(Context context, Class<?> cls){
        Intent i = new Intent(context, cls);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(i);
    }

    public static void startGameFromPackageId(Context context, String packageId){

        PackageManager pm = context.getPackageManager();

        Intent intent = new Intent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> listActivities = pm.queryIntentActivities(intent, 0);
        for(ResolveInfo resolveInfo : listActivities){
            if(resolveInfo.activityInfo.packageName.equals(packageId)){
                intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setAction(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setComponent(new ComponentName(packageId, resolveInfo.activityInfo.name));
                context.startActivity(intent);
            }
        }
    }

    public static void showDialogErrorCall(Activity activity, String content, String btnTex){
        showDialogError(activity, content, btnTex, -1, DialogActivity.APP_ERROR_CALL);
    }

    public static void showDialogError(Activity activity, String content, String btnTex, @IdRes int resId, int requestCode){
        Intent i = new Intent(activity, DialogActivity.class);
        if(content != null && !content.equals(""))
            i.putExtra(DialogActivity.DIALOG_CONTENT, content);
        if(btnTex != null && !btnTex.equals(""))
            i.putExtra(DialogActivity.DIALOG_BTN_TEXT, btnTex);
        i.putExtra(DialogActivity.DIALOG_RES_ID, resId);
//        i.putExtra(DialogActivity.DIALOG_RES_TITLE, activity.getResources().getString(R.string.oops));
        activity.startActivityForResult(i, requestCode);
    }

    public static void showDialogWithUrl(Activity activity, String title, String resUrl, String content, String btnTex, int requestCode){
        Intent i = new Intent(activity, DialogActivity.class);
        if(content != null && !content.equals(""))
            i.putExtra(DialogActivity.DIALOG_CONTENT, content);
        if(btnTex != null && !btnTex.equals(""))
            i.putExtra(DialogActivity.DIALOG_BTN_TEXT, btnTex);
        i.putExtra(DialogActivity.DIALOG_RES_URL, resUrl);
        if(title != null && !title.equals(""))
            i.putExtra(DialogActivity.DIALOG_RES_TITLE, title);
        activity.startActivityForResult(i, requestCode);
    }

    public static void failureCallWithMsgCloseActivity(final Activity activity, String content){
        new MaterialDialog.Builder(activity)
                .content(content)
                .cancelable(false)
                .positiveText(R.string.close)
                .callback(new MaterialDialog.ButtonCallback() {
                    @Override
                    public void onPositive(MaterialDialog dialog) {
                        super.onPositive(dialog);
                        activity.finish();
                    }
                })
                .build()
                .show();
        MiAppPreferences.clear(activity);
    }

    public static void openMarketPageWithReferrer(Context context, String url){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static final String REGEX_MAIL = "(([\\w\\-\\.]+)@([\\w\\-\\.]+)\\.([a-zA-Z]{2,3}))";
    public static final String REGEX_MSISDN = "[0-9]{4,10}";
    public static final String REGEX_PASSWORD = "[\\w._&$\\-]{4,}";

    /**
     * Static class to get all user's information
     */
    public static class Info{

        public static final String PACKAGE_FB = "com.facebook.orca";
        public static final String PACKAGE_WHATSAPP = "com.whatsapp";

        /**
         * language configuration
         * @param context
         * @return
         */
        public static String getLocale(Context context){
            String locale= "";
            try{
                locale = context.getResources().getConfiguration().locale.getCountry();
            }catch(Exception e){
                e.printStackTrace();
            }
            return locale;
        }

        public static String getLanguage(){
            return Locale.getDefault().getLanguage();
        }

        /**
         * get ip
         * @return
         */
        public static String getIpv4add() {
            try {
                List<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
                if(nilist.size() > 0){
                    for (NetworkInterface ni: nilist){
                        List<InetAddress>  ialist = Collections.list(ni.getInetAddresses());
                        if(ialist.size()>0){
                            for (InetAddress address: ialist){
                                if (!address.isLoopbackAddress() && address instanceof Inet4Address){
                                    return String.copyValueOf(address.getHostAddress().toCharArray());
                                }
                            }
                        }

                    }
                }

            } catch (SocketException ex) {
                ex.printStackTrace();
            }
            return null;
        }

        /**
         * get android id
         * @param context
         * @return
         */
        public static String getAndroidId(Context context){
            String android_id = "";

            try{
                android_id = Settings.Secure.getString(context.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
            }catch(Exception e){
                e.printStackTrace();
            }
            MiLog.i("utils", "AndroidId[" + android_id + "]");
            return android_id;
        }

        /**
         * get device IMEI
         * @param context
         * @return
         */
        public static String getDeviceId(Context context){
            String device_id = "";

            try{
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                device_id = telephonyManager.getDeviceId();
            }catch(Exception e){
                e.printStackTrace();
            }
            MiLog.i("utils", "IMEI [" + device_id + "]");
            return device_id;
        }

        /**
         * get device IMSI
         * @param context
         * @return
         */
        public static String getSubscriberId(Context context){
            String subscriber_id = "";

            try{
                TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                subscriber_id = telephonyManager.getSubscriberId();
            }catch(Exception e){
                e.printStackTrace();
            }
            MiLog.i("utils", "IMSI [" + subscriber_id + "]");
            return subscriber_id;
        }

        /**
         * return the location object
         * @param context
         * @return location
         */
        public static Location getUserLocation(Context context){
            Location location = null;
            /*if ( Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission( context, android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return location;
            }*/
            try{
                final LocationManager locationManager = (LocationManager) context
                        .getSystemService(Context.LOCATION_SERVICE);
                // Register the listener with the Location Manager to receive location
                // updates
                if (locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
                    location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return location;
        }

        /**
         * check connection state
         * @param context
         * @return
         */
        public static boolean isNetworkConnected(Context context) {

            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo ni = cm.getActiveNetworkInfo();
            if (ni == null) {
                // There are no active networks.
                return false;
            } else
                return true;
        }

        public static boolean hasAllParamsToValidate(Context context){
            if (!MiAppPreferences.hasUserMail(context) || MiAppPreferences.getToken(context) == null)
                return false;

            return true;
        }

        public static boolean isPackageInstalled(Context context, String packagename) {
            PackageManager pm = context.getPackageManager();
            try {
                pm.getPackageInfo(packagename, PackageManager.GET_ACTIVITIES);
                return true;
            } catch (PackageManager.NameNotFoundException e) {
                return false;
            }
        }

        public static void sendMessageBySMS(Context context, String message){
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"));
            sendIntent.putExtra("sms_body", message);
            context.startActivity(sendIntent);
        }
    }

    public static class Animations{

        private static final int ANIM_DURATION = 500;

        public static AnimatorSet setAnimation(View playInstall) {
            //alpha
            ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(playInstall, View.ALPHA, 0);
            alphaAnimator.setRepeatCount(1);
            alphaAnimator.setRepeatMode(ValueAnimator.REVERSE);

            //scale
            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat(View.SCALE_X, 2);
            PropertyValuesHolder pvhL = PropertyValuesHolder.ofFloat(View.SCALE_Y, 2);
            ObjectAnimator scaleAnimator = ObjectAnimator.ofPropertyValuesHolder(playInstall, pvhL, pvhX);
            scaleAnimator.setRepeatCount(1);
            scaleAnimator.setRepeatMode(ValueAnimator.REVERSE);

            AnimatorSet animatorSet;
            animatorSet = new AnimatorSet();
            animatorSet.play(alphaAnimator).with(scaleAnimator);
            return  animatorSet;
        }

        public static ObjectAnimator setFadeOutAnimator(Object view){
            // Fade out background
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "alpha", 0);
            objectAnimator.setDuration(ANIM_DURATION);
            return objectAnimator;
        }

        public static ObjectAnimator setFadeOutAnimator(Object view, int duration_millis){
            // Fade out background
            ObjectAnimator objectAnimator = ObjectAnimator.ofInt(view, "alpha", 0);
            objectAnimator.setDuration(duration_millis);
            return objectAnimator;
        }
    }

    public static class Drawable{
        public static ColorMatrix getGrayscaleColorMatrix() {
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            return colorMatrix;
        }

        public static Bitmap createGreyImage(Bitmap original, ColorMatrix colorMatrix) {
            Bitmap bitmap = process(original, colorMatrix);
            return bitmap;
        }

        public static Bitmap process(Bitmap original, ColorMatrix colorMatrix) {
            Bitmap bitmap = Bitmap.createBitmap(
                    original.getWidth(), original.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);

            Paint paint = new Paint();
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            canvas.drawBitmap(original, 0, 0, paint);

            return bitmap;
        }
    }

    public static class MiAppPreferences {

        public static final String APP_TOKEN = "mi_app_token";
        public static final String SESSION_ID = "mi_session_id";
        public static final String PASSWORD_AUT = "password_aut";
        public static final String MSISDN = "msisdn";
        public static final String MONEY_BALANCE = "money_balance";
        public static final String USER_EMAIL = "user_mail";
        public static final String TOKEN_EXPIRED = "token_expired";
        public static final String DEVICE_TYPE = "device_type";
        public static final String ID_PROFILE = "id_profile";
        public static final String LOGIN_STATUS_KEY = "login_status";
        public static final String LAST_CHECK_TIMESTAMP = "last_check_timestamp";
        public static final int LOGOUT = 1;
        public static final int LOGIN_NOT_SET = -1;
        public static final int LOGIN = 0;
        public static String CURRENCY_SYMBOL = "currency_symbol";
        public static String MSISDN_PREFIX = "msisdn_prefix";
        public static String MSISDN_FORMAT = "msisdn_format";

        public static final String MIAPP_SHARED_PREF_NAME = "mitelcel_shared_pref";

        public static final String TAG = "MiTelcelPreferences";

        private static SharedPreferences getSharedPreferences(final Context context) {
            return context.getSharedPreferences(MIAPP_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }

        public static void setToken(Context context, String token){
            MiLog.i(TAG, "Set App Token [" + token + "]");
            getSharedPreferences(context).edit().putString(APP_TOKEN, token).apply();
        }

        public static String getToken(Context context){
            String token = getSharedPreferences(context).getString(APP_TOKEN, "");
            MiLog.i(TAG, "Get App Token [" + token + "]");
            return token;
        }

        public static void setSessionId(Context context, String session_id){
            getSharedPreferences(context).edit().putString(SESSION_ID, session_id).apply();
        }

        public static String getSessionId(Context context){
            String s_id = getSharedPreferences(context).getString(SESSION_ID, "");
            MiLog.i(TAG, "Session ID [" + s_id + "]");
            return s_id;
        }

        public static boolean isInvalidSession(Context context){
            return (getToken(context) == null || getToken(context).equals(TOKEN_EXPIRED));
        }

        public static void setAuthPass(Context context, String value){
            getSharedPreferences(context).edit().putString(PASSWORD_AUT, value).apply();
        }

        public static String getAuthPass(Context context){
            return getSharedPreferences(context).getString(PASSWORD_AUT, "");
        }

        public static void setUserMail(Context context, String value){
            MiLog.i(TAG, "set email: " + value);
            getSharedPreferences(context).edit().putString(USER_EMAIL, value).apply();
        }

        public static boolean hasUserMail(Context context){
            return getSharedPreferences(context).contains(USER_EMAIL);
        }

        public static String getUserMail(Context context){
            String email = getSharedPreferences(context).getString(USER_EMAIL, "");
            MiLog.i(TAG, "get user email: " + email);
            return email;
        }

        public static void setMsisdn(Context context, String value){
            getSharedPreferences(context).edit().putString(MSISDN, value).apply();
        }

        public static String getMsisdn(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(MSISDN, "");
        }

        public static void setDeviceType(Context context, String value){
            getSharedPreferences(context).edit().putString(DEVICE_TYPE, value).apply();
        }

        public static String getDeviceType(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(DEVICE_TYPE, "android");
        }

        public static void setCurrentBalance(Context context, String value){
            getSharedPreferences(context).edit().putString(MONEY_BALANCE, value).apply();
        }

        public static String getCurrentBalance(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(MONEY_BALANCE, "100.00");
        }

        public static void setLogout(Context context){
            setLoggedStatus(context, LOGOUT);
            MiLog.i(TAG, "flow Logout setLogout[" + LOGOUT + "]");
        }

        public static void setLogin(Context context){
            setLoggedStatus(context, LOGIN);
            MiLog.i(TAG, "flow Logout setLogout[" + LOGIN + "]");
        }

        static void setLoggedStatus(Context context, int value){
            getSharedPreferences(context).edit().putInt(LOGIN_STATUS_KEY, value).apply();
        }

        public static int getLoggedStatus(Context context){
            int key = getSharedPreferences(context).getInt(LOGIN_STATUS_KEY, LOGIN_NOT_SET);
            MiLog.i(TAG, "getLoggedStatus[" + key + "]");
            return key;
        }

        public static void setLastCheckTimestamp(Context context){
            long timestamp = System.currentTimeMillis();
            MiLog.i(TAG, "setLastCheckTimestamp: " + timestamp);
            getSharedPreferences(context).edit().putLong(LAST_CHECK_TIMESTAMP, timestamp).apply();
        }

        public static long getLastCheckTimestamp(Context context){
            return getSharedPreferences(context).getLong(LAST_CHECK_TIMESTAMP, 0);
        }

        public static void clear(Context context){
            String token = getToken(context);
            getSharedPreferences(context).edit().clear().apply();
            setToken(context, token);
        }

        public static void logOut(Activity activity){
            MiAppPreferences.clear(activity);
            MiAppPreferences.setLogout(activity);
            MiUtils.startSkillActivityClearStack(activity, LoginOrRegister.class);
            activity.finish();
        }

        public static void registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener, Context context){
            getSharedPreferences(context).registerOnSharedPreferenceChangeListener(listener);
        }

        public static void unRegisterListener(SharedPreferences.OnSharedPreferenceChangeListener listener, Context context) {
            getSharedPreferences(context).unregisterOnSharedPreferenceChangeListener(listener);
        }

/*
        public static void saveDataRegisterUser(BeanActionCreateUserResponse beanActionCreateUserResponse, Context context){
            setIdProfile(context, beanActionCreateUserResponse.getIdProfile());
            setInvitedBy(context, beanActionCreateUserResponse.getCheckedInvitedBy());
        }

        public static void saveLoginUser(BeanUserValidResponse beanUserValidResponse, Context context){
            setIdProfile(context, beanUserValidResponse.getIdProfile());
            setInvitedBy(context, beanUserValidResponse.getCheckedInvitedBy());
        }
*/

        public static void setCurrencySymbol(Context context, String value){
            getSharedPreferences(context).edit().putString(CURRENCY_SYMBOL, value).apply();
        }

        public static String getCurrencySymbol(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(CURRENCY_SYMBOL, "$");
        }

        public static void setMsisdnPrefix(Context context, String value){
            getSharedPreferences(context).edit().putString(MSISDN_PREFIX, value).apply();
        }

        public static String getMsisdnPrefix(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(MSISDN_PREFIX, "");
        }

        public static void setMsisdnFormat(Context context, String value){
            getSharedPreferences(context).edit().putString(MSISDN_FORMAT, value).apply();
        }

        public static String getMsisdnFormat(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(MSISDN_FORMAT, "");
        }
    }
}
