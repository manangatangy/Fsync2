package com.wolfbang.fsync.feature2.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.feature2.Feature2Contract.Model;
import com.wolfbang.fsync.feature2.Feature2Contract.ModelState;
import com.wolfbang.fsync.feature2.Feature2Contract.ModelListener;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

/**
 * @author david
 * @date 10 Mar 2018.
 * This is straight copy of Feature1Model
 */

public class Feature2Model
        extends BaseMvpModel
        implements Model {

    private int mRequestCount = 0;
    private Feature2Data feature2Data;

    private String mSomeValue = "Initial model value";

    @VisibleForTesting
    ModelState mModelState = ModelState.IDLE;

    @NonNull
    private AtomicBoolean mBusy = new AtomicBoolean(false);

    @Inject
    public Feature2Model(Executor executor) {
        // Extra services may be injected as ctor params and stored as members.
        super(executor);
    }

    //region Feature2Contract.Contract
    @Override
    public void setFeature2Data(Feature2Data feature2Data) {
        this.feature2Data = feature2Data;
    }

    @Override
    public Feature2Data getFeature2Data() {
        return feature2Data;
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
