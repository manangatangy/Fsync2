package com.wolfbang.fsync.missionconfirm.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.model.compare.ActionableDirNode;
import com.wolfbang.fsync.model.compare.MergeComparator;
import com.wolfbang.fsync.model.compare.Precedence;
import com.wolfbang.fsync.model.mission.MissionNameData;
import com.wolfbang.fsync.model.mission.ScanResult;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Model;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.ModelListener;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.ModelState;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

/**
 * @author david
 * @date 22 Mar 2018.
 */

public class MissionConfirmModel
        extends BaseMvpModel
        implements Model {

    private Precedence mPrecedence = Precedence.NONE;
    private MissionNameData mMissionNameData;
    private ScanResult mScanResult;
    private ActionableDirNode mComparisonTree;

    @VisibleForTesting
    ModelState mModelState = ModelState.IDLE;

    @NonNull
    private AtomicBoolean mBusy = new AtomicBoolean(false);

    @Inject
    public MissionConfirmModel(Executor executor) {
        // Extra services may be injected as ctor params and stored as members.
        super(executor);
    }

    //region MissionConfirmContract.Contract
    @Override
    public void setPrecedence(final Precedence precedence) {
        if (mBusy.compareAndSet(false, true)) {
            busyChanged(true);
            getExecutor().execute(new Runnable() {
                @Override
                public void run() {
                    mPrecedence = precedence;
                    MergeComparator mergeComparator = new MergeComparator(precedence);
                    mComparisonTree = mergeComparator.compare(mScanResult.getDirA(), mScanResult.getDirB());
                    mModelState = ModelState.COMPARED;
                    mBusy.set(false);
                    ModelListener listener = busyChanged(false);
                    if (listener != null) {
                        listener.onCompared(mComparisonTree);
                    }
                }
            });
        }
    }

    @Override
    public Precedence getPrecedence() {
        return mPrecedence;
    }

    @Override
    public ActionableDirNode getComparisonTree() {
        return mComparisonTree;
    }

    @Override
    public void setMissionNameData(MissionNameData missionNameData) {
        mMissionNameData = missionNameData;
    }

    @Override
    public MissionNameData getMissionNameData() {
        return mMissionNameData;
    }

    @Override
    public void setScanResult(ScanResult scanResult) {
        mScanResult = scanResult;
    }

    @Override
    public ScanResult getScanResult() {
        return mScanResult;
    }

    //    @Override
//    public FileNode getSuccessResponse() {
//        return mFileNode;
//    }

    @Override
    public void doSync() {

        if (mBusy.compareAndSet(false, true)) {

            busyChanged(true);

            getExecutor().execute(new Runnable() {
                @Override
                public void run() {

                    // DO STUFF

                    mBusy.set(false);
                    busyChanged(false);


                    // NOTIFY LISTENER
                }
            });

        }
    }


//    private String mErrorMsg;
//    @Override
//    public String getErrorMsg() {
//        return mErrorMsg;
//    }
//
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
