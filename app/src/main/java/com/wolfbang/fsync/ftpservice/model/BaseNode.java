package com.wolfbang.fsync.ftpservice.model;

import java.util.Date;

/**
 * @author david
 * @date 14 Mar 2018.
 */

public interface BaseNode {

    enum NodeType {
        FILE,
        DIR,
        SYMLINK
    }

    NodeType getNodeType();
    String getName();
    Directory getParent();          //  path including all ancestor's paths, separated by /
    Date getTimeStamp();
    String getPath();
    boolean isRoot();

    String toString();
    String toStringWithPath();
    void dump(String tag);
}
