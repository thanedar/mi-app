package com.mitelcel.pack.dagger.module;

import android.content.Context;
import android.content.SharedPreferences;

import com.mitelcel.pack.MiApp;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Module
@Singleton
public class SharedPrefModule {

    public static final String SHARED_P_NAME = "damr";
    public static final String URL_ENDPOINT = "https://api.stackexchange.com";
    public static final String HAS_MOCK = "has_mock";
    public static final String HAS_CALL_ERROR = "has_call_error";

    @Provides
    public SharedPreferences providePreferences(MiApp app){
        return app.getSharedPreferences(SHARED_P_NAME, Context.MODE_PRIVATE);
    }

    @Provides
    @PerDebug
    boolean provideDebug(SharedPreferences sharedPreferences){
        return sharedPreferences.getBoolean(HAS_MOCK, false);
    }

    @Provides
    @PerErrorCall
    boolean provideErrorCall(SharedPreferences sharedPreferences){
        return sharedPreferences.getBoolean(HAS_CALL_ERROR, false);
    }

    @Provides
    @PerDebug
    String provideLinkEndpoint(){
        return URL_ENDPOINT;
    }

}
