package com.mitelcel.pack.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.mitelcel.pack.R;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.dagger.component.FragmentComponent;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by sudhanshut on 11/20/15.
 */

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentTutorialImage} interface
 * to handle interaction events.
 * Use the {@link FragmentTutorialImage#newInstance} factory method to
 * create an instance of this fragment.
 */

// Instances of this class are fragments representing a single object in our collection.
public class FragmentTutorialImage extends Fragment {
    public static final String ARG_COUNT = "count";
    public static final String ARG_IMAGE = "resource";

    @InjectView(R.id.tutorial_image)
    ImageView tutorial_image;

    @InjectView(R.id.tutorial_text_1)
    TextView tut_text_1;
    @InjectView(R.id.tutorial_text_2)
    TextView tut_text_2;
    @InjectView(R.id.tutorial_title)
    TextView tut_title;

    MaterialDialog dialog;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FragmentTutorialImage.
     */
    public static FragmentTutorialImage newInstance() {
        return new FragmentTutorialImage();
    }

    public FragmentTutorialImage() {
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
                R.layout.fragment_tutorial_image, container, false);
        ButterKnife.inject(this, rootView);

        Bundle args = getArguments();

        switch (args.getInt(ARG_COUNT, 1)) {
            case 1 :
                tutorial_image.setImageResource(R.drawable.tut_install);
                tut_text_1.setText(R.string.tutorial_install_1);
                tut_text_2.setText(R.string.tutorial_install_2);
                break;
            case 2 :
                tutorial_image.setImageResource(R.drawable.tut_play);
                tut_text_1.setText(R.string.tutorial_play_1);
                tut_text_2.setText(R.string.tutorial_play_2);
                break;
            case 3 :
                tutorial_image.setImageResource(R.drawable.tut_invite_friends);
                tut_text_1.setText(R.string.tutorial_invite_1);
                tut_text_2.setText(R.string.tutorial_invite_2);
                break;
            case 4 :
                tutorial_image.setImageResource(R.drawable.tut_cash_in);
                tut_text_1.setText("");
                tut_text_2.setText(R.string.tutorial_cash);
                tut_title.setText(R.string.tutorial_cash_in);
                break;
        }

        return rootView;
    }
}
