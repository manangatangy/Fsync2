package com.wolfbang.fsync.missionconfirm.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Model;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.ModelListener;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Navigation;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Presenter;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.View;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.ViewFieldID;
import com.wolfbang.fsync.model.compare.Action;
import com.wolfbang.fsync.model.compare.ActionableDirNode;
import com.wolfbang.fsync.model.compare.NodeCounter;
import com.wolfbang.fsync.model.compare.Precedence;
import com.wolfbang.fsync.model.filetree.DirNode;

/**
 * @author david
 * @date 22 Mar 2018.
 */

public class MissionConfirmPresenter
        extends BaseMvpPresenter<View, Model, Navigation>
        implements Presenter, ModelListener {

    public MissionConfirmPresenter(Model model) {
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
                // Display the initial value of the model values.
                String endPointA = model.getMissionNameData().getEndPointA().getEndPointName();
                String endPointB = model.getMissionNameData().getEndPointB().getEndPointName();
                view.setMissionName(model.getMissionNameData().getMissionName());
                view.setEndPointNameA(endPointA);
                view.setFieldCountsAndVisibility(ViewFieldID.FIELD_FROM_A, makeCounts(model.getScanResult().getDirA()));
                view.setEndPointNameB(endPointB);
                view.setFieldCountsAndVisibility(ViewFieldID.FIELD_FROM_B, makeCounts(model.getScanResult().getDirB()));
                view.setPrecedence(model.getPrecedence());
                break;
            case COMPARED:
                handleCompared(view, model.getComparisonTree());
                break;
            default:
                throw new IllegalStateException("Unsupported model state");
            }
        }
    }
    //endregion

    //region MissionConfirmContract.Presenter
    @Override
    public void onPrecedenceChecked(Precedence precedence) {
        getModel().setPrecedence(precedence);
    }

    @Override
    public void onShowTree(ViewFieldID viewFieldID) {

        Navigation navigation = getNavigation();
        if (navigation != null) {
            switch (viewFieldID) {
            case FIELD_FROM_A:
                navigation.navigateToBrowseTree(null,
                                                getModel().getScanResult().getDirA(),
                                                getModel().getMissionNameData(),
                                                "copy from: " + getModel().getMissionNameData().getEndPointA().getEndPointName());
                break;
            case FIELD_FROM_B:
                navigation.navigateToBrowseTree(null,
                                                getModel().getScanResult().getDirB(),
                                                getModel().getMissionNameData(),
                                                "copy from: " + getModel().getMissionNameData().getEndPointB().getEndPointName());
                break;
            case FIELD_TO_A:
                navigation.navigateToBrowseTree(Action.COPY_TO_A,
                                                getModel().getComparisonTree(),
                                                getModel().getMissionNameData(),
                                                "copy to: " + getModel().getMissionNameData().getEndPointA().getEndPointName());
                break;
            case FIELD_TO_B:
                navigation.navigateToBrowseTree(Action.COPY_TO_B,
                                                getModel().getComparisonTree(),
                                                getModel().getMissionNameData(),
                                                "copy to: " + getModel().getMissionNameData().getEndPointB().getEndPointName());
                break;
            case FIELD_ON_A:
                navigation.navigateToBrowseTree(Action.OVERWRITE_ON_A,
                                                getModel().getComparisonTree(),
                                                getModel().getMissionNameData(),
                                                "overwrite on: " + getModel().getMissionNameData().getEndPointA().getEndPointName());
                break;
            case FIELD_ON_B:
                navigation.navigateToBrowseTree(Action.OVERWRITE_ON_B,
                                                getModel().getComparisonTree(),
                                                getModel().getMissionNameData(),
                                                "overwrite on: " + getModel().getMissionNameData().getEndPointB().getEndPointName());
                break;
            case FIELD_NAME_CLASH:
                navigation.navigateToBrowseTree(Action.DO_NOTHING,
                                                getModel().getComparisonTree(),
                                                getModel().getMissionNameData(),
                                                "name clashes / ignore");
                break;
            }
        }
    }

    @Override
    public void onSyncButtonClicked() {
        getModel().doSync();
    }

    @Override
    public void onBackClicked() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateBack();
        }
    }
    //endregion

    //region MissionConfirmContract.ModelListener
    @Override
    public void onBusyChanged(boolean busy) {
        View view = getView();
        if (view != null) {
            handleBusyChanged(view, busy);
        }
    }

    @Override
    public void onCompared(ActionableDirNode comparisonTree) {
        View view = getView();
        if (view != null) {
            handleCompared(view, comparisonTree);
        }
    }

    //    @Override
//    public void onRetrieveSucceeded(@NonNull FileNode fileNode) {
//        Navigation navigation = getNavigation();
//        if (navigation != null) {
//            handleSuccess(navigation, fileNode);
//        }
//    }
//
//    @Override
//    public void onRetrieveFailed(String errorMsg) {
//        View view = getView();
//        if (view != null) {
//            handleError(view, errorMsg);
//        }
//    }
    //endregion

//    private void handleSuccess(@NonNull Navigation navigation, @NonNull FileNode fileNode) {
//        if (fileNode instanceof DirNode) {
//            getModel().resetModelState();
//            navigation.navigateToBrowseTree((DirNode)fileNode);
//        }
//    }
//
//    private void handleError(@NonNull View view, String errorMsg) {
//        view.showError(errorMsg);
//    }
//
    private void handleBusyChanged(@NonNull View view, boolean busy) {
        view.showLoadingState(busy);
    }

    private void handleCompared(@NonNull View view, ActionableDirNode comparisonTree) {
        Precedence precedence = getModel().getPrecedence();

        int totalFiles = 0;
        int[] counts;

        if (precedence == Precedence.A) {
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_TO_A, null);
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_ON_A, null);
        } else {
            counts = makeCounts(comparisonTree, Action.COPY_TO_A);
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_TO_A, counts);
            totalFiles += counts[0];
            counts = makeCounts(comparisonTree, Action.OVERWRITE_ON_A);
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_ON_A, counts);
            totalFiles += counts[0];
        }

        if (precedence == Precedence.B) {
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_TO_B, null);
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_ON_B, null);
        } else {
            counts = makeCounts(comparisonTree, Action.COPY_TO_B);
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_TO_B, counts);
            totalFiles += counts[0];
            counts = makeCounts(comparisonTree, Action.OVERWRITE_ON_B);
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_ON_B, counts);
            totalFiles += counts[0];
        }

        if (precedence != Precedence.NEWEST) {
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_NAME_CLASH, null);
        } else {
            counts = makeCounts(comparisonTree, Action.DO_NOTHING);
            view.setFieldCountsAndVisibility(ViewFieldID.FIELD_NAME_CLASH, counts);
            totalFiles += counts[0];
        }
        view.setComparisonAndSyncButton(true, totalFiles != 0);
    }

    private int[] makeCounts(DirNode dirNode) {
        NodeCounter counter = new NodeCounter(dirNode);
        return new int[] {
                counter.getFileCount(),
                counter.getDirCount()
        };
    }

    private int[] makeCounts(ActionableDirNode actionableDirNode, Action action) {
        NodeCounter counter = new NodeCounter(actionableDirNode, action);
        return new int[] {
                counter.getFileCount(),
                counter.getDirCount()
        };
    }

}
