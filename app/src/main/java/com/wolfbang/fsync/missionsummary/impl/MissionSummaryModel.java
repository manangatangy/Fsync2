package com.wolfbang.fsync.missionsummary.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Model;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.ModelListener;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.ModelState;
import com.wolfbang.fsync.model.filetree.DirNode;
import com.wolfbang.fsync.model.mission.EndPointResponse;
import com.wolfbang.fsync.model.mission.MissionNameData;
import com.wolfbang.fsync.model.mission.ScanResult;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class MissionSummaryModel
        extends BaseMvpModel
        implements Model {

    private MissionNameData mMissionNameData;
    private ScanResult mScanResult;
    private String mErrorMsg;

    @VisibleForTesting
    ModelState mModelState = ModelState.IDLE;

    @NonNull
    private AtomicBoolean mBusy = new AtomicBoolean(false);

    @Inject
    public MissionSummaryModel(Executor executor) {
        // Extra services may be injected as ctor params and stored as members.
        super(executor);
    }

    //region MissionSummaryContract.Contract
    @Override
    public void setMissionNameData(MissionNameData missionNameData) {
        this.mMissionNameData = missionNameData;
    }

    @Override
    public MissionNameData getMissionNameData() {
        return mMissionNameData;
    }

    @Override
    public ScanResult getScanResult() {
        return mScanResult;
    }

    @Override
    public String getErrorMsg() {
        return mErrorMsg;
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
    public void resetModelState() {
        mModelState = ModelState.IDLE;
    }

    @Override
    public void doScan() {

        // TODO time zone stuff
//        java.util.TimeZone timeZone;
//        timeZone.toZoneId()
        if (mBusy.compareAndSet(false, true)) {

            ModelListener listener = getListener();
            if (listener != null) {
                listener.onBusyChanged(true);
            }

            getExecutor().execute(new Runnable() {
                @Override
                public void run() {

                    ScanResult scanResult = null;
                    String errorText = null;
                    EndPointResponse<DirNode> endPointResponseA = mMissionNameData.getEndPointA().fetchFileTree();
                    if (endPointResponseA.isErrored()) {
                        errorText = endPointResponseA.getErrorText();
                    } else {
                        EndPointResponse<DirNode> endPointResponseB = mMissionNameData.getEndPointB().fetchFileTree();
                        if (endPointResponseB.isErrored()) {
                            errorText = endPointResponseB.getErrorText();
                        } else {
                            scanResult = new ScanResult(endPointResponseA.getResponse(), endPointResponseB.getResponse());
                        }
                    }

                    mBusy.set(false);
                    ModelListener listener = getListener();
                    if (listener != null) {
                        listener.onBusyChanged(false);
                    }
                    if (errorText != null) {
                        mModelState = ModelState.ERROR;
                        mErrorMsg = errorText;
                        if (listener != null) {
                            listener.onRetrieveFailed(mErrorMsg);
                        }
                    } else {
                        mModelState = ModelState.SUCCESS;
                        mScanResult = scanResult;
                        if (listener != null) {
                            listener.onRetrieveSucceeded(mMissionNameData, mScanResult);
                        }
                    }

                }
            });

        }
    }
    //endregion

}
