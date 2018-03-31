package com.wolfbang.fsync.ftpservice.model.mission;

/**
 * @author david
 * @date 18 Mar 2018.
 */

public class MissionNameData {

    private String mMissionName;
    private EndPoint mEndPointA;
    private EndPoint mEndPointB;

    public MissionNameData(String missionName, EndPoint endPointA, EndPoint endPointB) {
        this.mMissionName = missionName;
        this.mEndPointA = endPointA;
        this.mEndPointB = endPointB;
    }

    public String getMissionName() {
        return mMissionName;
    }

    public void setMissionName(String mMissionName) {
        this.mMissionName = mMissionName;
    }

    public EndPoint getEndPointA() {
        return mEndPointA;
    }

    public void setEndPointA(EndPoint mEndPointA) {
        this.mEndPointA = mEndPointA;
    }

    public EndPoint getEndPointB() {
        return mEndPointB;
    }

    public void setEndPointB(EndPoint mEndPointB) {
        this.mEndPointB = mEndPointB;
    }

}
