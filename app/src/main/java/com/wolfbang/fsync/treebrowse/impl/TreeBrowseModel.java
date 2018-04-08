package com.wolfbang.fsync.treebrowse.impl;

import android.support.annotation.Nullable;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.ftpservice.model.compare.Action;
import com.wolfbang.fsync.ftpservice.model.compare.ActionableDirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.mission.MissionNameData;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Model;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * @author david
 * @date 19 Mar 2018.
 */


public class TreeBrowseModel
        extends BaseMvpModel
        implements Model {

    private MissionNameData mMissionNameData;
    private Action mAction = null;      // non-null means that mBaseDirNode is an ActionableDirNode
    private DirNode mBaseDirNode;       // The start of the browse tree - can't pop up above this.
    private DirNode mCurrentDirNode;    // Currently shown in the browse tree
    private int mLevel = 0;             // Number of subdirs below base, that the current dir is.

    @Inject
    public TreeBrowseModel(Executor executor) {
        super(executor);
    }

    @Override
    public void setMissionNameData(MissionNameData missionNameData) {
        mMissionNameData = missionNameData;
    }

    @Override
    public MissionNameData getMissionNameData() {
        return mMissionNameData;
    }

    @Override
    public void setBaseAndCurrentDir(@Nullable Action action, DirNode dirNode) {
        this.mBaseDirNode = dirNode;
        this.mCurrentDirNode = dirNode;
        this.mAction = action;
    }

//    @Override
//    public DirNode getCurrentDir() {
//        return mCurrentDirNode;
//    }

    @Override
    public String getCurrentDirName() {
        return mCurrentDirNode.getName();
    }

    @Override
    public Node[] getCurrentDirChildren() {
        return (mAction == null)
               ? mCurrentDirNode.toChildrenArray()
               : ((ActionableDirNode)mCurrentDirNode).toChildrenArray(mAction);
    }

    @Override
    public String[] getCurrentPathAsNameList() {
        // Names from base dir to the current dir.
        String[] pathNames = new String[mLevel + 1];
        DirNode dirNode = mCurrentDirNode;
        for (int i = mLevel; i >= 0; i--) {
            pathNames[i] =  dirNode.getName();
            dirNode = dirNode.getParent();      // Will be null after last iteration.
        }
        return pathNames;
    }

    @Override
    public boolean moveCurrentDirToParent() {
        // Shift the current node to it's parent, or return false if already at the base
        if (mLevel <= 0) {
            return false;
        }
        mCurrentDirNode = mCurrentDirNode.getParent();
        mLevel--;
        return true;
    }

    @Override
    public boolean moveCurrentDirToChild(String childName) {
        // return false if there is no such child name that is a dirNode
        for (Node child : mCurrentDirNode.getChildren()) {
            if (childName.equals(child.getName())) {
                if (child instanceof DirNode) {
                    mCurrentDirNode = (DirNode)child;
                    mLevel++;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean moveCurrentDirToLevel(int level) {
        while (mLevel > level) {
            if (!moveCurrentDirToParent()) {
                return false;
            }
        }
        return true;
    }
}
