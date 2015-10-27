package com.mitelcel.pack.ui.fragment.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.R;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.dagger.component.FragmentComponent;
import com.mitelcel.pack.ui.fragment.AbFragment;

import butterknife.ButterKnife;

/**
 * Created by sudhanshut on 9/25/15.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragTutorialText} interface
 * to handle interaction events.
 * Use the {@link FragTutorialText#newInstance} factory method to
 * create an instance of this fragment.
 */

// Instances of this class are fragments representing a single object in our collection.
public class FragTutorialText extends AbFragment {
    public static final String ARG_OBJECT = "object";
    public static final String ARG_IMAGE = "resource";

    MaterialDialog dialog;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragTutorialText.
     */
    // TODO: Rename and change types and number of parameters
    public static FragTutorialText newInstance() {
        FragTutorialText fragment = new FragTutorialText();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragTutorialText() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        FragmentComponent.Initializer.init(MiApp.getInstance().getAppComponent()).inject(this);
        dialog = new MaterialDialog.Builder(getActivity())
                .content(R.string.loading)
                .progress(true, 0)
                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated properly.
        View rootView = inflater.inflate(
                R.layout.fragment_tutorial_text, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }
}
