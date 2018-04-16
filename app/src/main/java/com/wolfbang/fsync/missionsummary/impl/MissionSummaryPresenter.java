package com.wolfbang.fsync.missionsummary.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.model.mission.MissionNameData;
import com.wolfbang.fsync.model.mission.ScanResult;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Model;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.ModelListener;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Navigation;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.Presenter;
import com.wolfbang.fsync.missionsummary.MissionSummaryContract.View;

/**
 * @author david
 * @date 10 Mar 2018.
 */

public class MissionSummaryPresenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter, ModelListener {

    public MissionSummaryPresenter(Model model) {
        super(model);
        model.setListener(this);
    }

    //region SimpleMVP
    @Override
    protected void refreshView(@NonNull View view) {
        Model model = getModel();
        boolean busy = model.isBusy();
        handleBusyChanged(view, busy);

        if (!busy) {
            switch (model.getModelState()) {
                case IDLE:
                    // Display the initial value of the model value.
                    view.setMissionName(model.getMissionNameData().getMissionName());
                    view.setEndPointDetailsA(model.getMissionNameData().getEndPointA());
                    view.setEndPointDetailsB(model.getMissionNameData().getEndPointB());
                    break;
                case SUCCESS:
                    Navigation navigation = getNavigation();
                    if (navigation != null) {
                        handleSuccess(navigation, model.getMissionNameData(), model.getScanResult());
                    }
                    break;
                case ERROR:
                    handleError(view, model.getErrorMsg());
                    break;
                default:
                    throw new IllegalStateException("Unsupported model state");
            }
        }
    }
    //endregion

    //region MissionSummaryContract.Presenter
    @Override
    public void onScanButtonClicked() {
        getModel().doScan();
    }

    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateBack();
        }
    }
    //endregion

    //region MissionSummaryContract.ModelListener
    @Override
    public void onBusyChanged(boolean busy) {
        View view = getView();
        if (view != null) {
            handleBusyChanged(view, busy);
        }
    }

    @Override
    public void onRetrieveSucceeded(@NonNull MissionNameData missionNameData, @NonNull ScanResult scanResult) {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            handleSuccess(navigation, missionNameData, scanResult);
        }
    }

    @Override
    public void onRetrieveFailed(String errorMsg) {
        View view = getView();
        if (view != null) {
            handleError(view, errorMsg);
        }
    }
    //endregion

    private void handleSuccess(@NonNull Navigation navigation,
                               @NonNull MissionNameData missionNameData,
                               @NonNull ScanResult scanResult) {
        getModel().resetModelState();
        navigation.navigateToMissionConfirm(missionNameData, scanResult);
    }

    private void handleError(@NonNull View view, String errorMsg) {
        view.showError(errorMsg);
    }

    private void handleBusyChanged(@NonNull View view, boolean busy) {
        view.showLoadingState(busy);
    }
}
