package com.wolfbang.fsync.model.compare;

import com.wolfbang.fsync.model.filetree.DirNode;
import com.wolfbang.fsync.model.filetree.Node;
import com.wolfbang.fsync.model.mission.CannedDataEndPoint;

import org.junit.Test;

import static junit.framework.Assert.fail;

/**
 * @author david
 * @date 30 Mar 2018.
 */

public class MergeComparatorTest {

    String[] paths1 = new String[] {
            "1-AAA/2-aaa/2018-03-15 00:11:00.000",
            "1-AAA/2-bbb/2018-03-15 00:00:00.000",
            "1-AAA/2-ddd/2018-03-15 00:00:00.000",
            "1-AAA/2-eee/2018-03-15 00:00:00.000",
            "1-AAA/2-fff/2018-03-15 00:00:00.000",
            // Directory only here with one file and one dir
            "1-BBB/2-aaa/2018-03-15 00:00:00.000",
            "1-BBB/2-AAA/3-aaa/2018-03-15 00:00:00.000",
            "1-BBB/2-AAA/3-bbb/2018-03-15 00:00:00.000",
            // Directory present in both
            "1-CCC/2-AAA/3-aaa/2018-03-15 00:00:00.000",
            // File in this tree
            "1-CCC/2-AAA/3-ccc/2018-03-15 00:00:00.000",
    };
    String[] paths2 = new String[] {
            "1-AAA/2-aaa/2018-03-15 00:00:00.000",
            "1-AAA/2-bbb/2018-03-15 00:22:00.000",
            "1-AAA/2-ccc/2018-03-15 00:00:00.000",
            "1-AAA/2-ddd/2018-03-15 00:00:00.000",
            "1-AAA/2-fff/2018-03-15 00:00:00.000",
            "1-AAA/2-ggg/2018-03-15 00:00:00.000",
            // Directory only here with one file and one dir
            "1-CCC/2-aaa/2018-03-15 00:00:00.000",
            "1-CCC/2-AAA/3-aaa/2018-03-15 00:00:00.000",
            "1-CCC/2-AAA/3-bbb/2018-03-15 00:00:00.000",
            // Directory present in both
            "1-CCC/2-AAA/3-bbb/2018-03-15 00:00:00.000",
            // Directory in this tree
            "1-CCC/2-AAA/3-ccc/xxx/2018-03-15 00:00:00.000",
    };

    @Test
    public void testComparatorWithPrecedenceA() {

        DirNode dir1 = inflateTree("root1", paths1);
        DirNode dir2 = inflateTree("root2", paths2);

        MergeComparator mergeComparator = new MergeComparator(Precedence.A);
        DirNode resultDir = mergeComparator.compare(dir1, dir2);

        dir1.dump("dir1");
        dir2.dump("dir2");
        resultDir.dump("result");
    }

    @Test
    public void testComparatorWithPrecedenceB() {

        DirNode dir1 = inflateTree("root1", paths1);
        DirNode dir2 = inflateTree("root2", paths2);

        MergeComparator mergeComparator = new MergeComparator(Precedence.B);
        DirNode resultDir = mergeComparator.compare(dir1, dir2);

        dir1.dump("dir1");
        dir2.dump("dir2");
        resultDir.dump("result");
    }

    @Test
    public void testComparatorWithPrecedenceNewest() {

        DirNode dir1 = inflateTree("root1", paths1);
        DirNode dir2 = inflateTree("root2", paths2);

        MergeComparator mergeComparator = new MergeComparator(Precedence.NEWEST);
        DirNode resultDir = mergeComparator.compare(dir1, dir2);

        dir1.dump("dir1");
        dir2.dump("dir2");
        resultDir.dump("result");
    }

    @Test
    public void testCannedDataEndPoint() {
        CannedDataEndPoint cannedDataEndPoint1 = new CannedDataEndPoint("Canned-Local-A", "root", paths1);
        DirNode dirNode1 = cannedDataEndPoint1.fetchFileTree().response;
        dirNode1.dump("Canned-Local-A");

        CannedDataEndPoint cannedDataEndPoint2 = new CannedDataEndPoint("Canned-Local-B", "root", paths2);
        DirNode dirNode2 = cannedDataEndPoint2.fetchFileTree().response;
        dirNode2.dump("Canned-Local-B");
    }

    private DirNode inflateTree(String rootName, String[] paths) {
        DirNode root = new DirNode(rootName, null, Node.parseDateForInflation("2018-01-01 00:00:00.000"));
        for (String path : paths) {
            if (null == Node.inflateFile(root, path)) {
                fail(path + " cannot be inflated");
            }
        }
        return root;
    }

}
