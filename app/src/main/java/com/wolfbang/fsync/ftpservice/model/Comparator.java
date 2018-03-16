package com.wolfbang.fsync.ftpservice.model;

import com.wolfbang.fsync.ftpservice.model.Node.NodeType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author david
 * @date 15 Mar 2018.
 */

public class Comparator {

    List<Node> uniqueTolist1    = new ArrayList<>();
    List<Node> uniqueTolist2    = new ArrayList<>();
    List<Node> presentAndFile   = new ArrayList<>();
    List<Node> presentAndDir    = new ArrayList<>();
    List<Node> nodeTypeMismatch = new ArrayList<>();

    /**
     * Performs a depth-first tree traversal comparing the Nodes at each level
     * and placing the results of the comparison in the list members.
     * Assumes the child lists are sorted.
     * @param dir1
     * @param dir2
     */
    public void compare(DirNode dir1, DirNode dir2) {

        int size1 = dir1.getChildren().size();
        int size2 = dir2.getChildren().size();

        Node[] list1 = dir1.getChildren().toArray(new Node[size1]);
        Node[] list2 = dir2.getChildren().toArray(new Node[size2]);

        // Each item in these lists is either
        // 1. in only list1
        // 2. in only list2
        // 3. in both lists and both have type FILE
        // 4. in both lists and both have type DIR
        // 5. in both lists and have different types

        int index1 = 0;
        int index2 = 0;
        while (index1 < size1 && index2 < size2) {
            Node node1 = list1[index1++];
            Node node2 = list2[index2++];
            String name1 = node1.getName();
            String name2 = node2.getName();
            if (node1.equals(node2)) {
                if (node1.getNodeType() != node2.getNodeType()) {
                    // ==> list 5
                } else if (node1.getNodeType() == NodeType.DIR) {
                    // ==> list 4
                } else if (node1.getNodeType() == NodeType.DIR) {
                    // ==> list 3
                }
            }

        }

    }
}
