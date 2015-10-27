package com.mitelcel.pack.ui.fragment.tutorial;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;

/**
 * Created by sudhanshut on 9/25/15.
 */
    // Instances of this class are fragments representing a single
// object in our collection.
public class TutorialDemoFragment extends Fragment {
    public static final String ARG_OBJECT = "object";
    public static final String ARG_IMAGE = "resource";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        // The last two arguments ensure LayoutParams are inflated
        // properly.
        View rootView = inflater.inflate(
                R.layout.fragment_tutorial_demo, container, false);
        Bundle args = getArguments();
        /*((TextView) rootView.findViewById(R.id.text1)).setText(
                "Image " + Integer.toString(args.getInt(ARG_OBJECT)));*/
        int resId = this.getResources().getIdentifier(
                        args.getString(ARG_IMAGE), "drawable",
                        MiApp.getInstance().getPackageName());
        ((ImageView) rootView.findViewById(R.id.tutorial_image)).setImageResource(resId);
        return rootView;
    }
}
