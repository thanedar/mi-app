package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanGetFrequentNumbers;
import com.mitelcel.pack.api.bean.resp.BeanGetFrequentNumbersResponse;
import com.mitelcel.pack.bean.ui.BeanContactInfo;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.DialogActivity;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.ui.widget.ButtonFolks;
import com.mitelcel.pack.ui.widget.RoundedTransformation;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;
import com.mitelcel.pack.utils.Validator;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
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
    public static final String TAG = FragmentFrequentNumbers.class.getSimpleName();

    private OnFrequentNumbersFragmentInteractionListener mListener;

    private String msisdn_1 = "";
    private String msisdn_2 = "";
    private String msisdn_3 = "";
    private String msisdn_4 = "";
    private String msisdn_5 = "";

    private PhoneNumberFormattingTextWatcher phoneNumberFormattingTextWatcher;
    private EditText selectedView;
    private int order = 0;
    private boolean showDefault = true;

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

    @InjectView(R.id.frequent_con_1)
    ImageView con_1;
    @InjectView(R.id.frequent_con_2)
    ImageView con_2;
    @InjectView(R.id.frequent_con_3)
    ImageView con_3;
    @InjectView(R.id.frequent_con_4)
    ImageView con_4;
    @InjectView(R.id.frequent_con_5)
    ImageView con_5;

    @InjectView(R.id.frequent_pos_1)
    ImageView pos_1;
    @InjectView(R.id.frequent_pos_2)
    ImageView pos_2;
    @InjectView(R.id.frequent_pos_3)
    ImageView pos_3;
    @InjectView(R.id.frequent_pos_4)
    ImageView pos_4;
    @InjectView(R.id.frequent_pos_5)
    ImageView pos_5;

    @InjectView(R.id.frequent_ok_1)
    ImageView ok_1;
    @InjectView(R.id.frequent_ok_2)
    ImageView ok_2;
    @InjectView(R.id.frequent_ok_3)
    ImageView ok_3;
    @InjectView(R.id.frequent_ok_4)
    ImageView ok_4;
    @InjectView(R.id.frequent_ok_5)
    ImageView ok_5;

    @InjectView(R.id.frequent_neg_1)
    ImageView neg_1;
    @InjectView(R.id.frequent_neg_2)
    ImageView neg_2;
    @InjectView(R.id.frequent_neg_3)
    ImageView neg_3;
    @InjectView(R.id.frequent_neg_4)
    ImageView neg_4;
    @InjectView(R.id.frequent_neg_5)
    ImageView neg_5;

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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            phoneNumberFormattingTextWatcher = new PhoneNumberFormattingTextWatcher("MX");
        }
        else
            phoneNumberFormattingTextWatcher = new PhoneNumberFormattingTextWatcher();

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
                                MiLog.i(TAG, "MSISDN 1 - " + msisdn_1);
                                break;
                            case 2:
                                msisdn_2 = num.getMsisdn();
                                MiLog.i(TAG, "MSISDN 2 - " + msisdn_2);
                                break;
                            case 3:
                                msisdn_3 = num.getMsisdn();
                                MiLog.i(TAG, "MSISDN 3 - " + msisdn_3);
                                break;
                            case 4:
                                msisdn_4 = num.getMsisdn();
                                MiLog.i(TAG, "MSISDN 4 - " + msisdn_4);
                                break;
                            case 5:
                                msisdn_5 = num.getMsisdn();
                                MiLog.i(TAG, "MSISDN 5 - " + msisdn_5);
                                break;
                        }
                    }
                    refreshDisplay();
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

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //refreshDisplay();
    }

    @OnClick({R.id.frequent_pos_1, R.id.frequent_pos_2, R.id.frequent_pos_3, R.id.frequent_pos_4, R.id.frequent_pos_5})
    public void setEditTextActive(View view){
        MiLog.i(TAG, "setEditTextActive View clicked " + view.getId());
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        switch (view.getId()) {
            case R.id.frequent_pos_1:
                tvMsisdn_1.setFocusableInTouchMode(true);
                tvMsisdn_1.requestFocusFromTouch();
                tvMsisdn_1.setNextFocusDownId(R.id.frequent_ok_1);
                tvMsisdn_1.addTextChangedListener(phoneNumberFormattingTextWatcher);
                ok_1.setVisibility(View.VISIBLE);
                pos_1.setVisibility(View.GONE);
                imm.showSoftInput(tvMsisdn_1, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_1;
                order = 1;

                tvMsisdn_2.setFocusableInTouchMode(false);
                tvMsisdn_3.setFocusableInTouchMode(false);
                tvMsisdn_4.setFocusableInTouchMode(false);
                tvMsisdn_5.setFocusableInTouchMode(false);
                break;
            case R.id.frequent_pos_2:
                tvMsisdn_1.setFocusableInTouchMode(false);

                tvMsisdn_2.setFocusableInTouchMode(true);
                tvMsisdn_2.requestFocusFromTouch();
                tvMsisdn_2.setNextFocusDownId(R.id.frequent_ok_2);
                tvMsisdn_2.addTextChangedListener(phoneNumberFormattingTextWatcher);
                ok_2.setVisibility(View.VISIBLE);
                pos_2.setVisibility(View.GONE);
                imm.showSoftInput(tvMsisdn_2, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_2;
                order = 2;

                tvMsisdn_3.setFocusableInTouchMode(false);
                tvMsisdn_4.setFocusableInTouchMode(false);
                tvMsisdn_5.setFocusableInTouchMode(false);
                break;
            case R.id.frequent_pos_3:
                tvMsisdn_1.setFocusableInTouchMode(false);
                tvMsisdn_2.setFocusableInTouchMode(false);

                tvMsisdn_3.setFocusableInTouchMode(true);
                tvMsisdn_3.requestFocusFromTouch();
                tvMsisdn_3.setNextFocusDownId(R.id.frequent_ok_3);
                tvMsisdn_3.addTextChangedListener(phoneNumberFormattingTextWatcher);
                ok_3.setVisibility(View.VISIBLE);
                pos_3.setVisibility(View.GONE);
                imm.showSoftInput(tvMsisdn_3, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_3;
                order = 3;

                tvMsisdn_4.setFocusableInTouchMode(false);
                tvMsisdn_5.setFocusableInTouchMode(false);
                break;
            case R.id.frequent_pos_4:
                tvMsisdn_1.setFocusableInTouchMode(false);
                tvMsisdn_2.setFocusableInTouchMode(false);
                tvMsisdn_3.setFocusableInTouchMode(false);

                tvMsisdn_4.setFocusableInTouchMode(true);
                tvMsisdn_4.requestFocusFromTouch();
                tvMsisdn_4.setNextFocusDownId(R.id.frequent_ok_4);
                tvMsisdn_4.addTextChangedListener(phoneNumberFormattingTextWatcher);
                ok_4.setVisibility(View.VISIBLE);
                pos_4.setVisibility(View.GONE);
                imm.showSoftInput(tvMsisdn_4, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_4;
                order = 4;

                tvMsisdn_5.setFocusableInTouchMode(false);
                break;
            case R.id.frequent_pos_5:
                tvMsisdn_1.setFocusableInTouchMode(false);
                tvMsisdn_2.setFocusableInTouchMode(false);
                tvMsisdn_3.setFocusableInTouchMode(false);
                tvMsisdn_4.setFocusableInTouchMode(false);

                tvMsisdn_5.setFocusableInTouchMode(true);
                tvMsisdn_5.requestFocusFromTouch();
                tvMsisdn_5.setNextFocusDownId(R.id.frequent_ok_5);
                tvMsisdn_5.addTextChangedListener(phoneNumberFormattingTextWatcher);
                ok_5.setVisibility(View.VISIBLE);
                pos_5.setVisibility(View.GONE);
                imm.showSoftInput(tvMsisdn_5, InputMethodManager.SHOW_IMPLICIT);
                selectedView = tvMsisdn_5;
                order = 5;
                break;
        }
    }

    @OnClick({R.id.frequent_neg_1, R.id.frequent_neg_2, R.id.frequent_neg_3, R.id.frequent_neg_4, R.id.frequent_neg_5})
    public void deleteNumber(View view) {
        MiLog.i(TAG, "deleteNumber View clicked " + view.getId());
        switch (view.getId()) {
            case R.id.frequent_neg_1:
                msisdn_1 = "";
                selectedView = tvMsisdn_1;
                order = 1;
                break;
            case R.id.frequent_neg_2:
                msisdn_2 = "";
                selectedView = tvMsisdn_2;
                order = 2;
                break;
            case R.id.frequent_neg_3:
                msisdn_3 = "";
                selectedView = tvMsisdn_3;
                order = 3;
                break;
            case R.id.frequent_neg_4:
                msisdn_4 = "";
                selectedView = tvMsisdn_4;
                order = 4;
                break;
            case R.id.frequent_neg_5:
                msisdn_5 = "";
                selectedView = tvMsisdn_5;
                order = 5;
                break;
        }
        String msisdn = selectedView.getText().toString();
        mListener.onDeleteFrequentNumberInteraction(msisdn, order);
    }

    @OnClick({R.id.frequent_ok_1, R.id.frequent_ok_2, R.id.frequent_ok_3, R.id.frequent_ok_4, R.id.frequent_ok_5})
    public void saveFrequentNumber(View view){
        String msg_msisdn = null;

        MiLog.i(TAG, "Confirm clicked: " + view.getId());

        String newMsisdn = selectedView.getText().toString();
        MiLog.i(TAG, "New MSISDN - " + newMsisdn);

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

        if (msg_msisdn == null) {
            newMsisdn = MiUtils.getCleanMsisdn(newMsisdn);
            switch (selectedView.getId()) {
                case R.id.frequent_msisdn_1:
                    msisdn_1 = newMsisdn;
                    MiLog.i(TAG, "MSISDN 1 - " + msisdn_1);
                    break;
                case R.id.frequent_msisdn_2:
                    msisdn_2 = newMsisdn;
                    MiLog.i(TAG, "MSISDN 2 - " + msisdn_2);
                    break;
                case R.id.frequent_msisdn_3:
                    msisdn_3 = newMsisdn;
                    MiLog.i(TAG, "MSISDN 3 - " + msisdn_3);
                    break;
                case R.id.frequent_msisdn_4:
                    msisdn_4 = newMsisdn;
                    MiLog.i(TAG, "MSISDN 4 - " + msisdn_4);
                    break;
                case R.id.frequent_msisdn_5:
                    msisdn_5 = newMsisdn;
                    MiLog.i(TAG, "MSISDN 5 - " + msisdn_5);
                    break;
            }
            mListener.onSetFrequentNumberInteraction(newMsisdn, order);
        }
        else
            dialogListener.showDialogErrorCall(
                    getString(R.string.frequent_invalid_input),
                    getString(R.string.close),
                    DialogActivity.DIALOG_HIDDEN_ICO,
                    DialogActivity.APP_REQ
            );
    }

    @OnClick({R.id.frequent_con_1, R.id.frequent_con_2, R.id.frequent_con_3, R.id.frequent_con_4, R.id.frequent_con_5})
    public void selectContact(View view) {
        MiLog.i(TAG, "selectContact clicked: " + view.getId());
        switch (view.getId()) {
            case R.id.frequent_con_1:
                selectedView = tvMsisdn_1;
                order = 1;
                break;
            case R.id.frequent_con_2:
                selectedView = tvMsisdn_2;
                order = 2;
                break;
            case R.id.frequent_con_3:
                selectedView = tvMsisdn_3;
                order = 3;
                break;
            case R.id.frequent_con_4:
                selectedView = tvMsisdn_4;
                order = 4;
                break;
            case R.id.frequent_con_5:
                selectedView = tvMsisdn_5;
                order = 5;
                break;
        }
        mListener.selectContact();
    }

    public void displayContact(BeanContactInfo beanContactInfo) {
        MiLog.i(TAG, "Name: " + beanContactInfo.getName() + " Number: " + beanContactInfo.getPhone() + " Photo: " + beanContactInfo.getPhoto());

        ImageView display = null;
        switch (order){
            case 1:
                display = con_1;
                msisdn_1 = beanContactInfo.getPhone();
                ok_1.setVisibility(View.VISIBLE);
                pos_1.setVisibility(View.GONE);
                break;
            case 2:
                display = con_2;
                msisdn_2 = beanContactInfo.getPhone();
                ok_2.setVisibility(View.VISIBLE);
                pos_2.setVisibility(View.GONE);
                break;
            case 3:
                display = con_3;
                msisdn_3 = beanContactInfo.getPhone();
                ok_3.setVisibility(View.VISIBLE);
                pos_3.setVisibility(View.GONE);
                break;
            case 4:
                display = con_4;
                msisdn_4 = beanContactInfo.getPhone();
                ok_4.setVisibility(View.VISIBLE);
                pos_4.setVisibility(View.GONE);
                break;
            case 5:
                display = con_5;
                msisdn_5 = beanContactInfo.getPhone();
                ok_5.setVisibility(View.VISIBLE);
                pos_5.setVisibility(View.GONE);
                break;
        }

        String photo = beanContactInfo.getPhoto();
        if(photo != null && !photo.equalsIgnoreCase("")) {
            MiLog.i(TAG, "Loading image from Contacts path " + photo);
            Picasso.with(getActivity().getApplicationContext())
                    .load(photo)
                    .transform(new RoundedTransformation(20, 4))
                    .resize(192, 192)
                    .centerCrop()
                    .into(display);
        }

        selectedView.setText(beanContactInfo.getPhone());
//        selectedView.setEnabled(false);
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

    public void refreshDisplay() {

        tvMsisdn_1.setText(msisdn_1);
        MiLog.i(TAG, "refreshDisplay MSISDN 1 - " + msisdn_1);
        ok_1.setVisibility(View.GONE);
        if(msisdn_1.equals("")){
            pos_1.setVisibility(View.VISIBLE);
            neg_1.setVisibility(View.GONE);
            con_1.setClickable(true);
        }
        else {
            pos_1.setVisibility(View.GONE);
            neg_1.setVisibility(View.VISIBLE);
            con_1.setClickable(false);
        }
        tvMsisdn_2.setText(msisdn_2);
        MiLog.i(TAG, "refreshDisplay MSISDN 2 - " + msisdn_2);
        ok_2.setVisibility(View.GONE);
        if(msisdn_2.equals("")){
            pos_2.setVisibility(View.VISIBLE);
            neg_2.setVisibility(View.GONE);
            con_2.setClickable(true);
        }
        else {
            pos_2.setVisibility(View.GONE);
            neg_2.setVisibility(View.VISIBLE);
            con_2.setClickable(false);
        }
        tvMsisdn_3.setText(msisdn_3);
        MiLog.i(TAG, "refreshDisplay MSISDN 3 - " + msisdn_3);
        ok_3.setVisibility(View.GONE);
        if(msisdn_3.equals("")){
            pos_3.setVisibility(View.VISIBLE);
            neg_3.setVisibility(View.GONE);
            con_3.setClickable(true);
        }
        else {
            pos_3.setVisibility(View.GONE);
            neg_3.setVisibility(View.VISIBLE);
            con_3.setClickable(false);
        }
        tvMsisdn_4.setText(msisdn_4);
        MiLog.i(TAG, "refreshDisplay MSISDN 4 - " + msisdn_4);
        ok_4.setVisibility(View.GONE);
        if(msisdn_4.equals("")){
            pos_4.setVisibility(View.VISIBLE);
            neg_4.setVisibility(View.GONE);
            con_4.setClickable(true);
        }
        else {
            pos_4.setVisibility(View.GONE);
            neg_4.setVisibility(View.VISIBLE);
            con_4.setClickable(false);
        }
        tvMsisdn_5.setText(msisdn_5);
        MiLog.i(TAG, "refreshDisplay MSISDN 5 - " + msisdn_5);
        ok_5.setVisibility(View.GONE);
        if(msisdn_5.equals("")){
            pos_5.setVisibility(View.VISIBLE);
            neg_5.setVisibility(View.GONE);
            con_5.setClickable(true);
        }
        else {
            pos_5.setVisibility(View.GONE);
            neg_5.setVisibility(View.VISIBLE);
            con_5.setClickable(false);
        }
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
        void onSetFrequentNumberInteraction(String msisdn, int order);
        void onDeleteFrequentNumberInteraction(String msisdn, int order);
        void selectContact();
    }
}
