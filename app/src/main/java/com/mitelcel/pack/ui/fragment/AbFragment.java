package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class AbFragment extends Fragment{

    protected OnGoBackListener mListener;

    public interface OnGoBackListener {
        void goBackPreviousFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnGoBackListener) activity;
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

}
