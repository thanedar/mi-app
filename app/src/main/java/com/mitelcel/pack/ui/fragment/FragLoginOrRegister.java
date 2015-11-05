package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.listener.OnDialogListener;
//import com.tatssense.core.Buckstracks;

import butterknife.ButterKnife;

public class FragLoginOrRegister extends Fragment{

    MaterialDialog dialog;

    OnDialogListener mListener;

    public static final String TAG = FragLoginOrRegister.class.getSimpleName();

    public static FragLoginOrRegister newInstance() {
        FragLoginOrRegister fragment = new FragLoginOrRegister();
        return fragment;
    }

    public FragLoginOrRegister() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login_or_register, container, false);
        ButterKnife.inject(this, view);

        dialog = new MaterialDialog.Builder(getActivity())
//                    .title(R.string.loading)
                .content(R.string.please_wait)
                .progress(true, 0).build();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
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

}
