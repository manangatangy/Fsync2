package com.wolfbang.fsync.missionconfirm.impl;

import android.support.annotation.NonNull;

import com.lsmvp.simplemvp.BaseMvpPresenter;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.View;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Model;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Navigation;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.ModelListener;
import com.wolfbang.fsync.missionconfirm.MissionConfirmContract.Presenter;

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
                    String endPointA = model.getScanResult().getEndPointNameA();
                    String endPointB = model.getScanResult().getEndPointNameB();
                    // Display the initial value of the model value.
                    view.setMissionName(model.getScanResult().getMissionName());
                    setConflict(view, model.getScanResult().getNameConflict());
                    setCopiedToA(view, model.getScanResult().getCopiedToA(), endPointA, endPointB);
                    setCopiedToB(view, model.getScanResult().getCopiedToB(), endPointA, endPointB);
                    setOverriddenOnA(view, model.getScanResult().getOverriddenOnA(), endPointA, endPointB);
                    setOverriddenOnB(view, model.getScanResult().getOverriddenOnB(), endPointA, endPointB);

                    break;
//                case SUCCESS:
//                    Navigation navigation = getNavigation();
//                    if (navigation != null) {
//                        handleSuccess(navigation, model.getSuccessResponse());
//                    }
//                    break;
//                case ERROR:
//                    handleError(view, model.getErrorMsg());
//                    break;
                default:
                    throw new IllegalStateException("Unsupported model state");
            }
        }
    }
    //endregion

    //region MissionConfirmContract.Presenter
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

    private void setConflict(@NonNull View view, DirNode dirNode) {

        int fileCount = dirNode.getFileCount();
        int dirCount = dirNode.getDirCount();
        String text = (dirNode.getFileCount() == 0)
                ? null
                : (makeDescription(fileCount, dirCount) + "\nhave mismatching types\nand require resolution");
        view.setConflict(text, true);
    }

    private void setCopiedToA(@NonNull View view, DirNode dirNode,
                              String endPointNameA, String endPointNameB) {
        String text = makeDescription("will be copied", dirNode, endPointNameB, endPointNameA);
        view.setCopied1(text, (dirNode.getFileCount() > 0));
    }

    private void setCopiedToB(@NonNull View view, DirNode dirNode,
                              String endPointNameA, String endPointNameB) {
        String text = makeDescription("will be copied", dirNode, endPointNameA, endPointNameB);
        view.setCopied2(text, (dirNode.getFileCount() > 0));
    }

    private void setOverriddenOnA(@NonNull View view, DirNode dirNode,
                                  String endPointNameA, String endPointNameB) {
        String text = makeDescription("are older and will be overridden", dirNode, null, endPointNameA);
        view.setOverridden1(text, (dirNode.getFileCount() > 0));
    }

    private void setOverriddenOnB(@NonNull View view, DirNode dirNode,
                                  String endPointNameA, String endPointNameB) {
        String text = makeDescription("are older and will be overridden", dirNode, null, endPointNameB);
        view.setOverridden2(text, (dirNode.getFileCount() > 0));
    }

    private String makeDescription(String action, DirNode dirNode,
                                   String fromEndPointName, String toEndPointName) {
        int fileCount = dirNode.getFileCount();
        int dirCount = dirNode.getDirCount();
        String text = makeDescription(fileCount, dirCount) + "\n" + action;
        if (fromEndPointName == null) {
            text = text + "\non: " + toEndPointName;
        } else {
            text = text + "\nfrom: " + fromEndPointName + "\nto: " + toEndPointName;
        }

        return text;
    }

    private String makeDescription(int fileCount, int dirCount) {
        return fileCount + (fileCount > 1 ? " files in " : " file in ")
                + dirCount + (dirCount > 1 ? " folders" : " folder");
    }
}
