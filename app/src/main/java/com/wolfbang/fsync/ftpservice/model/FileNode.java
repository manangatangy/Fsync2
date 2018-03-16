package com.wolfbang.fsync.ftpservice.model;

import java.util.Date;

/**
 * @author david
 * @date 12 Mar 2018.
 */

public class FileNode extends Node {

    private final String mName;
    private final DirNode mParent;
    private final Date mTimeStamp;

    /**
     * @param name should not include path separators.
     * @param parent null to indicate root File
     * @param timeStamp
     */
    public FileNode(String name, DirNode parent, Date timeStamp) {
        mName = name;
        mParent = parent;
        mTimeStamp = timeStamp;
    }

    @Override
    public NodeType getNodeType() {
        return Node.NodeType.FILE;
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public DirNode getParent() {
        return mParent;
    }

    @Override
    public Date getTimeStamp() {
        return mTimeStamp;
    }

}