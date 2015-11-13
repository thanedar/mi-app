package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanLogin;
import com.mitelcel.pack.api.bean.resp.BeanLoginResponse;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.DialogActivity;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;
import com.mitelcel.pack.utils.Validator;
//import com.tatssense.core.Buckstracks;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragLogin extends Fragment implements View.OnClickListener{

    @InjectView(R.id.login_tv_msisdn)
    EditText msisdn;
    @InjectView(R.id.login_tv_pass)
    EditText pass;
    @InjectView(R.id.login_btn_logon)
    Button logon;

    @Inject
    MiApiClient miApiClient;
    @Inject
    Validator validator;

    MaterialDialog dialog;
    OnDialogListener mListener;

    public static final String TAG = FragLogin.class.getSimpleName();

    public static FragLogin newInstance() {
        FragLogin fragment = new FragLogin();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);

        msisdn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches(MiUtils.REGEX_MSISDN)) {
                    msisdn.setTextColor(getResources().getColor(R.color.red));
                } else
                    msisdn.setTextColor(getResources().getColor(R.color.dark_grey_more));
            }
        });

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnDialogListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick(R.id.login_btn_logon)
    @Override
    public void onClick(View v) {
        logon();
    }

    public void logon(){

        if(!isValidInput())
            return;

        dialog.show();

        BeanLogin beanLogin = new BeanLogin(getActivity(), msisdn.getText().toString(), pass.getText().toString());
        miApiClient.login(beanLogin, new Callback<BeanLoginResponse>() {
            @Override
            public void success(BeanLoginResponse beanLoginResponse, Response response) {
                dialog.dismiss();
//                MiLog.i(TAG, "Login response " + beanLoginResponse.toString());
//                MiLog.i(TAG, "Login Session Id " + beanLoginResponse.getResult().getSessionId());
                if (beanLoginResponse.getError().getCode() == Config.SUCCESS) {
                    MiUtils.MiAppPreferences.setSessionId(beanLoginResponse.getResult().getSessionId());

                    MiUtils.MiAppPreferences.setMsisdn(msisdn.getText().toString());
                    MiUtils.MiAppPreferences.setAuthPass(pass.getText().toString());
                    MiUtils.MiAppPreferences.setLogin();

                    MiUtils.startSkillActivity(getActivity(), MainActivity.class);
                    getActivity().finish();
                } else {
                    MiLog.i(TAG, "Login API error response " + beanLoginResponse.toString());
                    dialog.dismiss();
                    mListener.showDialogErrorCall(
                            String.format(getString(R.string.login_failed), beanLoginResponse.getError().getCode()),
                            getString(R.string.close),
                            DialogActivity.DIALOG_HIDDEN_ICO,
                            DialogActivity.REQ_SIGN_IN
                    );
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "Login failure " + error.toString());
                dialog.dismiss();
                mListener.showDialogErrorCall(
                        getString(R.string.somethings_goes_wrong),
                        getString(R.string.close),
                        DialogActivity.DIALOG_HIDDEN_ICO,
                        DialogActivity.REQ_SIGN_IN
                );
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    private boolean isValidInput() {

        String msg = validator.isNumberValid(msisdn.getText().toString());
        if(msg != null){
            msisdn.setError(msg);
            return false;
        }
        msg = validator.isValidPassSignUp(pass.getText().toString());
        if(msg != null){
            pass.setError(msg);
            return false;
        }
        return true;
    }
}
