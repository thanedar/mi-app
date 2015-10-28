package com.mitelcel.pack.dagger.data;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Qualifier
@Retention(RUNTIME)
public @interface UseMockBackend {

}
