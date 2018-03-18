package com.wolfbang.fsync.ftpservice.model.mission;

/**
 * @author david
 * @date 18 Mar 2018.
 */

public class EndPoint {

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

}
