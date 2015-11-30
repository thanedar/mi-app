package com.mitelcel.pack.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.fragment.FragmentSplashScreen;

//import com.tatssense.core.Buckstracks;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, FragmentSplashScreen.newInstance())
                    .commit();
        }

//        Buckstracks.trackInst(this);
//        Buckstracks.trackOpen(this);

        ((MiApp)getApplication()).getAppComponent().inject(this);
    }
}
