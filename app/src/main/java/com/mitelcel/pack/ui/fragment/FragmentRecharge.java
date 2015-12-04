package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.DialogActivity;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.ui.widget.ButtonFolks;
import com.mitelcel.pack.utils.MiLog;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentRecharge.OnRechargeFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentRecharge#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentRecharge extends Fragment
{
    public static final String TAG = FragmentRecharge.class.getName();

    private OnRechargeFragmentInteractionListener mListener;

    @InjectView(R.id.recharge_amount)
    TextView recharge_amount;
    @InjectView(R.id.recharge_other)
    ButtonFolks recharge_other;
    @InjectView(R.id.recharge_5)
    ButtonFolks recharge_5;
    @InjectView(R.id.recharge_10)
    ButtonFolks recharge_10;
    @InjectView(R.id.recharge_20)
    ButtonFolks recharge_20;

    @Inject
    MiApiClient miApiClient;

    MaterialDialog dialog;
    OnDialogListener dialogListener;

    public static FragmentRecharge newInstance() {
        FragmentRecharge fragment = new FragmentRecharge();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public FragmentRecharge() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recharge, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        recharge_amount.clearFocus();
    }

    @OnClick({R.id.recharge_other, R.id.recharge_5, R.id.recharge_10, R.id.recharge_20})
    public void startConfirm(View view){
        MiLog.i(TAG, "Confirm clicked");
        float recharge = 0;

        switch (view.getId()) {
            case R.id.recharge_5:
                recharge = 5;
                break;
            case R.id.recharge_10:
                recharge = 10;
                break;
            case R.id.recharge_20:
                recharge = 20;
                break;
            case R.id.recharge_other:
                String input = recharge_amount.getText().toString();
                MiLog.i(TAG, "In other with amount " + input);

                try {
                    recharge = Float.parseFloat(input);
                } catch (NumberFormatException e) {
                    MiLog.i(TAG, "In other with amount " + input);
                    recharge_amount.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_alert, 0);
//                    recharge_amount.setError(getString(R.string.check_input));
                }
                break;
        }

        if (recharge != 0)
            mListener.onRechargeFragmentInteraction(recharge);
        else
            dialogListener.showDialogErrorCall(
                    getString(R.string.recharge_invalid_input),
                    getString(R.string.close),
                    DialogActivity.DIALOG_HIDDEN_ICO,
                    DialogActivity.APP_REQ
            );
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnRechargeFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnRechargeFragmentInteractionListener");
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
    public interface OnRechargeFragmentInteractionListener {
        void onRechargeFragmentInteraction(float recharge);
    }
}
