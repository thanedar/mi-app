package com.mitelcel.pack.ui;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.annotation.IdRes;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanTransferBalance;
import com.mitelcel.pack.api.bean.resp.BeanTransferBalanceResponse;
import com.mitelcel.pack.bean.ui.BeanContactInfo;
import com.mitelcel.pack.ui.fragment.FragmentConfirm;
import com.mitelcel.pack.ui.fragment.FragmentTransfer;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TransferActivity extends BaseActivity
        implements FragmentConfirm.OnConfirmFragmentInteractionListener,
            FragmentTransfer.OnTransferFragmentInteractionListener,
            OnDialogListener
{

    private static final String TAG = TransferActivity.class.getName();

    private float amount = 0;
    private String msisdn = "";
    private String name = "";

    MaterialDialog dialog;

    @Inject
    MiApiClient miApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        if (savedInstanceState == null) {
            FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragmentTransfer.TAG, FragmentTransfer.newInstance(), R.id.container);
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
    public void onTransferFragmentInteraction(String msisdn, float amount, String name){
        MiLog.i(TAG, "onTransferFragmentInteraction event with " + amount + " for target " + msisdn);
        this.amount = amount;
        this.msisdn = msisdn;

        String display;
        if(name != ""){
            this.name = name;
            display = getString(R.string.transfer_check, MiUtils.MiAppPreferences.getCurrencySymbol(), amount, name);
        }
        else {
            display = getString(R.string.transfer_check, MiUtils.MiAppPreferences.getCurrencySymbol(), amount, msisdn);
        }

        FragmentHandler.addFragmentInBackStackWithAnimation(
                getSupportFragmentManager(),
                "Transfer",
                FragmentConfirm.class.getName(),
                FragmentConfirm.newInstance(display, String.valueOf(amount)),
                R.id.container);
    }

    @Override
    public void onConfirmFragmentInteraction(String password) {
        dialog.show();
        MiLog.i(TAG, "onConfirmFragmentInteraction event started");

        BeanTransferBalance beanTransferBalance;
        if(password.equals("")) {
            beanTransferBalance = new BeanTransferBalance(msisdn, amount);
        }
        else {
            beanTransferBalance = new BeanTransferBalance(msisdn, amount, password);
        }

        miApiClient.transfer_balance(beanTransferBalance, new Callback<BeanTransferBalanceResponse>() {
            @Override
            public void success(BeanTransferBalanceResponse beanTransferBalanceResponse, Response response) {
                dialog.dismiss();

                if (beanTransferBalanceResponse != null) {
                    if (beanTransferBalanceResponse.getError().getCode() == Config.SUCCESS) {
                        MiLog.i("Transfer", "Transfer API response " + beanTransferBalanceResponse.toString());

                        MiUtils.MiAppPreferences.setSessionId(beanTransferBalanceResponse.getResult().getSessionId());
                        MiUtils.MiAppPreferences.setLastCheckTimestamp();
                        MiUtils.MiAppPreferences.setCurrentBalance(MiUtils.MiAppPreferences.getCurrentBalance() - amount);

                        if (name != "") {
                            showDialogSuccessCall(getString(R.string.transfer_success, MiUtils.MiAppPreferences.getCurrencySymbol(), amount, name),
                                    getString(R.string.close), DialogActivity.DIALOG_HIDDEN_ICO);
                        } else {
                            showDialogSuccessCall(getString(R.string.transfer_success, MiUtils.MiAppPreferences.getCurrencySymbol(), amount, msisdn),
                                    getString(R.string.close), DialogActivity.DIALOG_HIDDEN_ICO);
                        }

                    } else {
                        MiLog.i("Transfer", "Transfer API Error response " + beanTransferBalanceResponse.toString());
                        showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
                    }
                } else {
                    MiLog.i("Transfer", "Transfer API NULL response");
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

    @Override
    public void selectContact() {
        MiUtils.startContactPhonePick(this, Config.PICK_CONTACT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MiLog.i(TAG, "onActivityResult: requestCode " + requestCode + " resultCode " + resultCode);
        if(requestCode  == DialogActivity.APP_RES && resultCode == DialogActivity.APP_REFRESH)
            finish();
        else if(requestCode == Config.PICK_CONTACT && resultCode == RESULT_OK){
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, new String[]{ContactsContract.Contacts.LOOKUP_KEY,
                    ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.Contacts.PHOTO_URI}, null, null, null);


            BeanContactInfo contactInfo = MiUtils.getContactInfoBean(cursor);
            FragmentTransfer frag = (FragmentTransfer) getSupportFragmentManager().findFragmentByTag(FragmentTransfer.TAG);
            frag.displayContact(contactInfo);
        }
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
