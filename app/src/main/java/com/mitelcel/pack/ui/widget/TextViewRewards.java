package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mitelcel.pack.utils.MiUtils;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class TextViewRewards extends TextView {
    public TextViewRewards(Context context) {
        super(context);
        init(context);
    }

    public TextViewRewards(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TextViewRewards(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        setText(MiUtils.MiAppPreferences.getMyEarnRewards(context));
    }
}
