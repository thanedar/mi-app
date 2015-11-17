package com.mitelcel.pack.ui;

import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.fragment.FragConfirm;
import com.mitelcel.pack.ui.fragment.FragHelp;
import com.mitelcel.pack.ui.fragment.FragRecharge;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import java.net.URI;

public class RechargeActivity extends BaseActivity
        implements FragConfirm.OnConfirmFragmentInteractionListener,
            FragRecharge.OnRechargeFragmentInteractionListener,
            OnDialogListener
{

    private static final String TAG = RechargeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge);

        if (savedInstanceState == null) {
            FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragRecharge.TAG, FragRecharge.newInstance(), R.id.container);
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
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
    public void onRechargeFragmentInteraction(int recharge){
        MiLog.i(TAG, "onRechargeFragmentInteraction event with " + recharge);

        String display = String.format(getString(R.string.recharge_check), MiUtils.MiAppPreferences.getCurrencySymbol(),recharge);
        FragmentHandler.addFragmentInBackStackWithAnimation(
                getSupportFragmentManager(),
                "RECHARGE",
                FragConfirm.class.getName(),
                FragConfirm.newInstance(display, String.valueOf(recharge)),
                R.id.container);
    }

    @Override
    public void onConfirmFragmentInteraction() {
        MiLog.i(TAG, "onConfirmFragmentInteraction event started");
    }

    @Override
    public void showDialogErrorCall(String content, String btnTex, @IdRes int resId, int requestCode) {
        if (requestCode == DialogActivity.APP_REQ || requestCode == DialogActivity.APP_REFRESH) {
            MiUtils.showDialogError(this, content, btnTex, resId, DialogActivity.APP_ERROR_CALL);
        }
    }
}
