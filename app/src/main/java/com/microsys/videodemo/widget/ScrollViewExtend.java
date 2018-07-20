package com.microsys.videodemo.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 自定义ScrollView
 * Created by shengf on 2017/2/26.
 */
public class ScrollViewExtend extends ScrollView {
    // 滑动距离及坐标
    private float xDistance;
    private float yDistance;
    private float xLast;
    private float yLast;

    private TTListViewHandleEvent lvhe;

    private int clickX;
    private int clickY;
    private int itemPos;

    public ScrollViewExtend(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();

                if (lvhe != null) {
                    lvhe.handleTouchEvent(ev);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();

                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;

                if (xDistance > yDistance) {
                    return false;
                }
                break;
            default:
                break;

        }

        return super.onInterceptTouchEvent(ev);
    }


    public void setLVHE(TTListViewHandleEvent le) {
        lvhe = le;
    }

    public interface TTListViewHandleEvent {
        void handleTouchEvent(MotionEvent ev);
    }
}


