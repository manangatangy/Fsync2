package com.wolfbang.fsync.ftpservice.model.compare;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;

/**
 * @author david
 * @date 30 Mar 2018.
 */
public class UniqueActionableFileNode extends ActionableFileNode {

    private UniqueTo mUniqueTo;

    public UniqueActionableFileNode(Precedence precedence,
                                    String name, DirNode parent,
                                    UniqueTo uniqueTo) {
        super(name, parent);
        mUniqueTo = uniqueTo;
        Action initialAction = Action.DO_NOTHING;
        if (uniqueTo == UniqueTo.A) {
            if (precedence == Precedence.A || precedence == Precedence.NEWEST) {
                initialAction = Action.COPY_TO_B;
            }
        } else {
            if (precedence == Precedence.B || precedence == Precedence.NEWEST) {
                initialAction = Action.COPY_TO_A;
            }
        }
        setAction(initialAction);
    }

    public String getDetails() {
        return "FILE UNIQUE To: " + mUniqueTo.name() + ", action: " + getAction().name();
    }

}
