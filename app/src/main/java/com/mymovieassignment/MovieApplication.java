package com.mymovieassignment;

import android.app.Application;
import android.content.Context;


/**
 * MovieApplication which initiates on app launch
 */
public class MovieApplication extends Application {

    public static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

   public static Context getContext() {
        return mContext;
    }
}
