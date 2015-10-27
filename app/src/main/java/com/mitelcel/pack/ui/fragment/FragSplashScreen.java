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
import com.mitelcel.pack.R;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

//import com.tatssense.core.Buckstracks;

import javax.inject.Inject;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class FragSplashScreen extends Fragment implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    MaterialDialog dialog;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public static final String TAG_TEST_FLOW = "SplashScreen";

    @Inject
    MiApiClient miApiClient;

    // TODO: Rename and change types and number of parameters
    public static FragSplashScreen newInstance(String param1, String param2) {
        FragSplashScreen fragment = new FragSplashScreen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragSplashScreen() {
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                autoLogin();
            }
        }, 1500);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        MiUtils.MiAppPreferences.registerListener(this, getActivity().getApplicationContext());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        MiUtils.MiAppPreferences.unRegisterListener(this, getActivity().getApplicationContext());
    }

    private void autoLogin() {
        MiLog.i(FragSplashScreen.class.getName(), "getLogout value [" + MiUtils.MiAppPreferences.getLogout(getActivity()) + "]");
        MiUtils.startSkillActivity(getActivity(), MainActivity.class);
        getActivity().finish();
    }

   @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
    }
}