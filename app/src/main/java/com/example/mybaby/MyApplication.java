package com.example.mybaby;

import android.app.Application;

import com.example.mybaby.utils.DeviceInfoUtil;

/**
 * Created by liu on 2017/12/1.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        DeviceInfoUtil.getInstance().init(this);
    }
}
