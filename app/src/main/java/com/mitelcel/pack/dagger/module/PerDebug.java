package com.mitelcel.pack.dagger.module;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface PerDebug {
}
