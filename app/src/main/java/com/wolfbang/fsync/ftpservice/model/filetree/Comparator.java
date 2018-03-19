package com.wolfbang.fsync.ftpservice.model.filetree;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author david
 * @date 15 Mar 2018.
 */

public class Comparator {

    /**
     * Comparing two Nodes (trees); each file-path will be unique to it's tree or be
     * present in both trees as either; both file-types, or a file-type and a dir-type.
     *
     */
    protected DirNode uniqueTolist1 = new DirNode("uniqueTolist1", null, null);
    protected DirNode uniqueTolist2 = new DirNode("uniqueTolist2", null, null);
    protected List<Pair<Node, Node>> presentAndFile     = new ArrayList<>();
    // todo this list prob not needed
    @Deprecated
    protected List<Node> presentAndDir                  = new ArrayList<>();

    protected List<Pair<Node, Node>> nodeTypeMismatch   = new ArrayList<>();

    private DirNode mEmptyDir = new DirNode(null, null, null);

    /**
     * Performs a depth-first tree traversal comparing the Nodes at each level
     * and placing the results of the comparison in the list members.
     * @param dir1
     * @param dir2
     */
    public void compare(@NonNull DirNode dir1, @NonNull DirNode dir2) {

        // Each item in these lists is either
        // 1. in only list1
        // 2. in only list2
        // 3. in both lists and both have type FILE
        // 4. in both lists and both have type DIR
        // 5. in both lists and have different types

        Log.d("about to compare ", dir1.toStringWithPath());
        Log.d("about to compare ", dir2.toStringWithPath());

        for (Node node1 : dir1.toChildrenArray()) {
            if (dir2.findChild(node1.getName()) == null) {
                if (node1.getNodeType() == Node.NodeType.FILE) {
                    // ==> 1. in only list1
                    uniqueTolist1.adopt((FileNode)node1);
                    Log.d("add to uniqueTolist1", node1.toStringWithPath());
                } else {
                    compare((DirNode)node1, mEmptyDir);
                }
            }
        }

        for (Node node2 : dir2.toChildrenArray()) {
            Node node1 = dir1.findChild(node2.getName());
            if (node1 == null) {
                if (node2.getNodeType() == Node.NodeType.FILE) {
                    // ==> 2. in only list2
                    uniqueTolist2.adopt((FileNode)node2);
                    Log.d("add to uniqueTolist2", node2.toStringWithPath());
                } else {
                    compare(mEmptyDir, (DirNode)node2);
                }
            } else {
                if (node1.getNodeType() != node2.getNodeType()) {
                    // ==> 5. in both lists and have different types
                    // This conflict blocks further descending to discover more paths.
                    nodeTypeMismatch.add(new Pair(node1, node2));
                } else if (node1.getNodeType() == Node.NodeType.DIR) {
                    // ==> 4. in both lists and both have type DIR
                    // Continue to recurse and discover more paths.
                    compare((DirNode)node1, (DirNode)node2);
                    presentAndDir.add(node1);
                } else if (node1.getNodeType() == Node.NodeType.FILE) {
                    // ==> 3. in both lists and both have type FILE
                    // Probably compare further on basis of timestamp
                    presentAndFile.add(new Pair(node1, node2));
                }
            }
        }

    }
}