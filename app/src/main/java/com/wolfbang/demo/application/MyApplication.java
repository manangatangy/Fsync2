package com.wolfbang.demo.application;

import com.lsmvp.application.BaseApplication;
import com.lsmvp.application.BaseApplicationComponent;
import com.lsmvp.application.BaseApplicationModule;
import com.wolfbang.demo.application._di.DaggerMyApplicationComponent;
import com.wolfbang.demo.application._di.MyApplicationComponent;
import com.wolfbang.demo.application._di.MyApplicationModule;

/**
 * @author david
 * @date 09 Mar 2018.
 */

public class MyApplication extends BaseApplication {

    private static MyApplicationComponent myApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        myApplicationComponent = DaggerMyApplicationComponent.builder()
                .myApplicationModule(new MyApplicationModule(this))
                .baseApplicationModule(new BaseApplicationModule(this))
                .build();
    }

    public static MyApplicationComponent getMyApplicationComponent() {
        return myApplicationComponent;
    }

    @Override
    protected BaseApplicationComponent getApplicationComponent() {
        return getMyApplicationComponent();
    }

    /** The App settings containing endpoints */
//    @Inject
//    AppSettings mAppSettings;
//    @Inject
//    SessionManager mSessionManager;
//    @Inject
//    Bus mBus;
//    @Inject
//    CryptoProvider mCryptoProvider;
//    @Inject
//    SharedPreferenceManager mSharedPreferenceManager;
//    @Inject
//    PermissionsUtil mPermissionsUtil;

//    protected MyApplicationComponent mMyApplicationComponent;
//    protected MyApplicationModule mMyApplicationModule;

//    public MyApplicationComponent getMyApplicationComponent() {
//        return mMyApplicationComponent;
//    }

//    @Override
//    public void onCreate() {
//        super.onCreate();
//        sInstance = this;
//
//        Timber.uprootAll();
//
//        mMyApplicationModule = null;
//        mMyApplicationComponent = DaggerMyApplicationComponent
//                .builder()
//                .myApplicationModule(getMyApplicationModule())
////                .accessibilityModule(new AccessibilityModule())
//                .build();
//        mMyApplicationComponent.inject(this);
//    }

//    protected MyApplicationModule getMyApplicationModule() {
//        if (mMyApplicationModule == null) {
//            mMyApplicationModule = new MyApplicationModule(this);
//        }
//        return mMyApplicationModule;
//    }

}
