package com.buckstracksui;

import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.buckstracksui.widget.ButtonCustom;
import com.buckstracksui.widget.TextViewCustom;

/**
 * Created by carmelo.iriti on 30/03/2015.
 */
public class ScFeedPopup extends Activity implements View.OnClickListener{

    ButtonCustom send;
    ButtonCustom cancel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.9f;
        params.dimAmount = 0.5f;
        getWindow().setAttributes(params);

        // Working around the IllegalStateException thrown by ActionBarView if the window size
        // is changed. Eg. android:windowIsFloating is set to true.
        Point size = new Point();
        Display display = getWindowManager().getDefaultDisplay();

        if(Build.VERSION.SDK_INT >= 11){
            fillSizeObjPostHoneycomb(size, display);
        }else{
            fillSizeObjPreHoneycomb(size, display);
        }

        getWindow().setLayout(size.x, size.y);


        setContentView(R.layout.feed_layout);


        Log.i(ScFeedPopup.class.getSimpleName(), "onCreate");

        send = (ButtonCustom) findViewById(R.id.feed_send);
        cancel = (ButtonCustom) findViewById(R.id.feed_cancel);

        send.setOnClickListener(this);
        cancel.setOnClickListener(this);


    }



    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void fillSizeObjPreHoneycomb(Point size, Display display) {
        fillSizeObjPreHoneycomb(size, display);
        size.x = display.getWidth();  // deprecated
        size.y = display.getHeight();  // deprecated
        size.x *= 0.8f;
        size.y *= 0.8f;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void fillSizeObjPostHoneycomb(Point size, Display display) {
        display.getSize(size);
        size.x *= 0.8f;
        size.y *= 0.8f;
    }


    @Override
    public void onClick(View v) {
        finish();
    }
}
