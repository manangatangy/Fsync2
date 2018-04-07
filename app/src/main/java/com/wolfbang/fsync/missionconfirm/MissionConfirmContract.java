package com.wolfbang.fsync.missionconfirm;

import android.support.annotation.Nullable;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.compare.ActionableDirNode;
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
        void navigateToBrowseTree(@Nullable Action action, DirNode dirNode, String title);
        void navigateBack();
    }

    interface View extends AbstractMvpContract.AbstractView {
        void setPrecedence(Precedence precedence);
        void setMissionName(String missionName);
        void setEndPointNameA(String endPointNameA);
        void setEndPointNameB(String endPointNameB);
        void setFieldCountsAndVisibility(final ViewFieldID viewFieldID, @Nullable final int[] counts);
        void setComparisonAndSyncButton(boolean comparisonVisible, boolean syncButtonEnabled);
        void showLoadingState(boolean show);
    }

    enum ViewFieldID {
        FIELD_FROM_A,
        FIELD_FROM_B,
        FIELD_TO_A,
        FIELD_TO_B,
        FIELD_ON_A,
        FIELD_ON_B,
        FIELD_NAME_CLASH
    }

    interface Presenter extends AbstractMvpContract.AbstractPresenter<View, Model, Navigation> {
        void onPrecedenceChecked(Precedence precedence);
        void onShowTree(ViewFieldID viewFieldID);
        void onSyncButtonClicked();
        void onBackClicked();
    }

    interface Model extends AbstractMvpContract.AbstractModel {
        void setPrecedence(Precedence precedence);      // And perform comparison
        Precedence getPrecedence();

        ActionableDirNode getComparisonTree();

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
        void onCompared(ActionableDirNode comparisonTree);
//        void onRetrieveSucceeded(@NonNull FileNode fileNode);
//        void onRetrieveFailed(String errorMsg);
    }
}
