package com.mitelcel.pack.utils;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
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
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.DialogActivity;
//import com.tatssense.core.Buckstracks;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
         * return the location object
         * @param context
         * @return
         */
        public static Location getUserLocation(Context context){
            Location location = null;
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
            if(!MiAppPreferences.hasUserMail(context) || MiAppPreferences.getToken(context) == null)
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

        public static void sendMessageByFb(Context context, String message){
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.putExtra(Intent.EXTRA_TEXT, message);
            sharingIntent.setType("text/plain");
            sharingIntent.setPackage(PACKAGE_FB);
            try
            {
                context.startActivity(sharingIntent);
            }catch (android.content.ActivityNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
        public static void sendMessageByWhatsapp(Context context, String message){
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, message);
            sendIntent.setType("text/plain");
            sendIntent.setPackage(PACKAGE_WHATSAPP);
            try
            {
                context.startActivity(sendIntent);
            }catch (android.content.ActivityNotFoundException ex)
            {
                ex.printStackTrace();
            }
        }
        public static void sendMessageBySMS(Context context, String message){
            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
            sendIntent.setData(Uri.parse("sms:"));
            sendIntent.putExtra("sms_body", message);
            context.startActivity(sendIntent);
        }

        /**
         * http://stackoverflow.com/questions/5734678/custom-filtering-of-intent-chooser-based-on-installed-android-package-name
         * @param context
         * @param message
         */
        public static void sendMessageByEmail(Context context, String message){
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getString(R.string.app_name));
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, message);
            sharingIntent.setType("message/rfc822");

            List<LabeledIntent> intentList = new ArrayList<>();
            PackageManager pm = context.getPackageManager();
            Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setType("message/rfc822");
            Observable
                    .from(context.getPackageManager().queryIntentActivities(intent, 0))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .filter(ri -> (ri.activityInfo.packageName.contains("mail")))
                    .subscribe(new Observer<ResolveInfo>() {

                        @Override
                        public void onCompleted() {
                            // convert intentList to array
                            LabeledIntent[] extraIntents = intentList.toArray(new LabeledIntent[intentList.size()]);
                            Intent appChooser = Intent.createChooser(sharingIntent, "Send mail");
                            appChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, extraIntents);
                            context.startActivity(appChooser);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onNext(ResolveInfo ri) {
                            Intent intent = new Intent();
                            intent.setComponent(new ComponentName(ri.activityInfo.packageName, ri.activityInfo.name));
                            intent.setAction(Intent.ACTION_SEND);
                            intent.setType("message/rfc822");
                            intent.setPackage(ri.activityInfo.packageName);
                            intentList.add(new LabeledIntent(intent, ri.activityInfo.packageName, ri.loadLabel(pm), ri.icon));
                        }
                    });
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
        public static int getRewardResId(Context context){
            if(MiAppPreferences.getRewardsCurrency(context).equals(Config.COINS)){
                return R.drawable.act_bar_coin;
            }
            if(MiAppPreferences.getRewardsCurrency(context).equals(Config.CASH)){
                return R.drawable.ic_cash;
            }
            return 0;
        }

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

        public static final String TOKEN = "token";
        public static final String PASSWORD_AUT = "password_aut";
        public static final String ID_APPLICATION = "id_application";
        public static final String MSISDN = "msisdn";
        public static final String COIN_COUNT = "coin_count";
        public static final String MONEY_COUNT = "money_count";
        public static final String USER_EMAIL = "user_mail";
        public static final String TOKEN_EXPIRED = "token_expired";
        public static final String DEVICE_TYPE = "device_type";
        public static final String ID_PROFILE = "id_profile";
        public static final String LOGIN_STATUS_KEY = "login_status";
        public static final String LAST_CHECK_TIMESTAMP = "last_check_timestamp";
        public static final int LOGOUT = 1;
        public static final int LOGIN_NOT_SET = -1;
        public static final int LOGIN = 0;
        public static String REWARD_CURRENCY = "reward_currency";
        public static String CURRENCY_SYMBOL = "currency_symbol";
        public static String MSISDN_PREFIX = "msisdn_prefix";
        public static String MSISDN_FORMAT = "msisdn_format";

        public static final String APP_ID_VAL = "FLYSKILLAPPSR";
        public static final String AUTHENTICATION_PASS = "acotel80skill";

        public static final String SKILL_SHARED_PREF_NAME = "mitelcel_shared_pref";

        private static SharedPreferences getSharedPreferences(final Context context) {
            return context.getSharedPreferences(SKILL_SHARED_PREF_NAME, Context.MODE_PRIVATE);
        }

        public static void setToken(Context context, String token){
            getSharedPreferences(context).edit().putString(TOKEN, token).apply();
        }

        public static String getToken(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            MiLog.i(MiAppPreferences.class.getName(), "Token[" + sp.getString(TOKEN, null) + "]");
            return sp.getString(TOKEN, null);
        }

        public static void setAuthPass(Context context, String value){
            getSharedPreferences(context).edit().putString(PASSWORD_AUT, value).apply();
        }

        public static String getAuthPass(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(PASSWORD_AUT, "");
        }

        public static void setUserMail(Context context, String value){
            MiLog.i("Sh pref", "set email: " + value);
            getSharedPreferences(context).edit().putString(USER_EMAIL, value).apply();
        }

        public static boolean isInvalidToken(Context context){
            return (getToken(context)==null || getToken(context).equals(TOKEN_EXPIRED));
        }

        public static boolean hasUserMail(Context context){
            return getSharedPreferences(context).contains(USER_EMAIL);
        }

        public static String getUserMail(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            String email = sp.getString(USER_EMAIL, "");
            MiLog.i("Sh pref", "get user email: " + email);
            return email;
        }

        public static String getRewardsCurrency(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(REWARD_CURRENCY, "");
        }

        public static String getMyEarnRewards(Context context){
            if(getRewardsCurrency(context).equals(Config.COINS))
                return getCoinCount(context);
            if(getRewardsCurrency(context).equals(Config.CASH))
                return getMoneyCount(context);
            return "";
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

        public static void setCoinCount(Context context, String value){
            getSharedPreferences(context).edit().putString(COIN_COUNT, value).apply();
        }

        public static String getCoinCount(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(COIN_COUNT, "");
        }

        public static void setMoneyCount(Context context, String value){
            getSharedPreferences(context).edit().putString(MONEY_COUNT, value).apply();
        }

        public static String getMoneyCount(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(MONEY_COUNT, "");
        }

        public static void setLogout(Context context){
            setLoggedStatus(context, LOGOUT);
            MiLog.i(MiUtils.class.getName(), "flow Logout setLogout[" + LOGOUT + "]");
        }

        public static void setLogin(Context context){
            setLoggedStatus(context, LOGIN);
            MiLog.i(MiUtils.class.getName(), "flow Logout setLogout[" + LOGIN + "]");
        }

        static void setLoggedStatus(Context context, int value){
            getSharedPreferences(context).edit().putInt(LOGIN_STATUS_KEY, value).apply();
        }

        public static int getLogout(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            MiLog.i(MiUtils.class.getName(), "flow Logout getLogout[" + sp.getInt(LOGIN_STATUS_KEY, LOGIN_NOT_SET) + "]");
            return sp.getInt(LOGIN_STATUS_KEY, LOGIN_NOT_SET);
        }

        public static void setIdProfile(Context context, int value){
            MiLog.i(MiAppPreferences.class.getName(), "Profile id[" + value + "] setIdProfile");
            getSharedPreferences(context).edit().putInt(ID_PROFILE, value).apply();
        }

        public static int getIdProfile(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            MiLog.i(MiAppPreferences.class.getName(), "Profile id[" + sp.getInt(ID_PROFILE, 0) + "] getIdProfile");
            return sp.getInt(ID_PROFILE, 0);
        }

        public static void setLastCheckTimestamp(Context context){
            long timestamp = System.currentTimeMillis();
            MiLog.i("Sh pref", "setLastCheckTimestamp: " + timestamp);
            getSharedPreferences(context).edit().putLong(LAST_CHECK_TIMESTAMP, timestamp).apply();
        }

        public static long getLastCheckTimestamp(Context context){
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getLong(LAST_CHECK_TIMESTAMP, 0);
        }

        public static void clear(Context context){
            String token = getToken(context);
            getSharedPreferences(context).edit().clear().apply();
            setToken(context, token);
        }

        public static void logOut(Activity activity){
            MiAppPreferences.clear(activity);
            MiAppPreferences.setLogout(activity);
//            MiUtils.startSkillActivity(activity, LoginOrSignUp.class);
            activity.finish();
        }

        public static void registerListener(SharedPreferences.OnSharedPreferenceChangeListener listener, Context context){
            getSharedPreferences(context).registerOnSharedPreferenceChangeListener(listener);
        }

        public static void unRegisterListener(SharedPreferences.OnSharedPreferenceChangeListener listener, Context context){
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
            if(MiAppPreferences.getRewardsCurrency(context).equals(Config.COINS)){
                return "";
            }
            SharedPreferences sp = getSharedPreferences(context);
            return sp.getString(CURRENCY_SYMBOL, "");
        }

        public static String getVirtualCurrency(Context context){
            if(MiAppPreferences.getRewardsCurrency(context).equals(Config.CASH)){
                return "";
            }
            return " coins";
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
