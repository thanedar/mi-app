package com.mitelcel.pack.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.mitelcel.pack.ui.fragment.tutorial.FragTutorialImage;
import com.mitelcel.pack.ui.fragment.tutorial.FragTutorialText;

/**
 * Created by sudhanshut on 9/25/15.
 */

public class TutorialPagerDemoAdapter extends FragmentStatePagerAdapter {

    private static final String ARG_COUNT = "count";
    private static final String[] IMAGES = {"welcome", "install", "play", "invite_friends", "cash_in"};

    public TutorialPagerDemoAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        /*Fragment fragment = new TutorialDemoFragment();*/

        Bundle args = new Bundle();
        args.putInt(ARG_COUNT, i);
        //args.putString(TutorialDemoFragment.ARG_IMAGE, IMAGES[i]);

        Fragment fragment;
        if (i == 0) {
            fragment = new FragTutorialText();
        }
        else
            fragment = new FragTutorialImage();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 5;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
