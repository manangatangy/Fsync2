package com.wolfbang.fsync.missionconfirm;

import android.support.annotation.Nullable;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.ftpservice.model.compare.Precedence;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.fsync.ftpservice.model.mission.ScanResult;

/**
 * @author david
 * @date 22 Mar 2018.
 */

public interface MissionConfirmContract {

    interface Navigation extends AbstractMvpContract.BasicNavigation {
        void navigateToBrowseTree(DirNode dirNode, String title);
        void navigateBack();
    }

    interface View extends AbstractMvpContract.AbstractView {
        void setMissionName(String missionName);
        void setEndPointNameA(String endPointNameA);
        void setEndPointNameB(String endPointNameB);
        void setPrecedence(Precedence precedence);
        void setConflict(@Nullable String text, boolean buttonEnabled);
        void setCopied1(@Nullable String text, boolean buttonEnabled);
        void setCopied2(@Nullable String text, boolean buttonEnabled);
        void setOverridden1(@Nullable String text, boolean buttonEnabled);
        void setOverridden2(@Nullable String text, boolean buttonEnabled);
        void setSyncButtonEnabled(boolean visible);

        //        void setSomeField(String someValue);
//        void showError(String errorMsg);
        void showLoadingState(boolean show);
    }

    interface Presenter extends AbstractMvpContract.AbstractPresenter<View, Model, Navigation> {
        void onPrecedenceChecked(Precedence precedence);
        void onShowTreeEndPointA();
        void onShowTreeEndPointB();
        void onSyncButtonClicked();
        void onBackClicked();
    }

    interface Model extends AbstractMvpContract.AbstractModel {
        void setPrecedence(Precedence precedence);      // And perform comparison
        Precedence getPrecedence();

        DirNode getComparisonTree();

        void setMissionNameData(MissionNameData missionNameData);
        MissionNameData getMissionNameData();

        void setScanResult(ScanResult scanResult);
        ScanResult getScanResult();

        boolean isBusy();
        ModelState getModelState();
        void resetModelState();

        //        FileNode getSuccessResponse();
        void doSync();

//        String getErrorMsg();
    }

    enum ModelState {
        IDLE,           // means: scan results are available
        COMPARED,       // means: comparisonTree is available
        ERROR,
        SUCCESS
    }
    interface ModelListener extends AbstractMvpContract.AbstractModelListener {
        void onBusyChanged(boolean busy);
        void onCompared(DirNode comparisonTree);
//        void onRetrieveSucceeded(@NonNull FileNode fileNode);
//        void onRetrieveFailed(String errorMsg);
    }
}
