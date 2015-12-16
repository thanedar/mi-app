package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
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

public class FragmentChangePassword extends Fragment{

    @InjectView(R.id.change_password_one)
    EditText passwordOne;
    @InjectView(R.id.change_password_two)
    EditText passwordTwo;

    @Inject
    Validator validator;

    private OnChangePasswordFragmentInteractionListener interactionListener;

    public static final String TAG = FragmentChangePassword.class.getSimpleName();

    public static FragmentChangePassword newInstance() {
        return new FragmentChangePassword();
    }

    public FragmentChangePassword() {
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
        View rootView = inflater.inflate(R.layout.fragment_change_password, container, false);
        ButterKnife.inject(this, rootView);

        passwordOne.addTextChangedListener(new TextWatcher() {
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

        passwordTwo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String textOne = passwordOne.getText().toString();
                String textTwo = passwordTwo.getText().toString();

                if(!textOne.equals(textTwo)) {
                    passwordTwo.setError(getString(R.string.password_does_not_match), ContextCompat.getDrawable(getActivity(), R.drawable.ic_alert));
                }
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            interactionListener = (OnChangePasswordFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnChangePasswordFragmentInteractionListener");
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

    @OnClick(R.id.change_password_btn)
    public void onChangePassword(View v) {
        if(!isValidInput())
            return;

        interactionListener.onChangePasswordSubmit(passwordOne.getText().toString());
    }

    @OnClick(R.id.change_password_skip)
    public void onSkipPassword(View v) {
        interactionListener.onChangePasswordSkip();
    }

    private boolean isValidInput() {

        String msg = validator.isValidPassSignUp(passwordOne.getText().toString());
        if(msg != null){
            passwordOne.setError(msg);
            return false;
        }

        return true;
    }

    public interface OnChangePasswordFragmentInteractionListener {
        void onChangePasswordSubmit(String password);
        void onChangePasswordSkip();
    }
}
