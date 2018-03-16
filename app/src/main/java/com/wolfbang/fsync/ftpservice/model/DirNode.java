package com.wolfbang.fsync.ftpservice.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Comparator;
import java.util.Date;
import java.util.TreeSet;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class DirNode extends FileNode {

    private TreeSet<Node> mChildren = new TreeSet<>(new Comparator<Node>() {
        @Override
        public int compare(Node node1, Node node2) {
            return node1.compareTo(node2);
        }
    });

    public DirNode(String name, @NonNull DirNode parent, Date timeStamp) {
        super(name, parent, timeStamp);
    }

    @Override
    public NodeType getNodeType() {
        return Node.NodeType.DIR;
    }

    public TreeSet<Node> getChildren() {
        return mChildren;
    }

    @Nullable
    public Node findChild(String name) {
        for (Node child : mChildren) {
            if (child.getName().equals(name)) {
                return child;
            }
        }
        return null;
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
