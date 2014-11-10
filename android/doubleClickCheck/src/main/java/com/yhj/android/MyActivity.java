package com.yhj.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

//onTouch方法中判断事件是单击还是双击还是移动

public class MyActivity extends Activity {
    private long downTime;            //按下时间
    private long upTime;              //弹起时间
    private int currX;                //当前点击X坐标
    private int currY;                //当前点击Y坐标
    private int downX;                //按下时X坐标
    private int downY;                //按下时Y坐标
    private int upX;                  //弹起时X坐标
    private int upY;                  //弹起时Y坐标

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
 /*       this.currX = (int)event.getRawX();
        this.currY = (int)event.getRawY();*/
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.downX = (int)event.getRawX();
                this.downY = (int)event.getRawY();
                this.downTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                this.upX = (int)event.getX();
                this.upY = (int)event.getY();
                this.upTime = System.currentTimeMillis();
                if (upTime - downTime > 500 && (upX - downX) <= 5 && (upY - downY) <= 5) {
                    Toast.makeText(MyActivity.this,"long click",Toast.LENGTH_LONG).show();
                }
                if (upTime - downTime < 300 && (upX - downX) <= 5 && (upY - downY) <= 5) {
                    Toast.makeText(MyActivity.this,"short click",Toast.LENGTH_LONG).show();
                }
                this.downX = 0;
                this.downY = 0;
                this.currX = 0;
                this.currY = 0;
                this.downTime = 0;
                this.upTime = 0;
                break;
        }
        return super.onTouchEvent(event);
    }
}
