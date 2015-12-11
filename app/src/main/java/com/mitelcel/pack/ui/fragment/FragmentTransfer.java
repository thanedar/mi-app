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
import android.widget.ImageView;
import android.widget.TextView;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.bean.ui.BeanContactInfo;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.DialogActivity;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.ui.widget.ButtonFolks;
import com.mitelcel.pack.ui.widget.RoundedTransformation;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.Validator;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTransfer.OnTransferFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentTransfer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentTransfer extends Fragment
{
    public static final String TAG = FragmentTransfer.class.getSimpleName();

    private OnTransferFragmentInteractionListener mListener;

    private String msisdn = "";
    private boolean showDefault = true;

    @InjectView(R.id.transfer_amount)
    EditText transfer_amount;
    @InjectView(R.id.transfer_msisdn)
    TextView transfer_msisdn;

    @InjectView(R.id.transfer_5)
    ButtonFolks transfer_5;
    @InjectView(R.id.transfer_10)
    ButtonFolks transfer_10;
    @InjectView(R.id.transfer_other)
    ButtonFolks otherBtn;

    @InjectView(R.id.transfer_contact)
    ImageView transfer_contact;

    @Inject
    MiApiClient miApiClient;

    @Inject
    Validator validator;

    OnDialogListener dialogListener;

    public static FragmentTransfer newInstance() {
        FragmentTransfer fragment = new FragmentTransfer();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentTransfer() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_transfer, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            transfer_msisdn.addTextChangedListener(new PhoneNumberFormattingTextWatcher("MX"));
        else
            transfer_msisdn.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        if(showDefault) {
            Picasso.with(getActivity().getApplicationContext())
                    .load(R.drawable.ic_contacts_128)
                    .resize(192, 192)
                    .centerCrop()
                    .into(transfer_contact);
            MiLog.i(TAG, "Loading contacts PNG");
        }
    }

    @OnClick(R.id.transfer_contact)
    public void selectContact(){
        mListener.selectContact();
    }

    @OnClick({R.id.transfer_other, R.id.transfer_5, R.id.transfer_10})
    public void startConfirm(View view){
        String msg_amount = null;
        String msg_msisdn = null;

        MiLog.i(TAG, "Confirm clicked");
        float amount = 0;

        if(showDefault)
            msisdn = transfer_msisdn.getText().toString();

        switch (view.getId()) {
            case R.id.transfer_5:
                amount = 5;
                break;
            case R.id.transfer_10:
                amount = 10;
                break;
            case R.id.transfer_other:
                String input = transfer_amount.getText().toString();
                MiLog.i(TAG, "Target " + msisdn + " and Amount " + input);

                try {
                    amount = Float.parseFloat(input);
                } catch (NumberFormatException e) {
                    MiLog.d(TAG, "NumberFormatException");
                    transfer_amount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_alert, 0, 0, 0);
                }
                break;
        }
        MiLog.i(TAG, "Target " + msisdn + " and Amount " + amount);

        if(validator != null){
            msg_msisdn = validator.isNumberValid(msisdn);
            if(msg_msisdn != null) {
                MiLog.d(TAG, "Msg_msisdn is " + msg_msisdn);
                transfer_msisdn.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_alert, 0);
            }
            else
                MiLog.d(TAG, "Msg_msisdn is null");

            msg_amount = validator.isTransferAmountValid(amount);
            if(msg_amount != null) {
                MiLog.d(TAG, "Msg_amount is " + msg_amount);
                transfer_amount.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_alert, 0, 0, 0);
            }
            else
                MiLog.d(TAG, "Msg_amount is null");
        }
        else
            MiLog.d(TAG, "Validator is null");

        if (msg_amount == null && msg_msisdn == null) {
            String name = (showDefault) ? "" : transfer_msisdn.getText().toString();
            mListener.onTransferFragmentInteraction(msisdn, amount, name);
        }
        else {
            String error = msg_msisdn == null ? msg_amount : msg_msisdn;
            dialogListener.showDialogErrorCall(
                    error,
                    getString(R.string.close),
                    DialogActivity.DIALOG_HIDDEN_ICO,
                    DialogActivity.APP_REQ
            );
        }
    }

    public void displayContact(BeanContactInfo beanContactInfo) {
        MiLog.i(TAG, "Name: " + beanContactInfo.getName() + " Number: " + beanContactInfo.getPhone() + " Photo: " + beanContactInfo.getPhoto());
        String photo = beanContactInfo.getPhoto();
        if(photo != null && !photo.equalsIgnoreCase("")) {
            showDefault = false;
            MiLog.i(TAG, "Loading image from Contacts path " + photo);
            Picasso.with(getActivity().getApplicationContext())
                    .load(photo)
                    .transform(new RoundedTransformation(50, 4))
                    .resize(192, 192)
                    .centerCrop()
                    .into(transfer_contact);
        }
        else {
            showDefault = true;
        }

        msisdn = beanContactInfo.getPhone();
        transfer_msisdn.setText(beanContactInfo.getName());
        transfer_msisdn.setClickable(false);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTransferFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTransferFragmentInteractionListener");
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
    public interface OnTransferFragmentInteractionListener {
        void onTransferFragmentInteraction(String msisdn, float transfer, String name);
        void selectContact();
    }
}
