package com.mitelcel.pack.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.fragment.FragSplashScreen;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
//import com.tatssense.core.Buckstracks;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, FragSplashScreen.newInstance())
                    .commit();
        }

//        Buckstracks.trackInst(this);
//        Buckstracks.trackOpen(this);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        if(!ImageLoader.getInstance().isInited())
            ImageLoader.getInstance().init(imageLoaderConfiguration);

        ((MiApp)getApplication()).getAppComponent().inject(this);
    }
}
