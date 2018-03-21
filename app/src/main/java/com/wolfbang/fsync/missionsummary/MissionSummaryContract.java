package com.wolfbang.fsync.missionsummary;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.mission.EndPoint;
import com.wolfbang.fsync.ftpservice.model.mission.MissionData;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public interface MissionSummaryContract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
        void navigateToBrowseTree(DirNode dirNode);
        void navigateBack();
    }

    interface View extends AbstractView {
        void setMissionName(String missionName);
        void setEndPointDetailsA(EndPoint endPoint);
        void setEndPointDetailsB(EndPoint endPoint);
        void setSomeField(String someValue);
        void showError(String errorMsg);
        void showLoadingState(boolean show);
    }

    interface Presenter extends AbstractPresenter<View, Model, Navigation> {
        void onSyncScanButtonClicked();
        void onBackClicked();
    }

    interface Model extends AbstractModel {
        void setMissionData(MissionData missionData);
        MissionData getMissionData();

        FileNode getSuccessResponse();
        void doSyncScan();

        String getErrorMsg();
        boolean isBusy();
        ModelState getModelState();
        void resetModelState();
    }

    enum ModelState {
        IDLE,
        ERROR,
        SUCCESS
    }
    interface ModelListener extends AbstractModelListener {
        void onBusyChanged(boolean busy);
        void onRetrieveSucceeded(@NonNull FileNode fileNode);
        void onRetrieveFailed(String errorMsg);
    }
}
