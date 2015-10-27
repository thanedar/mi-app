package com.mitelcel.pack.dagger.component;

import android.content.SharedPreferences;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.dagger.module.AppModule;
import com.mitelcel.pack.dagger.module.ReleaseApiModule;
import com.mitelcel.pack.dagger.module.SharedPrefModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Singleton
@Component(
        modules = {
                AppModule.class,
                SharedPrefModule.class,
                ReleaseApiModule.class
        }
)
public interface AppComponent extends AppGraph {

    MiApiClient miApiClient();
    SharedPreferences sharedPreferences();
    MiApp getApp();

    final class Initializer {
        private Initializer() {
        } // No instances.

        public static AppComponent init(MiApp miApp) {
            return DaggerAppComponent.builder()
                    .appModule(new AppModule(miApp))
                    .build();
        }
    }
}
