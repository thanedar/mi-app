package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.ui.ListOfferActivity;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.ui.RechargeActivity;
import com.mitelcel.pack.ui.SplashScreen;
import com.mitelcel.pack.ui.TransferActivity;
import com.mitelcel.pack.ui.widget.LayoutBalance;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface AppGraph {
    void inject(MiApp miApp);
    void inject(MainActivity mainActivity);
    void inject(LayoutBalance layoutBalance);
    void inject(ListOfferActivity listOfferActivity);
    void inject(RechargeActivity rechargeActivity);
    void inject(SplashScreen splashScreen);
    void inject(TransferActivity transferActivity);
}
