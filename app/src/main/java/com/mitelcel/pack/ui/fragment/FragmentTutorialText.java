package com.mitelcel.pack.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.R;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.dagger.component.FragmentComponent;

import butterknife.ButterKnife;

/**
 * Created by sudhanshut on 11/20/15.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTutorialText} interface
 * to handle interaction events.
 * Use the {@link FragmentTutorialText#newInstance} factory method to
 * create an instance of this fragment.
 */

// Instances of this class are fragments representing a single object in our collection.
public class FragmentTutorialText extends Fragment {
    MaterialDialog dialog;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentTutorialText.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentTutorialText newInstance() {
        FragmentTutorialText fragment = new FragmentTutorialText();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public FragmentTutorialText() {
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
