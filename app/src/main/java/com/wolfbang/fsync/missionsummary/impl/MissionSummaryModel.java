package com.wolfbang.fsync.missionsummary.impl;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.util.Log;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.ftpservice.FtpListDir;
import com.wolfbang.fsync.ftpservice.FtpListFile;
import com.wolfbang.fsync.ftpservice.FtpListFiles;
import com.wolfbang.fsync.ftpservice.FtpResponse;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Model;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.ModelState;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.ModelListener;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

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

                    FtpResponse<FTPFile> ftpResponse1 = new FtpListDir(new FTPClient(), path).execute();        //==> Doesn't return symlinks
//                    FtpResponse<FTPFile> ftpResponse2 = new FtpListFile(new FTPClient(), path).execute();     //==> MalformedServerReplyException every time
                    FtpResponse<FTPFile> ftpResponse3 =  new FtpListFiles(new FTPClient(), path).execute();      //==> Doesn't return dot files
//                    FtpResponse<FTPFile> ftpResponse =  new FtpFileListService(path).execute();

                    mBusy.set(false);
                    if (listener != null) {
                        listener.onBusyChanged(false);
                    }

                    FtpResponse<FTPFile> ftpResponse = ftpResponse1;

                    if (ftpResponse.isErrored()) {
                        mModelState = ModelState.ERROR;
                        mErrorMsg = ftpResponse.errorMessage;
                        if (listener != null) {
                            listener.onRetrieveFailed(mErrorMsg);
                        }
                    } else {
                        mModelState = ModelState.SUCCESS;
                        FTPFile ftpFile = ftpResponse.getResponse();
                        Log.d("ftpFile", ftpFile.toFormattedString());

                        if (listener != null) {
                            listener.onRetrieveSomeResult(
                                    ftpFile.toFormattedString()
                            );
                        }
                    }
                }
            });

        }
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
