package com.wolfbang.fsync.ftpservice.model.compare;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;

/**
 * @author david
 * @date 30 Mar 2018.
 */

public class ActionableDirNode extends DirNode {

    private Action mAction = Action.DO_NOTHING;

    public ActionableDirNode(String name, DirNode parent) {
        // Timestamps not needed here.
        super(name, parent, null);
    }

    public Action getAction() {
        return mAction;
    }

    public void setAction(Action action) {
        mAction = action;
    }

    /**
     * @return a file count filtered by the action
     */
    public int getFileCount(Action action) {
        int count = 0;
        for (Node child : getChildren()) {
            if (child instanceof ActionableDirNode) {
                count += ((ActionableDirNode) child).getFileCount(action);
            } else if (child instanceof ActionableFileNode) {
                ActionableFileNode actionableFileNode = (ActionableFileNode)child;
                if (actionableFileNode.getAction() == action) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * @return a dir count filtered by the action
     */
    public int getDirCount(Action action) {
        int count = 0;      // Exclude the root dir from the count.
        for (Node child : getChildren()) {
            if (child instanceof ActionableDirNode) {
                ActionableDirNode actionableDirNode = (ActionableDirNode)child;
                if (actionableDirNode.getAction() == action) {
                    count++;
                }
                count += actionableDirNode.getDirCount(action);
            }
        }
        return count;
    }

}
