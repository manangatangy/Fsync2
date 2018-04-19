package com.wolfbang.fsync.model.compare;

import android.support.annotation.Nullable;

import com.wolfbang.fsync.model.filetree.DirNode;
import com.wolfbang.fsync.model.filetree.FileNode;
import com.wolfbang.fsync.model.filetree.Node;

/**
 * @author David Weiss
 * @date 19/4/18
 */
public class NodeCounter {

    private Action mAction;
    private int mFileCount;
    private int mDirCount;

    public NodeCounter(DirNode dirNode, @Nullable Action action) {
        mFileCount = 0;
        mDirCount = 0;
        if (dirNode instanceof ActionableDirNode && action != null) {
            mAction = action;
            countActionable((ActionableDirNode)dirNode);
        } else {
            count(dirNode);
        }
    }

    public NodeCounter(DirNode dirNode) {
        this(dirNode, null);
    }

    public int getFileCount() {
        return mFileCount;
    }

    public int getDirCount() {
        return mDirCount;
    }

    public String getFilesDirsCountText() {
        int files = getFileCount();
        int dirs = getDirCount() + 1;       // Add one, for this directory
        return files + (files != 1 ? " files" : " file") + " in " + dirs + (dirs != 1 ? " dirs" : " dir");
    }

    /**
     * @param node
     * @return true if the actionableDirNode has a countable file descendant.
     */
    private boolean countActionable(ActionableDirNode node) {

        boolean hasCountableFileOrDescendant = false;
        for (Node child : node.getChildren()) {
            if (child instanceof ActionableDirNode) {
                ActionableDirNode dirNode = (ActionableDirNode)child;
                if (ActionableDirNode.shouldNodeBeIncluded(dirNode, mAction)) {
                    if (countActionable(dirNode)) {
                        hasCountableFileOrDescendant = true;
                    }
                }
            } else if (child instanceof ActionableFileNode) {
                ActionableFileNode fileNode = (ActionableFileNode)child;
                if (fileNode.getAction() == mAction) {
                    mFileCount++;
                    hasCountableFileOrDescendant = true;
                }
            }
        }
        if (hasCountableFileOrDescendant) {
            mDirCount++;
        }
        return hasCountableFileOrDescendant;
    }

    /**
     * @param node
     * @return true if the dirNode has a countable file descendant.
     */
    private boolean count(DirNode node) {

        boolean hasCountableFileOrDescendant = false;
        for (Node child : node.getChildren()) {
            if (child instanceof DirNode) {
                DirNode dirNode = (DirNode)child;
                if (count(dirNode)) {
                    hasCountableFileOrDescendant = true;
                }
            } else if (child instanceof FileNode) {
                mFileCount++;
                hasCountableFileOrDescendant = true;
            }
        }
        if (hasCountableFileOrDescendant) {
            mDirCount++;
        }
        return hasCountableFileOrDescendant;
    }

}
