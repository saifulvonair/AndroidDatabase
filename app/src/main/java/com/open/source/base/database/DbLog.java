/**
 * @author open Source
 */

package com.open.source.base.database;

import android.util.Log;

public class DbLog {
    private static boolean mShowLog = false;
    private static String DEF_TAG = "OpenLog";

    //
    public static void d(String tag, String message){
        if(mShowLog)
            Log.d(""+tag, "" + message);
    }

    //
    public static void e(String tag, String message){
        if(mShowLog)
            Log.e(""+tag, "" + message);
    }

    //
    public static void d(String message){
        if(mShowLog)
            Log.d(DEF_TAG, "" + message);
    }

    //
    public static void e(String message){
        if(mShowLog)
            Log.e(DEF_TAG, "" + message);
    }
}
