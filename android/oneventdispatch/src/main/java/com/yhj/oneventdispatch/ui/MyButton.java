package com.yhj.oneventdispatch.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

import com.yhj.oneventdispatch.SLog;

/**
 * Created by scott on 14-11-26.
 */
public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                SLog.e("MyButton---dispatchTouchEvent---Down");
                break;
            case MotionEvent.ACTION_MOVE:
                SLog.e("MyButton---dispatchTouchEvent---Move");
                break;
            case MotionEvent.ACTION_UP:
                SLog.e("MyButton---dispatchTouchEvent---Up");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                SLog.e("MyButton---onTouchEvent---Down");
                break;
            case MotionEvent.ACTION_MOVE:
                SLog.e("MyButton---onTouchEvent---Move");
                break;
            case MotionEvent.ACTION_UP:
                SLog.e("MyButton---onTouchEvent---Up");
                break;
        }
        return super.onTouchEvent(event);
    }
}
