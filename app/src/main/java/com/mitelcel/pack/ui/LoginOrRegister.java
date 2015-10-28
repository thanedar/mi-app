package com.mitelcel.pack.ui;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.MenuItem;
import android.view.View;

import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.fragment.FragLogin;
import com.mitelcel.pack.ui.fragment.FragLoginOrRegister;
import com.mitelcel.pack.ui.fragment.FragRegister;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiUtils;

public class LoginOrRegister extends BaseActivityLogin implements OnDialogListener {

    public static String BACK_STACK_NAME = "back_stack";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_or_register);

        if (savedInstanceState == null) {
            //FragmentManager fragmentManager, String tag, Fragment fragment, int fragContainerResId
            FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragLoginOrRegister.TAG, FragLoginOrRegister.newInstance(), R.id.container);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void clickWidgetOnFragment(View view) {
        switch (view.getId()) {
            case R.id.signin_tv_new_user://1
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragRegister.TAG, FragRegister.newInstance("", ""), R.id.container);
                break;
            case R.id.login_or_register_already_reg://2
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragLogin.TAG, FragLogin.newInstance("", ""), R.id.container);
                break;
            case R.id.login_or_register_login://2
                FragmentHandler.addFragmentInBackStackWithAnimation(getSupportFragmentManager(), BACK_STACK_NAME, FragLogin.TAG, FragLogin.newInstance("", ""), R.id.container);
                break;
        }
    }


    @Override
    public void showDialogErrorCall(String content, String btnTex, @IdRes int resId, int requestCode) {
        if (requestCode == DialogActivity.REQ_SIGN_UP || requestCode == DialogActivity.REQ_SIGN_IN) {
            MiUtils.showDialogError(this, content, btnTex, resId, DialogActivity.APP_ERROR_CALL);
            MiUtils.MiAppPreferences.clear(this);
        }

    }
}
