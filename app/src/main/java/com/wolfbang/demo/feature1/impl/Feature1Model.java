package com.wolfbang.demo.feature1.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.demo.feature1.Feature1Contract.Model;
import com.wolfbang.demo.feature1.Feature1Contract.ModelState;
import com.wolfbang.demo.feature1.Feature1Contract.ModelListener;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class Feature1Model
        extends BaseMvpModel
        implements Model {

    private int mRequestCount = 0;
    private Feature1Data feature1Data;

    private String mSomeValue = "Initial model value";

    @VisibleForTesting
    ModelState mModelState = ModelState.IDLE;

    @NonNull
    private AtomicBoolean mBusy = new AtomicBoolean(false);

    @Inject
    public Feature1Model(Executor executor) {
        // Extra services may be injected as ctor params and stored as members.
        super(executor);
    }

    //region Feature1Contract.Contract
    @Override
    public void setFeature1Data(Feature1Data feature1Data) {
        this.feature1Data = feature1Data;
    }

    @Override
    public Feature1Data getFeature1Data() {
        return feature1Data;
    }

    @Override
    public String getSomeValue() {
        return mSomeValue;
    }

    @Override
    public void doSomeAction(final int timePeriod) {

        if (mBusy.compareAndSet(false, true)) {

            ModelListener listener = getListener();
            if (listener != null) {
                listener.onBusyChanged(true);
            }

            getExecutor().execute(new Runnable() {
                @Override
                public void run() {

                    ModelListener listener = getListener();

                    boolean isError = (timePeriod <=0);

                    // Perform some long running synchronous request and block for the reply.
                    try {
                        Thread.sleep(isError ? 2500 : timePeriod);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mSomeValue = "clicks: " + ++mRequestCount;
                    mBusy.set(false);

                    if (listener != null) {
                        listener.onBusyChanged(false);
                    }

                    if (isError) {
                        mModelState = ModelState.ERROR;
                        if (listener != null) {
                            listener.onRetrieveFailed();
                        }
                    } else {
                        mModelState = ModelState.SUCCESS;
                        if (listener != null) {
                            listener.onRetrieveSomeResult(mSomeValue);
                        }
                    }
                }
            });

        }
    }

    @Override
    public boolean isBusy() {
        return mBusy.get();
    }

    @Override
    public ModelState getModelState() {
        return mModelState;
    }
    //endregion

}
