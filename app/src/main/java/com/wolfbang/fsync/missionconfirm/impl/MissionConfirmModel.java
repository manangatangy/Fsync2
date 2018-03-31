package com.wolfbang.fsync.missionconfirm.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.ftpservice.model.compare.Precedence;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.fsync.ftpservice.model.mission.ScanResult;
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

    private Precedence mPrecedence = Precedence.NEWEST;
    private MissionNameData mMissionNameData;
    private ScanResult mScanResult;

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
    public void setPrecedence(Precedence precedence) {
        mPrecedence = precedence;
        // TODO compare . this will also set the busy spinner etc and then populate the counts fields.

//                            MergeComparator mergeComparator = new MergeComparator(precedence);
//                                    mergeComparator.compare((DirNode)fileNodeA, dirNodeB),

    }

    @Override
    public Precedence getPrecedence() {
        return mPrecedence;
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

            ModelListener listener = getListener();
            if (listener != null) {
                listener.onBusyChanged(true);
            }

            getExecutor().execute(new Runnable() {
                @Override
                public void run() {

//                    FtpEndPoint ftpEndPoint = (FtpEndPoint)mMissionData.getEndPointA();
//                    FtpResponse<FileNode> ftpResponse = new FtpRecursiveList(
//                            new SymLinkParsingFtpClient(), ftpEndPoint.getRootDir())
//                            .setShowProtocolTrace(true)
//                            .execute(
//                                    ftpEndPoint.getHost(),
//                                    ftpEndPoint.getUserName(),
//                                    ftpEndPoint.getPassword()
//                            );

                    mBusy.set(false);
                    ModelListener listener = getListener();
                    if (listener != null) {
                        listener.onBusyChanged(false);
                    }

//                    if (ftpResponse.isErrored()) {
//                        mModelState = ModelState.ERROR;
//                        mErrorMsg = ftpResponse.getErrorText();
//                        if (listener != null) {
//                            listener.onRetrieveFailed(mErrorMsg);
//                        }
//                    } else {
//                        mModelState = ModelState.SUCCESS;
//                        mFileNode = ftpResponse.getResponse();
//                        Log.d("ftpFile", mFileNode.getName());
//                        mFileNode.dump("mission");
//                        if (listener != null) {
//                            listener.onRetrieveSucceeded(mFileNode);
//                        }
//                    }
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

}
