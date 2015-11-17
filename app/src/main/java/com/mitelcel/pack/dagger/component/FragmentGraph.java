package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.ui.fragment.FragmentAccount;
import com.mitelcel.pack.ui.fragment.FragmentConfirm;
import com.mitelcel.pack.ui.fragment.FragmentHelp;
import com.mitelcel.pack.ui.fragment.FragmentLogin;
import com.mitelcel.pack.ui.fragment.FragmentLoginOrRegister;
import com.mitelcel.pack.ui.fragment.FragmentMain;
import com.mitelcel.pack.ui.fragment.FragmentRecent;
import com.mitelcel.pack.ui.fragment.FragmentRecharge;
import com.mitelcel.pack.ui.fragment.FragmentRegister;
import com.mitelcel.pack.ui.fragment.FragmentSplashScreen;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface FragmentGraph {
    void inject(FragmentAccount fragmentAccount);
    void inject(FragmentConfirm fragmentConfirm);
    void inject(FragmentHelp fragmentHelp);
    void inject(FragmentLogin fragmentLogin);
    void inject(FragmentLoginOrRegister fragmentLoginOrRegister);
    void inject(FragmentMain fragmentMain);
    void inject(FragmentRecent fragmentRecent);
    void inject(FragmentRecharge fragmentRecharge);
    void inject(FragmentRegister fragmentRegister);
    void inject(FragmentSplashScreen fragmentSplashScreen);
}
