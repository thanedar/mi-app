package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanGetFrequentNumbers;
import com.mitelcel.pack.api.bean.resp.BeanGetFrequentNumbersResponse;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.DialogActivity;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.ui.widget.ButtonFolks;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.Validator;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.Optional;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentFrequentNumbers.OnFrequentNumbersFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentFrequentNumbers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentFrequentNumbers extends Fragment
{
    public static final String TAG = FragmentFrequentNumbers.class.getName();

    private OnFrequentNumbersFragmentInteractionListener mListener;

    private String msisdn_1 = "";
    private String msisdn_2 = "";
    private String msisdn_3 = "";
    private String msisdn_4 = "";
    private String msisdn_5 = "";

    private EditText selectedView;
    private int order = 0;

    @InjectView(R.id.frequent_msisdn_1)
    EditText tvMsisdn_1;
    @InjectView(R.id.frequent_msisdn_2)
    EditText tvMsisdn_2;
    @InjectView(R.id.frequent_msisdn_3)
    EditText tvMsisdn_3;
    @InjectView(R.id.frequent_msisdn_4)
    EditText tvMsisdn_4;
    @InjectView(R.id.frequent_msisdn_5)
    EditText tvMsisdn_5;
    @InjectView(R.id.frequent_numbers_btn)
    ButtonFolks confirm_btn;

    @Inject
    MiApiClient miApiClient;

    @Inject
    Validator validator;

    MaterialDialog dialog;
    OnDialogListener dialogListener;

    public static FragmentFrequentNumbers newInstance() {
        return new FragmentFrequentNumbers();
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentFrequentNumbers() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();
        dialog.show();

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);

        BeanGetFrequentNumbers beanGetFrequentNumbers = new BeanGetFrequentNumbers();
        miApiClient.get_frequent_numbers(beanGetFrequentNumbers, new Callback<BeanGetFrequentNumbersResponse>() {
            @Override
            public void success(BeanGetFrequentNumbersResponse beanGetFrequentNumbersResponse, Response response) {
                dialog.dismiss();
                MiLog.i(TAG, "beanGetFrequentNumbersResponse - " + beanGetFrequentNumbersResponse);
                if (beanGetFrequentNumbersResponse.getError().getCode() == Config.SUCCESS) {
                    List<BeanGetFrequentNumbersResponse.FreqNumbers> freqNum = beanGetFrequentNumbersResponse.getResult();

                    for (BeanGetFrequentNumbersResponse.FreqNumbers num : freqNum) {
                        switch (num.getOrder()) {
                            case 1:
                                msisdn_1 = num.getMsisdn();
                                break;
                            case 2:
                                msisdn_2 = num.getMsisdn();
                                break;
                            case 3:
                                msisdn_3 = num.getMsisdn();
                                break;
                            case 4:
                                msisdn_4 = num.getMsisdn();
                                break;
                            case 5:
                                msisdn_5 = num.getMsisdn();
                                break;
                        }
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i(TAG, "beanGetFrequentNumbersResponse ERROR " + error.toString());
                dialog.dismiss();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_frequent_numbers, container, false);
        ButterKnife.inject(this, view);

        tvMsisdn_1.setText(msisdn_1);
        tvMsisdn_2.setText(msisdn_2);
        tvMsisdn_3.setText(msisdn_3);
        tvMsisdn_4.setText(msisdn_4);
        tvMsisdn_5.setText(msisdn_5);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /*@OnFocusChange({R.id.frequent_msisdn_1, R.id.frequent_msisdn_2, R.id.frequent_msisdn_3, R.id.frequent_msisdn_4, R.id.frequent_msisdn_5})
    public void setButtonActive(View view, boolean focus){
        if(!focus) {
            confirm_btn.requestFocusFromTouch();
            confirm_btn.requestFocus();
        }
    }*/

    @OnClick({R.id.frequent_list_1, R.id.frequent_list_2, R.id.frequent_list_3, R.id.frequent_list_4, R.id.frequent_list_5})
    public void setEditTextActive(View view){
        MiLog.i(TAG, "setEditTextActive View clicked " + view.getId());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.frequent_list_1:
                tvMsisdn_1.setFocusableInTouchMode(true);
                tvMsisdn_1.requestFocusFromTouch();
                tvMsisdn_1.setNextFocusDownId(R.id.frequent_numbers_btn);
                imm.showSoftInput(tvMsisdn_1, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_1;
                order = 1;

                tvMsisdn_2.setFocusableInTouchMode(false);
                tvMsisdn_3.setFocusableInTouchMode(false);
                tvMsisdn_4.setFocusableInTouchMode(false);
                tvMsisdn_5.setFocusableInTouchMode(false);
                break;
            case R.id.frequent_list_2:
                tvMsisdn_1.setFocusableInTouchMode(false);

                tvMsisdn_2.setFocusableInTouchMode(true);
                tvMsisdn_2.requestFocusFromTouch();
                tvMsisdn_2.setNextFocusDownId(R.id.frequent_numbers_btn);
                imm.showSoftInput(tvMsisdn_2, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_2;
                order = 2;

                tvMsisdn_3.setFocusableInTouchMode(false);
                tvMsisdn_4.setFocusableInTouchMode(false);
                tvMsisdn_5.setFocusableInTouchMode(false);
                break;
            case R.id.frequent_list_3:
                tvMsisdn_1.setFocusableInTouchMode(false);
                tvMsisdn_2.setFocusableInTouchMode(false);

                tvMsisdn_3.setFocusableInTouchMode(true);
                tvMsisdn_3.requestFocusFromTouch();
                tvMsisdn_3.setNextFocusDownId(R.id.frequent_numbers_btn);
                imm.showSoftInput(tvMsisdn_3, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_3;
                order = 3;

                tvMsisdn_4.setFocusableInTouchMode(false);
                tvMsisdn_5.setFocusableInTouchMode(false);
                break;
            case R.id.frequent_list_4:
                tvMsisdn_1.setFocusableInTouchMode(false);
                tvMsisdn_2.setFocusableInTouchMode(false);
                tvMsisdn_3.setFocusableInTouchMode(false);

                tvMsisdn_4.setFocusableInTouchMode(true);
                tvMsisdn_4.requestFocusFromTouch();
                tvMsisdn_4.setNextFocusDownId(R.id.frequent_numbers_btn);
                imm.showSoftInput(tvMsisdn_4, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_4;
                order = 4;

                tvMsisdn_5.setFocusableInTouchMode(false);
                break;
            case R.id.frequent_list_5:
                tvMsisdn_1.setFocusableInTouchMode(false);
                tvMsisdn_2.setFocusableInTouchMode(false);
                tvMsisdn_3.setFocusableInTouchMode(false);
                tvMsisdn_4.setFocusableInTouchMode(false);

                tvMsisdn_5.setFocusableInTouchMode(true);
                tvMsisdn_5.requestFocusFromTouch();
                tvMsisdn_5.setNextFocusDownId(R.id.frequent_numbers_btn);
                imm.showSoftInput(tvMsisdn_5, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_5;
                order = 5;
                break;
        }
    }

/*
    @OnClick(R.id.frequent_msisdn_1)
    public void setEditTextActive_1(View view){
        MiLog.i(TAG, "setEditTextActive_1 View clicked " + view);
        tvMsisdn_1.setEnabled(true);
        tvMsisdn_2.setEnabled(false);
        tvMsisdn_3.setEnabled(false);
        tvMsisdn_4.setEnabled(false);
        tvMsisdn_5.setEnabled(false);
    }

    @OnClick(R.id.frequent_msisdn_2)
    public void setEditTextActive_2(View view){
        MiLog.i(TAG, "View clicked " + view);
        tvMsisdn_1.setEnabled(false);
        tvMsisdn_2.setEnabled(true);
        tvMsisdn_3.setEnabled(false);
        tvMsisdn_4.setEnabled(false);
        tvMsisdn_5.setEnabled(false);
    }

    @OnClick(R.id.frequent_msisdn_3)
    public void setEditTextActive_3(View view){
        tvMsisdn_1.setEnabled(false);
        tvMsisdn_2.setEnabled(false);
        tvMsisdn_3.setEnabled(true);
        tvMsisdn_4.setEnabled(false);
        tvMsisdn_5.setEnabled(false);
    }

    @OnClick(R.id.frequent_msisdn_4)
    public void setEditTextActive_4(View view){
        tvMsisdn_1.setEnabled(false);
        tvMsisdn_2.setEnabled(false);
        tvMsisdn_3.setEnabled(false);
        tvMsisdn_4.setEnabled(true);
        tvMsisdn_5.setEnabled(false);
    }

    @OnClick(R.id.frequent_msisdn_5)
    public void setEditTextActive_5(View view){
        tvMsisdn_1.setEnabled(false);
        tvMsisdn_2.setEnabled(false);
        tvMsisdn_3.setEnabled(false);
        tvMsisdn_4.setEnabled(false);
        tvMsisdn_5.setEnabled(true);
    }
*/

    @OnClick(R.id.frequent_numbers_btn)
    public void saveFrequentNumber(View view){
        String msg_msisdn = null;

        MiLog.i(TAG, "Confirm clicked");

        String newMsisdn = selectedView.getText().toString();
//        msisdn_1 = tvMsisdn_1.getText().toString();
        MiLog.i(TAG, "MSISDN - " + newMsisdn);

        if(validator != null){
            msg_msisdn = validator.isNumberValid(newMsisdn);
            if(msg_msisdn != null) {
                MiLog.d(TAG, "Msg_msisdn is " + msg_msisdn);
                selectedView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_alert, 0);
            }
            else
                MiLog.d(TAG, "Msg_msisdn is null");
        }
        else
            MiLog.d(TAG, "Validator is null");

        if (msg_msisdn == null)
            mListener.onFrequentNumbersFragmentInteraction(newMsisdn, order);
        else
            dialogListener.showDialogErrorCall(
                    getString(R.string.frequent_invalid_input),
                    getString(R.string.close),
                    DialogActivity.DIALOG_HIDDEN_ICO,
                    DialogActivity.APP_REQ
            );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFrequentNumbersFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFrequentNumbersFragmentInteractionListener");
        }
        try {
            dialogListener = (OnDialogListener) activity;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFrequentNumbersFragmentInteractionListener {
        void onFrequentNumbersFragmentInteraction(String msisdn, int order);
    }
}
