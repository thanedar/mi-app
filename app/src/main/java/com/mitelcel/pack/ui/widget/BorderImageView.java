package com.mitelcel.pack.ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.mitelcel.pack.R;

/**
 * Created by sudhanshu.thanedar on 10/26/2015.
 */
public class BorderImageView extends ImageView {

    RectF rectF;
    Paint mBorderPaint;
    int borderWidth;
    int borderColor;

    RectF rectFBg;
    Paint mBorderPaintBG;

    public BorderImageView(Context context) {
        super(context);
        init(null);
    }

    public BorderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BorderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        canvas.drawRect(rectFBg,mBorderPaintBG);

        super.onDraw(canvas);
        canvas.drawRect(rectF, mBorderPaint);

    }

    void init(AttributeSet attrs){

        borderColor = Color.WHITE;
        borderWidth = getContext().getResources().getDimensionPixelOffset(R.dimen.borderimage_width);

        if(attrs != null){
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CircleImageView);
            try{
                borderColor = a.getInt(R.styleable.CircleImageView_border_color, borderColor);
                borderWidth = a.getInt(R.styleable.CircleImageView_border_width, borderWidth);
            }finally {
                a.recycle();
            }
        }

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStrokeWidth(borderWidth);

        mBorderPaintBG = new Paint();
        mBorderPaintBG.setColor(getContext().getResources().getColor(R.color.border_image));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(0,0, w-0, h-0);

        rectFBg = new RectF(0,0, w-0, h-0);
    }

}
