package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.ui.fragment.FragLoginOrSignup;
import com.mitelcel.pack.ui.fragment.FragMainListGrid;
import com.mitelcel.pack.ui.fragment.FragSignIn;
import com.mitelcel.pack.ui.fragment.FragSignUp;
import com.mitelcel.pack.ui.fragment.FragSplashScreen;
import com.mitelcel.pack.ui.fragment.tutorial.FragTutorialImage;
import com.mitelcel.pack.ui.fragment.tutorial.FragTutorialText;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface FragGraph {
    void inject(FragSignUp fragSignUp);
    void inject(FragMainListGrid fragMainListGrid);
    void inject(FragLoginOrSignup fragLoginOrSignup);
    void inject(FragSplashScreen fragSplashScreen);
    void inject(FragSignIn fragSignIn);
    void inject(FragTutorialImage fragTutorialImage);
    void inject(FragTutorialText fragTutorialText);

}
