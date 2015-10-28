package com.mitelcel.pack.ui.tests;

import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.test.ViewAsserts;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.LoginOrRegister;
import com.mitelcel.pack.ui.MainActivity;
import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class LoginOrRegisterTest extends ActivityInstrumentationTestCase2<LoginOrRegister> {

    LoginOrRegister loginOrRegister;
    View rootActivity;
    SharedPreferences pref;

    public LoginOrRegisterTest() {
        super(LoginOrRegister.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        //NOTE: Activities under test may not be started from within the UI thread. If your test method is annotated with UiThreadTest,
        // then you must call setActivityInitialTouchMode(boolean) from setUp().
        setActivityInitialTouchMode(false);
        loginOrRegister = getActivity();
        rootActivity = loginOrRegister.getWindow().getDecorView();
        pref = PreferenceManager.getDefaultSharedPreferences(loginOrRegister);
        pref.edit().clear().commit();
    }

    public void testCheckToken() throws Exception{
        // before the auth call
        assertNull(pref.getString(MiUtils.MiAppPreferences.TOKEN, null));
        //wai
        Thread.sleep(5000);
        // after the auth call
        assertNotNull(pref.getString(MiUtils.MiAppPreferences.TOKEN, null));
    }

    public void testLogIn() throws Exception{
        // get play as guest user
        Button btn = (Button)rootActivity.findViewById(R.id.login_or_register_register);
        // click on btn
        TouchUtils.clickView(this, btn);
        // get btn logon
        Button btnLogin = (Button)rootActivity.findViewById(R.id.signin_btn_logon);
        // check if the button is on screen
        ViewAsserts.assertOnScreen(rootActivity, btnLogin);

        final EditText password = (EditText)rootActivity.findViewById(R.id.reg_red_et_pass);
        final EditText email = (EditText)rootActivity.findViewById(R.id.reg_red_et_email);

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                password.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(PASSWORD);
        getInstrumentation().waitForIdleSync();

        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                email.requestFocus();
            }
        });

        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(EMAIL);
        getInstrumentation().waitForIdleSync();

        TouchUtils.clickView(this, btnLogin);

        // create monitor
        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, false);
        // get the acttivity
        MainActivity mainActivity = (MainActivity)monitor.waitForActivityWithTimeout(2000);
        // check if the activity is started
        assertNotNull(mainActivity);
    }

    public static String EMAIL = "aaa@aaa.it";
    public static String PASSWORD = "aaa";
    public static String CONFIRM_PASS = "aaa";
    public void testCreateNewAccount(){
        Button btnRegisterAccount = (Button)rootActivity.findViewById(R.id.signup_btn_submit);
        ViewAsserts.assertOnScreen(rootActivity, btnRegisterAccount);

        final EditText mail = (EditText)rootActivity.findViewById(R.id.signup_et_email);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                mail.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(EMAIL);
        getInstrumentation().waitForIdleSync();

        final EditText pass = (EditText)rootActivity.findViewById(R.id.signup_et_pass);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                pass.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(PASSWORD);
        getInstrumentation().waitForIdleSync();

        final EditText conf_pass = (EditText)rootActivity.findViewById(R.id.signup_et_confirm_pass);
        getInstrumentation().runOnMainSync(new Runnable() {
            @Override
            public void run() {
                conf_pass.requestFocus();
            }
        });
        getInstrumentation().waitForIdleSync();
        getInstrumentation().sendStringSync(CONFIRM_PASS);
        getInstrumentation().waitForIdleSync();

        TouchUtils.clickView(this, btnRegisterAccount);

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(MainActivity.class.getName(), null, true);
        MainActivity mainActivity2 = (MainActivity)monitor.waitForActivityWithTimeout(3000);
        assertNotNull(mainActivity2);

    }


}
