package com.wolfbang.fsync.treebrowse;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;

/**
 * @author david
 * @date 19 Mar 2018.
 */

public interface TreeBrowseContract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
        void navigateToBrowseTree(DirNode dirNode);
        void navigateBack();
    }

    interface View extends AbstractView {
        // Populate the path scroller with the specified elements
        void populatePathElements(String[] pathNames);
        void addPathElement(String pathName);
        void popPathElement();

        void populateList(Node[] nodes);
//        void setSomeField(String someValue);
//        void showError();
//        void showLoadingState(boolean show);
    }

    interface Presenter extends AbstractPresenter<View, Model, Navigation> {
        //        void onSomeButtonClicked(int timePeriod);
//        DirNode getDirNode();
        boolean onBackClicked();        // return true if consumed
        void onListItemClicked(FileNode fileNode);
        void onPathElementClicked(int index);
    }

    interface Model extends AbstractModel {
        // Set the start of the browse tree.
        void setBaseAndCurrentDir(DirNode dirNode);

        // The dir currently shown in the fragment.
        DirNode getCurrentDir();

        // Names from base dir to the current dir.
        String[] getPathAsNameList();

        // Shift the current node to it's parent, or return false if already at the base
        boolean moveCurrentDirToParent();

        // return false if there is no such child name that is a dirNode
        boolean moveCurrentDirToChild(String childName);

        // level 0 is baseDir
        boolean moveCurrentDirToLevel(int level);

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
