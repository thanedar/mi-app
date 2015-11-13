package com.mitelcel.pack.ui;

import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.MenuItem;

import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.fragment.FragRecent;
import com.mitelcel.pack.utils.FragmentHandler;

public class RecentActivity extends BaseActivity {

    private static final String TAG = RecentActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recent);

        if (savedInstanceState == null) {
            FragmentHandler.addFragmentInBackStack(getSupportFragmentManager(), null, FragRecent.TAG, FragRecent.newInstance(), R.id.container);
        }

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
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
