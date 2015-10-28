package com.mitelcel.pack.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerFragment {
}
