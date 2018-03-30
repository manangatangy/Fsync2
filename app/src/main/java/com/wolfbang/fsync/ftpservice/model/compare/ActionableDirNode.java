package com.wolfbang.fsync.ftpservice.model.compare;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;

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
}
