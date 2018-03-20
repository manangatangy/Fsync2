package com.wolfbang.fsync.missionsummary.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.ftpservice.FtpRecursiveList;
import com.wolfbang.fsync.ftpservice.FtpResponse;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.mission.FtpEndPoint;
import com.wolfbang.fsync.ftpservice.model.mission.MissionData;
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
    private MissionData mMissionData;

    private FileNode mFileNode;

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
    public void setMissionData(MissionData missionData) {
        this.mMissionData = missionData;
    }

    @Override
    public MissionData getMissionData() {
        return mMissionData;
    }

    @Override
    public FileNode getSuccessResponse() {
        return mFileNode;
    }

    @Override
    public void doSyncScan() {

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

                    FtpEndPoint ftpEndPoint = (FtpEndPoint)mMissionData.getEndPointA();
                    ModelListener listener = getListener();
                    FtpResponse<FileNode> ftpResponse = new FtpRecursiveList(
                            new SymLinkParsingFtpClient(), ftpEndPoint.getRootDir())
                            .setShowProtocolTrace(true)
                            .execute(
                                    ftpEndPoint.getHost(),
                                    ftpEndPoint.getUserName(),
                                    ftpEndPoint.getPassword()
                            );

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
                        mFileNode = ftpResponse.getResponse();
                        Log.d("ftpFile", mFileNode.getName());
                        mFileNode.dump("mission");
                        if (listener != null) {
                            listener.onRetrieveSucceeded(mFileNode);
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

    @Override
    public void resetModelState() {
        mModelState = ModelState.IDLE;
    }
    //endregion

}
