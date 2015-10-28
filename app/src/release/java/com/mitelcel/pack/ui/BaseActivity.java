package com.mitelcel.pack.ui;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

import com.android.debug.hv.ViewServer;
import com.mitelcel.pack.BuildConfig;
import com.mitelcel.pack.R;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public abstract class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(R.drawable.ic_launcher);


        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
        }

        /**
         * utility to avoid issue with hyerarchy view
         */
        if(BuildConfig.DEBUG){
            ViewServer.get(this).addWindow(this);
        }

    }


    public void actionBarDecorator(String numberCoins){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions()
                | ActionBar.DISPLAY_SHOW_CUSTOM);

        View coins = View.inflate(getApplicationContext(), R.layout.actionbar_coins, null);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        layoutParams.rightMargin = 40;
        coins.setLayoutParams(layoutParams);
        actionBar.setCustomView(coins);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(BuildConfig.DEBUG)
            ViewServer.get(this).removeWindow(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(BuildConfig.DEBUG)
            ViewServer.get(this).setFocusedWindow(this);
    }
}
