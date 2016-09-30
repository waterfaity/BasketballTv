package com.huizetime.basketballtv.application;

import android.app.Application;

import com.huizetime.basketballtv.manager.BTManager;

/**
 * Created by water_fairy on 2016/9/27.
 */
public class MyApp extends Application {
    private static MyApp myApp;
    private BTManager btManager;
    private int width;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    public static MyApp getApp() {
        return myApp;
    }

    public BTManager getBTManager() {

        if (btManager == null) {
            btManager = BTManager.getInstance();
        }
        return btManager;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
