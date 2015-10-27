package com.mitelcel.pack;

import android.support.multidex.MultiDexApplication;

import com.mitelcel.pack.dagger.component.AppComponent;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class MiApp extends MultiDexApplication{

    private static MiApp instance;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        appComponent = AppComponent.Initializer.init(this);
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static MiApp getInstance() {
        return instance;
    }
}
