package com.wolfbang.fsync.ftpservice.model.compare;

import android.support.annotation.NonNull;

import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;
import com.wolfbang.fsync.ftpservice.model.filetree.NodeType;

/**
 * @author david
 * @date 30 Mar 2018.
 */

/**
 * The comparison is predicated on two values; a direction and a precedence.
 * The direction specifies uni-directional or bi-directional, and is implied by the precedence.
 * The precedence specifies which side's file is copied to (or overwrites) the other.
 * A precedence of A or B means uni-directional, and a precedence of NEWEST means bi-directional.
 */
public class MergeComparator {

    private Precedence mPrecedence;

    private DirNode mEmptyDir = new DirNode(null, null, null);

    public MergeComparator(Precedence precedence) {
        mPrecedence = precedence;
    }

    /**
     * The nodes in this tree are all ActionableFileNode or ActionableDirNode, with Action values set
     * according to the specified Precedence as well as the node comparison.
     */
    public ActionableDirNode compare(@NonNull final DirNode dir1, @NonNull final DirNode dir2) {
        ActionableDirNode resultDir = new ActionableDirNode("COMPARISON", null);
        compare(resultDir, dir1, dir2);
        return resultDir;
    }

    /**
     * Performs a depth-first tree traversal comparing the Nodes at each level
     * and placing the results of the comparison in the resultDir.
     * @param dir1
     * @param dir2
     */
    private void compare(@NonNull DirNode resultDir, @NonNull final DirNode dir1, @NonNull final DirNode dir2) {

        for (Node node1 : dir1.toChildrenArray()) {
            Node node2 = dir2.findChild(node1.getName());
            if (node2 == null) {
                // unique to list1
                if (node1.getNodeType() == NodeType.FILE) {
                    FileNode fileNode = new UniqueActionableFileNode(mPrecedence, node1.getName(), resultDir, UniqueTo.A);
                    resultDir.add(fileNode);
                } else if (node1.getNodeType() == NodeType.DIR) {
                    DirNode dirNode = new UniqueActionableDirNode(mPrecedence, node1.getName(), resultDir, UniqueTo.A);
                    resultDir.add(dirNode);
                    // This compare call will recurse/traverse down the tree, adding in UniqueTo.A nodes only.
                    compare(dirNode, (DirNode)node1, mEmptyDir);
                }
            }
        }

        for (Node node2 : dir2.toChildrenArray()) {
            Node node1 = dir1.findChild(node2.getName());
            if (node1 == null) {
                // unique to list2
                if (node2.getNodeType() == NodeType.FILE) {
                    FileNode fileNode = new UniqueActionableFileNode(mPrecedence, node2.getName(), resultDir, UniqueTo.B);
                    resultDir.add(fileNode);
                } else if (node2.getNodeType() == NodeType.DIR) {
                    DirNode dirNode = new UniqueActionableDirNode(mPrecedence, node2.getName(), resultDir, UniqueTo.B);
                    resultDir.add(dirNode);
                    // This compare call will recurse/traverse down the tree, adding in UniqueTo.B nodes only.
                    compare(dirNode, mEmptyDir, (DirNode)node2);
                }
            } else {
                // name is in both list1 and list2
                String name = node2.getName();
                if (node1.getNodeType() != node2.getNodeType()) {
                    // type clash
                    if (node1.getNodeType() == NodeType.DIR) {
                        // node1 is the dir, node2 is the non-dir   TODO - what if node2 is a symlink - can that be ignored ?
                        DirNode dirNode = new TypeClashActionableDirNode(mPrecedence, name, resultDir, DirectoryOn.A);
                        resultDir.add(dirNode);
                        // This compare call will recurse/traverse down the tree, adding in UniqueTo.A nodes only.
                        compare(dirNode, (DirNode)node1, mEmptyDir);
                    } else if (node2.getNodeType() == NodeType.DIR) {
                        // node2 is DIR, node1 is the non-dir   TODO same as above
                        DirNode dirNode = new TypeClashActionableDirNode(mPrecedence, name, resultDir, DirectoryOn.B);
                        resultDir.add(dirNode);
                        // This compare call will recurse/traverse down the tree, adding in UniqueTo.B nodes only.
                        compare(dirNode, mEmptyDir, (DirNode)node2);
                    }
                } else if (node1.getNodeType() == NodeType.DIR) {
                    // both nodes are dir
                    DirNode dirNode = new ActionableDirNode(name, resultDir);
                    resultDir.add(dirNode);
                    // This compare call will recurse/traverse down the tree, adding in all types.
                    compare(dirNode, (DirNode)node1, (DirNode)node2);
                } else if (node1.getNodeType() == NodeType.FILE) {
                    // both nodes are file
                    FileNode fileNode = new CommonActionableFileNode(mPrecedence, name, resultDir,
                                                                     node1.getTimeStamp(), node2.getTimeStamp());
                    resultDir.add(fileNode);
                }
            }
        }

    }

}
