package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
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
import com.mitelcel.pack.dagger.component.FragmentComponent;
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

    @InjectView(R.id.register_tv_pass)
    EditText pass;
    @InjectView(R.id.register_tv_confirm_pass)
    EditText confirm_pass;
    @InjectView(R.id.register_tv_msisdn)
    EditText msisdn;

    MaterialDialog dialog;

    @Inject
    MiApiClient miApiClient;
    @Inject
    Validator validator;

    OnDialogListener mListener;

    public static final String TAG = FragRegister.class.getSimpleName();

    public static FragRegister newInstance() {
        FragRegister fragment = new FragRegister();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragRegister() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, rootView);

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

        return rootView;
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

        MiUtils.startSkillActivity(getActivity(), MainActivity.class);
        getActivity().finish();
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

    public void saveData(){
        MiUtils.MiAppPreferences.setUserMail(msisdn.getText().toString());
        MiUtils.MiAppPreferences.setAuthPass(pass.getText().toString());
    }
}
