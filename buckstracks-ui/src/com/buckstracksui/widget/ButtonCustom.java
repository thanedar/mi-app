package com.buckstracksui.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.buckstracksui.R;

/**
 * Created by carmelo.iriti on 30/03/2015.
 */
public class ButtonCustom extends Button{

    public ButtonCustom(Context context) {
        super(context);
        init();
    }

    public ButtonCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ButtonCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init(){
        setGravity(Gravity.CENTER);
        setBackgroundResource(R.drawable.selector_compound);
        setTextColor(getResources().getColor(R.color.feed_color));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        applyFilter(new float[] { 0f, 1f, 0.5f }, 1f, 2f, 2f);
    }

    public void applyFilter(float[] direction, float ambient, float specular, float blurRadius) {
        if (Build.VERSION.SDK_INT >= 11) {
            setSoftwareLayerType(this);
        }
        EmbossMaskFilter filter = new EmbossMaskFilter(direction, ambient, specular, blurRadius);
        getPaint().setMaskFilter(filter);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void setSoftwareLayerType(View view) {
        view.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }
}
