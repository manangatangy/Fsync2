package com.wolfbang.fsync.ftpservice.model;

import android.util.Log;

import java.util.Date;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class File implements BaseNode {

    private final String mName;
    private final Directory mParent;
    private final Date mTimeStamp;

    /**
     * @param name should not include path separators.
     * @param parent null to indicate root File
     * @param timeStamp
     */
    public File(String name, Directory parent, Date timeStamp) {
        mName = name;
        mParent = parent;
        mTimeStamp = timeStamp;
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.FILE;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public Directory getParent() {
        return mParent;
    }

    @Override
    public Date getTimeStamp() {
        return mTimeStamp;
    }

    @Override
    public String getPath() {
        return (getParent() != null ? (getParent().getPath() + "/") : "") + getName();
    }

    @Override
    public boolean isRoot() {
        return (getParent() == null);
    }

    @Override
    public String toString() {
        return "'" + getName() + "', " + getNodeType().name();
    }

    @Override
    public String toStringWithPath() {
        return "'" + getPath() + "', " + getNodeType().name();
    }

    @Override
    public void dump(String tag) {
        Log.d(tag, toStringWithPath());
    }

}
