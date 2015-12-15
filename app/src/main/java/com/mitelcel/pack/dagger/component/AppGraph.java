package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.ui.CommunicateActivity;
import com.mitelcel.pack.ui.FrequentNumbersActivity;
import com.mitelcel.pack.ui.ListOfferActivity;
import com.mitelcel.pack.ui.LoginOrRegister;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.ui.OfferDetailActivity;
import com.mitelcel.pack.ui.RecentActivity;
import com.mitelcel.pack.ui.RechargeActivity;
import com.mitelcel.pack.ui.ServiceActivity;
import com.mitelcel.pack.ui.SplashScreen;
import com.mitelcel.pack.ui.TransferActivity;
import com.mitelcel.pack.ui.TutorialActivity;
import com.mitelcel.pack.ui.WebViewActivity;
import com.mitelcel.pack.ui.widget.LayoutBalance;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface AppGraph {
    void inject(FrequentNumbersActivity frequentNumbersActivity);
    void inject(CommunicateActivity communicateActivity);
    void inject(MiApp miApp);
    void inject(MainActivity mainActivity);
    void inject(LayoutBalance layoutBalance);
    void inject(ListOfferActivity listOfferActivity);
    void inject(LoginOrRegister loginOrRegister);
    void inject(OfferDetailActivity offerDetailActivity);
    void inject(RecentActivity recentActivity);
    void inject(RechargeActivity rechargeActivity);
    void inject(ServiceActivity serviceActivity);
    void inject(SplashScreen splashScreen);
    void inject(TransferActivity transferActivity);
    void inject(TutorialActivity tutorialActivity);
    void inject(WebViewActivity webViewActivity);
}
