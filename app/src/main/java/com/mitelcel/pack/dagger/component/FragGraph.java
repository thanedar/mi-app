package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.ui.fragment.FragHelp;
import com.mitelcel.pack.ui.fragment.FragLogin;
import com.mitelcel.pack.ui.fragment.FragLoginOrRegister;
import com.mitelcel.pack.ui.fragment.FragMainListGrid;
import com.mitelcel.pack.ui.fragment.FragRegister;
import com.mitelcel.pack.ui.fragment.FragSplashScreen;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface FragGraph {
    void inject(FragRegister fragRegister);
    void inject(FragMainListGrid fragMainListGrid);
    void inject(FragHelp fragHelp);
    void inject(FragLoginOrRegister fragLoginOrRegister);
    void inject(FragSplashScreen fragSplashScreen);
    void inject(FragLogin fragLogin);
}
