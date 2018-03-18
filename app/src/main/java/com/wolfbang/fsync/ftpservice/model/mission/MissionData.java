package com.wolfbang.fsync.ftpservice.model.mission;

/**
 * @author david
 * @date 18 Mar 2018.
 */

public class MissionData {

    public enum Direction {
        A_TO_B,
        B_TO_A,
        A_BI_B      // Bidirectional
    }

    private String mMissionName;
    private Direction mDirection;
    private EndPoint mEndPointA;
    private EndPoint mEndPointB;

    public MissionData(String missionName, Direction direction, EndPoint endPointA, EndPoint endPointB) {
        this.mMissionName = missionName;
        this.mDirection = direction;
        this.mEndPointA = endPointA;
        this.mEndPointB = endPointB;
    }

    public String getMissionName() {
        return mMissionName;
    }

    public void setMissionName(String mMissionName) {
        this.mMissionName = mMissionName;
    }

    public Direction getDirection() {
        return mDirection;
    }

    public void setDirection(Direction mDirection) {
        this.mDirection = mDirection;
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
