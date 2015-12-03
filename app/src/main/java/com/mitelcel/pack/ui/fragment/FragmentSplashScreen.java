package com.mitelcel.pack.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.BuildConfig;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.R;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanLogin;
import com.mitelcel.pack.api.bean.req.BeanSubmitAppInfo;
import com.mitelcel.pack.api.bean.resp.BeanLoginResponse;
import com.mitelcel.pack.api.bean.resp.BeanSubmitAppInfoResponse;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.LoginOrRegister;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

//import com.tatssense.core.Buckstracks;

import javax.inject.Inject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class FragmentSplashScreen extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    MaterialDialog dialog;

    public static final String TAG = FragmentSplashScreen.class.getName();

    @Inject
    MiApiClient miApiClient;

    public static FragmentSplashScreen newInstance() {
        return new FragmentSplashScreen();
    }

    public FragmentSplashScreen() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        dialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_splash_screen, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();

        new Handler().postDelayed(() -> autoLogin(), 1500);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MiUtils.MiAppPreferences.registerListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MiUtils.MiAppPreferences.unRegisterListener(this);
    }

    private void submit_app_info(){

        if(MiUtils.MiAppPreferences.getToken().equals("")) {
            BeanSubmitAppInfo beanSubmitAppInfo = new BeanSubmitAppInfo(getActivity().getApplicationContext());
            miApiClient.submit_app_info(beanSubmitAppInfo, new Callback<BeanSubmitAppInfoResponse>() {
                @Override
                public void success(BeanSubmitAppInfoResponse beanSubmitAppInfoResponse, Response response) {
                    if (beanSubmitAppInfoResponse.getResult() != null) {
                        MiUtils.MiAppPreferences.setToken(beanSubmitAppInfoResponse.getResult().getAppToken());
                    }
                    if (BuildConfig.DEBUG) {
                        fakeLogin();
                    }
                    else {
                        MiUtils.startSkillActivity(getActivity(), LoginOrRegister.class);
                        getActivity().finish();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    MiLog.e(TAG, "BeanSubmitAppInfoResponse Error " + error.toString());
                }
            });
        }
    }
    private void autoLogin() {
        int status = MiUtils.MiAppPreferences.getLoggedStatus();
        MiLog.i(TAG, "getLoggedStatus value [" + status + "]");
        if(status == MiUtils.MiAppPreferences.LOGOUT){
            MiUtils.startSkillActivity(getActivity(), LoginOrRegister.class);
            getActivity().finish();
        }
        else if (status == MiUtils.MiAppPreferences.LOGIN_NOT_SET && MiUtils.Info.isNetworkConnected(getActivity())){
            /**
             * Only for first install
             */
            submit_app_info();
        }
        else{
            if(BuildConfig.DEBUG)
                fakeLogin();
            else {
                MiUtils.startSkillActivity(getActivity(), MainActivity.class);
                getActivity().finish();
            }
        }
    }

    private void fakeLogin() {
        String fakeMsisdn = "0000000002";
        String fakePassword = "yasar";
        BeanLogin beanLogin = new BeanLogin(getActivity().getApplicationContext(), fakeMsisdn, fakePassword);
        miApiClient.login(beanLogin, new Callback<BeanLoginResponse>() {
            @Override
            public void success(BeanLoginResponse beanLoginResponse, Response response) {
                if(beanLoginResponse.getError().getCode() == Config.SUCCESS) {
                    MiUtils.MiAppPreferences.setSessionId(beanLoginResponse.getResult().getSessionId());
                    MiUtils.MiAppPreferences.setMsisdn(fakeMsisdn);
                    MiUtils.startSkillActivity(getActivity(), MainActivity.class);
                    getActivity().finish();
                }
                else{
                    MiLog.i(TAG, "Fake Login error response " + beanLoginResponse.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "Fake Login failure " + error.toString());
            }
        });
    }

   @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }
}