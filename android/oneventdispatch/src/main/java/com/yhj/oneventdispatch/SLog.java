package com.yhj.oneventdispatch;

import android.util.Log;

/**
 * Created by scott on 14-11-26.
 */
public class SLog {
    private static  final String DEFAULT = "TAG";

    public static void e(String msg) {
        Log.e(DEFAULT,msg);
    }
}
