package com.wolfbang.fsync.endpointedit.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.Model;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.ModelListener;
import com.wolfbang.fsync.endpointedit.EndPointEditContract.ModelState;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

/**
 * @author david
 * @date 2018-05-03
 */
public class EndPointEditModel
        extends BaseMvpModel
        implements Model {

    @VisibleForTesting
    ModelState mModelState = ModelState.IDLE;

    @NonNull
    private AtomicBoolean mBusy = new AtomicBoolean(false);

    @Inject
    public EndPointEditModel(Executor executor) {
        // Extra services may be injected as ctor params and stored as members.
        super(executor);
    }

    //region EndPointEditContract.Contract
    @Override
    public boolean isBusy() {
        return mBusy.get();
    }

    @Override
    public ModelState getModelState() {
        return mModelState;
    }

    @Override
    public void resetModelState() {
        mModelState = ModelState.IDLE;
    }
    //endregion

    private ModelListener busyChanged(boolean busy) {
        ModelListener listener = getListener();
        if (listener != null) {
            listener.onBusyChanged(busy);
        }
        return listener;
    }

}
