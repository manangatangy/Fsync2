package com.wolfbang.fsync.treebrowse.impl;

import com.lsmvp.simplemvp.BaseMvpModel;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.treebrowse.TreeBrowseContract.Model;

import java.util.concurrent.Executor;

import javax.inject.Inject;

/**
 * @author david
 * @date 19 Mar 2018.
 */


public class TreeBrowseModel
        extends BaseMvpModel
        implements Model
{

    private DirNode mBaseDirNode;       // The start of the browse tree - can't pop up above this.
    private DirNode mCurrentDirNode;    // Currently shown in the browse tree
    private int mLevel = 0;             // Number of subdirs below base, that the current dir is.

    @Inject
    public TreeBrowseModel(Executor executor) {
        super(executor);
    }

    @Override
    public void setBaseAndCurrentDir(DirNode dirNode) {
        this.mBaseDirNode = dirNode;
        this.mCurrentDirNode = dirNode;
    }

    @Override
    public DirNode getCurrentDir() {
        return mCurrentDirNode;
    }

    @Override
    public String[] getPathAsNameList() {
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
        for (Node child : mCurrentDirNode.toChildrenArray()) {
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
