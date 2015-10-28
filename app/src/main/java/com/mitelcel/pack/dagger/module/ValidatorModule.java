package com.mitelcel.pack.dagger.module;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.utils.Validator;

import dagger.Module;
import dagger.Provides;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Module
public class ValidatorModule {

    @Provides
    public Validator provideValidator(MiApp app){
        return new Validator(app);
    }
}
