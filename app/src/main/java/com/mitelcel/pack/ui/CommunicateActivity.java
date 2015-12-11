package com.mitelcel.pack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jirbo.adcolony.AdColony;
import com.jirbo.adcolony.AdColonyVideoAd;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.ui.fragment.FragmentCommunicate;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

public class CommunicateActivity extends BaseActivity implements OnDialogListener, FragmentCommunicate.OnCommunicateFragmentInteractionListener {

    private static final String TAG = RechargeActivity.class.getName();

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

        ad = new AdColonyVideoAd(Config.ADCOLONY_ZONE_ID);
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
    }

    @Override
    public void onPause() {
        super.onPause();
        AdColony.pause();
    }

    @Override
    public void onCommunicateFragmentInteraction(View view){
        MiLog.i(TAG, "onCommunicateFragmentInteraction event");
        showDialogSuccessCall(getString(R.string.sample_offer), getString(R.string.ok), DialogActivity.DIALOG_HIDDEN_ICO);

        if(ad.isReady()){
            ad.show();
            ad = new AdColonyVideoAd(Config.ADCOLONY_ZONE_ID);
        }
        else
            dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MiLog.i(TAG, "onActivityResult: requestCode " + requestCode + " resultCode " + resultCode);
        if(requestCode  == DialogActivity.APP_RES && resultCode == DialogActivity.APP_REFRESH)
            finish();
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

}
