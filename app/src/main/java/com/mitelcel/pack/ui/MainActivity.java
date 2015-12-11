package com.mitelcel.pack.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mitelcel.pack.Config;
import com.mitelcel.pack.R;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.api.bean.req.BeanGetCurrentBalance;
import com.mitelcel.pack.api.bean.req.BeanLogout;
import com.mitelcel.pack.api.bean.resp.BeanGetCurrentBalanceResponse;
import com.mitelcel.pack.api.bean.resp.BeanLogoutResponse;
import com.mitelcel.pack.ui.fragment.FragmentAccount;
import com.mitelcel.pack.ui.fragment.FragmentMain;
import com.mitelcel.pack.ui.listener.OnDialogListener;
import com.mitelcel.pack.ui.listener.OnMainFragmentInteractionListener;
import com.mitelcel.pack.ui.widget.CustomDrawerLayout;
import com.mitelcel.pack.ui.widget.LayoutBalance;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends BaseActivity implements OnMainFragmentInteractionListener, FragmentAccount.OnAccountFragmentInteractionListener, OnDialogListener{

    @InjectView(R.id.drawer_layout)
    CustomDrawerLayout mDrawerLayout;
    RecyclerView mDrawerList;
    ActionBarDrawerToggle mDrawerToggle;

    FragmentMain fragmentMain;

    @Inject
    MiApiClient miApiClient;

    boolean mIsDialogStarted = false;

    protected static final String BACK_STACK_NAME = "bk_main";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MiUtils.MiAppPreferences.setLogin();
        setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        fragmentMain = FragmentMain.newInstance();

        if (savedInstanceState == null) {
            FragmentHandler.replaceFragment(
                    getSupportFragmentManager(),
                    FragmentMain.class.getName(),
                    fragmentMain,
                    R.id.main_content_fragment);
        }

        mDrawerToggle = new ActionBarDrawerToggle(
                this,              /* host Activity */
                mDrawerLayout,     /* DrawerLayout object */
                0,
                0
        );

        mDrawerLayout.setDrawerListener(mDrawerToggle);

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

        getCurrentBalance();
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
        switch (view.getId()){
            case R.id.navdrawer_item_home:
                FragmentHandler.addFragmentInBackStackWithAnimation(
                        getSupportFragmentManager(),
                        BACK_STACK_NAME,
                        FragmentMain.class.getName(),
                        fragmentMain,
                        R.id.main_content_fragment);
                break;
            case R.id.navdrawer_item_recent:
                startActivity(new Intent(this, RecentActivity.class));
                break;
            case R.id.navdrawer_item_frequent_numbers:
                startActivity(new Intent(this, FrequentNumbersActivity.class));
                break;
            case R.id.navdrawer_item_services:
                startActivity(new Intent(this, ServiceActivity.class));
                break;
            case R.id.navdrawer_item_recharge:
                startActivity(new Intent(this, RechargeActivity.class));
                break;
            case R.id.navdrawer_item_pastime:
                startActivity(new Intent(this, TransferActivity.class));
                break;
            case R.id.navdrawer_item_offers:
                startActivity(new Intent(this, ListOfferActivity.class));
                break;
            case R.id.navdrawer_item_communicate:
                startActivity(new Intent(this, CommunicateActivity.class));
                break;
            case R.id.navdrawer_item_help:
                Intent iHelp = new Intent(this, WebViewActivity.class);
                iHelp.putExtra(WebViewActivity.VIEW_TYPE, WebViewActivity.VIEW_HELP);
                startActivity(iHelp);
                break;
            case R.id.navdrawer_item_terms:
                Intent iTerms = new Intent(this, WebViewActivity.class);
                iTerms.putExtra(WebViewActivity.VIEW_TYPE, WebViewActivity.VIEW_TERMS);
                startActivity(iTerms);
                break;
            case R.id.navdrawer_item_privacy:
                Intent iPrivacy = new Intent(this, WebViewActivity.class);
                iPrivacy.putExtra(WebViewActivity.VIEW_TYPE, WebViewActivity.VIEW_PRIVACY);
                startActivity(iPrivacy);
                break;
            case R.id.navdrawer_item_tutorial:
                startActivity(new Intent(this, TutorialActivity.class));
                break;
            case R.id.navdrawer_item_logout:
                logout();
                break;
            default:
                MiLog.d("Main Activity", "onClickNavigationDrawer default started with View " + view.getId());
        }
    }

    private void logout(){
        BeanLogout beanLogout = new BeanLogout();
        miApiClient.logout(beanLogout, new Callback<BeanLogoutResponse>() {
            @Override
            public void success(BeanLogoutResponse beanLogoutResponse, Response response) {
//                MiLog.i(TAG, "Logout response " + beanLogoutResponse.toString());
                if (beanLogoutResponse.getError().getCode() == Config.SUCCESS) {
                    MiLog.i("Logout", "Logout API error response " + beanLogoutResponse.toString());

                    MiUtils.MiAppPreferences.logOut(MainActivity.this);
                } else {
                    MiLog.i("Logout", "Logout API error response " + beanLogoutResponse.toString());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                MiLog.i("Logout", "Logout failure " + error.toString());
                showDialogErrorCall(getString(R.string.something_is_wrong), getString(R.string.retry), DialogActivity.DIALOG_HIDDEN_ICO, DialogActivity.APP_REQ);
            }
        });
    }

    public void getCurrentBalance() {
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            BeanGetCurrentBalance beanGetCurrentBalance = new BeanGetCurrentBalance();

            View progressBar = actionBar.getCustomView().findViewById(R.id.progress_bar_balance);
            progressBar.setVisibility(View.VISIBLE);
            TextView balanceView = (TextView) actionBar.getCustomView().findViewById(R.id.act_bar_balance);
            balanceView.setVisibility(View.GONE);

            MiLog.i("MainActivity", "beanGetCurrentBalance [ " + beanGetCurrentBalance.toString() + " ]");
            miApiClient.get_current_balance(beanGetCurrentBalance, new Callback<BeanGetCurrentBalanceResponse>() {
                @Override
                public void success(BeanGetCurrentBalanceResponse beanGetCurrentBalanceResponse, Response response) {
                    if (beanGetCurrentBalanceResponse != null) {
                        MiLog.i("MainActivity", "BeanGetCurrentBalanceResponse response [ " + beanGetCurrentBalanceResponse.toString() + " ]");
                        progressBar.setVisibility(View.GONE);
                        balanceView.setVisibility(View.VISIBLE);
                        if (beanGetCurrentBalanceResponse.getError().getCode() == Config.SUCCESS) {
                            String balance = beanGetCurrentBalanceResponse.getResult().getCurrentBalance();
                            MiUtils.MiAppPreferences.setCurrentBalance(Float.parseFloat(balance));
                        }
                    }
                    else
                        MiLog.i("MainActivity", "BeanGetCurrentBalanceResponse response [ NULL ]");
                }

                @Override
                public void failure(RetrofitError error) {
                    progressBar.setVisibility(View.GONE);
                    balanceView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_alert, 0, 0, 0);
                    balanceView.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    @Override
    public void updateActionBar() {
    }

    @Override
    public void onAccountFragmentInteraction () {
        MiLog.i("OnAccountFragmentInteractionListener", " ");
    }

    @Override
    public void onMainFragmentInteraction (int id) {
        MiLog.i("OnMainFragmentInteractionListener", " ");
        switch (id){
            case R.id.empty_list:
            case R.id.home_recent_act:
            MiUtils.startSkillActivity(this, RecentActivity.class);
        }
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
}

