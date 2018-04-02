package com.wolfbang.fsync.missionconfirm.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.compare.ActionableDirNode;
import com.wolfbang.fsync.ftpservice.model.compare.Precedence;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Model;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.ModelListener;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Navigation;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Presenter;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.View;

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
                view.setEndPointNameB(endPointB);
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


//                    setConflict(view, model.getScanResult().getNameConflict());
//                    setCopiedToA(view, model.getScanResult().getCopiedToA(), endPointA, endPointB);
//                    setCopiedToB(view, model.getScanResult().getCopiedToB(), endPointA, endPointB);
//                    setOverriddenOnA(view, model.getScanResult().getOverriddenOnA(), endPointA, endPointB);
//                    setOverriddenOnB(view, model.getScanResult().getOverriddenOnB(), endPointA, endPointB);

//                case SUCCESS:
//                    Navigation navigation = getNavigation();
//                    if (navigation != null) {
//                        handleSuccess(navigation, model.getSuccessResponse());
//                    }
//                    break;
//                case ERROR:
//                    handleError(view, model.getErrorMsg());
//                    break;


    //region MissionConfirmContract.Presenter
    @Override
    public void onPrecedenceChecked(Precedence precedence) {
        getModel().setPrecedence(precedence);
    }

    @Override
    public void onShowTreeEndPointA() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateToBrowseTree(getModel().getScanResult().getDirA(),
                                            getModel().getMissionNameData().getEndPointA().getEndPointName());
        }
    }

    @Override
    public void onShowTreeEndPointB() {
        Navigation navigation = getNavigation();
        if (navigation != null) {
            navigation.navigateToBrowseTree(getModel().getScanResult().getDirB(),
                                            getModel().getMissionNameData().getEndPointB().getEndPointName());
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

        if (precedence == Precedence.A) {
            view.setCopiedSubHeadingA(null);
            view.setOverriddenSubHeadingA(null);
        } else {
            view.setCopiedSubHeadingA(makeSubHeading(comparisonTree, Action.COPY_TO_A));
            view.setOverriddenSubHeadingA(makeSubHeading(comparisonTree, Action.OVERWRITE_ON_A));
        }

        if (precedence == Precedence.B) {
            view.setCopiedSubHeadingB(null);
            view.setOverriddenSubHeadingB(null);
        } else {
            view.setCopiedSubHeadingB(makeSubHeading(comparisonTree, Action.COPY_TO_B));
            view.setOverriddenSubHeadingB(makeSubHeading(comparisonTree, Action.OVERWRITE_ON_B));
        }

        if (precedence == Precedence.NEWEST) {
            view.setClashSubHeading(makeSubHeading(comparisonTree, Action.DO_NOTHING));
        } else {
            view.setClashSubHeading(null);
        }
    }

    private String makeSubHeading(ActionableDirNode actionableDirNode, Action action) {
        int fileCount = actionableDirNode.getFileCount(action);
        int dirCount = actionableDirNode.getDirCount(action);
        String text = makeCountDescription(fileCount, dirCount);
        return text;
    }

    private String makeCountDescription(int fileCount, int dirCount) {
        return fileCount + (fileCount > 1 ? " files/in " : " file/in ")
                + dirCount + (dirCount > 1 ? " dirs" : " dir");
    }
}
