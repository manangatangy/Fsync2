package com.wolfbang.fsync.ftpservice.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class DirNode extends FileNode {

    private NodeList mChildren = new NodeList();

    public DirNode(String name, @NonNull DirNode parent, Date timeStamp) {
        super(name, parent, timeStamp);
    }

    @Override
    public NodeType getNodeType() {
        return Node.NodeType.DIR;
    }

    public NodeList getChildren() {
        return mChildren;
    }

    @Nullable
    public Node findChild(String name) {
        return mChildren.find(name);
    }

    public void add() {

    }

    @Override
    public String toString() {
        return super.toString() + ", [" + getChildren().size() + " children]";
    }

    @Override
    public String toStringWithPath() {
        return super.toStringWithPath() + ", [" + getChildren().size() + " children]";
    }

    /**
     * Perform a depth-first tree traversal and dump each node.
     * @param tag - prefix for each node trace
     */
    @Override
    public void dump(String tag) {
        super.dump(tag);
        for (Node child : getChildren()) {
            child.dump(tag);
        }
    }
}
