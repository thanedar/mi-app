package com.mitelcel.pack.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.fragment.FragmentSplashScreen;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

//import com.tatssense.core.Buckstracks;

public class SplashScreen extends AppCompatActivity {

    boolean mIsDialogStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        if (savedInstanceState == null) {
            FragmentHandler.addFragment(getSupportFragmentManager(), FragmentSplashScreen.TAG, FragmentSplashScreen.newInstance(), R.id.container);
        }

        checkInternetConnection();
//        Buckstracks.trackInst(this);
//        Buckstracks.trackOpen(this);

        ((MiApp)getApplication()).getAppComponent().inject(this);
    }

    private void checkInternetConnection() {
        MiLog.d("SplashScreen", "Started checkInternetConnection");
        if(!MiUtils.Info.isNetworkConnected(getApplicationContext()) && !mIsDialogStarted){
            mIsDialogStarted = true;
            int res_id = this.getResources().getIdentifier("ic_no_network", "drawable", MiApp.getInstance().getPackageName());
            MiUtils.showDialogQuery(this,
                    getString(R.string.oops),
                    getString(R.string.no_connection_popup),
                    getString(R.string.retry),
                    getString(R.string.close),
                    res_id,
                    DialogActivity.APP_REQ);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mIsDialogStarted = false;
        if(requestCode  == DialogActivity.APP_REQ && resultCode == DialogActivity.APP_RES)
            finish();
        else if(requestCode  == DialogActivity.APP_REQ && resultCode == DialogActivity.APP_REFRESH) {
            new Handler().postDelayed(() -> checkInternetConnection(), 1000);
        }
    }
}
