package com.yhj.myactionprovider;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.ActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    /*    if (id == R.id.action_settings) {
            return true;
        }
*/
        return super.onOptionsItemSelected(item);
    }
}

class MyActionProvider extends ActionProvider {
    private ContextWrapper mContextWrapper;
    private PopupMenu menu;

    @Override
    public boolean onPerformDefaultAction() {
        Toast.makeText(mContextWrapper,"onPerformDefaultAction",Toast.LENGTH_SHORT).show();
        return super.onPerformDefaultAction();
    }

    public MyActionProvider(Context context) {
        super(context);
        this.mContextWrapper = (ContextWrapper) context;

        menu = new PopupMenu(context);
        menu.setContentView(R.layout.menu_layout);
        menu.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        menu.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);
        menu.setFocusable(true);
        menu.setTouchable(true);
        menu.setOutsideTouchable(true);
        menu.setBackgroundDrawable(new BitmapDrawable());
    }

    @Override
    public View onCreateActionView() {
        LayoutInflater inflater = LayoutInflater.from(mContextWrapper);
        View rootView = inflater.inflate(R.layout.layout_action_view,null);
        ImageView img = (ImageView) rootView.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              menu.showAsDropDown(view);
            }
        });
        return rootView;
    }


}