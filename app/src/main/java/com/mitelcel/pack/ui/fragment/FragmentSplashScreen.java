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
import com.mitelcel.pack.ui.TutorialActivity;
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

        new Handler().postDelayed(() -> autoLogin(), 1000);
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
        BeanSubmitAppInfo beanSubmitAppInfo = new BeanSubmitAppInfo(getActivity().getApplicationContext());
        MiLog.i(TAG, "beanSubmitAppInfo: " + beanSubmitAppInfo.toString());
        miApiClient.submit_app_info(beanSubmitAppInfo, new Callback<BeanSubmitAppInfoResponse>() {
            @Override
            public void success(BeanSubmitAppInfoResponse beanSubmitAppInfoResponse, Response response) {
                if(beanSubmitAppInfoResponse != null) {
                    MiLog.i(TAG, "beanSubmitAppInfoResponse: " + beanSubmitAppInfoResponse.toString());
                    if (beanSubmitAppInfoResponse.getResult() != null) {
                        MiUtils.MiAppPreferences.setToken(beanSubmitAppInfoResponse.getResult().getAppToken());
                        MiUtils.startSkillActivity(getActivity(), LoginOrRegister.class);
                        getActivity().finish();
                    } else {
                        MiLog.e(TAG, "BeanSubmitAppInfoResponse Result Error");
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.e(TAG, "BeanSubmitAppInfoResponse Error " + error.toString());
            }
        });
    }

    public void autoLogin() {
        if(MiUtils.Info.isNetworkConnected(getActivity().getApplicationContext())) {
            int status = MiUtils.MiAppPreferences.getLoggedStatus();
            MiLog.i(TAG, "getLoggedStatus value [" + status + "]");
            // User has logged out of app
            if(status == MiUtils.MiAppPreferences.LOGOUT){
                MiLog.d(TAG, "LoginOrRegister");
                MiUtils.startSkillActivity(getActivity(), LoginOrRegister.class);
                getActivity().finish();
            }
            // Login status not set in app preferences
            else if (status == MiUtils.MiAppPreferences.LOGIN_NOT_SET){
                if(MiUtils.MiAppPreferences.getToken().equals("")) {
                    /** Only for first launch */
                    MiLog.d(TAG, "Calling submit_app_info");
                    submit_app_info();
                }
                else {
                    MiUtils.startSkillActivity(getActivity(), TutorialActivity.class);
                    getActivity().finish();
                }
            }
            // User has previously logged in so we take them to home activity
            else{
                /*MiLog.d(TAG, "fakeLogin");
                fakeLogin();*/

                MiUtils.startSkillActivity(getActivity(), MainActivity.class);
                getActivity().finish();
            }
        }
    }

    private void fakeLogin() {
        String fakeMsisdn;
        String fakePassword;

        if(BuildConfig.DEBUG) {
            fakeMsisdn = "0000000001";
            fakePassword = "blabla";
        }
        else {
            fakeMsisdn = "0000000002";
            fakePassword = "yasar";
        }

        BeanLogin beanLogin = new BeanLogin(getActivity().getApplicationContext(), fakeMsisdn, fakePassword);
        MiLog.i(TAG, "beanLogin: " + beanLogin.toString());
        miApiClient.login(beanLogin, new Callback<BeanLoginResponse>() {
            @Override
            public void success(BeanLoginResponse beanLoginResponse, Response response) {
                if (beanLoginResponse != null) {
                    MiLog.i(TAG, "beanLoginResponse: " + beanLoginResponse.toString());
                    if (beanLoginResponse.getError().getCode() == Config.SUCCESS) {
                        MiUtils.MiAppPreferences.setSessionId(beanLoginResponse.getResult().getSessionId());
                        MiUtils.MiAppPreferences.setMsisdn(fakeMsisdn);
                        MiUtils.startSkillActivityClearStack(getActivity(), MainActivity.class);
                        getActivity().finish();
                    } else {
                        MiLog.i(TAG, "Fake Login error response " + beanLoginResponse.toString());
                    }
                }
                else
                    MiLog.i(TAG, "Fake Login response is NULL");
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