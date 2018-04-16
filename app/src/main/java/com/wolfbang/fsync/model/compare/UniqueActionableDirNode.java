package com.wolfbang.fsync.model.compare;

import com.wolfbang.fsync.model.filetree.DirNode;

/**
 * @author david
 * @date 30 Mar 2018.
 */
public class UniqueActionableDirNode extends ActionableDirNode {

    private UniqueTo mUniqueTo;

    public UniqueActionableDirNode(Precedence precedence,
                                   String name, DirNode parent,
                                   UniqueTo uniqueTo) {
        super(name, parent);
        mUniqueTo = uniqueTo;
    }

    public String getDetails() {
        return "DIR UNIQUE To: " + mUniqueTo.name() + ", action: " + getAction().name();
    }

}
