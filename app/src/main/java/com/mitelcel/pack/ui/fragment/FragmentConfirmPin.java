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

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.utils.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FragmentConfirmPin extends Fragment{

    @InjectView(R.id.register_confirm_pin)
    EditText pinText;

    @Inject
    Validator validator;

    private OnConfirmPinFragmentInteractionListener interactionListener;

    public static final String TAG = FragmentConfirmPin.class.getSimpleName();

    public static FragmentConfirmPin newInstance() {
        return new FragmentConfirmPin();
    }

    public FragmentConfirmPin() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_confirm_pin, container, false);
        ButterKnife.inject(this, rootView);

        pinText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            interactionListener = (OnConfirmPinFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnConfirmPinFragmentInteractionListener");
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

    @OnClick(R.id.register_confirm_btn)
    public void onConfirmPin(View v) {
        /*if(!isValidInput())
            return;*/

        interactionListener.onConfirmPinSubmit(pinText.getText().toString());
    }

    private boolean isValidInput() {

        String msg = validator.isNumberValid(pinText.getText().toString());
        if(msg != null){
            pinText.setError(msg);
            return false;
        }

        return true;
    }

    public interface OnConfirmPinFragmentInteractionListener {
        void onConfirmPinSubmit(String msisdn);
    }
}
