package com.wolfbang.fsync.model.mission;

import com.wolfbang.fsync.model.filetree.DirNode;

/**
 * @author david
 * @date 18 Mar 2018.
 */

public abstract class EndPoint {

    private String mEndPointName;

    public EndPoint(String endPointName) {
        mEndPointName = endPointName;
    }

    public String getEndPointName() {
        return mEndPointName;
    }

    public void setEndPointName(String mEndPointName) {
        this.mEndPointName = mEndPointName;
    }

    /**
     * Should be executed in background.
     */
    public abstract EndPointResponse<DirNode> fetchFileTree();

}
