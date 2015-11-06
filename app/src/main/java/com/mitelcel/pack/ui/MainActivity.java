package com.mitelcel.pack.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.mitelcel.pack.R;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.MiRestClient;
import com.mitelcel.pack.api.bean.req.BeanLogout;
import com.mitelcel.pack.api.bean.resp.BeanLogoutResponse;
import com.mitelcel.pack.ui.fragment.FragHelp;
import com.mitelcel.pack.ui.fragment.FragMain;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.ui.listener.OnMainFragmentInteractionListener;
import com.mitelcel.pack.ui.widget.CustomDrawerLayout;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends BaseActivity implements OnMainFragmentInteractionListener, OnDialogListener{

    @InjectView(R.id.drawer_layout)
    CustomDrawerLayout mDrawerLayout;
    RecyclerView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;

    FragMain fragMain;

    @Inject
    MiApiClient miApiClient;

    boolean mIsDialogStarted = false;

    protected static final String BACK_STACK_NAME = "bk_main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MiUtils.MiAppPreferences.setLogin();
        setContentView(R.layout.main_activity);

        ButterKnife.inject(this);

        fragMain = FragMain.newInstance();

        if (savedInstanceState == null) {
            FragmentHandler.addFragmentInBackStackWithAnimation(
                    getSupportFragmentManager(),
                    BACK_STACK_NAME,
                    FragMain.class.getName(),
                    fragMain,
                    R.id.main_content_fragment);
        }

        mDrawerToggle = new ActionBarDrawerToggle(
                this,              /* host Activity */
                mDrawerLayout,     /* DrawerLayout object */
                0,
                0
        );

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        actionBarDecorator("300");

        ((MiApp)getApplication()).getAppComponent().inject(this);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkInternetConnection();
    }

    private void checkInternetConnection() {
        MiLog.d("Main Activity", "Started checkInternetConnection");
        if(!MiUtils.Info.isNetworkConnected(getApplicationContext()) && !mIsDialogStarted){
            mIsDialogStarted = true;
            int res_id = this.getResources().getIdentifier("ic_no_network", "drawable", MiApp.getInstance().getPackageName());
            MiLog.d("Main Activity", "in if check checkInternetConnection with resId " + res_id);
            MiUtils.showDialogError(this,
                    getString(R.string.no_connection_popup),
                    getString(R.string.retry),
                    getResources().getIdentifier("ic_no_network", "drawable", MiApp.getInstance().getPackageName()),
                    DialogActivity.APP_REQ);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickNavigationDrawer(View view){
        mDrawerLayout.closeDrawers();
        FragmentTransaction transaction;
        switch (view.getId()){
            case R.id.navdrawer_item_home:
                FragmentHandler.addFragmentInBackStackWithAnimation(
                        getSupportFragmentManager(),
                        BACK_STACK_NAME,
                        FragMain.class.getName(),
                        fragMain,
                        R.id.main_content_fragment);
                break;
            case R.id.navdrawer_item_recent:
//                startActivity(new Intent(this, InviteFriends.class));
                break;
            case R.id.navdrawer_item_frequent_numbers:
//                startActivity(new Intent(this, Tutorial.class));
                break;
            case R.id.navdrawer_item_services:
//                startActivity(new Intent(this, Settings.class));
                break;
            case R.id.navdrawer_item_recharge:
//                startActivity(new Intent(this, History.class));
                break;
            case R.id.navdrawer_item_pastime:
//                startActivity(new Intent(this, Rewards.class));
                break;
            case R.id.navdrawer_item_offers:
//                startActivity(new Intent(this, Rewards.class));
                break;
            case R.id.navdrawer_item_help:
                FragmentHandler.addFragmentInBackStackWithAnimation(
                        getSupportFragmentManager(),
                        BACK_STACK_NAME,
                        FragHelp.class.getName(),
                        FragHelp.newInstance("", ""),
                        R.id.main_content_fragment);
                break;
            case R.id.navdrawer_item_terms:
//                startActivity(new Intent(this, Rewards.class));
                break;
            case R.id.navdrawer_item_contact:
//                startActivity(new Intent(this, Rewards.class));
                break;
            case R.id.navdrawer_item_logout:
                logout();
                break;
        }
    }

    private void logout(){
        miApiClient = MiRestClient.init();
        BeanLogout beanLogout = new BeanLogout();
        miApiClient.logout(beanLogout, new Callback<BeanLogoutResponse>() {
            @Override
            public void success(BeanLogoutResponse beanLogoutResponse, Response response) {
//                MiLog.i(TAG, "Logout response " + beanLogoutResponse.toString());
                if (beanLogoutResponse.getError().getCode() == 0) {
                    MiLog.i("Logout", "Logout API error response " + beanLogoutResponse.toString());

                    MiUtils.MiAppPreferences.logOut(MainActivity.this);
                } else {
                    MiLog.i("Logout", "Logout API error response " + beanLogoutResponse.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i("Logout", "Logout failure " + error.toString());
            }
        });
    }

    @Override
    public void updateActionBar() {
    }

    @Override
    public void noInternetConnection() {
        checkInternetConnection();
    }

    @Override
    public void goBackPreviousFragment() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mIsDialogStarted = false;
        if(requestCode  == DialogActivity.APP_REQ && resultCode == DialogActivity.APP_RES)
            finish();
    }

    @Override
    public void showDialogErrorCall(String content, String btnTex, int resId, int requestCode) {
        MiUtils.showDialogError(this, content, btnTex, resId, requestCode);
    }

    public void actionBarDecorator(String numberCoins){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(actionBar.getDisplayOptions() | ActionBar.DISPLAY_SHOW_CUSTOM);

        View coins = View.inflate(getApplicationContext(), R.layout.actionbar_balance, null);

        ActionBar.LayoutParams layoutParams = new ActionBar.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT, Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        layoutParams.rightMargin = 40;
        coins.setLayoutParams(layoutParams);
        actionBar.setCustomView(coins);
    }
}

