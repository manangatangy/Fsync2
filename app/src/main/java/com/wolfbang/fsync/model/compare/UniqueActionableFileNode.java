package com.wolfbang.fsync.model.compare;

import com.wolfbang.fsync.model.filetree.DirNode;

/**
 * @author david
 * @date 30 Mar 2018.
 */
public class UniqueActionableFileNode extends ActionableFileNode {

    private UniqueTo mUniqueTo;

    /**
     * The allowable actions for this node are; COPY_TO_A, COPY_TO_B, DO_NOTHING.
     * For a specific instance, only one of COPY_TO_A and COPY_TO_B is allowed
     * depending on the value of UniqueTo.
     */
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

    public UniqueTo getUniqueTo() {
        return mUniqueTo;
    }

}
