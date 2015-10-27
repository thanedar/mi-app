package com.mitelcel.pack.dagger.component;

import com.mitelcel.pack.dagger.module.ValidatorModule;
import com.mitelcel.pack.dagger.scope.PerFragment;

import dagger.Component;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
@PerFragment
@Component(
        dependencies = AppComponent.class,
        modules = ValidatorModule.class
)
public interface FragmentComponent extends FragGraph{

    final class Initializer {
        private Initializer() {
        } // No instances.

        public static FragmentComponent init(AppComponent app) {
            return DaggerFragmentComponent.builder()
                    .appComponent(app)
                    .build();
        }
    }
}
