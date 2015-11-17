package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.ui.widget.ButtonFolks;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;
import com.mitelcel.pack.utils.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragConfirm.OnConfirmFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragConfirm#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragConfirm extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STRING = "string";
    private static final String ARG_AMOUNT = "amount";

    private String mString = "";
    private String mAmount = "0";

    @Inject
    Validator validator;

    @InjectView(R.id.confirm_details)
    TextView tvDetails;
    @InjectView(R.id.confirm_info)
    TextView tvInfo;
    @InjectView(R.id.confirm_password)
    TextView tvPassword;
    @InjectView(R.id.confirm_btn)
    ButtonFolks btn;

    MaterialDialog dialog;
    OnDialogListener dialogListener;

    private OnConfirmFragmentInteractionListener fragmentInteractionListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param mString Parameter 1.
     * @param mAmount Parameter 2.
     * @return A new instance of fragment FragConfirm.
     */
    public static FragConfirm newInstance(String mString, String mAmount) {
        FragConfirm fragment = new FragConfirm();
        Bundle args = new Bundle();
        args.putString(ARG_STRING, mString);
        args.putString(ARG_AMOUNT, mAmount);
        fragment.setArguments(args);
        return fragment;
    }

    public FragConfirm() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.please_wait)
                .progress(true, 0)
                .build();

        if (getArguments() != null) {
            mString = getArguments().getString(ARG_STRING);
            mAmount = getArguments().getString(ARG_AMOUNT);
        }
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_confirm, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        long timestamp = MiUtils.MiAppPreferences.getLastCheckTimestamp();

        if(System.currentTimeMillis() - timestamp <= Config.PASS_TIMEOUT * 60 * 1000) {
            tvPassword.setVisibility(View.INVISIBLE);
            tvInfo.setVisibility(View.INVISIBLE);
        }
        else
            tvInfo.setText(getString(R.string.confirm_info));

        tvDetails.setText(mString);
    }

    @OnClick(R.id.confirm_btn)
    public void transactionConfirmed() {
        MiLog.i("FragConfirm", "Confirm clicked");

        if(tvPassword.getVisibility() == View.VISIBLE){
            String msg = validator.isValidPassSignUp(tvPassword.getText().toString());
            if (msg != null)
                tvPassword.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_alert, 0);
            else if (fragmentInteractionListener != null) {
                MiLog.i("FragConfirm", "Confirm fragmentInteractionListener found");
                fragmentInteractionListener.onConfirmFragmentInteraction();
            }
        }
        else if (fragmentInteractionListener != null) {
            MiLog.i("FragConfirm", "Confirm fragmentInteractionListener found");
            fragmentInteractionListener.onConfirmFragmentInteraction();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentInteractionListener = (OnConfirmFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnConfirmFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentInteractionListener = null;
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
    public interface OnConfirmFragmentInteractionListener {
        void onConfirmFragmentInteraction();
    }
}
