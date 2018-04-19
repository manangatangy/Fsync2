package com.wolfbang.fsync.model.compare;

import com.wolfbang.fsync.model.filetree.DirNode;
import com.wolfbang.fsync.model.filetree.Node;

import java.util.ArrayList;

/**
 * @author david
 * @date 30 Mar 2018.
 */

public class ActionableDirNode extends DirNode {

    private Action mAction = Action.DO_NOTHING;

    public ActionableDirNode(String name, DirNode parent) {
        // Timestamps not needed here.
        super(name, parent, null);
    }

    public Action getAction() {
        return mAction;
    }

    public void setAction(Action action) {
        mAction = action;
    }

    /**
     * @return a new array of nodes, copied from this instance, that match the action
     */
    public Node[] toChildrenArray(Action action) {
        ArrayList<Node> list = new ArrayList<>();
        for (Node child : getChildren()) {
            if (child instanceof ActionableDirNode) {
                // A sub dir which holds zero files of the filtered type, is not displayed in the child
                // list since there is no files that can be viewed, after traversing through the subdirs.
                ActionableDirNode actionableDirNode = (ActionableDirNode)child;
                NodeCounter counter = new NodeCounter(actionableDirNode, action);

                if (counter.getFileCount() > 0) {
                    // Unlike other DirNodes, but like FileNodes, TypeClashADN's are filtered on
                    // their action. This is because they have a selectable action (like FileNodes).
                    // This same filtering is applied to the counting methods.
                    if (shouldNodeBeIncluded(actionableDirNode, action)) {
                        list.add(child);
                    }
                }
            } else if (child instanceof ActionableFileNode) {
                ActionableFileNode actionableFileNode = (ActionableFileNode)child;
                if (actionableFileNode.getAction() == action) {
                    list.add(child);
                }
            }
        }
        return list.toArray(new Node[list.size()]);
    }

    public static boolean shouldNodeBeIncluded(ActionableDirNode actionableDirNode,
                                               Action action) {
        // Non TypeClash nodes should always be included in counts and browses.
        if (!(actionableDirNode instanceof TypeClashActionableDirNode)) {
            return true;
        }
        // Type clash nodes included only if action matches (like file nodes).
        if (actionableDirNode.getAction() == action) {
            return true;
        }
        return false;
    }

}
