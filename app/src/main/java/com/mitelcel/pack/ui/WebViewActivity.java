package com.mitelcel.pack.ui;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.fragment.FragmentHelp;
import com.mitelcel.pack.ui.fragment.FragmentPrivacy;
import com.mitelcel.pack.ui.fragment.FragmentTerms;
import com.mitelcel.pack.utils.FragmentHandler;
import com.mitelcel.pack.utils.MiLog;

import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity {

    private static final String TAG = WebViewActivity.class.getName();

    public static final String VIEW_TYPE     = "";

    public static final String VIEW_FAQ      = "faq";
    public static final String VIEW_HELP     = "help";
    public static final String VIEW_TERMS    = "terms";
    public static final String VIEW_PRIVACY  = "privacy";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        ButterKnife.inject(this);

        ((MiApp)getApplication()).getAppComponent().inject(this);

        Bundle bundle = getIntent().getExtras();

        if(bundle == null || !bundle.containsKey(VIEW_TYPE)) return;

        String viewType = bundle.getString(VIEW_TYPE, VIEW_HELP);
        MiLog.i(TAG, "view type " + viewType);
        if(viewType.equals(VIEW_HELP)){
            MiLog.d(TAG, "In help");
            FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentHelp.TAG, FragmentHelp.newInstance(), R.id.container);
        }
        else if(viewType.equals(VIEW_PRIVACY)){
            MiLog.d(TAG, "In privacy");
            FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentPrivacy.TAG, FragmentPrivacy.newInstance(), R.id.container);
        }
        else if(viewType.equals(VIEW_TERMS)){
            MiLog.d(TAG, "In terms");
            FragmentHandler.replaceFragment(getSupportFragmentManager(), FragmentTerms.TAG, FragmentTerms.newInstance(), R.id.container);
        }
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
}
