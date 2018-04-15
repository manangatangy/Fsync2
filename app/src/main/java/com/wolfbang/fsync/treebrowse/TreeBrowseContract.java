package com.wolfbang.fsync.treebrowse;

import android.support.annotation.Nullable;

import com.lsmvp.simplemvp.AbstractMvpContract;
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;

/**
 * @author david
 * @date 19 Mar 2018.
 */

public interface TreeBrowseContract extends AbstractMvpContract {

    interface Navigation extends BasicNavigation {
//        void navigateToBrowseTree(DirNode dirNode);
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
        MissionNameData getMissionNameData();
        @Nullable
        Action getAction();

        boolean onBackClicked();        // return true if consumed
        void onListItemClicked(DirNode dirNode);
        void onPathElementClicked(int index);
    }

    interface Model extends AbstractModel {
        void setMissionNameData(MissionNameData missionNameData);
        MissionNameData getMissionNameData();
        @Nullable
        Action getAction();

        // Set the start of the browse tree.  A non-null action causes filtered browsing.
        void setBaseAndCurrentDir(@Nullable Action action, DirNode dirNode);

        String getCurrentDirName();
        Node[] getCurrentDirChildren();

        // Names from base dir to the current dir.
        String[] getCurrentPathAsNameList();

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
