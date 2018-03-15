package com.wolfbang.fsync.ftpservice.model;

import java.util.List;
import java.util.TreeSet;

/**
 * @author david
 * @date 15 Mar 2018.
 */

public class Comparator {

    /**
     * Assumes the child lists are sorted.
     * @param dir1
     * @param dir2
     */
    public void compare(DirNode dir1, DirNode dir2) {
        TreeSet<FileNode> list1 = dir1.getChildren();
        TreeSet<FileNode> list2 = dir2.getChildren();
        // Each item in these lists is either
        // 1. in only list1
        // 2. in only list2
        // 3. in both lists and both have type FILE
        // 4. in both lists and both have type DIR
        // 5. in both lists and have different types


    }
}
