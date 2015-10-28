package com.mitelcel.pack.ui.tests;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.TouchUtils;
import android.view.View;
import android.widget.GridView;

import com.mitelcel.pack.R;
import com.mitelcel.pack.ui.MainActivity;

import java.util.Random;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mainActivity;
    View rootView;

    public MainActivityTest() {
        super(MainActivity.class);

    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
        rootView = mainActivity.getWindow().getDecorView();
    }

    public void testClickList()throws Exception {

        Thread.sleep(3000);

        final int POSTITION_IN_LIST = 0;
        final GridView listLiew = (GridView) rootView.findViewById(R.id.grid_view);
//        getInstrumentation().runOnMainSync(new Runnable() {
//            @Override
//            public void run() {
//                listLiew.performItemClick(listLiew, POSTITION_IN_LIST, listLiew.getItemIdAtPosition(POSTITION_IN_LIST));
//            }
//        });

//        TouchUtils.clickView(this, listLiew.getChildAt(POSTITION_IN_LIST));

        Thread.sleep(2000);

        /*Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(GameDetail.class.getName(), null , false);
        GameDetail activity = (GameDetail)monitor.waitForActivityWithTimeout(2000);
        assertNotNull(activity);*/

    }

    public void testScrollListAndClickLatItem() throws Exception{
        Thread.sleep(3000);

        final GridView gridView = (GridView)rootView.findViewById(R.id.grid_view);

//        TouchUtils.scrollToBottom(this, mainActivity, gridView);
        for(int i = 0; i < 5; i++)
        TouchUtils.dragQuarterScreenUp(this, mainActivity);

        int position = (new Random().nextInt(gridView.getLastVisiblePosition()-gridView.getFirstVisiblePosition()));
        Thread.sleep(1000);
        /*TouchUtils.clickView(this, gridView.getChildAt(position));

        Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(GameDetail.class.getName(), null, false);

        GameDetail gameDetail = (GameDetail)monitor.waitForActivityWithTimeout(2000);

        assertNotNull(gameDetail);*/
    }
}
