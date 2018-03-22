package com.wolfbang.fsync.ftpservice.model.mission;

import android.support.v4.util.Pair;

import com.wolfbang.fsync.ftpservice.model.filetree.Comparator;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * @author david
 * @date 22 Mar 2018.
 */

public class ScanResult {
    private String missionName;
    private String mEndPointNameA;
    private String mEndPointNameB;


    private DirNode copiedToA;
    private DirNode copiedToB;
    private DirNode overriddenOnA;
    private DirNode overriddenOnB;
    private DirNode nameConflict;

    public ScanResult(Comparator comparator, String missionName, String endPointNameA, String endPointNameB) {
        this.missionName = missionName;
        this.mEndPointNameA = endPointNameA;
        this.mEndPointNameB = endPointNameB;
        copiedToA = comparator.uniqueTolist2;
        copiedToB = comparator.uniqueTolist1;
        overriddenOnA = comparator.olderInList1;
        overriddenOnB = comparator.olderInList2;
        nameConflict = comparator.nodeTypeMismatch;
    }

    public String getMissionName() {
        return missionName;
    }

    public String getEndPointNameA() {
        return mEndPointNameA;
    }

    public String getEndPointNameB() {
        return mEndPointNameB;
    }

    // These paths exist on A and do not yet exist on A
    public DirNode getCopiedToA() {
        return copiedToA;
    }

    // These paths exist on B and do not yet exist on B
    public DirNode getCopiedToB() {
        return copiedToB;
    }

    // These paths exist on both and the path on A is older than the path on B
    public DirNode getOverriddenOnA() {
        return overriddenOnA;
    }

    // These paths exist on both and the path on B is older than the path on A
    public DirNode getOverriddenOnB() {
        return overriddenOnB;
    }

    // These paths exist on both and the usr must decide which will be overridden.
    public DirNode getNameConflict() {
        return  nameConflict;
    }
}
