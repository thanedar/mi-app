package com.mitelcel.pack.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanRequestPin;
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
        FragmentRequestPin.OnRequestPinFragmentInteractionListener
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
                if(beanRequestPinResponse.isResult()){
                    MiLog.i(TAG, "Request Pin success " + beanRequestPinResponse.toString());
                    FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentConfirmPin.TAG, FragmentConfirmPin.newInstance(), R.id.container);
                }
                else {
                    MiLog.i(TAG, "Request Pin failure " + beanRequestPinResponse.toString());
                    showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "Request Pin Error " + error.toString());
                showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
            }
        });
    }
}
