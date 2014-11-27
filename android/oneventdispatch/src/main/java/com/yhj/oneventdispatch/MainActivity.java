package com.yhj.oneventdispatch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {
    private Button mBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mBtn = (Button) this.findViewById(R.id.btn);
        this.mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SLog.e("MyButton---onClick");
            }
        });

        this.mBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent ev) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        SLog.e("MyButton---onTouch---Down");
                        break;
                    case MotionEvent.ACTION_MOVE:
                        SLog.e("MyButton---onTouch---Move");
                        break;
                    case MotionEvent.ACTION_UP:
                        SLog.e("MyButton---onTouch---Up");
                        break;
                }
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
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                SLog.e("MainActivity---onTouchEvent---Down");
                break;
            case MotionEvent.ACTION_MOVE:
                SLog.e("MainActivity---onTouchEvent---Move");
                break;
            case MotionEvent.ACTION_UP:
                SLog.e("MainActivity---onTouchEvent---Up");
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                SLog.e("MainActivity---dispatchTouchEvent---Down");
                break;
            case MotionEvent.ACTION_MOVE:
                SLog.e("MainActivity---dispatchTouchEvent---Move");
                break;
            case MotionEvent.ACTION_UP:
                SLog.e("MainActivity---dispatchTouchEvent---Up");
                break;
        }
        return super.onTouchEvent(ev);
    }
}
