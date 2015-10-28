package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.mitelcel.pack.R;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class TextViewFolks extends TextView {

    String typeFace;
    Typeface font;

    public TextViewFolks(Context context) {
        super(context);
    }

    public TextViewFolks(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public TextViewFolks(Context context, AttributeSet attrs, int defStyleAttr) {
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

    public String getTypeFace() {
        return typeFace;
    }

    public void setTypeFace(String typeFace) {
        this.typeFace = typeFace;
        invalidate();
    }

    public Typeface getFont() {
        return font;
    }

    public void setFont(Typeface font) {
        this.font = font;
        invalidate();
    }
}
