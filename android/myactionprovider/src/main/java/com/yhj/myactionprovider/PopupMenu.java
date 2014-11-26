package com.yhj.myactionprovider;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by scott on 14-11-25.
 */
public class PopupMenu extends PopupWindow {
    private Context mContext;

    public PopupMenu(Context context) {
        this.mContext = context;
    }

    public void setContentView(int layoutId) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(layoutId,null);
        setContentView(view);
    }
}
