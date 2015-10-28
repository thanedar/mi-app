package com.mitelcel.pack.dagger.module;

import com.mitelcel.pack.MiApp;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Module
public class AppModule {

    MiApp app;

    public AppModule(MiApp app) {
        this.app = app;
    }

    @Provides
    public MiApp provideApplication(){
        return app;
    }

}
