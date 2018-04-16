package com.wolfbang.fsync.missionsummary;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.model.mission.EndPoint;
import com.wolfbang.fsync.model.mission.MissionNameData;
import com.wolfbang.fsync.model.mission.ScanResult;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public interface MissionSummaryContract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
//        void navigateToBrowseTree(DirNode dirNode);
        void navigateToMissionConfirm(MissionNameData missionNameData, ScanResult scanResult);
        void navigateBack();
    }

    interface View extends AbstractView {
        void setMissionName(String missionName);
        void setEndPointDetailsA(EndPoint endPoint);
        void setEndPointDetailsB(EndPoint endPoint);
        void showError(String errorMsg);
        void showLoadingState(boolean show);
    }

    interface Presenter extends AbstractPresenter<View, Model, Navigation> {
        void onScanButtonClicked();
        void onBackClicked();
    }

    interface Model extends AbstractModel {
        void setMissionNameData(MissionNameData missionNameData);
        MissionNameData getMissionNameData();

        void doScan();
        ScanResult getScanResult();

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
        void onRetrieveSucceeded(@NonNull MissionNameData missionNameData, @NonNull ScanResult scanResult);
        void onRetrieveFailed(String errorMsg);
    }
}
