package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jirbo.adcolony.AdColonyAd;
import com.jirbo.adcolony.AdColonyVideoAd;
import com.mitelcel.pack.Config;
import com.mitelcel.pack.R;

import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.mitelcel.pack.ui.fragment.FragmentCommunicate.OnCommunicateFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentCommunicate#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCommunicate extends Fragment {

    public static final String TAG = FragmentMain.class.getSimpleName();

    private OnCommunicateFragmentInteractionListener mListener;

    private AdColonyAd ad;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment BlankFragment.
     */
    public static FragmentCommunicate newInstance() {
        return new FragmentCommunicate();
    }

    public FragmentCommunicate() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ad = new AdColonyVideoAd(Config.ADCOLONY_ZONE_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_communicate, container, false);
    }

    @OnClick(R.id.video_btn)
    public void onVideoButtonPressed(View view) {
        if (mListener != null) {
            mListener.onCommunicateFragmentInteraction(view);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnCommunicateFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnCommunicateFragmentInteractionListener {
        void onCommunicateFragmentInteraction(View view);
    }

}
