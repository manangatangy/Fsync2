package com.wolfbang.fsync.ftpservice.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class Directory extends File {

    private List<File> mChildren = new ArrayList<>();

    public Directory(String name, @NonNull Directory parent, Date timeStamp) {
        super(name, parent, timeStamp);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.DIR;
    }

    @NonNull
    public List<File> getChildren() {
        return mChildren;
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
        for (File child : getChildren()) {
            child.dump(tag);
        }
    }
}
