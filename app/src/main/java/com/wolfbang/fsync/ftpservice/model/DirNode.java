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

    private TreeSet<FileNode> mChildren = new TreeSet<>(new Comparator<FileNode>() {
        @Override
        public int compare(FileNode fileNode1, FileNode fileNode2) {
            return fileNode1.getName().compareTo(fileNode2.getName());
        }
    });

    public DirNode(String name, @NonNull DirNode parent, Date timeStamp) {
        super(name, parent, timeStamp);
    }

    @Override
    public NodeType getNodeType() {
        return Node.NodeType.DIR;
    }

    public TreeSet<FileNode> getChildren() {
        return mChildren;
    }

    @Nullable
    public FileNode findChild(String name) {
        for (FileNode child : mChildren) {
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

    @Override
    public void dump(String tag) {
        super.dump(tag);
        for (FileNode child : getChildren()) {
            child.dump(tag);
        }
    }
}
