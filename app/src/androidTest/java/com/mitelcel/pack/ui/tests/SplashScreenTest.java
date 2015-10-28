package com.mitelcel.pack.ui.tests;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;

import com.mitelcel.pack.ui.LoginOrRegister;
import com.mitelcel.pack.ui.SplashScreen;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class SplashScreenTest extends ActivityInstrumentationTestCase2<SplashScreen> {

    SplashScreen splashScreen;
    View rootActivity;
    SharedPreferences pref;

    public SplashScreenTest() {
        super(SplashScreen.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);
        splashScreen = getActivity();
        rootActivity = splashScreen.getWindow().getDecorView();
        pref = PreferenceManager.getDefaultSharedPreferences(splashScreen);
        pref.edit().clear().commit();
    }

//    public void testPreconditions(){
//
//    }

    public void testOpenLoginOrSignupActivity(){

        // add monitor to check for the second activity
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(LoginOrRegister.class.getName(), null, false);

        // wait 2 seconds for the start of the activity
        LoginOrRegister loginOrRegister = (LoginOrRegister) monitor.waitForActivityWithTimeout(2000);
        assertNotNull(loginOrRegister);

        // check if token is not present
        assertNotNull(pref.getString(MiUtils.MiAppPreferences.TOKEN, null));

    }
}
