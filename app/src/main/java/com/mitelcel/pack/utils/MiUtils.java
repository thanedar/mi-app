package com.mitelcel.pack.utils;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.IdRes;
import android.telephony.TelephonyManager;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
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

    public static void showDialogErrorCall(Activity activity, String content, String btnTex){
        showDialogError(activity, content, btnTex, -1, DialogActivity.APP_ERROR_CALL);
    }

    public static void showDialogError(Activity activity, String content, String btnTex, @IdRes int resId, int requestCode){
        Intent i = new Intent(activity, DialogActivity.class);
        if(content != null && !content.equals(""))
            i.putExtra(DialogActivity.DIALOG_CONTENT, content);
        if(btnTex != null && !btnTex.equals(""))
            i.putExtra(DialogActivity.DIALOG_POS_BTN_TEXT, btnTex);
        i.putExtra(DialogActivity.DIALOG_RES_ID, resId);
        i.putExtra(DialogActivity.DIALOG_RES_TITLE, activity.getResources().getString(R.string.oops));
        activity.startActivityForResult(i, requestCode);
    }

    public static void showDialogSuccess(Activity activity, String content, String btnTex, @IdRes int resId, int requestCode){
        Intent i = new Intent(activity, DialogActivity.class);
        if(content != null && !content.equals(""))
            i.putExtra(DialogActivity.DIALOG_CONTENT, content);
        if(btnTex != null && !btnTex.equals(""))
            i.putExtra(DialogActivity.DIALOG_POS_BTN_TEXT, btnTex);
        i.putExtra(DialogActivity.DIALOG_RES_ID, resId);
        i.putExtra(DialogActivity.DIALOG_RES_TITLE, activity.getResources().getString(R.string.success));
        activity.startActivityForResult(i, requestCode);
    }

    public static void showDialogQuery(Activity activity, String content, String pBtnTex, String nBtnTex, @IdRes int resId, int requestCode){
        Intent i = new Intent(activity, DialogActivity.class);
        if(content != null && !content.equals(""))
            i.putExtra(DialogActivity.DIALOG_CONTENT, content);
        if(pBtnTex != null && !pBtnTex.equals(""))
            i.putExtra(DialogActivity.DIALOG_POS_BTN_TEXT, pBtnTex);
        if(nBtnTex != null && !nBtnTex.equals(""))
            i.putExtra(DialogActivity.DIALOG_NEG_BTN_TEXT, nBtnTex);
        i.putExtra(DialogActivity.DIALOG_RES_ID, resId);
        i.putExtra(DialogActivity.DIALOG_RES_TITLE, activity.getResources().getString(R.string.success));
        activity.startActivityForResult(i, requestCode);
    }

    public static void showDialogWithUrl(Activity activity, String title, String resUrl, String content, String btnTex, int requestCode){
        Intent i = new Intent(activity, DialogActivity.class);
        if(content != null && !content.equals(""))
            i.putExtra(DialogActivity.DIALOG_CONTENT, content);
        if(btnTex != null && !btnTex.equals(""))
            i.putExtra(DialogActivity.DIALOG_POS_BTN_TEXT, btnTex);
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
        MiAppPreferences.clear();
    }

    public static int getResIdFromAction(int action){
        int res;
        int ol = 0;

        switch (action){
            case Config.ACTION_TYPE_RECHARGE:
                res = R.drawable.recharge;
                break;
            case Config.ACTION_TYPE_TRANSFER:
                res = R.drawable.transfer;
                break;
            case Config.ACTION_TYPE_BONUS:
                res = R.drawable.ic_cash;
                break;
            default:
                res = R.drawable.ic_cash;
        }
        return res;
    }

    public static final String REGEX_MAIL = "(([\\w\\-\\.]+)@([\\w\\-\\.]+)\\.([a-zA-Z]{2,3}))";
    public static final String REGEX_MSISDN = "[0-9]{4,10}";
    public static final String REGEX_PASSWORD = "[\\w._&$\\-]{4,}";

    /**
     * Static class to get all user's information
     */
    public static class Info{

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

        public static String getFormattedCurrency(float value){
            return String.format("%.2f", value);
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

        public static boolean hasAllParamsToValidate(){
            if (!MiAppPreferences.hasUserMail() || MiAppPreferences.getToken() == null)
                return false;

            return true;
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

        private static SharedPreferences getSharedPreferences() {
            Context context = MiApp.getInstance().getApplicationContext();
            return context.getSharedPreferences(MIAPP_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }

        public static void setToken(String token){
            MiLog.i(TAG, "Set App Token [" + token + "]");
            getSharedPreferences().edit().putString(APP_TOKEN, token).apply();
        }

        public static String getToken(){
            String token = getSharedPreferences().getString(APP_TOKEN, "");
            MiLog.i(TAG, "Get App Token [" + token + "]");
            return token;
        }

        public static void setSessionId(String session_id){
            getSharedPreferences().edit().putString(SESSION_ID, session_id).apply();
        }

        public static String getSessionId(){
            String s_id = getSharedPreferences().getString(SESSION_ID, "");
            MiLog.i(TAG, "Session ID [" + s_id + "]");
            return s_id;
        }

        public static boolean isInvalidSession(){
            return (getSessionId() == null || getSessionId().equals(TOKEN_EXPIRED));
        }

        public static void setAuthPass(String value){
            getSharedPreferences().edit().putString(PASSWORD_AUT, value).apply();
        }

        public static String getAuthPass(){
            return getSharedPreferences().getString(PASSWORD_AUT, "");
        }

        public static void setUserMail(String value){
            MiLog.i(TAG, "set email: " + value);
            getSharedPreferences().edit().putString(USER_EMAIL, value).apply();
        }

        public static boolean hasUserMail(){
            return getSharedPreferences().contains(USER_EMAIL);
        }

        public static String getUserMail(){
            String email = getSharedPreferences().getString(USER_EMAIL, "");
            MiLog.i(TAG, "get user email: " + email);
            return email;
        }

        public static void setMsisdn(String value){
            getSharedPreferences().edit().putString(MSISDN, value).apply();
        }

        public static String getMsisdn(){
            SharedPreferences sp = getSharedPreferences();
            return sp.getString(MSISDN, "");
        }

        public static void setDeviceType(String value){
            getSharedPreferences().edit().putString(DEVICE_TYPE, value).apply();
        }

        public static String getDeviceType(){
            SharedPreferences sp = getSharedPreferences();
            return sp.getString(DEVICE_TYPE, "android");
        }

        public static void setCurrentBalance(float value){
            getSharedPreferences().edit().putFloat(MONEY_BALANCE, value).apply();
        }

        public static float getCurrentBalance(){
            SharedPreferences sp = getSharedPreferences();
            return sp.getFloat(MONEY_BALANCE, 100.00f);
        }

        public static String getCurrentBalanceString(){
            SharedPreferences sp = getSharedPreferences();
            float balance = sp.getFloat(MONEY_BALANCE, 100.00f);
            return String.format("%.2f", balance);
        }

        public static void setLogout(){
            setLoggedStatus(LOGOUT);
            MiLog.i(TAG, "flow Logout setLogout[" + LOGOUT + "]");
        }

        public static void setLogin(){
            setLoggedStatus(LOGIN);
            MiLog.i(TAG, "flow Logout setLogout[" + LOGIN + "]");
        }

        static void setLoggedStatus(int value){
            getSharedPreferences().edit().putInt(LOGIN_STATUS_KEY, value).apply();
        }

        public static int getLoggedStatus(){
            int key = getSharedPreferences().getInt(LOGIN_STATUS_KEY, LOGIN_NOT_SET);
            MiLog.i(TAG, "getLoggedStatus[" + key + "]");
            return key;
        }

        public static void setLastCheckTimestamp(){
            long timestamp = System.currentTimeMillis();
            MiLog.i(TAG, "setLastCheckTimestamp: " + timestamp);
            getSharedPreferences().edit().putLong(LAST_CHECK_TIMESTAMP, timestamp).apply();
        }

        public static long getLastCheckTimestamp(){
            return getSharedPreferences().getLong(LAST_CHECK_TIMESTAMP, 0);
        }

        public static void clear(){
            String token = getToken();
            getSharedPreferences().edit().clear().apply();
            setToken(token);
        }

        public static void logOut(Activity activity){
            MiAppPreferences.clear();
            MiAppPreferences.setLogout();
            MiUtils.startSkillActivityClearStack(activity, LoginOrRegister.class);
            activity.finish();
        }

        public static void registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener){
            getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
        }

        public static void unRegisterListener(SharedPreferences.OnSharedPreferenceChangeListener listener) {
            getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
        }

/*
        public static void saveDataRegisterUser(BeanActionCreateUserResponse beanActionCreateUserResponse){
            setIdProfile(beanActionCreateUserResponse.getIdProfile());
            setInvitedBy(beanActionCreateUserResponse.getCheckedInvitedBy());
        }

        public static void saveLoginUser(BeanUserValidResponse beanUserValidResponse){
            setIdProfile(beanUserValidResponse.getIdProfile());
            setInvitedBy(beanUserValidResponse.getCheckedInvitedBy());
        }
*/

        public static void setCurrencySymbol(String value){
            getSharedPreferences().edit().putString(CURRENCY_SYMBOL, value).apply();
        }

        public static String getCurrencySymbol(){
            SharedPreferences sp = getSharedPreferences();
            return sp.getString(CURRENCY_SYMBOL, "$");
        }

        public static void setMsisdnPrefix(String value){
            getSharedPreferences().edit().putString(MSISDN_PREFIX, value).apply();
        }

        public static String getMsisdnPrefix(){
            SharedPreferences sp = getSharedPreferences();
            return sp.getString(MSISDN_PREFIX, Config.MSISDN_PREFIX);
        }

        public static void setMsisdnFormat(String value){
            getSharedPreferences().edit().putString(MSISDN_FORMAT, value).apply();
        }

        public static String getMsisdnFormat(){
            SharedPreferences sp = getSharedPreferences();
            return sp.getString(MSISDN_FORMAT, "");
        }
    }
}
