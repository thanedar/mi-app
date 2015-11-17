package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.ui.fragment.FragAccount;
import com.mitelcel.pack.ui.fragment.FragConfirm;
import com.mitelcel.pack.ui.fragment.FragHelp;
import com.mitelcel.pack.ui.fragment.FragLogin;
import com.mitelcel.pack.ui.fragment.FragLoginOrRegister;
import com.mitelcel.pack.ui.fragment.FragMain;
import com.mitelcel.pack.ui.fragment.FragRecent;
import com.mitelcel.pack.ui.fragment.FragRecharge;
import com.mitelcel.pack.ui.fragment.FragRegister;
import com.mitelcel.pack.ui.fragment.FragSplashScreen;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface FragGraph {
    void inject(FragAccount fragAccount);
    void inject(FragConfirm fragConfirm);
    void inject(FragHelp fragHelp);
    void inject(FragLogin fragLogin);
    void inject(FragLoginOrRegister fragLoginOrRegister);
    void inject(FragMain fragMain);
    void inject(FragRecent fragRecent);
    void inject(FragRecharge fragRecharge);
    void inject(FragRegister fragRegister);
    void inject(FragSplashScreen fragSplashScreen);
}
