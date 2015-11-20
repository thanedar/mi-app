package com.mitelcel.pack.ui;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.api.MiApiClient;
import com.mitelcel.pack.ui.fragment.FragmentOffers;
import com.mitelcel.pack.ui.listener.OnMainFragmentInteractionListener;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class ListOfferActivity extends BaseActivity implements OnMainFragmentInteractionListener {

    private static final String TAG = ListOfferActivity.class.getName();
    FragmentOffers fragmentOffers;

    @Inject
    MiApiClient miApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);

        fragmentOffers = FragmentOffers.newInstance();

        if (savedInstanceState == null) {
            FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragmentOffers.TAG, FragmentOffers.newInstance(), R.id.container);
        }

        ((MiApp)getApplication()).getAppComponent().inject(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            super.onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void updateActionBar() {
    }

    @Override
    public void noInternetConnection() {
    }

    @Override
    public void goBackPreviousFragment() {
        super.onBackPressed();
    }


    @Override
    public void onMainFragmentInteraction (int id) {
        MiLog.i("OnMainFragmentInteractionListener", " ");
    }
}
