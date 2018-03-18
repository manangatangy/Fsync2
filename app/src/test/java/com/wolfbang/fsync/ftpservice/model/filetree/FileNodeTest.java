package com.wolfbang.fsync.ftpservice.model.filetree;

import android.support.v4.util.Pair;
import android.util.Log;

import com.wolfbang.fsync.ftpservice.model.filetree.Comparator;
import com.wolfbang.fsync.ftpservice.model.filetree.DirNode;
import com.wolfbang.fsync.ftpservice.model.filetree.FileNode;
import com.wolfbang.fsync.ftpservice.model.filetree.Node;

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
        assertEquals("'root/1-eee', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-eee/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-ddd', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-ddd/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-ccc/2018-03-15 00:00:00.000 AEDT").toStringWithPath());

        assertEquals("'root/1-AAA/2-bbb', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-AAA/2-bbb/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-AAA/2-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-AAA/2-ccc/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-BBB/2-aaa', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-BBB/2-aaa/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertEquals("'root/1-BBB/2-BBB/3-aaa', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-BBB/2-BBB/3-aaa/2018-03-15 00:00:00.000 AEDT").toStringWithPath());

        root.dump("rootDump");

    }

    @Test
    public void testDirNodeInflate() {
        DirNode root = new DirNode("root", null, Node.parseDate("2018-01-01 00:00:00.000 AEDT"));
        assertEquals("'root/1-AAA', DIR, [0 children]",
                Node.inflateDir(root, "1-AAA").toStringWithPath());

        assertEquals("'root/1-AAA/2-AAA', DIR, [0 children]",
                Node.inflateDir(root, "1-AAA/2-AAA").toStringWithPath());

        assertEquals("'root/1-AAA/2-AAA/3-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                Node.inflateFile(root, "1-AAA/2-AAA/3-ccc/2018-03-15 00:00:00.000 AEDT").toStringWithPath());
        assertNull(Node.inflateDir(root, "1-AAA/2-AAA/3-ccc"));

    }

    @Test
    public void testAdoption() {
        DirNode rootOld = new DirNode("rootOld", null, null);
        FileNode kid = Node.inflateFile(rootOld, "1-AAA/2-AAA/3-ccc/2018-03-15 00:00:00.000 AEDT");
        assertEquals("'rootOld/1-AAA/2-AAA/3-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                kid.toStringWithPath());

        // Adopt into brand new tree
        DirNode rootNew = new DirNode("rootNew", null, null);
        kid = rootNew.adopt(kid);
        assertEquals("'rootNew/1-AAA/2-AAA/3-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                kid.toStringWithPath());
        // Test that 3-ccc has been removed from old root by creating dir of that name.
        assertNotNull(Node.inflateDir(rootOld, "1-AAA/2-AAA/3-ccc"));

        // Adopt into partially new tree
        DirNode rootPartial = new DirNode("rootPartial", null, null);
        assertEquals("'rootPartial/1-AAA/2-AAA', DIR, [0 children]",
                Node.inflateDir(rootPartial, "1-AAA/2-AAA").toStringWithPath());
        kid = rootPartial.adopt(kid);
        assertEquals("'rootPartial/1-AAA/2-AAA/3-ccc', FILE', '2018-03-15 00:00:00.000 AEDT'",
                kid.toStringWithPath());
        // Test that 3-ccc has been removed from previous root by creating dir of that name.
        assertNotNull(Node.inflateDir(rootNew, "1-AAA/2-AAA/3-ccc"));

        // Adopt a complete subtree from partial root back to old root
        FileNode dir = Node.inflateDir(rootPartial, "1-AAA");
        assertEquals("'rootPartial/1-AAA', DIR, [1 children]", dir.toStringWithPath());
        dir = rootOld.adopt(dir);
        // It's now in the adoptive parent
        assertEquals("'rootOld/1-AAA', DIR, [1 children]", dir.toStringWithPath());
        assertNull(Node.inflateFile(rootOld, "1-AAA/2-AAA/3-ccc/2018-03-15 00:11:11.111 AEDT"));
        // and no longer the previous parent (by creating file of that name)
        assertNotNull(Node.inflateFile(rootPartial, "1-AAA/2018-03-15 00:00:00.000 AEDT"));

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

        comparator.uniqueTolist1.dump("uniqueTolist1");
        comparator.uniqueTolist2.dump("uniqueTolist2");
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

    public void dumpList(String tag, DirNode nodeList) {
        Log.d(tag, "has " + nodeList.size() + " elements");
        for (Node node : nodeList.getChildren()) {
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
