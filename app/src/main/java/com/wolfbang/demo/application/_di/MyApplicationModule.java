package com.wolfbang.demo.application._di;

import com.wolfbang.demo.application.MyApplication;
import com.wolfbang.demo.application.SharedPreferenceManager;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * @author david
 * @date 09 Mar 2018.
 */

@Module
public class MyApplicationModule {

    public MyApplicationModule(MyApplication myApplication) {
        mMyApplication = myApplication;
    }

    private final MyApplication mMyApplication;

    @Provides
    @Singleton
    public MyApplication application() {
        return this.mMyApplication;
    }



    @Singleton
    @Provides
    public SharedPreferenceManager provideSharedPreferenceManager() {
        return SharedPreferenceManager.getInstance(mMyApplication);
    }

    //    @Provides
//    @Singleton
//    public UserRepository provideUserRepository(@Named(APPLICATION_CONTEXT) Context context,
//                                                @Named(COMMON_SECURE_SHARED_PREFS)
//                                                        KeyValueRepository commonSecureRepository,
//                                                CryptoUtils cryptoUtils) {
//        return new UserRepository(context, commonSecureRepository, cryptoUtils);
//    }
//
//
//    @Provides
//    @Named(COMMON_SECURE_SHARED_PREFS)
//    public KeyValueRepository provideCommonSecureRepository(@Named(APPLICATION_CONTEXT) Context context,
//                                                            CryptoUtils cryptoUtils) {
//        // This a common, app wide secure storage
//        // use a hashed name so that it's not clear what the file is for
//        String prefName = FormatUtil.getSafeFileName(cryptoUtils.hashSHA256WithAppKey(COMMON_SECURE_SHARED_PREFS));
//        return new SecureSharedPrefsRepository(context, prefName, cryptoUtils);
//    }

//    @Provides
//    @Singleton
//    public AccountsService provideAccountsService(AppSettings appSettings,
//                                                  LocationProvider locationProvider,
//                                                  HeaderValueProvider headerValueProvider,
//                                                  MibCustomUserAgentSettingProvider customUserAgentSettingProvider) {
//        return setupBasicBuilder(
//                new AccountsSdk().builder().setBalanceTimeout(TimeUnit.SECONDS.toMillis(30)).shouldRetry(true),
//                appSettings, headerValueProvider, locationProvider, customUserAgentSettingProvider)
//                .build();
//    }

}
