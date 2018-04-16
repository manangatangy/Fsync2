package com.wolfbang.fsync.ftpservice.model.compare;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;

import java.util.Date;

/**
 * @author david
 * @date 30 Mar 2018.
 */

public class TypeClashActionableDirNode extends ActionableDirNode {

    private DirectoryOn mDirectoryOn;
    private Date mFileTimeStamp;

    /**
     * The allowable actions for this node are; OVERWRITE_ON_A, OVERWRITE_ON_B, DO_NOTHING,
     * regardless of the DirectoryOn value.
     */
    public TypeClashActionableDirNode(Precedence precedence,
                                      String name, DirNode parent, Date fileTimeStamp,
                                      DirectoryOn directoryOn) {
        super(name, parent);
        mDirectoryOn = directoryOn;
        mFileTimeStamp = fileTimeStamp;

        Action initialAction = Action.DO_NOTHING;
        if (precedence == Precedence.A) {
            initialAction = Action.OVERWRITE_ON_B;
        } else if (precedence == Precedence.B) {
            initialAction = Action.OVERWRITE_ON_A;
        }
        setAction(initialAction);
    }

    public DirectoryOn getDirectoryOn() {
        return mDirectoryOn;
    }

    public Date getFileTimeStamp() {
        return mFileTimeStamp;
    }

    public String getDetails() {
        return "TYPE CLASH directoryOn: " + mDirectoryOn.name() + ", action: " + getAction().name();
    }

}
