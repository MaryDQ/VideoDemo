package com.microsys.videodemo.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MyRecyclerView extends RecyclerView {

    private final int MAXHEIGHT = 900;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int heightMode = MeasureSpec.getMode(heightSpec);
        int heightSize = MeasureSpec.getSize(heightSpec);

        if (heightMode == MeasureSpec.EXACTLY) {
            heightSize = heightSize <= MAXHEIGHT ? heightSize
                    : (int) MAXHEIGHT;
        }

        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = heightSize <= MAXHEIGHT ? heightSize
                    : (int) MAXHEIGHT;
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            heightSize = heightSize <= MAXHEIGHT ? heightSize
                    : (int) MAXHEIGHT;
        }
        int maxHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize,
                heightMode);
        super.onMeasure(widthSpec, maxHeightMeasureSpec);
    }
}
