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
import com.mitelcel.pack.api.bean.req.BeanDeleteFrequentNumber;
import com.mitelcel.pack.api.bean.req.BeanSetFrequentNumber;
import com.mitelcel.pack.api.bean.resp.BeanDeleteFrequentNumberResponse;
import com.mitelcel.pack.api.bean.resp.BeanSetFrequentNumberResponse;
import com.mitelcel.pack.bean.ui.BeanContactInfo;
import com.mitelcel.pack.ui.fragment.FragmentFrequentNumbers;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FrequentNumbersActivity extends BaseActivity
        implements FragmentFrequentNumbers.OnFrequentNumbersFragmentInteractionListener,
            OnDialogListener
{

    private static final String TAG = FrequentNumbersActivity.class.getName();

    private int order = 0;
    private String msisdn = "";

    MaterialDialog dialog;

    @Inject
    MiApiClient miApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        if (savedInstanceState == null) {
            FragmentHandler.addFragment(getSupportFragmentManager(), FragmentFrequentNumbers.TAG, FragmentFrequentNumbers.newInstance(), R.id.container);
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
    public void onSetFrequentNumberInteraction(String msisdn, int order){
        MiLog.i(TAG, "onSetFrequentNumberInteraction event with " + msisdn + " and order " + order);
        this.msisdn = msisdn;
        this.order = order;

        dialog.show();

        BeanSetFrequentNumber beanSetFrequentNumber;
        beanSetFrequentNumber = new BeanSetFrequentNumber(msisdn, order);

        miApiClient.set_frequent_number(beanSetFrequentNumber, new Callback<BeanSetFrequentNumberResponse>() {
            @Override
            public void success(BeanSetFrequentNumberResponse beanSetFrequentNumberResponse, Response response) {
                dialog.dismiss();
                if (beanSetFrequentNumberResponse.getError().getCode() == Config.SUCCESS) {
                    MiLog.i(TAG, "Set Freq Number API response " + beanSetFrequentNumberResponse.toString());

//                    MiUtils.MiAppPreferences.setSessionId(beanSetFrequentNumberResponse.getResult().getSessionId());
//                    MiUtils.MiAppPreferences.setLastCheckTimestamp();
//                    MiUtils.MiAppPreferences.setCurrentBalance(MiUtils.MiAppPreferences.getCurrentBalance() - amount);

                    showDialogSuccessCall(getString(R.string.frequent_numbers_success, msisdn),
                            getString(R.string.close), DialogActivity.DIALOG_HIDDEN_ICO);

                }
                else if (beanSetFrequentNumberResponse.getError().getCode() == Config.RECORD_EXISTS) {
                    MiLog.i(TAG, "Set Freq Number API Error response " + beanSetFrequentNumberResponse.toString());
                    showDialogErrorCall(getString(R.string.check_input), getString(R.string.close), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
                }
                else {
                    MiLog.i(TAG, "Set Freq Number API Error response " + beanSetFrequentNumberResponse.toString());
                    showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                MiLog.i(TAG, "Set Freq Number failure " + error.toString());
                showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
            }
        });
    }

    @Override
    public void onDeleteFrequentNumberInteraction(String msisdn, int order) {
        MiLog.i(TAG, "onDeleteFrequentNumberInteraction event with " + msisdn + " and order " + order);

        this.msisdn = msisdn;
        this.order = order;

        MiUtils.showDialogQuery(this, getString(R.string.frequent_check, msisdn), getString(R.string.ok), getString(R.string.cancel), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_API_CALL);
    }

    @Override
    public void selectContact(){
        MiUtils.startContactPhonePick(this, Config.PICK_CONTACT);
    }

    private void makeDeleteApiCall() {
        dialog.show();
        BeanDeleteFrequentNumber beanDeleteFrequentNumber = new BeanDeleteFrequentNumber(order);

        miApiClient.delete_frequent_number(beanDeleteFrequentNumber, new Callback<BeanDeleteFrequentNumberResponse>() {
            @Override
            public void success(BeanDeleteFrequentNumberResponse beanDeleteFrequentNumberResponse, Response response) {
                dialog.dismiss();
                if (beanDeleteFrequentNumberResponse.getError().getCode() == Config.SUCCESS) {
                    MiLog.i(TAG, "Delete Freq Number API response " + beanDeleteFrequentNumberResponse.toString());

                    showDialogSuccessCall(getString(R.string.frequent_numbers_success_delete, msisdn),
                            getString(R.string.close), DialogActivity.DIALOG_HIDDEN_ICO);

                    FragmentFrequentNumbers frag = (FragmentFrequentNumbers) getSupportFragmentManager().findFragmentByTag(FragmentFrequentNumbers.TAG);
                    frag.refreshDisplay();
                } else {
                    MiLog.i(TAG, "Delete Freq Number API Error response " + beanDeleteFrequentNumberResponse.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                MiLog.i(TAG, "Delete Freq Number failure " + error.toString());
                showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MiLog.i(TAG, "onActivityResult: requestCode " + requestCode + " resultCode " + resultCode);
        if(requestCode  == DialogActivity.APP_REQ && resultCode == DialogActivity.APP_REFRESH) {
            FragmentFrequentNumbers frag = (FragmentFrequentNumbers) getSupportFragmentManager().findFragmentByTag(FragmentFrequentNumbers.TAG);
            frag.refreshDisplay();
        }
        else if(requestCode  == DialogActivity.APP_API_CALL && resultCode == DialogActivity.APP_REFRESH) {
            makeDeleteApiCall();
        }
        else if(requestCode == Config.PICK_CONTACT && resultCode == RESULT_OK){
            Uri contactUri = data.getData();
            Cursor cursor = getContentResolver().query(contactUri, new String[]{ContactsContract.Contacts.LOOKUP_KEY,
                    ContactsContract.Contacts.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER,
                    ContactsContract.Contacts.PHOTO_URI}, null, null, null);

            BeanContactInfo bean = MiUtils.getContactInfoBean(cursor);
            FragmentFrequentNumbers frag = (FragmentFrequentNumbers) getSupportFragmentManager().findFragmentByTag(FragmentFrequentNumbers.TAG);
            frag.displayContact(bean);
        }
    }

    @Override
    public void showDialogErrorCall(String content, String btnTex, @IdRes int resId, int requestCode) {
        if (requestCode == DialogActivity.APP_REQ) {
            MiUtils.showDialogError(this, content, btnTex, resId, DialogActivity.APP_ERROR_CALL);
        }
    }

    public void showDialogSuccessCall(String content, String btnTex, @IdRes int resId) {
        MiUtils.showDialogSuccess(this, content, btnTex, resId, DialogActivity.APP_REQ);
    }
}
