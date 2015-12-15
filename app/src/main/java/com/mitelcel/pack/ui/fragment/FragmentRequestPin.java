package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
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
import com.mitelcel.pack.api.bean.req.BeanRequestPin;
import com.mitelcel.pack.api.bean.resp.BeanRequestPinResponse;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;
import com.mitelcel.pack.utils.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class FragmentRequestPin extends Fragment{

    @InjectView(R.id.register_tv_msisdn)
    EditText msisdn;

    MaterialDialog dialog;

    @Inject
    MiApiClient miApiClient;
    @Inject
    Validator validator;

    OnDialogListener mListener;

    public static final String TAG = FragmentRequestPin.class.getSimpleName();

    public static FragmentRequestPin newInstance() {
        return new FragmentRequestPin();
    }

    public FragmentRequestPin() {
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
        View rootView = inflater.inflate(R.layout.fragment_register_request, container, false);
        ButterKnife.inject(this, rootView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            msisdn.addTextChangedListener(new PhoneNumberFormattingTextWatcher("MX"));
        else
            msisdn.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

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

    @OnClick(R.id.register_request_btn)
    public void onRequestPin(View v) {
        if(!isValidInput())
            return;

        dialog.show();

        BeanRequestPin beanRequestPin = new BeanRequestPin(msisdn.getText().toString());

        miApiClient.request_pin(beanRequestPin, new Callback<BeanRequestPinResponse>() {
            @Override
            public void success(BeanRequestPinResponse beanRequestPinResponse, Response response) {
                MiLog.i(TAG, "Request Pin success " + beanRequestPinResponse.toString());
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "Request Pin Error " + error.toString());
            }
        });

        MiUtils.MiAppPreferences.setMsisdn(msisdn.getText().toString());

        MiUtils.startSkillActivity(getActivity(), MainActivity.class);
        getActivity().finish();

    }

    private boolean isValidInput() {

        String msg = validator.isNumberValid(msisdn.getText().toString());
        if(msg != null){
            msisdn.setError(msg);
            return false;
        }

        return true;
    }

}
