package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.MiUtils;
//import com.tatssense.core.Buckstracks;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FragLogin extends Fragment implements View.OnClickListener{

    @InjectView(R.id.reg_red_et_email)
    EditText mail;
    @InjectView(R.id.reg_red_et_pass)
    EditText pass;
    @InjectView(R.id.signin_btn_logon)
    Button logon;
    MaterialDialog dialog;

    @Inject
    MiApiClient miApiClient;

    OnDialogListener mListener;

    public static final String TAG = FragLogin.class.getSimpleName();
    public static final String TAG_TEST_FLOW = "Login";

    public static FragLogin newInstance(String param1, String param2) {
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
//        executeAuthCall();
//        ((MiApp)getActivity().getApplication()).getAppComponent().inject(this);
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (context instanceof Activity) ? (Activity) context : getActivity();
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

    @OnClick(R.id.signin_btn_logon)
    @Override
    public void onClick(View v) {
//        executeAuthCall();
    }

    public void logon(){

        dialog.show();

        MiUtils.MiAppPreferences.setUserMail(getActivity(), mail.getText().toString());
        MiUtils.MiAppPreferences.setAuthPass(getActivity(), pass.getText().toString());

        MiUtils.startSkillActivity(getActivity(), MainActivity.class);
        getActivity().finish();

        /*BeanUserValid bean = new BeanUserValid(getActivity().getApplicationContext());
        MiLog.i(FragLogin.class.getSimpleName(), "beanUserValid [ " + bean.toString() + " ]");

        MiRestClient.init().userValid(bean, new Callback<BeanUserValidResponse>() {
            @Override
            public void success(BeanUserValidResponse beanUserValidResponse, Response response) {
                dialog.dismiss();
                MiLog.i(FragLogin.class.getSimpleName(), "beanUserValidResponse [ " + beanUserValidResponse.toString() + " ]");
                if (beanUserValidResponse.getStatus()) {
                    Buckstracks.trackCustomEvent(Config.EVENT_LOGIN_ID, Config.EVENT_LOGIN_REGISTER, getActivity());
                    MiUtils.MiAppPreferences.saveLoginUser(beanUserValidResponse, getActivity());
                    MiUtils.startSkillActivity(getActivity(), MainActivity.class);
                    getActivity().finish();
                } else {
                    if (beanUserValidResponse.getResult() != null && beanUserValidResponse.getResult().getMessage() != null){
                        mListener.showDialogErrorCall(
                                beanUserValidResponse.getResult().getMessage(),
                                getString(R.string.close),
                                DialogActivity.DIALOG_HIDDEN_ICO,
                                DialogActivity.REQ_SIGN_IN
                        );
                    }

                    else
                        mListener.showDialogErrorCall(
                                getString(R.string.somethings_goes_wrong),
                                getString(R.string.close),
                                DialogActivity.DIALOG_HIDDEN_ICO,
                                DialogActivity.REQ_SIGN_IN
                        );
                }
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                mListener.showDialogErrorCall(
                        getString(R.string.somethings_goes_wrong),
                        getString(R.string.close),
                        DialogActivity.DIALOG_HIDDEN_ICO,
                        DialogActivity.REQ_SIGN_IN
                );
            }
        });*/
    }

    /*public void executeAuthCall() {
        if(MiUtils.MiAppPreferences.getToken(getActivity()) == null) {
            BeanAuthenticate bean = new BeanAuthenticate(getActivity().getApplicationContext(), MiUtils.MiAppPreferences.AUTHENTICATION_PASS);
            miApiClient.authenticate(bean, new Callback<BeanAuthenticateResponse>() {
                @Override
                public void success(BeanAuthenticateResponse beanAuthenticateResponse, Response response) {
                    MiUtils.MiAppPreferences.setToken(getActivity(), beanAuthenticateResponse.getIdToken());
                    logon();
                }

                @Override
                public void failure(RetrofitError error) {
                    mListener.showDialogErrorCall(
                            getString(R.string.somethings_goes_wrong),
                            getString(R.string.close),
                            DialogActivity.DIALOG_HIDDEN_ICO,
                            DialogActivity.REQ_SIGN_IN
                    );
                }
            });
        }
        else
            logon();
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

//    private boolean isValidInput() {
//
//        if(!mail.getText().toString().matches(MiUtils.REGEX_MAIL)){
//            MiUtils.failureCallWithMsg(getActivity(), getString(R.string.email_not_valid));
//            mail.requestFocus();
//            return false;
//        }else if(!pass.getText().toString(). matches(MiUtils.REGEX_PASSWORD)){
//            MiUtils.failureCallWithMsg(getActivity(), getString(R.string.password_doesnt_match));
//            pass.requestFocus();
//            return false;
//        }
//        return true;
//    }
}
