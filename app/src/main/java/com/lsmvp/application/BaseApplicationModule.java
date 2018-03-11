package com.lsmvp.application;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import com.lsmvp.simplemvp.DefaultObjectRegistry;
import com.lsmvp.simplemvp.ObjectRegistry;
import com.lsmvp.simplemvp.PostOnMainHandlerExecutor;

import dagger.Module;
import dagger.Provides;

/**
 * Created by david on 8/03/18.
 */

@Module
public class BaseApplicationModule {

    public static final String APPLICATION_CONTEXT = "Application Context";
    public static final String MAIN_THREAD_EXECUTOR = "Main Thread Executor";

    public static final String COMMON_SECURE_SHARED_PREFS = "COMMON_SECURE_SHARED_PREFS";
    public static final String UNPINNED_SECURITY_SERVICE = "UNPINNED_SECURITY_SERVICE";
    public static final String INACTIVITY_TIMEOUT = "Inactivity Timeout";
    public static final String ESG_HEARTBEAT = "ESG Heartbeat";
    public static final String IB_HEARTBEAT = "IB Heartbeat";
    public static final String GOOGLE_API_CLIENT_FOR_OMNITURE = "GOOGLE_API_CLIENT_FOR_OMNITURE";

    private final Context mApplicationContext;

    public BaseApplicationModule(Context applicationContext) {
        mApplicationContext = applicationContext;
    }

    @Singleton
    @Provides
    public ObjectRegistry getObjectRegistry() {
        return new DefaultObjectRegistry();
    }

    @Provides
    @Named(APPLICATION_CONTEXT)
    public Context provideApplicationContext() {
        return mApplicationContext;
    }

    @Singleton
    @Provides
    public Executor getSharedExecutor() {
        return Executors.newCachedThreadPool();
    }

    @Singleton
    @Provides
    Handler getMainLooperHandler() {
        return new Handler(Looper.getMainLooper());
    }

    @Singleton
    @Provides
    @Named(MAIN_THREAD_EXECUTOR)
    public Executor getPostOnMainHandlerExecutor(Handler handler) {
        return new PostOnMainHandlerExecutor(handler);
    }

}
