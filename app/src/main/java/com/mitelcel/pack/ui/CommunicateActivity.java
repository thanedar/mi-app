package com.mitelcel.pack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyAd;
import com.jirbo.adcolony.AdColonyAdAvailabilityListener;
import com.jirbo.adcolony.AdColonyAdListener;
import com.jirbo.adcolony.AdColonyVideoAd;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.bean.api.request.BeanRechargeAccount;
import com.mitelcel.pack.bean.api.response.BeanRechargeAccountResponse;
import com.mitelcel.pack.ui.fragment.FragmentCommunicate;
import com.mitelcel.pack.ui.fragment.FragmentVideoAd;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CommunicateActivity extends BaseActivity implements OnDialogListener,
        FragmentCommunicate.OnCommunicateFragmentInteractionListener, FragmentVideoAd.OnCommunicateFragmentInteractionListener,
        AdColonyAdAvailabilityListener, AdColonyAdListener
{

    private static final String TAG = CommunicateActivity.class.getName();

    MaterialDialog dialog;

    @Inject
    MiApiClient miApiClient;

    private AdColonyVideoAd ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        if (savedInstanceState == null) {
            FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragmentCommunicate.TAG, FragmentCommunicate.newInstance(), R.id.container);

            AdColony.setDeviceID(MiUtils.MiAppPreferences.getToken());
            String client_options = "version:"+ com.mitelcel.pack.BuildConfig.VERSION_NAME + ",store:google";
            AdColony.configure(this, client_options, Config.ADCOLONY_APP_ID, Config.ADCOLONY_ZONE_ID);
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        dialog = new MaterialDialog.Builder(this)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        ((MiApp)getApplication()).getAppComponent().inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        AdColony.resume(this);
        ad = new AdColonyVideoAd(Config.ADCOLONY_ZONE_ID).withListener(this);
        AdColony.addAdAvailabilityListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        AdColony.removeAdAvailabilityListener(this);
        AdColony.pause();
    }

    @Override
    public void onStartVideoClick(View view){
        MiLog.i(TAG, "onStartVideoClick event");
        FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), TAG, FragmentVideoAd.TAG, FragmentVideoAd.newInstance(), R.id.container);
    }

    @Override
    public void onWatchVideoClick(){
        MiLog.i(TAG, "onWatchVideoClick event");

        if(ad.isReady()){
            dialog.hide();
            ad.show();
            ad = new AdColonyVideoAd(Config.ADCOLONY_ZONE_ID).withListener(this);
        }
        else
            dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MiLog.i(TAG, "onActivityResult: requestCode " + requestCode + " resultCode " + resultCode);
    }

    @Override
    public void showDialogErrorCall(String content, String btnTex, @IdRes int resId, int requestCode) {
        if (requestCode == DialogActivity.APP_REQ) {
            MiUtils.showDialogError(this, content, btnTex, resId, DialogActivity.APP_ERROR_CALL);
        }
    }

    public void showDialogSuccessCall(String content, String btnTex, @IdRes int resId) {
        MiUtils.showDialogSuccess(this, content, btnTex, resId, DialogActivity.APP_RES);
    }

    @Override
    public void onAdColonyAdAvailabilityChange( final boolean available, String zone_id )
    {
        runOnUiThread(() -> {
            MiLog.i(TAG, "onAdColonyAdAvailabilityChange with " + available);
            FragmentVideoAd videoAd = (FragmentVideoAd) getSupportFragmentManager().findFragmentByTag(FragmentVideoAd.TAG);
            if(videoAd != null) {
                if (available) {
                    videoAd.enableWatch();
                    dialog.hide();
                } else {
                    videoAd.disableWatch();
                    dialog.show();
                }
            }
        });
    }

    @Override
    public void onAdColonyAdAttemptFinished( AdColonyAd ad )
    {
        //Can use the ad object to determine information about the ad attempt:
        //ad.shown();
        //ad.notShown();
        //ad.canceled();
        //ad.noFill();
        //ad.skipped();

        if(ad.shown()){
            MiLog.i(TAG, "onAdColonyAdAttemptFinished ad shown call started");

            float amount = 0.25f;
            BeanRechargeAccount beanRechargeAccount = new BeanRechargeAccount(amount);

            miApiClient.recharge_account(beanRechargeAccount, new Callback<BeanRechargeAccountResponse>() {
                @Override
                public void success(BeanRechargeAccountResponse beanRechargeAccountResponse, Response response) {
                    dialog.dismiss();
                    if(beanRechargeAccountResponse != null) {
                        if (beanRechargeAccountResponse.getError().getCode() == Config.SUCCESS) {
                            MiLog.i(TAG, "Recharge API response " + beanRechargeAccountResponse.toString());

                            MiUtils.MiAppPreferences.setSessionId(beanRechargeAccountResponse.getResult().getSessionId());
                            MiUtils.MiAppPreferences.setCurrentBalance(MiUtils.MiAppPreferences.getCurrentBalance() + amount);

                            showDialogSuccessCall(getString(R.string.communicate_success, MiUtils.MiAppPreferences.getCurrencySymbol(), amount),
                                    getString(R.string.close), DialogActivity.DIALOG_HIDDEN_ICO);

                        } else {
                            MiLog.i("Recharge", "Recharge API Error response " + beanRechargeAccountResponse.toString());
                            showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
                        }
                    } else {
                        MiLog.i("Recharge", "Recharge API NULL response");
                        showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    dialog.dismiss();
                    MiLog.i("Logout", "Logout failure " + error.toString());
                    showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
                }
            });
        }
    }

    @Override
    public void onAdColonyAdStarted( AdColonyAd ad )
    {
        //Called when the ad has started playing
    }

}
