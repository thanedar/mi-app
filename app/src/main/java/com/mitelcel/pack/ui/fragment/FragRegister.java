package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.dagger.component.DaggerFragmentComponent;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.MiUtils;
import com.mitelcel.pack.utils.Validator;
//import com.tatssense.core.Buckstracks;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FragRegister extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @InjectView(R.id.signup_et_pass)
    EditText pass;
    @InjectView(R.id.signup_et_confirm_pass)
    EditText confirm_pass;
    @InjectView(R.id.signup_et_email)
    EditText email;

    MaterialDialog dialog;

    @Inject
    MiApiClient miApiClient;
    @Inject
    Validator validator;

    OnDialogListener mListener;

    public static final String TAG = FragRegister.class.getSimpleName();
    public static final String TAG_TEST_FLOW = "SignUp";

    // TODO: Rename and change types and number of parameters
    public static FragRegister newInstance(String param1, String param2) {
        FragRegister fragment = new FragRegister();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public FragRegister() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        dialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        DaggerFragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, rootView);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().matches(MiUtils.REGEX_MAIL)) {
                    email.setTextColor(getActivity().getResources().getColor(R.color.red));
                } else
                    email.setTextColor(getActivity().getResources().getColor(R.color.dark_grey_more));
            }
        });

        return rootView;
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void submit(){

        if(!isValidInput())
            return;

        dialog.show();

        saveData();

        //prendo i parametri per il signup
        String pass = MiUtils.MiAppPreferences.getAuthPass(getActivity());
        String email = MiUtils.MiAppPreferences.getUserMail(getActivity());

        MiUtils.startSkillActivity(getActivity(), MainActivity.class);
        getActivity().finish();

        /*BeanActionCreateUser bean = new BeanActionCreateUser(getActivity().getApplicationContext(), pass, email, referralCode);
        MiLog.i(FragRegister.class.getSimpleName(), "beanActionCreateUserRequest [ " + bean.toString() + " ]");

        MiRestClient.init().actionCreateUser(bean, new Callback<BeanActionCreateUserResponse>() {
            @Override
            public void success(BeanActionCreateUserResponse beanActionCreateUserResponse, Response response) {
                dialog.dismiss();
                MiLog.i(FragRegister.class.getSimpleName(), "beanActionCreateUserResponse [ " + beanActionCreateUserResponse.toString() + " ]");
                if (beanActionCreateUserResponse.getStatus()) {
                    // event custom
                    Buckstracks.trackCustomEvent(Config.EVENT_NEW_USER_ID, Config.EVENT_NEW_USER_REGISTER, getActivity());

                    MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + " authcall ok");
                    executeValidateUser();

                    MiUtils.MiAppPreferences.saveDataRegisterUser(beanActionCreateUserResponse, getActivity());
                    MiUtils.startSkillActivity(getActivity(), MainActivity.class);
                    getActivity().finish();
                } else {
                    if (beanActionCreateUserResponse.getResult() != null && beanActionCreateUserResponse.getResult().getMessage() != null) {
                        MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + "CreateUser KO Status");
                        mListener.showDialogErrorCall(
                                beanActionCreateUserResponse.getResult().getMessage(),
                                getString(R.string.close),
                                R.string.close,
                                DialogActivity.REQ_SIGN_UP);
                    } else {
                        MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + "CreateUser KO Empty");
                        mListener.showDialogErrorCall(
                                getString(R.string.somethings_goes_wrong),
                                getString(R.string.close),
                                R.string.close,
                                DialogActivity.REQ_SIGN_UP
                        );
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + "CreateUser KO Failure");
                dialog.dismiss();
                mListener.showDialogErrorCall(
                        getString(R.string.somethings_goes_wrong),
                        getString(R.string.close),
                        R.string.close,
                        DialogActivity.REQ_SIGN_UP
                );
            }
        });*/
    }

    public void executeValidateUser() {
        /*MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + " validateuser");
        BeanUserValid bean = new BeanUserValid(getActivity().getApplicationContext());
        miApiClient.userValid(bean, new Callback<BeanUserValidResponse>() {
            @Override
            public void success(BeanUserValidResponse beanUserValidResponse, Response response) {
                MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + " validateuser ok - " + beanUserValidResponse);
                dialog.dismiss();
            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                MiUtils.failureCallWithMsgCloseActivity(getActivity(), getString(R.string.somethings_goes_wrong));
            }
        });*/
    }

    private boolean isValidInput() {

        String msg = validator.isValidEmail(email.getText().toString());
        if(msg != null){
            email.setError(msg);
            return false;
        }
        msg = validator.isValidPassSignUp(pass.getText().toString());
        if(msg != null){
            pass.setError(msg);
            return false;
        }
        msg = validator.doesPassMatchSignUp(pass.getText().toString(), confirm_pass.getText().toString());
        if(msg != null){
            confirm_pass.setError(msg);
            return false;
        }
        return true;
    }


    @OnClick(R.id.signup_btn_submit)
    @Override
    public void onClick(View v) {
        submit();
    }

    public void executeAuthCall() {
        /*if(MiUtils.MiAppPreferences.getToken(getActivity()) == null) {
            dialog.show();
            BeanAuthenticate bean = new BeanAuthenticate(getActivity().getApplicationContext(), MiUtils.MiAppPreferences.AUTHENTICATION_PASS);
            MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + "Auth");
            miApiClient.authenticate(bean, new Callback<BeanAuthenticateResponse>() {
                @Override
                public void success(BeanAuthenticateResponse beanAuthenticateResponse, Response response) {
                    dialog.dismiss();
                    MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + "Auth OK");
                    MiUtils.MiAppPreferences.setToken(getActivity(), beanAuthenticateResponse.getIdToken());
                    submit();
                }

                @Override
                public void failure(RetrofitError error) {
                    dialog.dismiss();
                    mListener.showDialogErrorCall(
                            getString(R.string.somethings_goes_wrong),
                            getString(R.string.close),
                            R.string.close,
                            DialogActivity.REQ_SIGN_UP
                    );
                    MiLog.i(FragRegister.class.getName(), TAG_TEST_FLOW + "Auth KO - Error[ " + error + " ]");
                }
            });
        }
        else
            submit();*/
    }

    public void saveData(){
        // salvo la i parametri per il signup
        MiUtils.MiAppPreferences.setUserMail(getActivity(), email.getText().toString());
        MiUtils.MiAppPreferences.setAuthPass(getActivity(), pass.getText().toString());
    }
}
