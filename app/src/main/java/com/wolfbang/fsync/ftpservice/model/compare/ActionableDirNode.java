package com.wolfbang.fsync.ftpservice.model.compare;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;

import java.util.ArrayList;

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
     * @return a new array of nodes, copied from this instance, that match the action
     */
    public Node[] toChildrenArray(Action action) {
        ArrayList<Node> list = new ArrayList<>();
        for (Node child : getChildren()) {
            if (child instanceof ActionableDirNode) {
                list.add(child);
            } else if (child instanceof ActionableFileNode) {
                ActionableFileNode actionableFileNode = (ActionableFileNode)child;
                if (actionableFileNode.getAction() == action) {
                    list.add(child);
                }
            }
        }
        return list.toArray(new Node[list.size()]);
    }

    /**
     * @return a file count filtered by the action
     */
    @Override
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
    @Override
    public int getDirCount(Action action) {
        int count = 0;      // Don't include the root dir in the count.
        for (Node child : getChildren()) {
            if (child instanceof ActionableDirNode) {
                count++;
                count += ((ActionableDirNode)child).getDirCount(action);
            }
        }
        return count;
    }

    @Override
    public String getFilesDirsCountText(Action action) {
        int files = getFileCount(action);
        int dirs = getDirCount(action) + 1;       // Add one, for this directory
        return files + (files != 1 ? " files" : " file") + " in " + dirs + (dirs != 1 ? " dirs" : " dir");
    }

}
