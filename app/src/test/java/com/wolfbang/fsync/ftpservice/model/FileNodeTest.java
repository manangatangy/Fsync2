package com.wolfbang.fsync.ftpservice.model;

import android.support.v4.util.Pair;
import android.util.Log;

import org.junit.Test;

import java.util.List;

import static junit.framework.Assert.*;

/**
 * @author david
 * @date 15 Mar 2018.
 */

public class FileNodeTest {

    @Test
    public void testFileNodeInflate() {
        // Syntax for names; N-XXX [N=directory level, root is 0] [XXX=dir-name, xxx=file-name]
        DirNode root = new DirNode("root", null, Node.parseDate("2018-01-01 00:00:00.000 AEDT"));
        assertEquals("'root/1-AAA/2-aaa', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-AAA/2-aaa/2018-03-15 00:00:00.000 AEDT").toStringWithPath());

        // Inflating same path will create nothing,
        assertEquals("'root/1-AAA/2-aaa', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-AAA/2-aaa/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        // Mismatch on timestamp
        assertNull(Node.inflateFile(root, "1-AAA/2-aaa/2018-03-15 00:00:00.001 AEDT"));
        // Mismatch on node type - new=child
        assertNull(Node.inflateFile(root, "1-AAA/2018-03-15 00:00:00.000 AEDT"));
        // Mismatch on node type - new=dir
        assertNull(Node.inflateFile(root, "1-AAA/2-aaa/3-ccc/2018-03-15 00:00:00.000 AEDT"));

        // Now create diverging tree
        assertEquals("'root/1-AAA/2-bbb', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-AAA/2-bbb/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-AAA/2-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-AAA/2-ccc/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-BBB/2-aaa', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-BBB/2-aaa/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-BBB/2-BBB/3-aaa', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-BBB/2-BBB/3-aaa/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-eee', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-eee/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-ddd', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-ddd/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-ccc/2018-03-15 00:00:00.000 AEDT").toStringWithPath());

        root.dump("rootDump");

    }

    @Test
    public void testDirNodeInflate() {
        DirNode root = new DirNode("root", null, Node.parseDate("2018-01-01 00:00:00.000 AEDT"));
        assertEquals("'root/1-AAA', DIR', '2018-03-15 00:00:00.000 AEDT', [0 children]",
                Node.inflateDir(root, "1-AAA/2018-03-15 00:00:00.000 AEDT").toStringWithPath());

        assertEquals("'root/1-AAA/2-AAA', DIR', '2018-03-15 00:00:00.000 AEDT', [0 children]",
                Node.inflateDir(root, "1-AAA/2-AAA/2018-03-15 00:00:00.000 AEDT").toStringWithPath());

        assertEquals("'root/1-AAA/2-AAA/3-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-AAA/2-AAA/3-ccc/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertNull(Node.inflateDir(root, "1-AAA/2-AAA/3-ccc/2018-03-15 00:00:00.000 AEDT"));

    }

    @Test
    public void testComparator() {

        String[] paths1 = new String[] {
                "1-AAA/2-aaa/2018-03-15 00:11:00.000 AEDT",
                "1-AAA/2-bbb/2018-03-15 00:00:00.000 AEDT",
                "1-AAA/2-ddd/2018-03-15 00:00:00.000 AEDT",
                "1-AAA/2-eee/2018-03-15 00:00:00.000 AEDT",
                "1-AAA/2-fff/2018-03-15 00:00:00.000 AEDT",
                // Directory only here with one file and one dir
                "1-BBB/2-aaa/2018-03-15 00:00:00.000 AEDT",
                "1-BBB/2-AAA/3-aaa/2018-03-15 00:00:00.000 AEDT",
                "1-BBB/2-AAA/3-bbb/2018-03-15 00:00:00.000 AEDT",
                // Directory present in both
                "1-CCC/2-AAA/3-aaa/2018-03-15 00:00:00.000 AEDT",
                // File in this tree
                "1-CCC/2-AAA/3-ccc/2018-03-15 00:00:00.000 AEDT",
        };
        String[] paths2 = new String[] {
                "1-AAA/2-aaa/2018-03-15 00:00:00.000 AEDT",
                "1-AAA/2-bbb/2018-03-15 00:22:00.000 AEDT",
                "1-AAA/2-ccc/2018-03-15 00:00:00.000 AEDT",
                "1-AAA/2-ddd/2018-03-15 00:00:00.000 AEDT",
                "1-AAA/2-fff/2018-03-15 00:00:00.000 AEDT",
                "1-AAA/2-ggg/2018-03-15 00:00:00.000 AEDT",
                // Directory only here with one file and one dir
                "1-CCC/2-aaa/2018-03-15 00:00:00.000 AEDT",
                "1-CCC/2-AAA/3-aaa/2018-03-15 00:00:00.000 AEDT",
                "1-CCC/2-AAA/3-bbb/2018-03-15 00:00:00.000 AEDT",
                // Directory present in both
                "1-CCC/2-AAA/3-bbb/2018-03-15 00:00:00.000 AEDT",
                // Directory in this tree
                "1-CCC/2-AAA/3-ccc/xxx/2018-03-15 00:00:00.000 AEDT",
        };
        DirNode dir1 = inflateTree("root1", paths1);
        DirNode dir2 = inflateTree("root2", paths2);

        Comparator comparator = new Comparator();
        comparator.compare(dir1, dir2);

        dumpList("uniqueTolist1", comparator.uniqueTolist1);
        dumpList("uniqueTolist2", comparator.uniqueTolist2);
        dumpPairList("presentAndFile", comparator.presentAndFile);
        dumpPairList("nodeTypeMismatch", comparator.nodeTypeMismatch);
    }

    private DirNode inflateTree(String rootName, String[] paths) {
        DirNode root = new DirNode(rootName, null, Node.parseDate("2018-01-01 00:00:00.000 AEDT"));
        for (String path : paths) {
            if (null == Node.inflateFile(root, path)) {
                fail(path + " cannot be inflated");
            }
        }
        return root;
    }

    public void dumpList(String tag, NodeList nodeList) {
        Log.d(tag, "has " + nodeList.size() + " elements");
        for (Node node : nodeList) {
            Log.d(tag, node.toStringWithPath());
        }
    }

    // Pair<Node, Node>
    public void dumpPairList(String tag, List<Pair<Node, Node>> pairList) {
        Log.d(tag, "has " + pairList.size() + " elements");
        for (Pair<Node, Node> pair : pairList) {
            Log.d(tag + "-1", pair.first.toStringWithPath());
            Log.d(tag + "-2", pair.second.toStringWithPath());
        }
    }

}
