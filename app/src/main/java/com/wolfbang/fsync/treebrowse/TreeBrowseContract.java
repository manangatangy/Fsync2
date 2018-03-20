package com.wolfbang.fsync.treebrowse;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;

/**
 * @author david
 * @date 19 Mar 2018.
 */

public interface TreeBrowseContract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
        void navigateToBrowseTree(DirNode dirNode);
    }

    interface View extends AbstractView {
//        void setSomeField(String someValue);
//        void showError();
//        void showLoadingState(boolean show);
    }

    interface Presenter extends AbstractPresenter<View, Model, Navigation> {
        //        void onSomeButtonClicked(int timePeriod);
        DirNode getDirNode();
        void onBackClicked();
        void onItemClicked(FileNode fileNode);
    }

    interface Model extends AbstractModel {
        void setDirNode(DirNode dirNode);
        DirNode getDirNode();

//        void setFeature2Data(Feature2Data feature2Data);
//        Feature2Data getFeature2Data();
//
//        String getSuccessResponse();
//        void doSomeAction(int timePeriod);
//
//        boolean isBusy();
//        ModelState getModelState();
    }

//    enum ModelState {
//        IDLE,
//        ERROR,
//        SUCCESS
//    }
    interface ModelListener extends AbstractModelListener {
//        void onBusyChanged(boolean busy);
//        void onRetrieveSucceeded(@NonNull String resultValue);
//        void onRetrieveFailed();
    }
}
