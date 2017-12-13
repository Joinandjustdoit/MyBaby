package com.example.mybaby;

import android.app.Application;
import android.content.Context;

import com.example.mybaby.utils.DeviceInfoUtil;

/**
 * Created by liu on 2017/12/1.
 */

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DeviceInfoUtil.getInstance().init(this);
    }

    public static Context getContext() {
        return context;
    }
}
