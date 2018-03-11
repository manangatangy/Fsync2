package com.wolfbang.fsync.application._di;

import com.lsmvp.application.BaseApplicationComponent;
import com.lsmvp.application.BaseApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@Singleton
@Component(modules = {FsyncApplicationModule.class, BaseApplicationModule.class})
public interface FsyncApplicationComponent extends BaseApplicationComponent {

    // All things use this component to inject themselves
//    void inject(MyApplication myApplication);
//    void inject(OtnLocationManager otnLocationManager);
//    void inject(ClientTimeoutReceiver receiver);
//    void inject(EsgHeartbeatReceiver esgHeartbeatReceiver);

    // All app-wide service/manager getters are declared here.
//    InitVersionManager getInitVersionManager();
//    ApplicationForegroundDetector getApplicationForegroundDetector();
//    NabFingerprintManager getNabFingerprintManager();

}
