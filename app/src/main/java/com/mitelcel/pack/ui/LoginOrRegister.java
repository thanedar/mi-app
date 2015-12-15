package com.mitelcel.pack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanRequestPin;
import com.mitelcel.pack.api.bean.req.BeanConfirmPin;
import com.mitelcel.pack.api.bean.resp.BeanConfirmPinResponse;
import com.mitelcel.pack.api.bean.resp.BeanRequestPinResponse;
import com.mitelcel.pack.ui.fragment.FragmentConfirmPin;
import com.mitelcel.pack.ui.fragment.FragmentLogin;
import com.mitelcel.pack.ui.fragment.FragmentLoginOrRegister;
import com.mitelcel.pack.ui.fragment.FragmentRequestPin;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginOrRegister extends BaseActivityLogin implements OnDialogListener,
        FragmentRequestPin.OnRequestPinFragmentInteractionListener, FragmentConfirmPin.OnConfirmPinFragmentInteractionListener
{

    public static String BACK_STACK_NAME = "back_stack";
    public static String TAG = LoginOrRegister.class.getSimpleName();

    @Inject
    MiApiClient miApiClient;

    MaterialDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        if (savedInstanceState == null) {
            int status = MiUtils.MiAppPreferences.getLoggedStatus();
            MiLog.i(TAG, "getLoggedStatus value [" + status + "]");
            if(status == MiUtils.MiAppPreferences.LOGOUT) {
                FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragmentLogin.TAG, FragmentLogin.newInstance(), R.id.container);
            }
            else{
                FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragmentLoginOrRegister.TAG, FragmentLoginOrRegister.newInstance(), R.id.container);
            }
        }

        dialog = new MaterialDialog.Builder(this)
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        ((MiApp)getApplication()).getAppComponent().inject(this);
    }

    public void clickWidgetOnFragment(View view) {
        switch (view.getId()) {
            case R.id.login_new_user://1
            case R.id.login_or_register_register://1
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragmentRequestPin.TAG, FragmentRequestPin.newInstance(), R.id.container);
                break;
            case R.id.register_already_reg://2
            case R.id.login_or_register_login://2
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragmentLogin.TAG, FragmentLogin.newInstance(), R.id.container);
                break;
        }
    }

    @Override
    public void showDialogErrorCall(String content, String btnTex, @IdRes int resId, int requestCode) {
        if (requestCode == DialogActivity.REQ_SIGN_UP || requestCode == DialogActivity.REQ_SIGN_IN) {
            MiUtils.showDialogError(this, content, btnTex, resId, DialogActivity.APP_ERROR_CALL);
            MiUtils.MiAppPreferences.clear();
        }
    }

    @Override
    public void onRequestPinSubmit(String msisdn) {
        MiLog.i(TAG, "onRequestPinSubmit called with " + msisdn);

        dialog.show();

        BeanRequestPin beanRequestPin = new BeanRequestPin(msisdn);

        miApiClient.request_pin(beanRequestPin, new Callback<BeanRequestPinResponse>() {
            @Override
            public void success(BeanRequestPinResponse beanRequestPinResponse, Response response) {
                dialog.hide();
                if(beanRequestPinResponse.isResult()){
                    MiLog.i(TAG, "Request Pin success " + beanRequestPinResponse.toString());
                    MiUtils.MiAppPreferences.setMsisdn(MiUtils.getCleanMsisdn(msisdn));
                    FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentConfirmPin.TAG, FragmentConfirmPin.newInstance(), R.id.container);
                }
                else {
                    MiLog.i(TAG, "Request Pin failure " + beanRequestPinResponse.toString());
                    showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.hide();
                MiLog.i(TAG, "Request Pin Error " + error.toString());
                showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
            }
        });
    }


    @Override
    public void onConfirmPinSubmit(String pin) {
        MiLog.i(TAG, "onConfirmPinSubmit called with " + pin);

        dialog.show();

        BeanConfirmPin beanConfirmPin = new BeanConfirmPin(pin);

        miApiClient.confirm_pin(beanConfirmPin, new Callback<BeanConfirmPinResponse>() {
            @Override
            public void success(BeanConfirmPinResponse beanConfirmPinResponse, Response response) {
                dialog.hide();
                if (beanConfirmPinResponse.getError().getCode() == Config.SUCCESS && beanConfirmPinResponse.getResult() != null) {
                    MiLog.i(TAG, "Confirm Pin success " + beanConfirmPinResponse.toString());
                    MiUtils.MiAppPreferences.setSessionId(beanConfirmPinResponse.getResult().getSessionId());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                } else if (beanConfirmPinResponse.getError().getCode() == Config.INVALID_PARAMS) {
                    MiLog.i(TAG, "Confirm Pin success " + beanConfirmPinResponse.toString());
                    showDialogErrorCall(getString(R.string.check_input), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
                } else {
                    MiLog.i(TAG, "Confirm Pin failure " + beanConfirmPinResponse.toString());
                    showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.hide();
                MiLog.i(TAG, "Request Pin Error " + error.toString());
                showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
            }
        });
    }
}
