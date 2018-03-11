package com.wolfbang.demo.feature2.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.demo.feature2.Feature2Contract.Model;
import com.wolfbang.demo.feature2.Feature2Contract.ModelState;
import com.wolfbang.demo.feature2.Feature2Contract.ModelListener;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class Feature2Model
        extends BaseMvpModel
        implements Model {

    // Used to determine response type
    private int mRequestCount = 0;

    private String mSomeValue = "Click to see what happens";

    @VisibleForTesting
    ModelState mModelState = ModelState.IDLE;

    @NonNull
    private AtomicBoolean mBusy = new AtomicBoolean(false);

    @Inject
    public Feature2Model(Executor executor) {
        // Extra services may be injected as ctor params and stored as members.
        super(executor);
    }

    @Override
    public void setSomeValue(String someValue) {
        mSomeValue = someValue;
    }

    @Override
    public String getSomeValue() {
        return mSomeValue;
    }

    @Override
    public boolean isBusy() {
        return mBusy.get();
    }

    @Override
    public ModelState getModelState() {
        return mModelState;
    }

    @Override
    public void doSomeAction() {

        if (mBusy.compareAndSet(false, true)) {

            ModelListener listener = getListener();
            if (listener != null) {
                listener.onBusyChanged(true);
            }

            getExecutor().execute(new Runnable() {
                @Override
                public void run() {

                    // Perform some long running synchronous request and block for the reply.
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mBusy.set(false);

                    ModelListener listener = getListener();

                    if (listener != null) {
                        listener.onBusyChanged(false);
                    }

                    if ((++mRequestCount % 2) == 0) {
                        mModelState = ModelState.SUCCESS;
                        if (listener != null) {
                            listener.onRetrieveSomeResult("clicks: " + mRequestCount);
                        }
                    } else {
                        mModelState = ModelState.ERROR;
                        if (listener != null) {
                            listener.onRetrieveFailed();
                        }
                    }
                }
            });

        }
    }

}
