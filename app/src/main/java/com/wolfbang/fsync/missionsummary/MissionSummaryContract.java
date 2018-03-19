package com.wolfbang.fsync.missionsummary;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.ftpservice.model.mission.EndPoint;
import com.wolfbang.fsync.ftpservice.model.mission.MissionData;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public interface MissionSummaryContract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
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

        String getSomeValue();
        void doSyncScan();

        String getErrorMsg();
        boolean isBusy();
        ModelState getModelState();
    }

    enum ModelState {
        IDLE,
        ERROR,
        SUCCESS
    }
    interface ModelListener extends AbstractModelListener {
        void onBusyChanged(boolean busy);
        void onRetrieveSomeResult(@NonNull String resultValue);
        void onRetrieveFailed(String errorMsg);
    }
}
