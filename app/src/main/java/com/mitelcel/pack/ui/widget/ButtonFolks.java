package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.Button;

import com.mitelcel.pack.R;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class ButtonFolks extends Button{

    String typeFace;
    Typeface font;

    public ButtonFolks(Context context) {
        super(context);
    }

    public ButtonFolks(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ButtonFolks(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    void init(AttributeSet attrs){

        if(attrs == null) return;

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WidgetTypeFaceFolks);
        try{
            typeFace = a.getString(R.styleable.WidgetTypeFaceFolks_type_face);
        }finally {
            a.recycle();
        }

        if(typeFace != null && !typeFace.equals("")){
            font = Typeface.createFromAsset(getContext().getAssets(), typeFace);
            setTypeface(font);
        }

    }
}
