package com.mitelcel.pack.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.widget.ButtonFolks;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

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
    private CountDownTimer timer;
    private long videoDelay = 0;

    @InjectView(R.id.watch_video_btn)
    ButtonFolks watchVideoButton;
    @InjectView(R.id.timer_text)
    TextView timerText;
    @InjectView(R.id.video_info)
    TextView timerInfo;
    @InjectView(R.id.video_timer)
    RelativeLayout timerLayout;

    @InjectView(R.id.progressWheel)
    ProgressBar wheel;

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

        watchVideoButton.setVisibility(View.VISIBLE);
        timerInfo.setVisibility(View.INVISIBLE);
        timerText.setVisibility(View.INVISIBLE);
        wheel.setVisibility(View.INVISIBLE);
        wheel.setMax(Config.VIDEO_TIMER_DELAY);

        long delay = MiUtils.MiAppPreferences.getVideoDelay();
        if (delay == 0 || System.currentTimeMillis() >= delay + Config.VIDEO_TIMER_DELAY){
            MiLog.i(TAG, "onCreateView 1 delay: " + delay + " and current " + System.currentTimeMillis());
            videoDelay = 0;
        }
        else {
            MiLog.i(TAG, "onCreateView 2 delay: " + delay + " and current " + System.currentTimeMillis());
            videoDelay = Config.VIDEO_TIMER_DELAY - (System.currentTimeMillis() - delay);
        }

        return view;
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
        if(timer != null) {
            MiLog.i(TAG, "onDestroyView destroy timer");
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void onResume(){
        super.onResume();
        MiLog.i(TAG, "onResume timer for " + videoDelay);
        startTimer(videoDelay);
        if(videoDelay != 0) disableWatchWithDelay();
    }

    @OnClick(R.id.watch_video_btn)
    public void onWatchVideoPressed(View view) {
        MiLog.i(TAG, "onWatchVideoPressed timer RESET");
        videoDelay = 0;

        watchVideoButton.setVisibility(View.INVISIBLE);
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
        watchVideoButton.setEnabled(false);
    }

    public void disableWatchWithDelay() {
        MiLog.i(TAG, "Disable watch video button, start timer countdown");
        watchVideoButton.setEnabled(false);
        watchVideoButton.setVisibility(View.INVISIBLE);
        timerInfo.setVisibility(View.VISIBLE);
        timerText.setVisibility(View.VISIBLE);
        wheel.setVisibility(View.VISIBLE);
        timer.start();
    }

    public void enableWatch() {
        if(videoDelay == 0) {
            MiLog.i(TAG, "Enable watch video button");
            watchVideoButton.setEnabled(true);
            watchVideoButton.setVisibility(View.VISIBLE);
            timerInfo.setVisibility(View.INVISIBLE);
            timerText.setVisibility(View.INVISIBLE);
            wheel.setVisibility(View.INVISIBLE);
        }
    }

    private void startTimer(long delay){
        if(delay == 0)
            delay = Config.VIDEO_TIMER_DELAY;

        if(timer != null) {
            timer.cancel();
            timer = null;
        }

        MiLog.i(TAG, "start timer " + delay);
        timer = new CountDownTimer(delay, 1000) {

            public void onTick(long millisUntilFinished) {
                timerText.setText(getString(R.string.communicate_video_timer, millisUntilFinished / (60 * 1000), (millisUntilFinished / 1000) % 60 ));
                wheel.setProgress((int) millisUntilFinished);
                videoDelay = millisUntilFinished;
            }

            public void onFinish() {
                videoDelay = 0;
                enableWatch();
            }
        };
    }
}
