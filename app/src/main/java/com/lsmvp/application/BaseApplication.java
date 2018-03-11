package com.lsmvp.application;

import android.app.Application;

/**
 * @author david
 * @date 09 Mar 2018.
 */

public abstract class BaseApplication extends Application {

    private static BaseApplication sInstance;

    public BaseApplication() {
        super();
        sInstance = this;
    }

    public static BaseApplicationComponent getBaseApplicationComponent() {
        return sInstance.getApplicationComponent();
    }

    protected abstract BaseApplicationComponent getApplicationComponent();
}
