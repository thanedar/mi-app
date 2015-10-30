package com.buckstracksui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.buckstracksui.R;

/**
 * Created by carmelo.iriti on 30/03/2015.
 */
public class FeedLayout extends LinearLayout {

    public FeedLayout(Context context) {
        super(context);
        init();
    }

    public FeedLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    void init(){
        setOrientation(LinearLayout.HORIZONTAL);
        setWeightSum(2f);

        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.compound_send_cancel, this, true);

        setBackgroundResource(android.R.color.black);
    }
}
