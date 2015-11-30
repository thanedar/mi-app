package com.mitelcel.pack.ui;

import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanRechargeAccount;
import com.mitelcel.pack.api.bean.resp.BeanRechargeAccountResponse;
import com.mitelcel.pack.ui.fragment.FragmentConfirm;
import com.mitelcel.pack.ui.fragment.FragmentRecharge;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RechargeActivity extends BaseActivity
        implements FragmentConfirm.OnConfirmFragmentInteractionListener,
            FragmentRecharge.OnRechargeFragmentInteractionListener,
            OnDialogListener
{

    private static final String TAG = RechargeActivity.class.getName();

    private float amount = 0;

    MaterialDialog dialog;

    @Inject
    MiApiClient miApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        if (savedInstanceState == null) {
            FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragmentRecharge.TAG, FragmentRecharge.newInstance(), R.id.container);
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
    public void onRechargeFragmentInteraction(float amount){
        MiLog.i(TAG, "onRechargeFragmentInteraction event with " + amount);
        this.amount = amount;

        String display = getString(R.string.recharge_check, MiUtils.MiAppPreferences.getCurrencySymbol(), amount);
        FragmentHandler.addFragmentInBackStackWithAnimation(
                getSupportFragmentManager(),
                "RECHARGE",
                FragmentConfirm.class.getName(),
                FragmentConfirm.newInstance(display, String.valueOf(amount)),
                R.id.container);
    }

    @Override
    public void onConfirmFragmentInteraction(String password) {
        dialog.show();
        MiLog.i(TAG, "onConfirmFragmentInteraction event started");

        BeanRechargeAccount beanRechargeAccount;
        if(password == "") {
            beanRechargeAccount = new BeanRechargeAccount(amount);
        }
        else {
            beanRechargeAccount = new BeanRechargeAccount(amount, password);
        }

        miApiClient.recharge_account(beanRechargeAccount, new Callback<BeanRechargeAccountResponse>() {
            @Override
            public void success(BeanRechargeAccountResponse beanRechargeAccountResponse, Response response) {
                dialog.dismiss();
                if (beanRechargeAccountResponse.getError().getCode() == Config.SUCCESS) {
                    MiLog.i("Recharge", "Recharge API response " + beanRechargeAccountResponse.toString());

                    MiUtils.MiAppPreferences.setSessionId(beanRechargeAccountResponse.getResult().getSessionId());
                    MiUtils.MiAppPreferences.setLastCheckTimestamp();
                    MiUtils.MiAppPreferences.setCurrentBalance(MiUtils.MiAppPreferences.getCurrentBalance() + amount);

                    showDialogSuccessCall(getString(R.string.recharge_success, MiUtils.MiAppPreferences.getCurrencySymbol(), amount),
                            getString(R.string.close), DialogActivity.DIALOG_HIDDEN_ICO);

                } else {
                    MiLog.i("Recharge", "Recharge API Error response " + beanRechargeAccountResponse.toString());
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
