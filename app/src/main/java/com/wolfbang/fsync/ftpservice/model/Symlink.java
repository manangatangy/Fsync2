package com.wolfbang.fsync.ftpservice.model;

import android.support.annotation.NonNull;

import java.util.Date;

/**
 * @author david
 * @date 14 Mar 2018.
 */

public class Symlink extends File {

    public Symlink(String name, Directory parent, Date timeStamp) {
        super(name, parent, timeStamp);
    }

    @Override
    public NodeType getNodeType() {
        return NodeType.SYMLINK;
    }

}
