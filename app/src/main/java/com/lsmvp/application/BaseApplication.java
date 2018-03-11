package com.lsmvp.application;

import android.app.Application;

/**
 * @author david
 * @date 09 Mar 2018.
 */

public class BaseApplication extends Application {

    private static BaseApplication sInstance;
    protected BaseApplicationComponent mBaseApplicationComponent;

    public BaseApplication() {
        super();
        sInstance = this;
    }

    public static BaseApplicationComponent getBaseApplicationComponent() {
        return sInstance.mBaseApplicationComponent;
    }

}
