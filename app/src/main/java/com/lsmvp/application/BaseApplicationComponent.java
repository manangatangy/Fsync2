package com.lsmvp.application;

import android.os.Handler;

import java.util.concurrent.Executor;
import com.lsmvp.simplemvp.ObjectRegistry;
import javax.inject.Named;

/**
 * @author david
 * @date 09 Mar 2018.
 */

public interface BaseApplicationComponent {

    ObjectRegistry getObjectRegistry();

    Executor getSharedExecutor();

    Handler getMainLooperHandler();

    @Named(BaseApplicationModule.MAIN_THREAD_EXECUTOR)
    Executor getMainThreadExecutor();

}
