package com.wolfbang.fsync.ftpservice.model.compare;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;

import java.util.Date;

/**
 * @author david
 * @date 30 Mar 2018.
 */
public class CommonActionableFileNode extends ActionableFileNode {

    private Date mTimeStampA;
    private Date mTimeStampB;

    /**
     * The allowable actions for this node are; OVERWRITE_ON_A, OVERWRITE_ON_B, DO_NOTHING.
     */
    public CommonActionableFileNode(Precedence precedence,
                                    String name, DirNode parent,
                                    Date timeStampA, Date timeStampB) {
        super(name, parent);
        mTimeStampA = timeStampA;
        mTimeStampB = timeStampB;

        Action initialAction = Action.DO_NOTHING;
        if (precedence == Precedence.A) {
            initialAction = Action.OVERWRITE_ON_B;
        } else if (precedence == Precedence.B) {
            initialAction = Action.OVERWRITE_ON_A;
        } else if (precedence == Precedence.NEWEST && timeStampA != null && timeStampB != null) {
            if (timeStampA.after(timeStampB)) {
                // A is newer than B, so overwrite B
                initialAction = Action.OVERWRITE_ON_B;
            } else if (timeStampB.after(timeStampA)) {
                // B is newer than A, so overwrite A
                initialAction = Action.OVERWRITE_ON_A;
            }
        }
        setAction(initialAction);
    }

    public String getDetails() {
        return "COMMON FILE" + ", action: " + getAction().name();
    }

    public Date getTimeStampA() {
        return mTimeStampA;
    }

    public Date getTimeStampB() {
        return mTimeStampB;
    }
}
