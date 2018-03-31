package com.wolfbang.fsync.ftpservice.model.mission;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;

/**
 * @author david
 * @date 22 Mar 2018.
 */

public class ScanResult {
    private DirNode mDirA;
    private DirNode mDirB;

    public ScanResult(DirNode dirA, DirNode dirB) {
        mDirA = dirA;
        mDirB = dirB;
    }

    public DirNode getDirA() {
        return mDirA;
    }

    public DirNode getDirB() {
        return mDirB;
    }
}
