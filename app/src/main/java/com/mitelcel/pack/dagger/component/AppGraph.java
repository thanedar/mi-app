package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.ui.MainActivity;
//import com.mitelcel.pack.ui.widget.LayoutCashCoins;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public interface AppGraph {
    void inject(MiApp miApp);
    void inject(MainActivity mainActivity);
//    void inject(LayoutCashCoins layoutCashCoins);
}
