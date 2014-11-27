package com.yhj.oneventdispatch.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.yhj.oneventdispatch.SLog;

/**
 * Created by scott on 14-11-26.
 */
public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                SLog.e("MyLinearLayout---dispatchTouchEvent---Down");
                break;
            case MotionEvent.ACTION_MOVE:
                SLog.e("MyLinearLayout---dispatchTouchEvent---Move");
                break;
            case MotionEvent.ACTION_UP:
                SLog.e("MyLinearLayout---dispatchTouchEvent---Up");
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                SLog.e("MyLinearLayout---onTouchEvent---Down");
                break;
            case MotionEvent.ACTION_MOVE:
                SLog.e("MyLinearLayout---onTouchEvent---Move");
                break;
            case MotionEvent.ACTION_UP:
                SLog.e("MyLinearLayout---onTouchEvent---Up");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                SLog.e("MyLinearLayout---onInterceptTouchEvent---Down");
                break;
            case MotionEvent.ACTION_MOVE:
                SLog.e("MyLinearLayout---onInterceptTouchEvent---Move");
                break;
            case MotionEvent.ACTION_UP:
                SLog.e("MyLinearLayout---onInterceptTouchEvent---Up");
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
