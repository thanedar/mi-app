package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.utils.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FragmentRequestPin extends Fragment{

    @InjectView(R.id.register_tv_msisdn)
    EditText msisdn;

    @InjectView(R.id.label_register)
    TextView viewLabel;
    @InjectView(R.id.request_details)
    TextView viewDetails;

    @Inject
    Validator validator;

    private OnRequestPinFragmentInteractionListener interactionListener;

    public static final String TAG = FragmentRequestPin.class.getSimpleName();

    private static final String ARG_TYPE = "type";
    private String mType;

    public static FragmentRequestPin newInstance(String type) {
        FragmentRequestPin fragmentRequestPin = new FragmentRequestPin();
        Bundle args = new Bundle();
        args.putString(ARG_TYPE, type);
        fragmentRequestPin.setArguments(args);

        return fragmentRequestPin;
    }

    public FragmentRequestPin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            mType = getArguments().getString(ARG_TYPE);
        }
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_request_pin, container, false);
        ButterKnife.inject(this, rootView);

        if(mType.equals("pwreset")){
            viewLabel.setText(getString(R.string.reset_password));
            viewDetails.setText(R.string.request_pin_reset);
        }
        else if(mType.equals("register")){
            viewLabel.setText(getString(R.string.register));
            viewDetails.setText(R.string.request_pin_register);
        }

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
            interactionListener = (OnRequestPinFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRequestPinFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
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

        interactionListener.onRequestPinSubmit(msisdn.getText().toString(), mType);
    }

    private boolean isValidInput() {

        String msg = validator.isNumberValid(msisdn.getText().toString());
        if(msg != null){
            msisdn.setError(msg);
            return false;
        }

        return true;
    }

    public interface OnRequestPinFragmentInteractionListener {
        void onRequestPinSubmit(String msisdn, String reason);
    }
}
