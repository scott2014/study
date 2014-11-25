package com.yhj.popupmenu;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import com.yhj.popupmenu.ui.PopupMenu;


public class MainActivity extends Activity implements View.OnLongClickListener {
    private PopupMenu menu;
    private RelativeLayout mLayout;
    private int downX,downY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mLayout = (RelativeLayout) this.findViewById(R.id.ll_layout);

        menu = new PopupMenu(this);
        menu.setContentView(R.layout.menu_layout);
        menu.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        menu.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        menu.setFocusable(true);
        menu.setTouchable(true);
        menu.setOutsideTouchable(true);
        menu.setBackgroundDrawable(new BitmapDrawable());

        //如果设置为true,所有的按键均由PopupWindow处理

        this.mLayout.setOnLongClickListener(this);
       /* this.mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (menu.isShowing()) {
                    menu.dismiss();
                }
                return false;
            }
        });*/
        this.mLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                downX = (int) motionEvent.getRawX();
                downY = (int) motionEvent.getRawY();
                return false;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onLongClick(View view) {
       // menu.showAtLocation(view, Gravity.NO_GRAVITY,(int)view.getX(),(int)view.getY());
      //  menu.showAtLocation(view,Gravity.TOP|Gravity.LEFT,(int)view.getX(),(int)view.getY());
       // menu.showAsDropDown(view);
        int[] location = new int[2];
        menu.getContentView().getLocationOnScreen(location);
        Log.e("downX<<<<<<<", downX + "");
        Log.e("downY<<<<<<<", downY + "");
        menu.showAtLocation(view,Gravity.TOP|Gravity.LEFT,downX,downY);
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = (int) event.getRawX();
            downY = (int) event.getRawY();
        Log.e("downX", downX + "");
        Log.e("downY", downY + "");
        //}
        return true;
    }
}
