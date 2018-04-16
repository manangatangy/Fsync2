package com.wolfbang.fsync.model.compare;

import com.wolfbang.fsync.model.filetree.DirNode;
import com.wolfbang.fsync.model.filetree.FileNode;

/**
 * @author david
 * @date 30 Mar 2018.
 */
public class ActionableFileNode extends FileNode {

    private Action mAction = Action.DO_NOTHING;

    public ActionableFileNode(String name, DirNode parent) {
        // Timestamps not needed here.
        super(name, parent, null);
    }

    public Action getAction() {
        return mAction;
    }

    public void setAction(Action action) {
        mAction = action;
    }
}
