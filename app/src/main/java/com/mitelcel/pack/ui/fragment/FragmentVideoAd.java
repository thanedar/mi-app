package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.widget.ButtonFolks;
import com.mitelcel.pack.utils.MiLog;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentVideoAd.OnCommunicateFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentVideoAd#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentVideoAd extends Fragment {

    public static final String TAG = FragmentVideoAd.class.getSimpleName();

    private OnCommunicateFragmentInteractionListener mListener;

    @InjectView(R.id.watch_video_btn)
    ButtonFolks watch_video;

    /**
     * Use this factory method to create a new instance of
     * this fragment.
     *
     * @return A new instance of fragment.
     */
    public static FragmentVideoAd newInstance() {
        return new FragmentVideoAd();
    }

    public FragmentVideoAd() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video_ad, container, false);
        ButterKnife.inject(this, view);

        return view;
    }

    @OnClick(R.id.watch_video_btn)
    public void onWatchVideoPressed(View view) {
        MiLog.i("FragmentVideoAd", "Video btn clicked for " + view.getId());
        if (mListener != null) {
            mListener.onWatchVideoClick();
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
        void onWatchVideoClick();
    }

    public void disableWatch() {
        MiLog.i(TAG, "Disable watch video button");
        watch_video.setEnabled(false);
    }

    public void enableWatch() {
        MiLog.i(TAG, "Enable watch video button");
        watch_video.setEnabled(true);
    }
}
