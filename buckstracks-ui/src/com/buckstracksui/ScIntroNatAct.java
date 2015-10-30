package com.buckstracksui;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.tatssense.core.retargeting.beans.response.intro.PageIntroNative;
import com.tatssense.core.retargeting.controller.AbLayerScreenFragmentActivity;
import com.tatssense.core.retargeting.controller.ILayerScreenListner;
import com.tatssense.core.retargeting.controller.databean.DataIntroNativeBean;

import java.util.List;


public class ScIntroNatAct extends AbLayerScreenFragmentActivity implements ILayerScreenListner.ScreenIntroNativeListner{

    private static final String TAG = "ScIntroNatAct";

    /**
     * The pager widget, which handles animation and allows swiping horizontally to access previous
     * and next wizard steps.
     */
    private ViewPager mPager;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onReceive(DataIntroNativeBean bean) {

        String layout = bean.getIntroNative().getTplName();

        Log.d(TAG, layout);
        int resID = getApplicationContext().getResources().getIdentifier(layout, "layout", getApplicationContext().getPackageName());
        setContentView(resID);

        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), bean.getPageIntroNative());
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // When changing pages, reset the action bar actions since they are dependent
                // on which page is currently active. An alternative approach is to have each
                // fragment expose actions itself (rather than the activity exposing actions),
                // but for simplicity, the activity provides the actions in this sample.
                if (android.os.Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB) {
                    supportInvalidateOptionsMenu();
                } else
                    invalidateOptionsMenu();
            }
        });

    }



    public void btnClick(View view){
        if( view.getId() == R.id.btn_left){
            // Go to the previous step in the wizard. If there is no previous step,
            // setCurrentItem will do nothing.
            if(mPager.getCurrentItem()>0)
                mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }else if(view.getId() == R.id.btn_right){
            // Advance to the next step in the wizard. If there is no next step, setCurrentItem
            // will do nothing.
            if(mPager.getCurrentItem()<mPagerAdapter.getCount()-1)
                mPager.setCurrentItem(mPager.getCurrentItem() + 1);
        }else if(view.getId() == R.id.btn_close)

            // Advance to the next step in the wizard. If there is no next step, setCurrentItem
            // will do nothing.
            finish();
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private List<PageIntroNative> images_link;

        public ScreenSlidePagerAdapter(FragmentManager fm, List<PageIntroNative> images_link) {
            super(fm);
            this.images_link = images_link;
        }

        @Override
        public Fragment getItem(int position) {
            return ScreenSlidePageFragment.create(images_link.get(position));
        }

        @Override
        public int getCount() {
            return images_link.size();
        }
    }

    public static class ScreenSlidePageFragment extends Fragment {

        public static final String ARG_PAGE = "link";
        private PageIntroNative pageIntroNative;

        public static ScreenSlidePageFragment create(PageIntroNative pageIntroNative) {
            ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
            Bundle args = new Bundle();
            args.putSerializable(ARG_PAGE, pageIntroNative);
            fragment.setArguments(args);
            return fragment;
        }

        public ScreenSlidePageFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            pageIntroNative = (PageIntroNative)getArguments().getSerializable(ARG_PAGE);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Inflate the layout containing a title and body text.
            ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_screen_slide_page_image, container, false);

            if (pageIntroNative!=null) {
                ImageView image = (ImageView) rootView.findViewById(R.id.image);
                AQuery a = new AQuery(getActivity());
                a.id(image).image(pageIntroNative.getSrc());
                ((TextView) rootView.findViewById(R.id.title)).setText(pageIntroNative.getText());
            }
            return rootView;
        }
    }


}