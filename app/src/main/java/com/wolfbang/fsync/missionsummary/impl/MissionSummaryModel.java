package com.wolfbang.fsync.missionsummary.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.ftpservice.FtpRecursiveList;
import com.wolfbang.fsync.ftpservice.FtpResponse;
import com.wolfbang.fsync.ftpservice.model.FileNode;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Model;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.ModelListener;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.ModelState;

import org.apache.commons.net.ftp.SymLinkParsingFtpClient;

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

    private int mRequestCount = 0;
    private MissionSummaryData missionSummaryData;

    private String mSomeValue = "Initial model value";

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
    public void setMissionSummaryData(MissionSummaryData missionSummaryData) {
        this.missionSummaryData = missionSummaryData;
    }

    @Override
    public MissionSummaryData getMissionSummaryData() {
        return missionSummaryData;
    }

    @Override
    public String getSomeValue() {
        return mSomeValue;
    }

    @Override
    public void doSomeAction(final String path) {

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

                    ModelListener listener = getListener();
                    FtpResponse<FileNode> ftpResponse = new FtpRecursiveList(new SymLinkParsingFtpClient(), path)
                            .setShowProtocolTrace(true)
                            .execute();

                    mBusy.set(false);
                    if (listener != null) {
                        listener.onBusyChanged(false);
                    }

                    if (ftpResponse.isErrored()) {
                        mModelState = ModelState.ERROR;
                        mErrorMsg = ftpResponse.getErrorText();
                        if (listener != null) {
                            listener.onRetrieveFailed(mErrorMsg);
                        }
                    } else {
                        mModelState = ModelState.SUCCESS;
                        FileNode fileNode = ftpResponse.getResponse();
                        Log.d("ftpFile", fileNode.getName());
                        fileNode.dump("mission");
                        if (listener != null) {
                            listener.onRetrieveSomeResult(fileNode.getName());
                        }
                    }
                }
            });

        }
    }

    public void dump(FileNode fileNode) {

    }


    private String mErrorMsg;
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
    //endregion

}
