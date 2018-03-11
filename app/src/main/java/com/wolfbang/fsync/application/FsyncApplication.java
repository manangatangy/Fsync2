package com.wolfbang.fsync.application;

import com.lsmvp.application.BaseApplication;
import com.lsmvp.application.BaseApplicationComponent;
import com.lsmvp.application.BaseApplicationModule;
import com.wolfbang.fsync.application._di.DaggerFsyncApplicationComponent;
import com.wolfbang.fsync.application._di.FsyncApplicationComponent;
import com.wolfbang.fsync.application._di.FsyncApplicationModule;

/**
 * @author david
 * @date 09 Mar 2018.
 */

public class FsyncApplication extends BaseApplication {

    private static FsyncApplicationComponent fsyncApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        fsyncApplicationComponent = DaggerFsyncApplicationComponent.builder()
                .fsyncApplicationModule(new FsyncApplicationModule(this))
                .baseApplicationModule(new BaseApplicationModule(this))
                .build();
    }

    public static FsyncApplicationComponent getFsyncApplicationComponent() {
        return fsyncApplicationComponent;
    }

    @Override
    protected BaseApplicationComponent getApplicationComponent() {
        return getFsyncApplicationComponent();
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
