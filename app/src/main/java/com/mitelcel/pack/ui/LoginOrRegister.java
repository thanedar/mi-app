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
import com.mitelcel.pack.bean.api.request.BeanRequestPin;
import com.mitelcel.pack.bean.api.request.BeanConfirmPin;
import com.mitelcel.pack.bean.api.request.BeanUpdateUserInfo;
import com.mitelcel.pack.bean.api.response.BeanConfirmPinResponse;
import com.mitelcel.pack.bean.api.response.BeanRequestPinResponse;
import com.mitelcel.pack.bean.api.response.BeanUpdateUserInfoResponse;
import com.mitelcel.pack.ui.fragment.FragmentChangePassword;
import com.mitelcel.pack.ui.fragment.FragmentConfirmPin;
import com.mitelcel.pack.ui.fragment.FragmentLogin;
import com.mitelcel.pack.ui.fragment.FragmentLoginOrRegister;
import com.mitelcel.pack.ui.fragment.FragmentRegister;
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
        FragmentRequestPin.OnRequestPinFragmentInteractionListener,
        FragmentConfirmPin.OnConfirmPinFragmentInteractionListener,
        FragmentChangePassword.OnChangePasswordFragmentInteractionListener
{

    public static String BACK_STACK_NAME = "back_stack";
    public static String TAG = LoginOrRegister.class.getSimpleName();

    private int errorCode = 0;

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
        MiLog.i(TAG, "clickWidgetOnFragment start errorCode " + errorCode + " with view " + view.getId());
        switch (view.getId()) {
            case R.id.login_new_user://1
            case R.id.login_or_register_register://1
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragmentRequestPin.TAG, FragmentRequestPin.newInstance("register"), R.id.container);
                break;
            case R.id.login_forgot:
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragmentRequestPin.TAG, FragmentRequestPin.newInstance("pwreset"), R.id.container);
                break;
            case R.id.register_already_reg://2
            case R.id.login_or_register_login://2
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragmentLogin.TAG, FragmentLogin.newInstance(), R.id.container);
                break;
            /*case R.id.reset_password:
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragmentChangePassword.TAG, FragmentChangePassword.newInstance(), R.id.container);
                break;*/
            default:
                FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragmentLoginOrRegister.TAG, FragmentLoginOrRegister.newInstance(), R.id.container);
        }
    }

    @Override
    public void showDialogErrorCall(String content, String btnTex, @IdRes int resId, int requestCode) {
        if (requestCode == DialogActivity.REQ_SIGN_UP || requestCode == DialogActivity.REQ_SIGN_IN) {
            MiUtils.showDialogError(this, content, btnTex, resId, DialogActivity.APP_ERROR_CALL);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DialogActivity.APP_ERROR_CALL) {
            MiLog.i(TAG, "onActivityResult start errorCode " + errorCode);
            switch (errorCode) {
                case Config.USER_DOES_NOT_EXISTS:
                    MiLog.i(TAG, "onActivityResult errorCode USER_DOES_NOT_EXISTS");
                    FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentRegister.TAG, FragmentRegister.newInstance(), R.id.container);
                    break;
                case Config.USER_EXISTS:
                    MiLog.i(TAG, "onActivityResult errorCode USER_EXISTS");
                    FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentLogin.TAG, FragmentLogin.newInstance(), R.id.container);
                    break;
                default:
                    MiLog.i(TAG, "onActivityResult default errorCode " + errorCode);
            }
            errorCode = 0;
        }
        else
            MiUtils.MiAppPreferences.clear();
    }

    @Override
    public void onRequestPinSubmit(String msisdn, String reason) {
        MiLog.i(TAG, "onRequestPinSubmit called with " + msisdn + " and reason " + reason);

        dialog.show();

        BeanRequestPin beanRequestPin = new BeanRequestPin(msisdn, reason);

        miApiClient.request_pin(beanRequestPin, new Callback<BeanRequestPinResponse>() {
            @Override
            public void success(BeanRequestPinResponse beanRequestPinResponse, Response response) {
                dialog.hide();
                errorCode = beanRequestPinResponse.getError().getCode();
                if(errorCode == Config.SUCCESS){
                    MiLog.i(TAG, "Request Pin success " + beanRequestPinResponse.toString());
                    MiUtils.MiAppPreferences.setMsisdn(MiUtils.getCleanMsisdn(msisdn));
                    FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentConfirmPin.TAG, FragmentConfirmPin.newInstance(), R.id.container);
                }
                else if(errorCode == Config.USER_DOES_NOT_EXISTS) {
                    MiLog.i(TAG, "Request Pin failure " + beanRequestPinResponse.toString());
                    showDialogErrorCall(getString(R.string.user_not_found), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
                }
                else if(errorCode == Config.USER_EXISTS){
                    MiLog.i(TAG, "Request Pin failure " + beanRequestPinResponse.toString());
                    showDialogErrorCall(getString(R.string.user_found), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
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
                errorCode = beanConfirmPinResponse.getError().getCode();
                if (errorCode == Config.SUCCESS && beanConfirmPinResponse.getResult() != null) {
                    MiLog.i(TAG, "Confirm Pin success " + beanConfirmPinResponse.toString());
                    MiUtils.MiAppPreferences.setSessionId(beanConfirmPinResponse.getResult().getSessionId());
                    MiUtils.MiAppPreferences.setLogin();
                    FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentChangePassword.TAG, FragmentChangePassword.newInstance(), R.id.container);
                } else if (errorCode == Config.INVALID_PARAMS) {
                    MiLog.i(TAG, "Confirm Pin params failure " + beanConfirmPinResponse.toString());
                    showDialogErrorCall(getString(R.string.check_input), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
                } else {
                    MiLog.i(TAG, "Confirm Pin failure " + beanConfirmPinResponse.toString());
                    showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.hide();
                MiLog.i(TAG, "Confirm Pin Error " + error.toString());
                showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
            }
        });
    }

    @Override
    public void onChangePasswordSubmit(String password) {
        MiLog.i(TAG, "onChangePasswordSubmit called with " + password);

        dialog.show();

        BeanUpdateUserInfo beanUpdateUserInfo = new BeanUpdateUserInfo(password);

        miApiClient.update_user_info(beanUpdateUserInfo, new Callback<BeanUpdateUserInfoResponse>() {
            @Override
            public void success(BeanUpdateUserInfoResponse beanUpdateUserInfoResponse, Response response) {
                dialog.hide();
                errorCode = beanUpdateUserInfoResponse.getError().getCode();
                if (errorCode == Config.SUCCESS) {
                    MiLog.i(TAG, "Change Password success " + beanUpdateUserInfoResponse.toString());
                    MiUtils.MiAppPreferences.setSessionId(beanUpdateUserInfoResponse.getResult().getSessionId());
                    MiUtils.startSkillActivityClearStack(getApplicationContext(), MainActivity.class);
                    finish();
                } else if (errorCode == Config.INVALID_PARAMS) {
                    MiLog.i(TAG, "Change Password failure " + beanUpdateUserInfoResponse.toString());
                    showDialogErrorCall(getString(R.string.check_input), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
                } else {
                    MiLog.i(TAG, "Change Password failure " + beanUpdateUserInfoResponse.toString());
                    showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.hide();
                MiLog.i(TAG, "Change Password Error " + error.toString());
                showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.REQ_SIGN_UP);
            }
        });
    }

    @Override
    public void onChangePasswordSkip(){
        MiUtils.startSkillActivityClearStack(getApplicationContext(), MainActivity.class);
        finish();
    }
}
