package com.mitelcel.pack.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.ImageView;

import com.buckstracksui.ScreensUI;
import com.mitelcel.pack.MiApp;
import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.fragment.FragmentTutorialImage;
import com.mitelcel.pack.ui.fragment.FragmentTutorialText;
import com.mitelcel.pack.utils.MiLog;
import com.mitelcel.pack.utils.MiUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TutorialActivity extends BaseActivity {

    private static int currentItem = 0;

    @InjectView(R.id.tutorial_pager)
    ViewPager viewPager;

    @InjectView(R.id.progress_1)
    ImageView progress_1;
    @InjectView(R.id.progress_2)
    ImageView progress_2;
    @InjectView(R.id.progress_3)
    ImageView progress_3;
    @InjectView(R.id.progress_4)
    ImageView progress_4;
    @InjectView(R.id.progress_5)
    ImageView progress_5;

    @InjectView(R.id.btn_next)
    Button btn_next;
    @InjectView(R.id.btn_skip)
    Button btn_skip;
    @InjectView(R.id.btn_start)
    Button btn_start;

    TutorialPagerAdapter pagerAdapter;
    TutorialPageListener pageListener = new TutorialPageListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.inject(this);

        ButterKnife.inject(this);

        ((MiApp)getApplication()).getAppComponent().inject(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);

        pagerAdapter = new TutorialPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(pageListener);

//        ScreensUI.getInstance(this, getString(R.string.tutorial_screen));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void nextScreen(View v){
        currentItem = viewPager.getCurrentItem();
        MiLog.i("TutorialActivity", "nextScreen currentItem value [" + currentItem + "]");
        if(currentItem < 4) {
            viewPager.setCurrentItem(currentItem + 1);
        }
    }

    public void startApp(View v) {
        MiLog.i("TutorialActivity", "getLogout value [" + MiUtils.MiAppPreferences.getLoggedStatus() + "]");
        if(MiUtils.MiAppPreferences.getLoggedStatus() != MiUtils.MiAppPreferences.LOGIN) {
            MiUtils.startSkillActivity(this, LoginOrRegister.class);
        }
        this.finish();
    }

    public void changeButtons(boolean skip) {
        if(skip) {
            btn_next.setVisibility(View.VISIBLE);
            btn_skip.setVisibility(View.VISIBLE);
            btn_start.setVisibility(View.INVISIBLE);
        }
        else {
            btn_next.setVisibility(View.INVISIBLE);
            btn_skip.setVisibility(View.INVISIBLE);
            btn_start.setVisibility(View.VISIBLE);
        }
    }

    class TutorialPagerAdapter extends FragmentStatePagerAdapter{

        private final String ARG_COUNT = "count";

        public TutorialPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            currentItem = i;

            Bundle args = new Bundle();
            args.putInt(ARG_COUNT, i);

            Fragment fragment;
            if (i == 0) {
                fragment = new FragmentTutorialText();
            }
            else {
                fragment = new FragmentTutorialImage();
            }

            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 5;
        }
    }

    class TutorialPageListener extends ViewPager.SimpleOnPageChangeListener{

        @Override
        public void onPageSelected(int position) {
            MiLog.i("TutorialActivity", "onPageSelected position value [" + position + "]");

            switch (position){
                case 0:
                    progress_1.setImageResource(R.drawable.progress_filled);
                    progress_2.setImageResource(R.drawable.progress_empty);
                    progress_3.setImageResource(R.drawable.progress_empty);
                    progress_4.setImageResource(R.drawable.progress_empty);
                    progress_5.setImageResource(R.drawable.progress_empty);
                    changeButtons(true);
                    break;
                case 1 :
                    progress_1.setImageResource(R.drawable.progress_filled);
                    progress_2.setImageResource(R.drawable.progress_filled);
                    progress_3.setImageResource(R.drawable.progress_empty);
                    progress_4.setImageResource(R.drawable.progress_empty);
                    progress_5.setImageResource(R.drawable.progress_empty);
                    changeButtons(true);
                    break;
                case 2 :
                    progress_1.setImageResource(R.drawable.progress_filled);
                    progress_2.setImageResource(R.drawable.progress_filled);
                    progress_3.setImageResource(R.drawable.progress_filled);
                    progress_4.setImageResource(R.drawable.progress_empty);
                    progress_5.setImageResource(R.drawable.progress_empty);
                    changeButtons(true);
                    break;
                case 3 :
                    progress_1.setImageResource(R.drawable.progress_filled);
                    progress_2.setImageResource(R.drawable.progress_filled);
                    progress_3.setImageResource(R.drawable.progress_filled);
                    progress_4.setImageResource(R.drawable.progress_filled);
                    progress_5.setImageResource(R.drawable.progress_empty);
                    changeButtons(true);
                    break;
                case 4 :
                    progress_1.setImageResource(R.drawable.progress_filled);
                    progress_2.setImageResource(R.drawable.progress_filled);
                    progress_3.setImageResource(R.drawable.progress_filled);
                    progress_4.setImageResource(R.drawable.progress_filled);
                    progress_5.setImageResource(R.drawable.progress_filled);
                    changeButtons(false);
                    break;
            }
        }

    }
}
