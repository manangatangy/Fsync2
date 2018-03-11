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
 * @author david
 * @date 09 Mar 2018.
 */

@Module
public class BaseApplicationModule {

    public static final String APPLICATION_CONTEXT = "Application Context";
    public static final String MAIN_THREAD_EXECUTOR = "Main Thread Executor";

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
